package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.file.EmlFileService;
import com.oreumio.james.rest.file.EmlFileVo;
import com.oreumio.james.rest.util.FileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
@RequestMapping("file")
public class FileController {

    private String uploadTempDir;

    @Autowired
    private EmlFileService emlFileService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public List<EmlFileVo> uploadFile(MultipartHttpServletRequest request) throws IOException {

        List<EmlFileVo> emlFileVoList = new ArrayList<EmlFileVo>();

        Map<String, MultipartFile> fileMap = request.getFileMap();

        for (String name : fileMap.keySet()) {
            MultipartFile multipartFile = fileMap.get(name);
            System.out.println(name);
            System.out.println("name=" + multipartFile.getName());
            System.out.println("filename=" + multipartFile.getOriginalFilename());
            System.out.println("fileSize=" + multipartFile.getSize());
            System.out.println("content type=" + multipartFile.getContentType());
            if (true) {
                continue;
            }
            String fileExt = FileUtil.getFileExtn(multipartFile.getOriginalFilename());
            String saveFileName = FileUtil.makeFileName();
            if (!org.springframework.util.StringUtils.isEmpty(fileExt)) {
                saveFileName = saveFileName + "." + fileExt;
            }
            String tempFilePath = FileUtil.createTempSaveDir(uploadTempDir, "email");

            EmlFileVo emlFileVo = new EmlFileVo();
            emlFileVo.setModule("email");
            emlFileVo.setFileName(multipartFile.getOriginalFilename());
            emlFileVo.setSaveFileName(saveFileName);
            emlFileVo.setFileSize(multipartFile.getSize());
            emlFileVo.setFilePath(tempFilePath.replace(uploadTempDir, ""));
            emlFileVo.setFileType(multipartFile.getContentType());

            File saveFile = new File(tempFilePath, saveFileName);
            multipartFile.transferTo(saveFile);

            emlFileVoList.add(emlFileVo);
            emlFileService.add(emlFileVo);
        }

        return emlFileVoList;
    }

    /**
     * 파일 다운로드
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downFile", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downFile(HttpServletRequest request) throws IOException {
        String fileId = StringUtils.defaultString(request.getParameter("fileId"));
        String filePath = StringUtils.defaultString(request.getParameter("filePath"));
        String fileName = StringUtils.defaultString(request.getParameter("fileName"));
        if(StringUtils.isNotEmpty(filePath)){
            String decodeFileName = URLDecoder.decode(filePath, "utf-8");
            if (StringUtils.isEmpty(fileName) || decodeFileName.equalsIgnoreCase("..")
                    || decodeFileName.equalsIgnoreCase("/")) {
                return null;
            }
            fileName = FileUtil.getValidFileName(fileName);
            File file = new File(uploadTempDir + filePath);
            return getFileResponseEntity(fileName, file);
        }else{
            return getFileResponseEntity(fileId);
        }
    }

    /**
     * 이미지 조회
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/viewImage", method = RequestMethod.GET)
    public HttpEntity<byte[]> downloadImage(HttpServletRequest request) throws IOException {
        String fileId = request.getParameter("fileId");
        String filePath = request.getParameter("filePath");
        if (StringUtils.isEmpty(filePath)) {
            return getFileHttpEntity(fileId);
        } else {
            return getTempFileHttpEntity(filePath);
        }
    }

    private HttpEntity<byte[]> getTempFileHttpEntity(String filePath) throws IOException {
        String fullPath = uploadTempDir + filePath.replace("../", ""); //보안을 위해서 ../ 를 공백 처리한다.

        File file = new File(fullPath);

        String fileType = new MimetypesFileTypeMap().getContentType(file);
        String[] fileTypeArr = fileType.split("/");

        InputStream is = new FileInputStream(fullPath);
        byte[] fileBytes = IOUtils.toByteArray(is);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(fileTypeArr[0], fileTypeArr[1]));
        headers.setContentLength(file.length());

        return new HttpEntity<byte[]>(fileBytes, headers);
    }

    private HttpEntity<byte[]> getFileHttpEntity(String fileId) throws IOException {
        EmlFileVo emlFileVo =  emlFileService.get(fileId);

        String fullPath = uploadTempDir + emlFileVo.getFilePath() + emlFileVo.getSaveFileName();
        InputStream is = new FileInputStream(fullPath);

        byte[] fileBytes = IOUtils.toByteArray(is);

        String[] fileTypeArr = emlFileVo.getFileType().split("/");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(fileTypeArr[0], fileTypeArr[1]));
        headers.setContentLength(emlFileVo.getFileSize());

        return new HttpEntity<byte[]>(fileBytes, headers);
    }

    private ResponseEntity<InputStreamResource> getFileResponseEntity(String fileId) throws IOException {
        EmlFileVo emlFileVo =  emlFileService.get(fileId);

        String fullPath = uploadTempDir + emlFileVo.getFilePath() + emlFileVo.getSaveFileName();
        File file = new File(fullPath);

        String[] fileTypeArr = emlFileVo.getFileType().split("/");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(new MediaType(fileTypeArr[0], fileTypeArr[1]));
        respHeaders.setContentLength(emlFileVo.getFileSize());
        //respHeaders.setContentDispositionFormData("attachment", FileUtil.getEncodFileName(cmmFile.getFileName()));
        respHeaders.set("Content-Disposition", "attachment; filename=" + FileUtil.getEncodFileName(emlFileVo.getFileName()));
        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
    }

    public ResponseEntity<InputStreamResource> getFileResponseEntity(String fileName, File file) throws IOException {
        long fileSize = file.length();

        String fileType = new MimetypesFileTypeMap().getContentType(file);
        String[] fileTypeArr = fileType.split("/");

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(new MediaType(fileTypeArr[0], fileTypeArr[1]));
        respHeaders.setContentLength(fileSize);
        //respHeaders.setContentDispositionFormData("attachment", FileUtil.getEncodFileName(fileName));
        respHeaders.set("Content-Disposition", "attachment; filename=" + FileUtil.getEncodFileName(fileName));
        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
        return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
    }
}

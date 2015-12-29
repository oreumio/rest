package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.form.EmlMailFormService;
import com.oreumio.james.rest.form.EmlMailFormVo;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
public class MailFormController {

    private static Logger logger = LoggerFactory.getLogger(MailFormController.class);

    @Autowired
    private EmlMailFormService emlMailFormService;

    @RequestMapping(value = "forms", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlMailFormVo> list(HttpServletRequest request) {
        logger.debug("메일 폼을 검색합니다.");

        SessionVo sessionVo = SessionUtil.getSession(request);

        return emlMailFormService.list(sessionVo.getUserId());
    }

    @RequestMapping(value = "forms", method = RequestMethod.POST)
    @ResponseBody
    public EmlMailFormVo post(HttpServletRequest request, @RequestBody EmlMailFormVo mailFormVo) {
        logger.debug("메일 폼을 추가합니다." + mailFormVo);

        SessionVo sessionVo = SessionUtil.getSession(request);

        mailFormVo.setUserId(sessionVo.getUserId());

        try {
            return emlMailFormService.add(mailFormVo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "forms/{mailFormId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlMailFormVo get(HttpServletRequest request, @PathVariable String mailFormId) {
        logger.debug("메일 폼을 조회합니다.: mailFormId=" + mailFormId);

        SessionVo sessionVo = SessionUtil.getSession(request);

        return emlMailFormService.get(sessionVo.getUserId(), mailFormId);
    }

    @RequestMapping(value = "forms/{mailFormId}", method = RequestMethod.PUT)
    @ResponseBody
    public void put(HttpServletRequest request, @PathVariable String mailFormId, @RequestBody EmlMailFormVo mailFormVo) {
        logger.debug("메일 폼을 변경합니다.: mailFormId=" + mailFormId + ", mailFormVo" + mailFormVo);
        SessionVo sessionVo = SessionUtil.getSession(request);

        emlMailFormService.update(sessionVo.getUserId(), mailFormId, mailFormVo);
    }

    @RequestMapping(value = "forms/{mailFormId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(HttpServletRequest request, @PathVariable String mailFormId) {
        logger.debug("메일 폼을 삭제합니다.: mailFormId=" + mailFormId);

        SessionVo sessionVo = SessionUtil.getSession(request);

        emlMailFormService.remove(sessionVo.getUserId(), mailFormId);
    }
}

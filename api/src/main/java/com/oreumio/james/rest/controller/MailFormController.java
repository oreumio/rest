package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.form.EmlMailFormService;
import com.oreumio.james.rest.form.EmlMailFormVo;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import com.oreumio.james.rest.user.EmlUserService;
import com.oreumio.james.rest.user.EmlUserVo;
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

    @Autowired
    private EmlUserService userService;

    @RequestMapping(value = "forms", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlMailFormVo> list(HttpServletRequest request) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일 폼을 검색합니다.");

        return emlMailFormService.list(userVo.getId());
    }

    @RequestMapping(value = "forms", method = RequestMethod.POST)
    @ResponseBody
    public EmlMailFormVo post(HttpServletRequest request, @RequestBody EmlMailFormVo mailFormVo) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일 폼을 추가합니다." + mailFormVo);

        try {
            return emlMailFormService.add(userVo.getId(), mailFormVo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "forms/{mailFormId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlMailFormVo get(HttpServletRequest request, @PathVariable String mailFormId) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일 폼을 조회합니다.: mailFormId=" + mailFormId);

        return emlMailFormService.get(userVo.getId(), mailFormId);
    }

    @RequestMapping(value = "forms/{mailFormId}", method = RequestMethod.PUT)
    @ResponseBody
    public void put(HttpServletRequest request, @PathVariable String mailFormId, @RequestBody EmlMailFormVo mailFormVo) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일 폼을 변경합니다.: mailFormId=" + mailFormId + ", mailFormVo" + mailFormVo);

        emlMailFormService.update(userVo.getId(), mailFormId, mailFormVo);
    }

    @RequestMapping(value = "forms/{mailFormId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(HttpServletRequest request, @PathVariable String mailFormId) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일 폼을 삭제합니다.: mailFormId=" + mailFormId);

        emlMailFormService.remove(userVo.getId(), mailFormId);
    }
}

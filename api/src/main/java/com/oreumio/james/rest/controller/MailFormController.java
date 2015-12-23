package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.form.EmlMailFormService;
import com.oreumio.james.rest.form.EmlMailFormVo;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
@RequestMapping("form")
public class MailFormController {

    private static Logger logger = LoggerFactory.getLogger(MailFormController.class);

    @Autowired
    private EmlMailFormService emlMailFormService;

    @RequestMapping("list")
    @ResponseBody
    public List<EmlMailFormVo> list(HttpServletRequest request) {
        SessionVo sessionVo = SessionUtil.getSession(request);

        return emlMailFormService.list(sessionVo.getUserId());
    }

    @RequestMapping("add")
    @ResponseBody
    public EmlMailFormVo add(HttpServletRequest request, @RequestBody EmlMailFormVo emlMailFormVo) {
        logger.debug("" + emlMailFormVo);

        SessionVo sessionVo = SessionUtil.getSession(request);

        emlMailFormVo.setUserId(sessionVo.getUserId());

        try {
            return emlMailFormService.add(emlMailFormVo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("remove")
    @ResponseBody
    public void remove(HttpServletRequest request, String emlMailFormId) {
        SessionVo sessionVo = SessionUtil.getSession(request);

        emlMailFormService.remove(sessionVo.getUserId(), emlMailFormId);
    }
}

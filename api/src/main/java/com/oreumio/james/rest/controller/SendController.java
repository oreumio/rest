package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.form.EmlMailFormVo;
import com.oreumio.james.rest.send.SendService;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import com.oreumio.james.rest.user.EmlUserService;
import com.oreumio.james.rest.user.EmlUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
public class SendController {

    @Autowired
    SendService sendService;

    @Autowired
    private EmlUserService userService;

    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public String insertSendMail(HttpServletRequest request, @RequestBody EmlMailFormVo emlMailFormVo)
            throws Exception {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        emlMailFormVo.setUserId(userVo.getId());
/*
        emlMailFormVo.setEmail(session.getEmail());
        emlMailFormVo.setDeptId(session.getDeptId());
        emlMailFormVo.setCmpId(session.getClientId());
        emlMailFormVo.setLangCd(session.getLangCode());
        emlMailFormVo.setGwUrl(gwUrl);
*/
        emlMailFormVo.setRemoteAddr(request.getRemoteHost());
        try {
            return sendService.sendMail(emlMailFormVo);
        } catch (Exception e) {
            e.printStackTrace();
            return "BAD";
        }
    }
}

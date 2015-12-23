package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.message.EmlMailSearchVo;
import com.oreumio.james.rest.message.EmlMailService;
import com.oreumio.james.rest.message.EmlMailVo;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
@RequestMapping("mail")
public class MailController {

    private static Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    EmlMailService emlMailService;

    @RequestMapping("list")
    @ResponseBody
    public List<EmlMailVo> list(HttpServletRequest request, String mailboxName) {
        SessionVo sessionVo = SessionUtil.getSession(request);

        logger.debug("메일 목록을 구합니다.: mailboxName=" + mailboxName);

        try {
            return emlMailService.list(sessionVo.getUserId(), mailboxName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("search")
    @ResponseBody
    public List<EmlMailVo> search(HttpServletRequest request, EmlMailSearchVo emlMailSearchVo) {
        SessionVo sessionVo = SessionUtil.getSession(request);

        return emlMailService.search(sessionVo.getUserId(), emlMailSearchVo);
    }
}

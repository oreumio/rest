package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.message.EmlMailSearchVo;
import com.oreumio.james.rest.message.EmlMailService;
import com.oreumio.james.rest.message.EmlMailVo;
import com.oreumio.james.rest.user.EmlUserService;
import com.oreumio.james.rest.user.EmlUserVo;
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

    @Autowired
    private EmlUserService userService;

    @RequestMapping("list")
    @ResponseBody
    public List<EmlMailVo> list(HttpServletRequest request, String mailboxName) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일 목록을 구합니다.: mailboxName=" + mailboxName);

        try {
            return emlMailService.list(userVo.getId(), mailboxName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("search")
    @ResponseBody
    public List<EmlMailVo> search(HttpServletRequest request, EmlMailSearchVo emlMailSearchVo) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        return emlMailService.search(userVo.getId(), emlMailSearchVo);
    }
}

package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlGroupVo;
import com.oreumio.james.rest.mailbox.EmlMailboxService;
import com.oreumio.james.rest.mailbox.EmlMailboxVo;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
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
@RequestMapping("mailbox")
public class MailboxController {

    @Autowired
    private EmlMailboxService emlMailboxService;

    @RequestMapping("list")
    @ResponseBody
    public List<EmlMailboxVo> list(HttpServletRequest request, String mailboxName) {
        SessionVo sessionVo = SessionUtil.getSession(request);

        return emlMailboxService.list(sessionVo.getUserId(), mailboxName);
    }
}

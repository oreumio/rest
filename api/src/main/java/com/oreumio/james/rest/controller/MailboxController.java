package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.mailbox.EmlMailboxService;
import com.oreumio.james.rest.mailbox.EmlMailboxVo;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
public class MailboxController {
    private static Logger logger = LoggerFactory.getLogger(MailboxController.class);

    @Autowired
    private EmlMailboxService emlMailboxService;

    @RequestMapping("mailboxes")
    @ResponseBody
    public List<EmlMailboxVo> list(HttpServletRequest request, String mailboxName) {

        logger.debug("메일박스 목록을 검색합니다.: mailboxName=" + mailboxName);
        SessionVo sessionVo = SessionUtil.getSession(request);

        return emlMailboxService.list(sessionVo.getUserId(), mailboxName);
    }

    @RequestMapping(value = "mailboxes", method = RequestMethod.POST)
    @ResponseBody
    public EmlMailboxVo addMailbox(HttpServletRequest request, String mailboxName) {
        logger.debug("메일박스를 생성합니다.: mailboxName=" + mailboxName);
        SessionVo sessionVo = SessionUtil.getSession(request);
        return emlMailboxService.add(sessionVo.getUserId(), mailboxName);
    }

    @RequestMapping(value = "mailboxes/{mailboxId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlMailboxVo get(HttpServletRequest request, @PathVariable long mailboxId) {
        logger.debug("메일박스를 조회합니다.: mailboxId=" + mailboxId);
        SessionVo sessionVo = SessionUtil.getSession(request);
        return emlMailboxService.get(sessionVo.getUserId(), mailboxId);
    }

    @RequestMapping(value = "mailboxes/{mailboxId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable long mailboxId) {
        logger.debug("메일박스를 삭제합니다.: mailboxId=" + mailboxId);
        SessionVo sessionVo = SessionUtil.getSession(request);
        emlMailboxService.remove(sessionVo.getUserId(), mailboxId);
    }

    @RequestMapping(value = "mailboxes/{mailboxId}/changeName", method = RequestMethod.POST)
    @ResponseBody
    public void changeName(HttpServletRequest request, @PathVariable long mailboxId, String newMailboxName) {
        logger.debug("메일박스를 삭제합니다.: mailboxId=" + mailboxId + ", newMailboxName=" + newMailboxName);
        SessionVo sessionVo = SessionUtil.getSession(request);
        emlMailboxService.changeName(sessionVo.getUserId(), mailboxId, newMailboxName);
    }
}

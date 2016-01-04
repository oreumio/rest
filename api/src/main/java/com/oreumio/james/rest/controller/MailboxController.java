package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.mailbox.EmlMailboxService;
import com.oreumio.james.rest.mailbox.EmlMailboxVo;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import com.oreumio.james.rest.user.EmlUserService;
import com.oreumio.james.rest.user.EmlUserVo;
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

    @Autowired
    private EmlUserService userService;

    @RequestMapping("mailboxes")
    @ResponseBody
    public List<EmlMailboxVo> list(HttpServletRequest request, String mailboxName) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일박스 목록을 검색합니다.: mailboxName=" + mailboxName);

        return emlMailboxService.list(userVo.getId(), mailboxName);
    }

    @RequestMapping(value = "mailboxes", method = RequestMethod.POST)
    @ResponseBody
    public EmlMailboxVo addMailbox(HttpServletRequest request, String mailboxName) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일박스를 생성합니다.: mailboxName=" + mailboxName);

        return emlMailboxService.add(userVo.getId(), mailboxName);
    }

    @RequestMapping(value = "mailboxes/{mailboxId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlMailboxVo get(HttpServletRequest request, @PathVariable long mailboxId) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일박스를 조회합니다.: mailboxId=" + mailboxId);

        return emlMailboxService.get(userVo.getId(), mailboxId);
    }

    @RequestMapping(value = "mailboxes/{mailboxId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable long mailboxId) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일박스를 삭제합니다.: mailboxId=" + mailboxId);

        emlMailboxService.remove(userVo.getId(), mailboxId);
    }

    @RequestMapping(value = "mailboxes/{mailboxId}/changeName", method = RequestMethod.POST)
    @ResponseBody
    public void changeName(HttpServletRequest request, @PathVariable long mailboxId, String newMailboxName) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("메일박스를 삭제합니다.: mailboxId=" + mailboxId + ", newMailboxName=" + newMailboxName);

        emlMailboxService.changeName(userVo.getId(), mailboxId, newMailboxName);
    }
}

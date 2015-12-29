package com.oreumio.james.rest.controller;

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
import java.util.Map;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private EmlUserService userService;

    @RequestMapping(value = "users", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlUserVo> list(HttpServletRequest request, int pageNo, int pageSize) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("사용자 목록을 검색합니다.: apiKey=" + sessionVo.getGroupId() + ", pageNo=" + pageNo + ", pageSize=" + pageSize);
        return userService.list(sessionVo.getGroupId());
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    @ResponseBody
    public EmlUserVo add(HttpServletRequest request, @RequestBody EmlUserVo emlUserVo) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("add emlUserVo=" + emlUserVo);
        return userService.add(sessionVo.getGroupId(), emlUserVo);
    }

    @RequestMapping(value = "users/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlUserVo get(HttpServletRequest request, @PathVariable String userId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("get userId=" + userId);
        return userService.get(sessionVo.getGroupId(), userId);
    }

    @RequestMapping(value = "users/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable String userId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("remove userId=" + userId);
        userService.remove(sessionVo.getGroupId(), userId);
    }

    @RequestMapping(value = "users/{userId}/suspend", method = RequestMethod.POST)
    @ResponseBody
    public void suspend(HttpServletRequest request, @PathVariable String userId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("suspend userId=" + userId);
        userService.changeState(sessionVo.getGroupId(), userId, "N", "R");
    }

    @RequestMapping(value = "users/{userId}/resume", method = RequestMethod.POST)
    @ResponseBody
    public void resume(HttpServletRequest request, @PathVariable String userId, String state) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("resume userId=" + userId);
        userService.changeState(sessionVo.getGroupId(), userId, "R", "N");
    }

    @RequestMapping(value = "users/{userId}/changeQuota", method = RequestMethod.POST)
    @ResponseBody
    public void changeQuota(HttpServletRequest request, @PathVariable String userId, long quota) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("changeQuota userId=" + userId);
        userService.changeQuota(sessionVo.getGroupId(), userId, quota);
    }

    @RequestMapping(value = "users/{userId}/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public void changePassword(HttpServletRequest request, @PathVariable String userId, @RequestParam String newPassword) {
//        String newPassword = form;
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("changePassword userId=" + userId + ", newPassword=" + newPassword);
        userService.changePassword(sessionVo.getGroupId(), userId, newPassword);
    }
}

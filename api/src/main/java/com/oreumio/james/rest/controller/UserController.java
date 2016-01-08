package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlGroupService;
import com.oreumio.james.rest.group.EmlGroupVo;
import com.oreumio.james.rest.user.EmlUserRoleService;
import com.oreumio.james.rest.user.EmlUserRoleVo;
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
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private EmlUserService userService;

    @Autowired
    private EmlUserRoleService userRoleService;

    @Autowired
    private EmlGroupService groupService;

    @RequestMapping(value = "users", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlUserVo> list(HttpServletRequest request, int pageNo, int pageSize) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("사용자 목록을 검색합니다.: pageNo=" + pageNo + ", pageSize=" + pageSize);
        return userService.list(groupVo.getId());
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    @ResponseBody
    public EmlUserVo add(HttpServletRequest request, @RequestBody EmlUserVo userVo) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("add user" + userVo);
        return userService.add(groupVo.getId(), userVo);
    }

    @RequestMapping(value = "users/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlUserVo get(HttpServletRequest request, @PathVariable String userId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("get userId=" + userId);
        return userService.get(groupVo.getId(), userId);
    }

    @RequestMapping(value = "users/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable String userId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("remove userId=" + userId);
        userService.remove(groupVo.getId(), userId);
    }

    @RequestMapping(value = "users/{userId}/suspend", method = RequestMethod.POST)
    @ResponseBody
    public void suspend(HttpServletRequest request, @PathVariable String userId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("suspend userId=" + userId);
        userService.changeState(groupVo.getId(), userId, "N", "R");
    }

    @RequestMapping(value = "users/{userId}/resume", method = RequestMethod.POST)
    @ResponseBody
    public void resume(HttpServletRequest request, @PathVariable String userId, String state) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("resume userId=" + userId);
        userService.changeState(groupVo.getId(), userId, "R", "N");
    }

    @RequestMapping(value = "users/{userId}/changeQuota", method = RequestMethod.POST)
    @ResponseBody
    public void changeQuota(HttpServletRequest request, @PathVariable String userId, long quota) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("changeQuota userId=" + userId);
        userService.changeQuota(groupVo.getId(), userId, quota);
    }

    @RequestMapping(value = "users/{userId}/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public void changePassword(HttpServletRequest request, @PathVariable String userId, @RequestParam String newPassword) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

//        String newPassword = form;
        logger.debug("changePassword userId=" + userId + ", newPassword=" + newPassword);
        userService.changePassword(groupVo.getId(), userId, newPassword);
    }

    @RequestMapping(value = "users/{userId}/roles", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlUserRoleVo> listRoles(HttpServletRequest request, @PathVariable String userId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("list roles userId=" + userId);
        return userRoleService.list(userId);
    }

    @RequestMapping(value = "users/{userId}/roles", method = RequestMethod.POST)
    @ResponseBody
    public void addRole(HttpServletRequest request, @PathVariable String userId, @RequestBody EmlUserRoleVo userRoleVo) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

//        String newPassword = form;
        logger.debug("add Role userId=" + userId + ", userRoleVo=" + userRoleVo);
        userRoleService.add(userId, userRoleVo);
    }

    @RequestMapping(value = "users/{userId}/roles/{roleId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeRole(HttpServletRequest request, @PathVariable String userId, @PathVariable String roleId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

//        String newPassword = form;
        logger.debug("remove Role userId=" + userId + ", roleId=" + roleId);
        userRoleService.remove(userId, roleId);
    }
}

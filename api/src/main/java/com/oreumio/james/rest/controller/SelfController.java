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
public class SelfController {

    private static Logger logger = LoggerFactory.getLogger(SelfController.class);

    @Autowired
    private EmlUserService userService;

    @Autowired
    private EmlUserRoleService userRoleService;

    @Autowired
    private EmlGroupService groupService;

    @RequestMapping(value = "user", method = RequestMethod.GET)
    @ResponseBody
    public EmlUserVo get(HttpServletRequest request) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("사용자 자신의 정보를 취득합니다.: userId=" + userVo.getId());
        return userService.get(userVo.getGroupId(), userVo.getId());
    }

    @RequestMapping(value = "user/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public void changePassword(HttpServletRequest request, @RequestParam String oldPassword, @RequestParam String newPassword) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

//        String newPassword = form;
        logger.debug("사용자 자신의 패스워드를 변경합니다.: userId=" + userVo.getId() + ", newPassword=" + newPassword);
        userService.changePassword(userVo.getGroupId(), userVo.getId(), oldPassword, newPassword);
    }

    @RequestMapping(value = "user/roles", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlUserRoleVo> listRoles(HttpServletRequest request) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("사용자 자신의 역할 정보를 취득합니다.: userId=" + userVo.getId());
        return userRoleService.list(userVo.getId());
    }

    @RequestMapping(value = "group", method = RequestMethod.GET)
    @ResponseBody
    public EmlGroupVo getGroup(HttpServletRequest request) {
        EmlUserVo userVo = userService.getByName(request.getUserPrincipal().getName());

        logger.debug("그룹을 조회합니다.: groupId=" + userVo.getGroupId());
        return groupService.get(null, userVo.getGroupId());
    }
}

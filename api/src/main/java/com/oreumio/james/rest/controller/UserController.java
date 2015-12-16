package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.user.EmlUserService;
import com.oreumio.james.rest.user.EmlUserVo;
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
@RequestMapping("user")
public class UserController {

    @Autowired
    private EmlUserService userService;

    @RequestMapping("get")
    @ResponseBody
    public EmlUserVo get(HttpServletRequest request, String userId) {
        return userService.get(userId);
    }

    @RequestMapping("getBy")
    @ResponseBody
    public EmlUserVo get(HttpServletRequest request, String userName, String domainName) {
        return userService.get(userName, domainName);
    }

    @RequestMapping("list")
    @ResponseBody
    public List<EmlUserVo> list(HttpServletRequest request, String groupId) {
        return userService.list(groupId);
    }

    @RequestMapping("add")
    @ResponseBody
    public EmlUserVo add(String name, String groupId, long quota) {
        return userService.add(name, groupId, quota);
    }

    @RequestMapping("remove")
    @ResponseBody
    public void remove(String name, String groupId, long quota) {
        userService.remove(name, groupId);
    }

    @RequestMapping("changeState")
    @ResponseBody
    public void changeState(String id, String state) {
        userService.changeState(id, state);
    }

    @RequestMapping("changeQuota")
    @ResponseBody
    public void changeQuota(String id, long quota) {
        userService.changeQuota(id, quota);
    }

    @RequestMapping("changePassword")
    @ResponseBody
    public void changePassword(String id, String password) {
        userService.changePassword(id, password);
    }
}

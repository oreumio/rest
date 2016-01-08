package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlGroupService;
import com.oreumio.james.rest.group.EmlGroupVo;
import com.oreumio.james.rest.user.EmlUserService;
import com.oreumio.james.rest.user.EmlUserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
public class GroupSelfController {

    private static Logger logger = LoggerFactory.getLogger(GroupSelfController.class);

    @Autowired
    private EmlUserService userService;

    @Autowired
    private EmlGroupService groupService;

    @RequestMapping(value = "group", method = RequestMethod.GET)
    @ResponseBody
    public EmlGroupVo getGroup(HttpServletRequest request) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("그룹을 조회합니다.: groupId=" + groupVo.getId());

        return groupService.get(null, groupVo.getId());
    }

    @RequestMapping(value = "group/getQuotaUsage", method = RequestMethod.POST)
    @ResponseBody
    public EmlGroupVo getQuotaUsage(HttpServletRequest request) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("그룹의 사용량 실적을 취득합니다.: groupId=" + groupVo.getId());

        return groupService.getQuotaUsage(null, groupVo.getId());
    }

    @RequestMapping(value = "group/changeConfig", method = RequestMethod.POST)
    @ResponseBody
    public void changeConfig(HttpServletRequest request) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("그룹의 설정을 변경합니다.: groupId=" + groupVo.getId());
    }
}

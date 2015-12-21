package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlGroupService;
import com.oreumio.james.rest.group.EmlGroupVo;
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
@RequestMapping("group")
public class GroupController {

    private static Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private EmlGroupService groupService;

    @RequestMapping("get")
    @ResponseBody
    public EmlGroupVo get(HttpServletRequest request, String id) {
        return groupService.get(id);
    }

    @RequestMapping("list")
    @ResponseBody
    public List<EmlGroupVo> list(HttpServletRequest request, String clientId) {
        return groupService.list(clientId);
    }

    @RequestMapping("add")
    @ResponseBody
    public EmlGroupVo add(EmlGroupVo emlGroupVo) {
        logger.debug("" + emlGroupVo);
        try {
            groupService.add(emlGroupVo);
            return emlGroupVo;
        } catch (Exception e) {
            logger.warn("에러가 발생했습니다.", e);
            return null;
        }
    }

    @RequestMapping("remove")
    @ResponseBody
    public void remove(String id) {
        groupService.remove(id);
    }

    @RequestMapping("changeName")
    @ResponseBody
    public void changeName(String id, String name) {
        groupService.updateName(id, name);
    }

    @RequestMapping("changeQuota")
    @ResponseBody
    public void changeQuota(String id, long quota) {
        groupService.updateQuota(id, quota);
    }

    @RequestMapping("changeState")
    @ResponseBody
    public void changeState(String id, String state) {
        groupService.updateState(id, state);
    }

//    주 도메인 추가
//    주 도메인 제거
//    부 도메인 추가
//    부 도메인 제거

    @RequestMapping("addDomain")
    @ResponseBody
    public void addDomain(String groupId, String domainName) {
        groupService.addDomain(groupId, domainName);
    }

    @RequestMapping("removeDomain")
    @ResponseBody
    public void removeDomain(String id, String domain) {
        groupService.removeDomain(id, domain);
    }


    @RequestMapping("addSecDomain")
    @ResponseBody
    public void addSecDomain(String id, String domain, String secDomain) {
        groupService.addSecDomain(id, domain, secDomain);
    }

    @RequestMapping("removeSecDomain")
    @ResponseBody
    public void removeSecDomain(String id, String domain, String secDomain) {
        groupService.removeSecDomain(id, domain, secDomain);
    }
}

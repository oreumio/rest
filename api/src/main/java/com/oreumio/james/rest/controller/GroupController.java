package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.*;
import com.oreumio.james.rest.session.SessionUtil;
import com.oreumio.james.rest.session.SessionVo;
import com.oreumio.james.rest.user.EmlUserService;
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
public class GroupController {

    private static Logger logger = LoggerFactory.getLogger(GroupController.class);

    @Autowired
    private EmlGroupService groupService;

    @Autowired
    private EmlClientService clientService;

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlGroupVo> list(HttpServletRequest request) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        return groupService.list(sessionVo.getClientId());
    }

    @RequestMapping(value = "groups", method = RequestMethod.POST)
    @ResponseBody
    public EmlGroupVo add(HttpServletRequest request, @RequestBody EmlGroupVo emlGroupVo) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        logger.debug("그룹을 추가합니다: " + emlGroupVo);
        try {
            groupService.add(sessionVo.getClientId(), emlGroupVo);
            return emlGroupVo;
        } catch (Exception e) {
            logger.warn("에러가 발생했습니다.", e);
            return null;
        }
    }

    /**
     * org.springframework.security.authentication.UsernamePasswordAuthenticationToken 오브젝트가 Principal 로 옴.
     *
     * @param request
     * @param groupId
     * @return
     */
    @RequestMapping(value = "groups/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlGroupVo get(HttpServletRequest request, @PathVariable String groupId) {
        logger.debug("그룹을 조회합니다.: groupId=" + groupId);
        SessionVo sessionVo = SessionUtil.getSession(request);
        return groupService.get(sessionVo.getClientId(), groupId);
    }

    @RequestMapping(value = "groups/{groupId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable String groupId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        groupService.remove(sessionVo.getClientId(), groupId);
    }

    @RequestMapping("groups/{groupId}/changeName")
    @ResponseBody
    public void changeName(HttpServletRequest request, @PathVariable String groupId, String name) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        groupService.updateName(sessionVo.getClientId(), groupId, name);
    }

    @RequestMapping("groups/{groupId}/changeQuota")
    @ResponseBody
    public void changeQuota(HttpServletRequest request, @PathVariable String groupId, long quota) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        groupService.updateQuota(sessionVo.getClientId(), groupId, quota);
    }

    @RequestMapping("groups/{groupId}/suspend")
    @ResponseBody
    public void suspend(HttpServletRequest request, @PathVariable String groupId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        groupService.updateState(sessionVo.getClientId(), groupId, "R");
    }

    @RequestMapping("groups/{groupId}/resume")
    @ResponseBody
    public void resume(HttpServletRequest request, @PathVariable String groupId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        groupService.updateState(sessionVo.getClientId(), groupId, "N");
    }

//    주 도메인 추가
//    주 도메인 제거
//    부 도메인 추가
//    부 도메인 제거

    @RequestMapping(value = "groups/{groupId}/domains", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlGroupDomainVo> listDomains(HttpServletRequest request, @PathVariable String groupId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        return groupService.listDomains(sessionVo.getClientId(), groupId);
    }

    @RequestMapping(value = "groups/{groupId}/domains", method = RequestMethod.POST)
    @ResponseBody
    public EmlGroupDomainVo addDomain(HttpServletRequest request, @PathVariable String groupId, @RequestBody EmlGroupDomainVo groupDomainVo) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        return groupService.addDomain(sessionVo.getClientId(), groupId, groupDomainVo.getGroupDomainName());
    }

    @RequestMapping(value = "groups/{groupId}/domains/{domainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String domainId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        groupService.removeDomain(sessionVo.getClientId(), groupId, domainId);
    }

    @RequestMapping(value = "groups/{groupId}/domains/{domainId}/secDomains", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlGroupSecDomainVo> listSecDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String domainId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        return groupService.listSecDomains(sessionVo.getClientId(), groupId, domainId);
    }

    @RequestMapping(value = "groups/{groupId}/domains/{domainId}/secDomains", method = RequestMethod.POST)
    @ResponseBody
    public EmlGroupSecDomainVo addSecDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String domainId, String secDomainName) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        return groupService.addSecDomain(sessionVo.getClientId(), groupId, domainId, secDomainName);
    }

    @RequestMapping(value = "groups/{groupId}/domains/{domainId}/secDomains/{secDomainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeSecDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String domainId, @PathVariable String secDomainId) {
        SessionVo sessionVo = SessionUtil.getSession(request);
        groupService.removeSecDomain(sessionVo.getClientId(), groupId, domainId, secDomainId);
    }
}

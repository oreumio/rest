package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 사용자 그룹을 관리하는 API 를 제공하는 클래스입니다.
 * 주로 고객이 사용합니다.
 *
 * 주요 기능으로, 그룹 관리(검색, 등록, 해지, 정보 변경, 도메인 등록 및 해지)가 있습니다.
 *
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
        logger.debug("그룹을 검색합니다." );

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        return groupService.list(clientVo.getId());
    }

    @RequestMapping(value = "groups", method = RequestMethod.POST)
    @ResponseBody
    public EmlGroupVo add(HttpServletRequest request, @RequestBody EmlGroupVo emlGroupVo) {
        logger.debug("그룹을 추가합니다: " + emlGroupVo);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        try {
            groupService.add(clientVo.getId(), emlGroupVo);
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

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        return groupService.get(clientVo.getId(), groupId);
    }

    @RequestMapping(value = "groups/{groupId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable String groupId) {
        logger.debug("그룹을 삭제합니다.: groupId=" + groupId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        groupService.remove(clientVo.getId(), groupId);
    }

    @RequestMapping("groups/{groupId}/changeDisplayName")
    @ResponseBody
    public void changeName(HttpServletRequest request, @PathVariable String groupId, String displayName) {
        logger.debug("그룹의 이름을 수정합니다.: groupId=" + groupId + ", displayName=" + displayName);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        groupService.updateName(clientVo.getId(), groupId, displayName);
    }

    @RequestMapping("groups/{groupId}/changeQuota")
    @ResponseBody
    public void changeQuota(HttpServletRequest request, @PathVariable String groupId, long quota) {
        logger.debug("그룹의 쿼타를 수정합니다.: groupId=" + groupId + ", quota=" + quota);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        groupService.updateQuota(clientVo.getId(), groupId, quota);
    }

    @RequestMapping("groups/{groupId}/getQuotaUsage")
    @ResponseBody
    public EmlGroupVo getQuotaUsage(HttpServletRequest request, @PathVariable String groupId) {
        logger.debug("그룹의 쿼타 사용실적을 취득합니다.: groupId=" + groupId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        return groupService.getQuotaUsage(clientVo.getId(), groupId);
    }

    @RequestMapping("groups/{groupId}/suspend")
    @ResponseBody
    public void suspend(HttpServletRequest request, @PathVariable String groupId) {
        logger.debug("그룹을 정지시킵니다.: groupId=" + groupId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        groupService.updateState(clientVo.getId(), groupId, "R");
    }

    @RequestMapping("groups/{groupId}/resume")
    @ResponseBody
    public void resume(HttpServletRequest request, @PathVariable String groupId) {
        logger.debug("그룹을 재개시킵니다.: groupId=" + groupId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        groupService.updateState(clientVo.getId(), groupId, "N");
    }

//    주 도메인 추가
//    주 도메인 제거
//    부 도메인 추가
//    부 도메인 제거

    @RequestMapping(value = "groups/{groupId}/domains", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlGroupDomainVo> listDomains(HttpServletRequest request, @PathVariable String groupId) {
        logger.debug("그룹의 도메인을 검색합니다.: groupId=" + groupId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        return groupService.listDomains(clientVo.getId(), groupId);
    }

    @RequestMapping(value = "groups/{groupId}/domains", method = RequestMethod.POST)
    @ResponseBody
    public EmlGroupDomainVo addDomain(HttpServletRequest request, @PathVariable String groupId, @RequestBody EmlGroupDomainVo groupDomainVo) {
        logger.debug("그룹 도메인을 추가합니다.: groupId=" + groupId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        return groupService.addDomain(clientVo.getId(), groupId, groupDomainVo.getDomainName());
    }

    @RequestMapping(value = "groups/{groupId}/domains/{groupDomainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String groupDomainId) {
        logger.debug("그룹 도메인을 삭제합니다.: groupId=" + groupId + ", groupDomainId=" + groupDomainId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        groupService.removeDomain(clientVo.getId(), groupId, groupDomainId);
    }

    @RequestMapping(value = "groups/{groupId}/domains/{groupDomainId}/secDomains", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlGroupSecDomainVo> listSecDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String groupDomainId) {
        logger.debug("그룹의 부 도메인을 검색합니다.: groupId=" + groupId + ", groupDomainId=" + groupDomainId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        return groupService.listSecDomains(clientVo.getId(), groupId, groupDomainId);
    }

    @RequestMapping(value = "groups/{groupId}/domains/{groupDomainId}/secDomains", method = RequestMethod.POST)
    @ResponseBody
    public EmlGroupSecDomainVo addSecDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String groupDomainId, @RequestBody EmlGroupSecDomainVo groupSecDomainVo) {
        logger.debug("그룹의 부 도메인을 추가합니다.: groupId=" + groupId + ", groupDomainId=" + groupDomainId + ", " + groupSecDomainVo);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        return groupService.addSecDomain(clientVo.getId(), groupId, groupDomainId, groupSecDomainVo.getDomainName());
    }

    @RequestMapping(value = "groups/{groupId}/domains/{groupDomainId}/secDomains/{groupSecDomainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeSecDomain(HttpServletRequest request, @PathVariable String groupId, @PathVariable String groupDomainId, @PathVariable String groupSecDomainId) {
        logger.debug("그룹의 부 도메인을 삭제합니다.: groupId=" + groupId + ", groupDomainId=" + groupDomainId + ", groupSecDomainId=" + groupSecDomainId);

        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());
        groupService.removeSecDomain(clientVo.getId(), groupId, groupDomainId, groupSecDomainId);
    }
}

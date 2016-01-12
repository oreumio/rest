package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlGroupService;
import com.oreumio.james.rest.group.EmlGroupVo;
import com.oreumio.james.rest.org.EmlOrgService;
import com.oreumio.james.rest.org.EmlOrgUnitVo;
import com.oreumio.james.rest.org.EmlOrgVo;
import com.oreumio.james.rest.org.EmlUserOrgVo;
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
public class OrgController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    EmlOrgService emlOrgService;

    @Autowired
    private EmlGroupService groupService;

    @RequestMapping(value = "orgs", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlOrgVo> listOrgs(HttpServletRequest request) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직을 검색합니다.");

        return emlOrgService.listSystems(groupVo.getId());
    }

    @RequestMapping(value = "orgs", method = RequestMethod.POST)
    @ResponseBody
    public EmlOrgVo addOrg(HttpServletRequest request, @RequestBody EmlOrgVo orgVo) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직을 추가합니다.: orgName=" + orgVo.getDisplayName());

        return emlOrgService.addSystem(groupVo.getId(), orgVo.getDisplayName());
    }

    @RequestMapping(value = "orgs/{orgId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlOrgVo getOrg(HttpServletRequest request, @PathVariable String orgId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직 정보를 취득합니다.: orgId=" + orgId);

        return emlOrgService.getSystem(groupVo.getId(), orgId);
    }

    @RequestMapping(value = "orgs/{orgId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeOrg(HttpServletRequest request, @PathVariable String orgId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직을 삭제합니다.: orgId=" + orgId);

        emlOrgService.removeSystem(groupVo.getId(), orgId);
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlOrgUnitVo> listOrgUnits(HttpServletRequest request, @PathVariable String orgId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위를 검색합니다.: orgId=" + orgId);

        return emlOrgService.listSystemUnits(groupVo.getId(), orgId);
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits/listSubSystemUnits", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlOrgUnitVo> listSubSystemUnits(HttpServletRequest request, String orgUnitId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        return emlOrgService.listSubSystemUnits(orgUnitId);
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits", method = RequestMethod.POST)
    @ResponseBody
    public EmlOrgUnitVo addOrgUnit(HttpServletRequest request, @PathVariable String orgId, @RequestBody EmlOrgUnitVo orgSystemUnitVo) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위를 추가합니다.: orgId=" + orgId + ", orgUnitName=" + orgSystemUnitVo.getDisplayName() + ", parentOrgUnitId=" + orgSystemUnitVo.getParentOrgUnitId());
        return emlOrgService.addSystemUnit(orgId, orgSystemUnitVo.getDisplayName(), orgSystemUnitVo.getParentOrgUnitId());
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits/{orgUnitId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlOrgUnitVo getOrgUnit(HttpServletRequest request, @PathVariable String orgId, @PathVariable String orgUnitId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위의 정보를 취득합니다.: orgId=" + orgId + ", orgUnitId=" + orgUnitId);
        return emlOrgService.getSystemUnit(orgId, orgUnitId);
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits/{orgUnitId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeOrgUnit(HttpServletRequest request, @PathVariable String orgId, @PathVariable String orgUnitId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위를 삭제합니다.: orgId=" + orgId + ", orgUnitId=" + orgUnitId);
        emlOrgService.removeSystemUnit(orgId, orgUnitId);
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits/{orgUnitId}/members", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlUserOrgVo> listOrgUnitMembers(HttpServletRequest request, @PathVariable String orgId, @PathVariable String orgUnitId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위의 사용자를 검색합니다.: orgId=" + orgId + ", orgUnitId=" + orgUnitId);
        return emlOrgService.listSystemUnitUsers(orgId, orgUnitId);
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits/{orgUnitId}/members", method = RequestMethod.POST)
    @ResponseBody
    public EmlUserOrgVo addOrgUnitMember(HttpServletRequest request, @PathVariable String orgId, @PathVariable String orgUnitId, @RequestBody EmlUserOrgVo userOrgVo) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위의 사용자를 추가합니다.: orgId=" + orgId + ", orgUnitId=" + orgUnitId + ", userId=" + userOrgVo.getUserId());
        return emlOrgService.addSystemUnitUser(orgId, orgUnitId, userOrgVo.getUserId());
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits/{orgUnitId}/members/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlUserOrgVo getOrgUnitMember(HttpServletRequest request, @PathVariable String orgId, @PathVariable String orgUnitId, @PathVariable String memberId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위의 사용자 정보를 취득합니다.: orgId=" + orgId + ", orgUnitId=" + orgUnitId + ", memberId=" + memberId);
        try {
            return emlOrgService.getSystemUnitUser(orgId, orgUnitId, memberId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @RequestMapping(value = "orgs/{orgId}/orgUnits/{orgUnitId}/members/{memberId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeOrgUnitMember(HttpServletRequest request, @PathVariable String orgId, @PathVariable String orgUnitId, @PathVariable String memberId) {
        EmlGroupVo groupVo = groupService.getByName(request.getUserPrincipal().getName());

        logger.debug("조직의 단위의 사용자를 삭제합니다.: orgId=" + orgId + ", orgUnitId=" + orgUnitId + ", memberId=" + memberId);
        emlOrgService.removeSystemUnitUser(orgId, orgUnitId, memberId);
    }
}

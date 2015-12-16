package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.org.EmlOrgService;
import com.oreumio.james.rest.org.EmlOrgSystemUnitVo;
import com.oreumio.james.rest.org.EmlOrgSystemVo;
import com.oreumio.james.rest.org.EmlUserOrgVo;
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
@RequestMapping("org")
public class OrgController {

    @Autowired
    EmlOrgService emlOrgService;

    @RequestMapping("listSystems")
    @ResponseBody
    public List<EmlOrgSystemVo> listSystems(HttpServletRequest request, String groupId) {
        return emlOrgService.listSystems(groupId);
    }

    @RequestMapping("addSystem")
    @ResponseBody
    public EmlOrgSystemVo addSystem(HttpServletRequest request, String groupId, String systemName) {
        return emlOrgService.addSystem(groupId, systemName);
    }

    @RequestMapping("removeSystem")
    @ResponseBody
    public void removeSystem(HttpServletRequest request, String groupId, String systemName) {
        emlOrgService.removeSystem(groupId, systemName);
    }

    @RequestMapping("listSystemUnits")
    @ResponseBody
    public List<EmlOrgSystemUnitVo> listSystemUnits(HttpServletRequest request, String systemId) {
        return emlOrgService.listSystemUnits(systemId);
    }

    @RequestMapping("listSubSystemUnits")
    @ResponseBody
    public List<EmlOrgSystemUnitVo> listSubSystemUnits(HttpServletRequest request, String systemUnitId) {
        return emlOrgService.listSubSystemUnits(systemUnitId);
    }

    @RequestMapping("addSystemUnit")
    @ResponseBody
    public EmlOrgSystemUnitVo addSystemUnit(HttpServletRequest request, String systemId, String systemUnitName, String parentOrgSystemUnitId) {
        return emlOrgService.addSystemUnit(systemId, systemUnitName, parentOrgSystemUnitId);
    }

    @RequestMapping("removeSystemUnit")
    @ResponseBody
    public void removeSystemUnit(HttpServletRequest request, String systemId, String systemUnitName) {
        emlOrgService.removeSystemUnit(systemId, systemUnitName);
    }

    @RequestMapping("addSystemUnitUser")
    @ResponseBody
    public EmlUserOrgVo addSystemUnitUser(HttpServletRequest request, String userId, String systemUnitId) {
        return emlOrgService.addSystemUnitUser(userId, systemUnitId);
    }

    @RequestMapping("removeSystemUnitUser")
    @ResponseBody
    public void removeSystemUnitUser(HttpServletRequest request, String userId, String systemUnitId) {
        emlOrgService.removeSystemUnitUser(userId, systemUnitId);
    }

    @RequestMapping("listSystemUnitUsers")
    @ResponseBody
    public List<EmlUserOrgVo> listSystemUnitUsers(HttpServletRequest request, String systemUnitId) {
        return emlOrgService.listSystemUnitUsers(systemUnitId);
    }
}

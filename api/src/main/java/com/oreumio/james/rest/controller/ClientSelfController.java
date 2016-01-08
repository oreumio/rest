package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 고객을 관리하는 API 를 제공하는 클래스입니다.
 * 주로 고객이 사용합니다.
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
public class ClientSelfController {

    private static Logger logger = LoggerFactory.getLogger(ClientSelfController.class);

    @Autowired
    private EmlClientService clientService;

    @Autowired
    private EmlGroupService groupService;

    @RequestMapping(value = "client", method = RequestMethod.GET)
    @ResponseBody
    public EmlClientVo get(HttpServletRequest request) {
        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());

        logger.debug("고객의 정보를 취득합니다.: clientId=" + clientVo.getId());

        return clientService.get(clientVo.getId());
    }

    @RequestMapping(value = "client/getQuotaUsage", method = RequestMethod.POST)
    @ResponseBody
    public EmlClientVo getQuotaUsage(HttpServletRequest request) {
        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());

        logger.debug("고객의 사용량 실적을 취득합니다.: clientId=" + clientVo.getId());

        return clientService.getQuotaUsage(clientVo.getId());
    }

    @RequestMapping(value = "client/changeGroupQuota", method = RequestMethod.POST)
    @ResponseBody
    public void changeQuota(HttpServletRequest request, String groupId, long quota) {
        EmlClientVo clientVo = clientService.getByName(request.getUserPrincipal().getName());

        logger.debug("그룹의 쿼타를 수정합니다.: groupId=" + groupId + ", quota=" + quota);

        groupService.updateQuota(clientVo.getId(), groupId, quota);
    }
}

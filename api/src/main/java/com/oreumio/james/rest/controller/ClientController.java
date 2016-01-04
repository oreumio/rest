package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlClientDomainVo;
import com.oreumio.james.rest.group.EmlClientService;
import com.oreumio.james.rest.group.EmlClientVo;
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
public class ClientController {

    private static Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private EmlClientService clientService;

    @RequestMapping(value = "clients", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlClientVo> list(HttpServletRequest request) {
        logger.debug("고객을 검색합니다.");

        return clientService.list();
    }

    @RequestMapping(value = "clients", method = RequestMethod.POST)
    @ResponseBody
    public EmlClientVo add(HttpServletRequest request, @RequestBody EmlClientVo clientVo) {
        logger.debug("고객을 추가합니다.: " + clientVo);

        return clientService.register(clientVo);
    }

    @RequestMapping(value = "clients/{clientId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlClientVo get(HttpServletRequest request, @PathVariable String clientId) {
        logger.debug("고객의 정보를 취득합니다.: clientId=" + clientId);

        return clientService.get(clientId);
    }

    @RequestMapping(value = "clients/{clientId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable String clientId) {
        logger.debug("고객을 해지합니다.: clientId=" + clientId);

        clientService.unregister(clientId);
    }

    @RequestMapping(value = "clients/{clientId}/changeDisplayName", method = RequestMethod.POST)
    @ResponseBody
    public void changeName(HttpServletRequest request, @PathVariable String clientId, String displayName) {
        logger.debug("고객의 표시명을 변경합니다.: clientId=" + clientId + ", displayName=" + displayName);

        clientService.updateName(clientId, displayName);
    }

    @RequestMapping(value = "clients/{clientId}/changeQuota", method = RequestMethod.POST)
    @ResponseBody
    public void changeQuota(HttpServletRequest request, @PathVariable String clientId, long quota) {
        logger.debug("고객의 쿼타를 변경합니다.: clientId=" + clientId + ", quota=" + quota);

        clientService.updateQuota(clientId, quota);
    }

    @RequestMapping(value = "clients/{clientId}/changeState", method = RequestMethod.POST)
    @ResponseBody
    public void changeState(HttpServletRequest request, @PathVariable String clientId, String state) {
        logger.debug("고객의 상태를 변경합니다.: clientId=" + clientId + ", state=" + state);

        clientService.updateState(clientId, state);
    }

    @RequestMapping(value = "clients/{clientId}/domains", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlClientDomainVo> listDomains(HttpServletRequest request, @PathVariable String clientId) {
        logger.debug("고객 도메인을 검색합니다.: clientId=" + clientId);

        return clientService.listDomains(clientId);
    }

    @RequestMapping(value = "clients/{clientId}/domains", method = RequestMethod.POST)
    @ResponseBody
    public void addDomain(HttpServletRequest request, @PathVariable String clientId, String domain) {
        logger.debug("고객 도메인을 추가합니다.: clientId=" + clientId + ", domainId=" + domain);

        clientService.addDomain(clientId, domain);
    }

    @RequestMapping(value = "clients/{clientId}/domains/{domainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeDomain(HttpServletRequest request, @PathVariable String clientId, @PathVariable String domainId) {
        logger.debug("고객 도메인을 삭제합니다.: clientId=" + clientId + ", domainId=" + domainId);
        clientService.removeDomain(clientId, domainId);
    }
}

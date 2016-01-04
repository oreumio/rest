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
        return clientService.list();
    }

    @RequestMapping(value = "clients", method = RequestMethod.POST)
    @ResponseBody
    public EmlClientVo add(HttpServletRequest request, @RequestBody EmlClientVo clientVo) {
        return clientService.register(clientVo);
    }

    @RequestMapping(value = "clients/{clientId}", method = RequestMethod.GET)
    @ResponseBody
    public EmlClientVo get(HttpServletRequest request, @PathVariable String clientId) {
        return clientService.get(clientId);
    }

    @RequestMapping(value = "clients/{clientId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void remove(HttpServletRequest request, @PathVariable String clientId) {
        clientService.unregister(clientId);
    }

    @RequestMapping(value = "clients/{clientId}/changeDisplayName", method = RequestMethod.POST)
    @ResponseBody
    public void changeName(HttpServletRequest request, @PathVariable String clientId, String displayName) {
        clientService.updateName(clientId, displayName);
    }

    @RequestMapping(value = "clients/{clientId}/changeQuota", method = RequestMethod.POST)
    @ResponseBody
    public void changeQuota(HttpServletRequest request, @PathVariable String clientId, long quota) {
        clientService.updateQuota(clientId, quota);
    }

    @RequestMapping(value = "clients/{clientId}/changeState", method = RequestMethod.POST)
    @ResponseBody
    public void changeState(HttpServletRequest request, @PathVariable String clientId, String state) {
        clientService.updateState(clientId, state);
    }

    @RequestMapping(value = "clients/{clientId}/domains", method = RequestMethod.POST)
    @ResponseBody
    public void addDomain(HttpServletRequest request, @PathVariable String clientId, String domain) {
        clientService.addDomain(clientId, domain);
    }

    @RequestMapping(value = "clients/{clientId}/domains", method = RequestMethod.GET)
    @ResponseBody
    public List<EmlClientDomainVo> listDomains(HttpServletRequest request, @PathVariable String clientId) {
        return clientService.listDomains(clientId);
    }

    @RequestMapping(value = "clients/{clientId}/domains/{domainId}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeDomain(HttpServletRequest request, @PathVariable String clientId, @PathVariable String domainId) {
        logger.debug("고객 도메인을 삭제합니다.: clientId=" + clientId + ", domainId=" + domainId);
        clientService.removeDomain(clientId, domainId);
    }
}

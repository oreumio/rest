package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlClientDomainVo;
import com.oreumio.james.rest.group.EmlClientService;
import com.oreumio.james.rest.group.EmlClientVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
@RequestMapping("client")
public class ClientController {

    @Autowired
    private EmlClientService clientService;

    @RequestMapping("get")
    @ResponseBody
    public EmlClientVo get(String id) {
        return clientService.get(id);
    }

    @RequestMapping("list")
    @ResponseBody
    public List<EmlClientVo> list() {
        return clientService.list();
    }

    @RequestMapping("register")
    @ResponseBody
    public EmlClientVo register(String name, long quota) {
        return clientService.register(name, quota);
    }

    @RequestMapping("unregister")
    @ResponseBody
    public void unregister(String id) {
        clientService.unregister(id);
    }

    @RequestMapping("changeName")
    @ResponseBody
    public void changeName(String id, String name) {
        clientService.updateName(id, name);
    }

    @RequestMapping("changeQuota")
    @ResponseBody
    public void changeQuota(String id, long quota) {
        clientService.updateQuota(id, quota);
    }

    @RequestMapping("changeState")
    @ResponseBody
    public void changeState(String id, String state) {
        clientService.updateState(id, state);
    }

    @RequestMapping("addDomain")
    @ResponseBody
    public void addDomain(String id, String domain) {
        clientService.addDomain(id, domain);
    }

    @RequestMapping("removeDomain")
    @ResponseBody
    public void removeDomain(String id, String domain) {
        clientService.removeDomain(id, domain);
    }

    @RequestMapping("listDomains")
    @ResponseBody
    public List<EmlClientDomainVo> listDomains(String id) {
        return clientService.listDomains(id);
    }
}

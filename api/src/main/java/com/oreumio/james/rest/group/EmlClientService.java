package com.oreumio.james.rest.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlClientService {

    @Autowired
    private EmlClientDao clientDao;

    @Autowired
    private EmlClientDomainDao clientDomainDao;

    public EmlClientVo get(String clientId) {
        EmlClient emlClient = clientDao.selectClient(clientId);
        return new EmlClientVo(emlClient);
    }

    public List<EmlClientVo> list() {
        List<EmlClient> emlClientList = clientDao.list();
        List<EmlClientVo> emlClientVoList = new ArrayList<EmlClientVo>();
        for (EmlClient emlClient : emlClientList) {
            emlClientVoList.add(new EmlClientVo(emlClient));
        }
        return emlClientVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlClientVo register(String clientName, long quota) {
        EmlClient emlClient = clientDao.insert(clientName, "R", quota);
        return new EmlClientVo(emlClient);
    }

    @Transactional(value = "rest_tm")
    public void unregister(String clientId) {
        clientDao.delete(clientId);
    }

    @Transactional(value = "rest_tm")
    public void updateName(String clientId, String clientName) {
        clientDao.updateName(clientId, clientName);
    }

    @Transactional(value = "rest_tm")
    public void updateQuota(String clientId, long clientQuota) {
        clientDao.updateQuota(clientId, clientQuota);
    }

    @Transactional(value = "rest_tm")
    public void updateState(String clientId, String clientState) {
        clientDao.updateState(clientId, clientState);
    }

    @Transactional(value = "rest_tm")
    public void addDomain(String clientId, String domain) {
        clientDomainDao.insert(clientId, domain);
    }

    @Transactional(value = "rest_tm")
    public void removeDomain(String clientId, String domain) {
        clientDomainDao.delete(clientId, domain);
    }

    @Transactional(value = "rest_tm")
    public List<EmlClientDomainVo> listDomains(String clientId) {
        List<EmlClientDomainVo> emlClientDomainVoList = new ArrayList<EmlClientDomainVo>();
        List<EmlClientDomain> emlClientDomainList = clientDomainDao.list(clientId);
        for (EmlClientDomain emlClientDomain : emlClientDomainList) {
            emlClientDomainVoList.add(new EmlClientDomainVo(emlClientDomain));
        }
        return emlClientDomainVoList;
    }
}

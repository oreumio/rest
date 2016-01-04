package com.oreumio.james.rest.group;

import com.oreumio.james.util.IdProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

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
    public EmlClientVo register(EmlClientVo clientVo) {
        EmlClient emlClient = new EmlClient(clientVo);
        emlClient.setId(idProvider.next());
        clientDao.insert(emlClient);
        return clientVo;
    }

    @Transactional(value = "rest_tm")
    public void unregister(String clientId) {
        EmlClient client = clientDao.select(clientId);
        clientDao.delete(client);
    }

    @Transactional(value = "rest_tm")
    public void updateName(String clientId, String displayName) {
        EmlClient client = clientDao.select(clientId);
        client.setDisplayName(displayName);
    }

    @Transactional(value = "rest_tm")
    public void updateQuota(String clientId, long clientQuota) {
        EmlClient client = clientDao.select(clientId);
        client.setQuota(clientQuota);
    }

    @Transactional(value = "rest_tm")
    public void updateState(String clientId, String clientState) {
        EmlClient client = clientDao.select(clientId);
        client.setState(clientState);
    }

    @Transactional(value = "rest_tm")
    public void addDomain(String clientId, String domain) {
        EmlClientDomain clientDomain = new EmlClientDomain();
        clientDomain.setId(idProvider.next());
        clientDomain.setClientId(clientId);
        clientDomain.setDomain(domain);
        clientDomainDao.insert(clientDomain);
    }

    @Transactional(value = "rest_tm")
    public void removeDomain(String clientId, String domain) {
        EmlClientDomain clientDomain = clientDomainDao.select(clientId, domain);
        clientDomainDao.delete(clientDomain);
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

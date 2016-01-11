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

    @Autowired
    private EmlGroupDao groupDao;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    public EmlClientVo get(String clientId) {
        EmlClient emlClient = clientDao.selectClient(clientId);
        return new EmlClientVo(emlClient);
    }

    public EmlClientVo getByName(String principal) {
        int i = principal.indexOf("@");
        String userName = principal.substring(0, i);
        String host = principal.substring(i + 1);
        EmlClient client = clientDao.selectClient(userName, host);
        return new EmlClientVo(client);
    }

    public EmlClientVo getByName(String userName, String host) {
        EmlClient client = clientDao.selectClient(userName, host);
        return new EmlClientVo(client);
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
        EmlClient client = new EmlClient(clientVo);
        client.setId(idProvider.next());
        clientDao.insert(client);
        return new EmlClientVo(client);
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
    public EmlClientDomainVo addDomain(String clientId, EmlClientDomainVo clientDomainVo) {
        EmlClientDomain clientDomain = new EmlClientDomain(clientDomainVo);
        clientDomain.setClientId(clientId);
        clientDomain.setId(idProvider.next());
        clientDomainDao.insert(clientDomain);
        return new EmlClientDomainVo(clientDomain);
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

    @Transactional(value = "rest_tm")
    public EmlClientVo getQuotaUsage(String clientId) {
        long usage = 0;
        List<EmlGroup> groupList = groupDao.list(clientId);
        for (EmlGroup groupVo : groupList) {
            usage += groupVo.getQuota();
        }
        EmlClientVo clientVo = new EmlClientVo();
        clientVo.setQuota(usage);
        return clientVo;
    }

}

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
public class EmlGroupService {

    @Autowired
    private EmlGroupDao groupDao;

    @Autowired
    private EmlGroupDomainDao groupDomainDao;

    @Autowired
    private EmlGroupSecDomainDao groupSecDomainDao;

    public EmlGroupVo get(String groupId) {
        EmlGroup emlGroup = groupDao.selectGroup(groupId);
        return new EmlGroupVo(emlGroup);
    }

    public List<EmlGroupVo> list(String clientId) {
        List<EmlGroup> emlGroupList = groupDao.list(clientId);
        List<EmlGroupVo> emlGroupVoList = new ArrayList<EmlGroupVo>();
        for (EmlGroup emlGroup : emlGroupList) {
            emlGroupVoList.add(new EmlGroupVo(emlGroup));
        }
        return emlGroupVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlGroupVo add(String groupName, String clientId, long groupQuota) {
        EmlGroup emlGroup = groupDao.insert(groupName, clientId, "R", groupQuota);
        return new EmlGroupVo(emlGroup);
    }

    @Transactional(value = "rest_tm")
    public void remove(String groupId) {
        groupDao.delete(groupId);
    }

    @Transactional(value = "rest_tm")
    public void updateName(String groupId, String groupName) {
        groupDao.updateName(groupId, groupName);
    }

    @Transactional(value = "rest_tm")
    public void updateQuota(String groupId, long groupQuota) {
        groupDao.updateQuota(groupId, groupQuota);
    }

    @Transactional(value = "rest_tm")
    public void updateState(String groupId, String groupState) {
        groupDao.updateState(groupId, groupState);
    }

    @Transactional(value = "rest_tm")
    public void addDomain(String groupId, String domain) {
        groupDomainDao.insert(groupId, domain);
    }

    @Transactional(value = "rest_tm")
    public void removeDomain(String groupId, String domain) {
        groupDomainDao.delete(groupId, domain);
    }

    @Transactional(value = "rest_tm")
    public void addSecDomain(String groupId, String domain, String secDomain) {
        groupSecDomainDao.insert(groupId, domain, secDomain);
    }

    @Transactional(value = "rest_tm")
    public void removeSecDomain(String groupId, String domain, String secDomain) {
        groupSecDomainDao.delete(groupId, domain, secDomain);
    }
}

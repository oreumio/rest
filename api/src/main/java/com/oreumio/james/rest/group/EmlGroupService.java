package com.oreumio.james.rest.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreumio.james.rest.user.EmlUser;
import com.oreumio.james.rest.user.EmlUserDao;
import com.oreumio.james.util.IdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlGroupService {

    private static Logger logger = LoggerFactory.getLogger(EmlGroupService.class);

    @Autowired
    private EmlGroupDao groupDao;

    @Autowired
    private EmlGroupDomainDao groupDomainDao;

    @Autowired
    private EmlGroupSecDomainDao groupSecDomainDao;

    @Autowired
    private EmlUserDao userDao;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    public EmlGroupVo get(String clientId, String groupId) {
        EmlGroup emlGroup = groupDao.selectGroup(groupId);

        EmlGroupVo emlGroupVo = new EmlGroupVo(emlGroup);
        try {
            EmlGroupConfigVo emlGroupConfigVo = objectMapper.readValue(emlGroup.getServerConfig(), EmlGroupConfigVo.class);
            emlGroupVo.setServerConfig(emlGroupConfigVo);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return emlGroupVo;
    }

    public EmlGroupVo getByName(String principal) {
        int i = principal.indexOf("@");
        String userName = principal.substring(0, i);
        String host = principal.substring(i + 1);
        EmlGroup group = groupDao.selectGroup(userName, host);
        return new EmlGroupVo(group);
    }

    public EmlGroupVo getByName(String userName, String host) {
        EmlGroup group = groupDao.selectGroup(userName, host);
        return new EmlGroupVo(group);
    }

    public List<EmlGroupVo> list(String clientId) {
        List<EmlGroup> emlGroupList = groupDao.list(clientId);
        List<EmlGroupVo> emlGroupVoList = new ArrayList<EmlGroupVo>();
        for (EmlGroup emlGroup : emlGroupList) {
            EmlGroupVo emlGroupVo = new EmlGroupVo(emlGroup);
            try {
                EmlGroupConfigVo emlGroupConfigVo = objectMapper.readValue(emlGroup.getServerConfig(), EmlGroupConfigVo.class);
                emlGroupVo.setServerConfig(emlGroupConfigVo);

            } catch (IOException e) {
                e.printStackTrace();
            }
            emlGroupVoList.add(emlGroupVo);
        }
        return emlGroupVoList;
    }

    @Transactional(value = "rest_tm")
    public void add(String clientId, EmlGroupVo groupVo) {

        groupVo.setClientId(clientId);

        // 그룹 설정 초기화
        groupVo.setServerConfig(new EmlGroupConfigVo());

        // 엔티티 생성

        EmlGroup group = new EmlGroup(groupVo);

        // 아이디 생성
        group.setId(idProvider.next());

        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, groupVo.getServerConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }

        group.setServerConfig(writer.toString());

        // 그룹 엔티티 추가
        logger.debug("그룹을 추가합니다.: " + group);
        groupDao.insert(group);

        groupVo.setId(group.getId());
    }

    @Transactional(value = "rest_tm")
    public void remove(String clientId, String groupId) {
        groupDao.delete(groupId);
    }

    @Transactional(value = "rest_tm")
    public void updateName(String clientId, String groupId, String displayName) {
        groupDao.updateDisplayName(groupId, displayName);
    }

    @Transactional(value = "rest_tm")
    public void updateQuota(String clientId, String groupId, long groupQuota) {
        groupDao.updateQuota(groupId, groupQuota);
    }

    @Transactional(value = "rest_tm")
    public void updateState(String clientId, String groupId, String groupState) {
        groupDao.updateState(groupId, groupState);
    }

    @Transactional(value = "rest_tm")
    public List<EmlGroupDomainVo> listDomains(String clientId, String groupId) {

        List<EmlGroupDomainVo> groupDomainVoList = new ArrayList<EmlGroupDomainVo>();

        logger.debug("그룹 주 도메인을 검색합니다.: groupId=" + groupId);

        List<EmlGroupDomain> groupDomainList = groupDomainDao.list(groupId);

        for (EmlGroupDomain groupDomain : groupDomainList) {
            groupDomainVoList.add(new EmlGroupDomainVo(groupDomain));
        }

        return groupDomainVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlGroupDomainVo addDomain(String clientId, String groupId, String domainName) {
        logger.debug("그룹 도메인을 추가합니다.: groupId=" + groupId + ", domain=" + domainName);
        EmlGroupDomain groupDomain = groupDomainDao.insert(groupId, domainName);
        return new EmlGroupDomainVo(groupDomain);
    }

    @Transactional(value = "rest_tm")
    public void removeDomain(String clientId, String groupId, String domainId) {
        EmlGroupDomain groupDomain = groupDomainDao.select(groupId, domainId);
        groupDomainDao.delete(groupDomain);
    }

    @Transactional(value = "rest_tm")
    public List<EmlGroupSecDomainVo> listSecDomains(String clientId, String groupId, String domainId) {
        List<EmlGroupSecDomainVo> groupSecDomainVoList = new ArrayList<EmlGroupSecDomainVo>();

        logger.debug("그룹 부 도메인을 검색합니다.: groupId=" + groupId + ", domainId=" + domainId);

        List<EmlGroupSecDomain> groupSecDomainList = groupSecDomainDao.list(groupId, domainId);

        for (EmlGroupSecDomain groupSecDomain : groupSecDomainList) {
            groupSecDomainVoList.add(new EmlGroupSecDomainVo(groupSecDomain));
        }

        return groupSecDomainVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlGroupSecDomainVo addSecDomain(String clientId, String groupId, String domainId, String secDomainName) {
        logger.debug("그룹 부 도메인을 추가합니다.: groupId=" + groupId + ", domainId=" + domainId + ", secDomainName=" + secDomainName);
        EmlGroupSecDomain groupSecDomain = groupSecDomainDao.insert(groupId, domainId, secDomainName);
        return new EmlGroupSecDomainVo(groupSecDomain);
    }

    @Transactional(value = "rest_tm")
    public void removeSecDomain(String clientId, String groupId, String domainId, String secDomainId) {
        EmlGroupSecDomain groupSecDomain = groupSecDomainDao.select(groupId, domainId, secDomainId);
        groupSecDomainDao.delete(groupSecDomain);
    }

    @Transactional(value = "rest_tm")
    public EmlGroupVo getQuotaUsage(String clientId, String groupId) {
        long usage = 0;
        List<EmlUser> userList = userDao.list(groupId);
        for (EmlUser userVo : userList) {
            usage += userVo.getQuota();
        }
        EmlGroupVo groupVo = new EmlGroupVo();
        groupVo.setQuota(usage);
        return groupVo;
    }
}

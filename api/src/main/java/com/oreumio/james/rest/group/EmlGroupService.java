package com.oreumio.james.rest.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreumio.james.util.IdProvider;
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

    @Autowired
    private EmlGroupDao groupDao;

    @Autowired
    private EmlGroupDomainDao groupDomainDao;

    @Autowired
    private EmlGroupSecDomainDao groupSecDomainDao;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    public EmlGroupVo get(String groupId) {
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
    public void add(EmlGroupVo emlGroupVo) {

        // 아이디 생성
        emlGroupVo.setId("G" + idProvider.next());

        // 그룹 설정 초기화
        emlGroupVo.setServerConfig(new EmlGroupConfigVo());

        // 엔티티 생성

        EmlGroup emlGroup = new EmlGroup(emlGroupVo);

        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, emlGroupVo.getServerConfig());
        } catch (IOException e) {
            e.printStackTrace();
        }

        emlGroup.setServerConfig(writer.toString());

        // 그룹 엔티티 추가
        groupDao.insert(emlGroup);
    }

    @Transactional(value = "rest_tm")
    public void remove(String groupId) {
        groupDao.delete(groupId);
    }

    @Transactional(value = "rest_tm")
    public void updateName(String groupId, String displayName) {
        groupDao.updateDisplayName(groupId, displayName);
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

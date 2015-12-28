package com.oreumio.james.rest.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 메일 사용자 관련 처리
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlOrgService {

    @Autowired
    private EmlOrgDao orgSystemDao;

    @Autowired
    private EmlOrgUnitDao orgSystemUnitDao;

    @Autowired
    private EmlUserOrgDao emlUserOrgDao;

    public List<EmlOrgVo> listSystems(String groupId) {
        List<EmlOrgVo> emlOrgSystemVoList = new ArrayList<EmlOrgVo>();

        List<EmlOrg> emlOrgSystemList = orgSystemDao.list(groupId);
        for (EmlOrg emlOrgSystem : emlOrgSystemList) {
            emlOrgSystemVoList.add(new EmlOrgVo(emlOrgSystem));
        }
        return emlOrgSystemVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlOrgVo addSystem(String groupId, String orgName) {
        EmlOrg emlOrgSystem = orgSystemDao.insert(groupId, orgName);
        return new EmlOrgVo(emlOrgSystem);
    }

    @Transactional(value = "rest_tm")
    public void removeSystem(String groupId, String orgId) {
        EmlOrg emlOrgSystem = orgSystemDao.select(groupId, orgId);
        orgSystemDao.delete(emlOrgSystem);
    }

    public List<EmlOrgUnitVo> listSystemUnits(String groupId, String systemId) {
        List<EmlOrgUnitVo> emlOrgSystemUnitVoList = new ArrayList<EmlOrgUnitVo>();

        List<EmlOrgUnit> emlOrgSystemUnitList = orgSystemUnitDao.list(systemId);
        for (EmlOrgUnit emlOrgSystemUnit : emlOrgSystemUnitList) {
            emlOrgSystemUnitVoList.add(new EmlOrgUnitVo(emlOrgSystemUnit));
        }
        return emlOrgSystemUnitVoList;
    }

    public List<EmlOrgUnitVo> listSubSystemUnits(String systemUnitId) {
        List<EmlOrgUnitVo> emlOrgSystemUnitVoList = new ArrayList<EmlOrgUnitVo>();

        List<EmlOrgUnit> emlOrgSystemUnitList = orgSystemUnitDao.listSubSystemUnits(systemUnitId);
        for (EmlOrgUnit emlOrgSystemUnit : emlOrgSystemUnitList) {
            emlOrgSystemUnitVoList.add(new EmlOrgUnitVo(emlOrgSystemUnit));
        }
        return emlOrgSystemUnitVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlOrgUnitVo addSystemUnit(String orgId, String orgUnitName, String parentOrgUnitId) {
        EmlOrgUnit emlOrgSystemUnit = orgSystemUnitDao.insert(orgId, orgUnitName, parentOrgUnitId);
        return new EmlOrgUnitVo(emlOrgSystemUnit);
    }

    @Transactional(value = "rest_tm")
    public void removeSystemUnit(String orgId, String orgUnitId) {
        EmlOrgUnit emlOrgSystemUnit = orgSystemUnitDao.select(orgId, orgUnitId);
        orgSystemUnitDao.delete(emlOrgSystemUnit);
    }

    @Transactional(value = "rest_tm")
    public EmlUserOrgVo addSystemUnitUser(String orgId, String orgUnitId, String userId) {
        EmlUserOrg emlOrgSystemUnitUser = emlUserOrgDao.insert(orgUnitId, userId);
        return new EmlUserOrgVo(emlOrgSystemUnitUser);
    }

    @Transactional(value = "rest_tm")
    public void removeSystemUnitUser(String orgId, String orgUnitId, String userId) {
        EmlUserOrg emlOrgSystemUnitUser = emlUserOrgDao.select(orgUnitId, userId);
        emlUserOrgDao.delete(emlOrgSystemUnitUser);
    }

    @Transactional(value = "rest_tm")
    public List<EmlUserOrgVo> listSystemUnitUsers(String orgId, String orgUnitId) {
        List<EmlUserOrgVo> emlUserOrgVoList = new ArrayList<EmlUserOrgVo>();
        List<EmlUserOrg> emlOrgSystemUnitUsers = emlUserOrgDao.list(orgUnitId);
        for (EmlUserOrg emlUserOrg : emlOrgSystemUnitUsers) {
            emlUserOrgVoList.add(new EmlUserOrgVo(emlUserOrg));
        }
        return emlUserOrgVoList;
    }
}

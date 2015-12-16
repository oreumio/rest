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
    private EmlOrgSystemDao orgSystemDao;

    @Autowired
    private EmlOrgSystemUnitDao orgSystemUnitDao;

    @Autowired
    private EmlUserOrgDao emlUserOrgDao;

    public List<EmlOrgSystemVo> listSystems(String groupId) {
        List<EmlOrgSystemVo> emlOrgSystemVoList = new ArrayList<EmlOrgSystemVo>();

        List<EmlOrgSystem> emlOrgSystemList = orgSystemDao.list(groupId);
        for (EmlOrgSystem emlOrgSystem : emlOrgSystemList) {
            emlOrgSystemVoList.add(new EmlOrgSystemVo(emlOrgSystem));
        }
        return emlOrgSystemVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlOrgSystemVo addSystem(String groupId, String name) {
        EmlOrgSystem emlOrgSystem = orgSystemDao.insert(groupId, name);
        return new EmlOrgSystemVo(emlOrgSystem);
    }

    @Transactional(value = "rest_tm")
    public void removeSystem(String groupId, String name) {
        EmlOrgSystem emlOrgSystem = orgSystemDao.select(groupId, name);
        orgSystemDao.delete(emlOrgSystem);
    }

    public List<EmlOrgSystemUnitVo> listSystemUnits(String systemId) {
        List<EmlOrgSystemUnitVo> emlOrgSystemUnitVoList = new ArrayList<EmlOrgSystemUnitVo>();

        List<EmlOrgSystemUnit> emlOrgSystemUnitList = orgSystemUnitDao.list(systemId);
        for (EmlOrgSystemUnit emlOrgSystemUnit : emlOrgSystemUnitList) {
            emlOrgSystemUnitVoList.add(new EmlOrgSystemUnitVo(emlOrgSystemUnit));
        }
        return emlOrgSystemUnitVoList;
    }

    public List<EmlOrgSystemUnitVo> listSubSystemUnits(String systemUnitId) {
        List<EmlOrgSystemUnitVo> emlOrgSystemUnitVoList = new ArrayList<EmlOrgSystemUnitVo>();

        List<EmlOrgSystemUnit> emlOrgSystemUnitList = orgSystemUnitDao.listSubSystemUnits(systemUnitId);
        for (EmlOrgSystemUnit emlOrgSystemUnit : emlOrgSystemUnitList) {
            emlOrgSystemUnitVoList.add(new EmlOrgSystemUnitVo(emlOrgSystemUnit));
        }
        return emlOrgSystemUnitVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlOrgSystemUnitVo addSystemUnit(String systemId, String systemUnitName, String parentOrgSystemUnitId) {
        EmlOrgSystemUnit emlOrgSystemUnit = orgSystemUnitDao.insert(systemId, systemUnitName, parentOrgSystemUnitId);
        return new EmlOrgSystemUnitVo(emlOrgSystemUnit);
    }

    @Transactional(value = "rest_tm")
    public void removeSystemUnit(String systemId, String systemUnitName) {
        EmlOrgSystemUnit emlOrgSystemUnit = orgSystemUnitDao.select(systemId, systemUnitName);
        orgSystemUnitDao.delete(emlOrgSystemUnit);
    }

    @Transactional(value = "rest_tm")
    public EmlUserOrgVo addSystemUnitUser(String systemUnitId, String userId) {
        EmlUserOrg emlOrgSystemUnitUser = emlUserOrgDao.insert(systemUnitId, userId);
        return new EmlUserOrgVo(emlOrgSystemUnitUser);
    }

    @Transactional(value = "rest_tm")
    public void removeSystemUnitUser(String systemUnitId, String userId) {
        EmlUserOrg emlOrgSystemUnitUser = emlUserOrgDao.select(systemUnitId, userId);
        emlUserOrgDao.delete(emlOrgSystemUnitUser);
    }

    @Transactional(value = "rest_tm")
    public List<EmlUserOrgVo> listSystemUnitUsers(String systemUnitId) {
        List<EmlUserOrgVo> emlUserOrgVoList = new ArrayList<EmlUserOrgVo>();
        List<EmlUserOrg> emlOrgSystemUnitUsers = emlUserOrgDao.list(systemUnitId);
        for (EmlUserOrg emlUserOrg : emlOrgSystemUnitUsers) {
            emlUserOrgVoList.add(new EmlUserOrgVo(emlUserOrg));
        }
        return emlUserOrgVoList;
    }
}

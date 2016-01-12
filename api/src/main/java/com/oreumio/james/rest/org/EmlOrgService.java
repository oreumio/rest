package com.oreumio.james.rest.org;

import com.oreumio.james.util.IdProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    public void setIdProvider(IdProvider<String> idProvider) {
        this.idProvider = idProvider;
    }

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
    public EmlOrgVo getSystem(String groupId, String orgId) {
        EmlOrg orgSystem = orgSystemDao.select(groupId, orgId);
        return new EmlOrgVo(orgSystem);
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
    public EmlOrgUnitVo getSystemUnit(String orgId, String orgUnitId) {
        EmlOrgUnit orgSystemUnit = orgSystemUnitDao.select(orgId, orgUnitId);
        return new EmlOrgUnitVo(orgSystemUnit);
    }

    @Transactional(value = "rest_tm")
    public void removeSystemUnit(String orgId, String orgUnitId) {
        EmlOrgUnit emlOrgSystemUnit = orgSystemUnitDao.select(orgId, orgUnitId);
        orgSystemUnitDao.delete(emlOrgSystemUnit);
    }

    @Transactional(value = "rest_tm")
    public EmlUserOrgVo addSystemUnitUser(String orgId, String orgUnitId, String userId) {
        EmlUserOrg userOrg = new EmlUserOrg();
        userOrg.setId(idProvider.next());
        userOrg.setOrgSystemUnitId(orgUnitId);
        userOrg.setUserId(userId);
        emlUserOrgDao.insert(userOrg);
        return new EmlUserOrgVo(userOrg);
    }

    @Transactional(value = "rest_tm")
    public EmlUserOrgVo getSystemUnitUser(String orgId, String orgUnitId, String memberId) {
        EmlUserOrg userOrg = emlUserOrgDao.select(orgUnitId, memberId);
        return new EmlUserOrgVo(userOrg);
    }

    @Transactional(value = "rest_tm")
    public void removeSystemUnitUser(String orgId, String orgUnitId, String memberId) {
        EmlUserOrg userOrg = emlUserOrgDao.select(orgUnitId, memberId);
        emlUserOrgDao.delete(userOrg);
    }

    @Transactional(value = "rest_tm")
    public List<EmlUserOrgVo> listSystemUnitUsers(String orgId, String orgUnitId) {
        List<EmlUserOrgVo> userOrgVoList = new ArrayList<EmlUserOrgVo>();
        List<EmlUserOrg> userOrgList = emlUserOrgDao.list(orgUnitId);
        for (EmlUserOrg userOrg : userOrgList) {
            userOrgVoList.add(new EmlUserOrgVo(userOrg));
        }
        return userOrgVoList;
    }
}

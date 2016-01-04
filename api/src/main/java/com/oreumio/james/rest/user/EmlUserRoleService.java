package com.oreumio.james.rest.user;

import com.oreumio.james.util.IdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class EmlUserRoleService {

    private static Logger logger = LoggerFactory.getLogger(EmlUserRoleService.class);

    @Autowired
    private EmlUserRoleDao userRoleDao;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    public List<EmlUserRoleVo> list(String userId) {
        logger.debug("사용자 역할을 검색합니다. : userId=" + userId);

        List<EmlUserRole> userRoleList = userRoleDao.list(userId);
        List<EmlUserRoleVo> userRoleVoList = new ArrayList<EmlUserRoleVo>();
        for (EmlUserRole userRole : userRoleList) {
            userRoleVoList.add(new EmlUserRoleVo(userRole));
        }
        return userRoleVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlUserRoleVo add(String userId, EmlUserRoleVo userRoleVo) {
        logger.debug("사용자 역할을 추가합니다: userId=" + userId + ", " + userRoleVo);

        EmlUserRole userRole = new EmlUserRole(userRoleVo);
        userRole.setId(idProvider.next());
        userRole.setUserId(userId);
        userRoleDao.insert(userRole);
        return userRoleVo;
    }

    @Transactional(value = "rest_tm")
    public void remove(String userId, String roleId) {
        logger.debug("사용자 역할을 삭제합니다: userId=" + userId + ", roleId=" + roleId);

        EmlUserRole userRole = userRoleDao.select(userId, roleId);
        userRoleDao.delete(userRole);
    }
}

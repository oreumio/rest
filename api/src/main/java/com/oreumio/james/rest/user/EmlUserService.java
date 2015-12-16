package com.oreumio.james.rest.user;

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
public class EmlUserService {

    @Autowired
    private EmlUserDao userDao;

    public EmlUserVo get(String userId) {
        EmlUser emlUser = userDao.selectUser(userId);
        return new EmlUserVo(emlUser);
    }

    public EmlUserVo get(String userName, String domainName) {
        EmlUser emlUser = userDao.selectUser(userName, domainName);
        return new EmlUserVo(emlUser);
    }

    public List<EmlUserVo> list(String groupId) {
        List<EmlUser> emlUserList = userDao.list(groupId);
        List<EmlUserVo> emlUserVoList = new ArrayList<EmlUserVo>();
        for (EmlUser emlUser : emlUserList) {
            emlUserVoList.add(new EmlUserVo(emlUser));
        }
        return emlUserVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlUserVo add(String userName, String groupId, long userQuota) {
        EmlUser emlUser = userDao.insert(userName, groupId, "R", userQuota);
        return new EmlUserVo(emlUser);
    }

    @Transactional(value = "rest_tm")
    public void remove(String userName, String groupId) {
        userDao.delete(userName, groupId);
    }

    @Transactional(value = "rest_tm")
    public void changeState(String userId, String state) {
        EmlUser emlUser = userDao.selectUser(userId);
        emlUser.setState(state);
    }

    @Transactional(value = "rest_tm")
    public void changeQuota(String userId, long quota) {
        EmlUser emlUser = userDao.selectUser(userId);
        emlUser.setQuota(quota);
    }

    @Transactional(value = "rest_tm")
    public void changePassword(String userId, String password) {
        EmlUser emlUser = userDao.selectUser(userId);
        emlUser.setPassword(password);
    }
}

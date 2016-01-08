package com.oreumio.james.rest.user;

import com.fasterxml.jackson.databind.ObjectMapper;
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
 * <pre>
 * 메일 사용자 관련 처리
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlUserService implements EmlUserSupportService {

    private static Logger logger = LoggerFactory.getLogger(EmlUserService.class);

    @Autowired
    private EmlUserDao userDao;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

    public EmlUserVo get(String groupId, String userId) {
        EmlUser emlUser = userDao.selectUser(userId);
        return new EmlUserVo(emlUser);
    }

    public EmlUserVo getByName(String principal) {
        int i = principal.indexOf("@");
        String userName = principal.substring(0, i);
        String host = principal.substring(i + 1);
        EmlUser emlUser = userDao.selectUser(userName, host);
        return new EmlUserVo(emlUser);
    }

    public EmlUserVo getByName(String userName, String host) {
        EmlUser emlUser = userDao.selectUser(userName, host);
        return new EmlUserVo(emlUser);
    }

    public List<EmlUserVo> list(String groupId) {
        logger.debug("사용자를 검색합니다. : groupId=" + groupId);

        List<EmlUser> emlUserList = userDao.list(groupId);
        List<EmlUserVo> emlUserVoList = new ArrayList<EmlUserVo>();
        for (EmlUser emlUser : emlUserList) {
            emlUserVoList.add(new EmlUserVo(emlUser));
        }
        return emlUserVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlUserVo add(String groupId, EmlUserVo userVo) {

        userVo.setGroupId(groupId);

        logger.debug("사용자를 추가합니다: " + userVo);

        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, new EmlUserConfigVo());
        } catch (IOException e) {
            e.printStackTrace();
        }
        userVo.setServerConfig(writer.toString());
        userVo.setPassword("pass");

        EmlUser user = new EmlUser(userVo);
        user.setId(idProvider.next());
        userDao.insert(user);
        return new EmlUserVo(user);
    }

    @Transactional(value = "rest_tm")
    public void remove(String groupId, String userId) {
        EmlUser user = userDao.select(groupId, userId);
        userDao.delete(user);
    }

    @Transactional(value = "rest_tm")
    public void removeByName(String userName, String groupId) {
        EmlUser user = userDao.select(userName, groupId);
        userDao.delete(user);
    }

    @Transactional(value = "rest_tm")
    public void changeState(String groupId, String userId, String prestate, String poststate) {
        EmlUser user = userDao.selectUser(userId);
        user.setState(poststate);
    }

    @Transactional(value = "rest_tm")
    public void changeQuota(String groupId, String userId, long quota) {
        EmlUser user = userDao.selectUser(userId);
        user.setQuota(quota);
    }

    @Transactional(value = "rest_tm")
    public void changePassword(String groupId, String userId, String password) {
        EmlUser user = userDao.selectUser(userId);
        user.setPassword(password);
    }

    @Transactional(value = "rest_tm")
    public void changePassword(String groupId, String userId, String oldPassword, String newPassword) {
        EmlUser user = userDao.selectUser(userId);
        if (oldPassword.equals(user.getPassword())) {
            user.setPassword(newPassword);
        }
    }

    @Override
    public String getPersonal(String address, String lang) {
        String userName = null, domainName = null;
        EmlUserVo userVo = get(userName, domainName);
        return userVo.getDisplayName();
    }
}

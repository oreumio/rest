package com.oreumio.james.rest.user;

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
 * <pre>
 * 메일 사용자 관련 처리
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlUserService implements EmlUserSupportService {

    @Autowired
    private EmlUserDao userDao;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    private ObjectMapper objectMapper = new ObjectMapper();

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
    public EmlUserVo add(EmlUserVo emlUserVo) {
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, new EmlUserConfigVo());
        } catch (IOException e) {
            e.printStackTrace();
        }
        emlUserVo.setServerConfig(writer.toString());
        EmlUser emlUser = userDao.insert(emlUserVo);
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

    @Override
    public String getPersonal(String address, String lang) {
        String userName = null, domainName = null;
        EmlUserVo emlUserVo = get(userName, domainName);
        return emlUserVo.getDisplayName();
    }
}

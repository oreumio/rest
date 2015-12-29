package com.oreumio.james.rest.mailbox;

import com.oreumio.james.util.IdProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlMailboxService {

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    @Autowired
    EmlMailboxDao emlMailboxDao;

    /**
     *
     * @param userId 사용자 아이디
     * @param mailboxName 상위 메일박스 이름
     * @return
     */
    public List<EmlMailboxVo> list(String userId, String mailboxName) {
        List<EmlMailboxVo> emlMailboxVoList = new ArrayList<EmlMailboxVo>();
        List<EmlMailbox> emlMailboxList = emlMailboxDao.list(userId);
        for (EmlMailbox emlMailbox : emlMailboxList) {
            emlMailboxVoList.add(new EmlMailboxVo(emlMailbox));
        }
        return emlMailboxVoList;
    }

    @Transactional(value = "rest_tm")
    public EmlMailboxVo add(String userId, String mailboxName) {
        EmlMailbox emlMailbox = new EmlMailbox();
        emlMailbox.setId(1);
        emlMailbox.setUserId(userId);
        emlMailbox.setName(mailboxName);
        emlMailbox.setType("S");
        emlMailboxDao.insert(emlMailbox);
        return new EmlMailboxVo(emlMailbox);
    }

    public EmlMailboxVo get(String userId, long mailboxId) {
        EmlMailbox mailbox = emlMailboxDao.select(mailboxId);
        return new EmlMailboxVo(mailbox);
    }

    @Transactional(value = "rest_tm")
    public void remove(String userId, long mailboxId) {
        EmlMailbox mailbox = emlMailboxDao.select(mailboxId);
        emlMailboxDao.delete(mailbox);
    }

    @Transactional(value = "rest_tm")
    public void changeName(String userId, long mailboxId, String newMailboxName) {
        EmlMailbox mailbox = emlMailboxDao.select(mailboxId);
        mailbox.setName(newMailboxName);
    }
}

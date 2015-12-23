package com.oreumio.james.rest.mailbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlMailboxService {

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
        List<EmlMailbox> emlMailboxList = emlMailboxDao.list(userId, mailboxName);
        for (EmlMailbox emlMailbox : emlMailboxList) {
            emlMailboxVoList.add(new EmlMailboxVo(emlMailbox));
        }
        return emlMailboxVoList;
    }
}

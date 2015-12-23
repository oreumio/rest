package com.oreumio.james.rest.message;

import com.oreumio.james.rest.mailbox.EmlMailbox;
import com.oreumio.james.rest.mailbox.EmlMailboxDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlMailService {

    private static Logger logger = LoggerFactory.getLogger(EmlMailService.class);

    @Autowired
    EmlMailDao emlMaildao;

    @Autowired
    EmlMailboxDao emlMailboxdao;

    public List<EmlMailVo> list(String userId, String mailboxName) {
        logger.debug("userId=" + userId + ", mailboxName=" + mailboxName);

        EmlMailbox emlMailbox = emlMailboxdao.get(userId, mailboxName);

        logger.debug("" + emlMailbox);

        List<EmlMailVo> emlMailVoList = new ArrayList<EmlMailVo>();
        List<EmlMail> emlMailList = emlMaildao.list(emlMailbox.getId());
        for (EmlMail emlMail : emlMailList) {
            System.out.println(new String(emlMail.getBody()));
            System.out.println(new String(emlMail.getHeader()));
            emlMailVoList.add(new EmlMailVo(emlMail));
        }
        return emlMailVoList;
    }


    public List<EmlMailVo> search(String userId, EmlMailSearchVo emlMailSearchVo) {
        return new ArrayList<EmlMailVo>();
    }
}

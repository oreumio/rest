package com.oreumio.james.rest.form;

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
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlMailFormService {

    private static Logger logger = LoggerFactory.getLogger(EmlMailFormService.class);

    @Autowired
    EmlMailFormDao emlMailFormDao;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

    @Transactional("rest_tm")
    public List<EmlMailFormVo> list(String userId) {
        List<EmlMailFormVo> emlMailFormVoList = new ArrayList<EmlMailFormVo>();
        List<EmlMailForm> emlMailFormList = emlMailFormDao.list(userId);
        for (EmlMailForm emlMailForm : emlMailFormList) {
            emlMailFormVoList.add(new EmlMailFormVo(emlMailForm));
        }
        return emlMailFormVoList;
    }

    @Transactional("rest_tm")
    public EmlMailFormVo get(String userId, String mailFormId) {
        EmlMailForm mailForm = emlMailFormDao.select(userId, mailFormId);
        return new EmlMailFormVo(mailForm);
    }

    @Transactional("rest_tm")
    public void update(String userId, String mailFormId, EmlMailFormVo mailFormVo) {
        EmlMailForm mailForm = emlMailFormDao.select(userId, mailFormId);
    }

    @Transactional("rest_tm")
    public EmlMailFormVo add(String userId, EmlMailFormVo emlMailFormVo) {
        emlMailFormVo.setUserId(userId);
        EmlMailForm emlMailForm = new EmlMailForm(emlMailFormVo);
        emlMailForm.setId(idProvider.next());
        for (EmlMailFormFrom emlMailFormFrom : emlMailForm.getMailFrom()) {
            emlMailFormFrom.setId(idProvider.next());
        }
        for (EmlMailFormTo emlMailFormTo : emlMailForm.getMailTo()) {
            emlMailFormTo.setId(idProvider.next());
        }
        for (EmlMailFormCc emlMailFormCc : emlMailForm.getMailCc()) {
            emlMailFormCc.setId(idProvider.next());
        }
        for (EmlMailFormBcc emlMailFormBcc : emlMailForm.getMailBcc()) {
            emlMailFormBcc.setId(idProvider.next());
        }
        logger.debug("" + emlMailForm);
        emlMailFormDao.insert(emlMailForm);
        return emlMailFormVo;
    }

    @Transactional("rest_tm")
    public void remove(String userId, String emlMailFormId) {
        EmlMailForm emlMailForm = emlMailFormDao.select(userId, emlMailFormId);
        emlMailFormDao.delete(emlMailForm);
    }
}

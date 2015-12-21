package com.oreumio.james.rest.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Service
public class EmlFileService {

    @Autowired
    EmlFileDao emmFileDao;

    @Transactional(value = "rest_tm")
    public EmlFileVo add(EmlFileVo emlFileVo) {
        EmlFile emlFile = new EmlFile(emlFileVo);
        emmFileDao.insert(emlFile);
        return new EmlFileVo(emlFile);
    }

    @Transactional(value = "rest_tm")
    public void remove(String fileId) {
        EmlFile emlFile = emmFileDao.select(fileId);
        emmFileDao.delete(emlFile);
    }

    public EmlFileVo get(String fileId) {
        EmlFile emlFile = emmFileDao.select(fileId);
        return new EmlFileVo(emlFile);
    }
}

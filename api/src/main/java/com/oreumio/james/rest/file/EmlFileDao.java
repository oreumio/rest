package com.oreumio.james.rest.file;

import com.oreumio.james.rest.group.EmlClient;
import com.oreumio.james.rest.group.EmlGroupConfigVo;
import com.oreumio.james.util.IdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * <pre>
 * 회사 설정 DB 작업
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Repository
public class EmlFileDao {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlFileDao.class);

	@PersistenceContext(unitName = "rest")
	private EntityManager em;

    @Resource(name = "idProvider")
    private IdProvider<String> idProvider;

	/**
	 * @param em EntityManager
	 */
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

    public void setIdProvider(IdProvider<String> idProvider) {
        this.idProvider = idProvider;
    }

	public EmlFile select(String fileId) {
		return em.find(EmlFile.class, fileId);
	}

    public List<EmlFile> list() {
        List<EmlFile> fileList = em.createQuery("SELECT EmlFile FROM EmlFile file", EmlFile.class)
                .getResultList();
        return fileList;
    }

    public String insert(EmlFile emlFile) {
        em.persist(emlFile);
        return emlFile.getFileId();
    }

    public void delete(EmlFile emlFile) {
        em.remove(emlFile);
    }
}

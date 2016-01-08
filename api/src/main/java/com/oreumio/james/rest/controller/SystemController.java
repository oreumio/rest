package com.oreumio.james.rest.controller;

import com.oreumio.james.rest.group.EmlClientDomainVo;
import com.oreumio.james.rest.group.EmlClientService;
import com.oreumio.james.rest.group.EmlClientVo;
import com.oreumio.james.rest.system.EmlSystemVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 시스템을 관리하는 API 를 제공하는 클래스입니다.
 * 주로 시스템 관리자가 사용합니다.
 *
 * 주요 기능으로, 고객 관리(검색, 등록, 해지, 정보 변경, 도메인 등록 및 해지)가 있습니다.
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
public class SystemController {

    private static Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private EmlClientService clientService;

    @RequestMapping(value = "system", method = RequestMethod.GET)
    @ResponseBody
    public EmlSystemVo list(HttpServletRequest request) {
        logger.debug("시스템 정보를 취득합니다.");

        EmlSystemVo systemVo = new EmlSystemVo();
        return systemVo;
    }

    @RequestMapping(value = "system/getQuotaUsage", method = RequestMethod.POST)
    @ResponseBody
    public EmlSystemVo add(HttpServletRequest request) {
        logger.debug("시스템의 쿼타 사용 실적을 취득합니다.");

        long usage = 0;

        List<EmlClientVo> clientVoList = clientService.list();

        for (EmlClientVo clientVo : clientVoList) {
            usage += clientVo.getQuota();
        }

        EmlSystemVo systemVo = new EmlSystemVo();
        systemVo.setQuota(usage);

        return systemVo;
    }
}

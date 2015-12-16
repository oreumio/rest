package com.oreumio.james.rest.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * 회사 설정 화면 URL 처리
 * </pre>
 *
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
@Controller
@RequestMapping("{sitemesh}/emlGroupConfig")
public class EmlGroupConfigController {

	static final Logger LOGGER = LoggerFactory.getLogger(EmlGroupConfigController.class);
}

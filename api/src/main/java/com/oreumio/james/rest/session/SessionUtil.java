package com.oreumio.james.rest.session;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class SessionUtil {
    public static SessionVo getSession(HttpServletRequest request) {
        SessionVo sessionVo = (SessionVo) request.getSession().getAttribute("session");
        if (sessionVo == null) {
            sessionVo = new SessionVo("Uc203ee60015110000001", "G1");
            request.getSession().setAttribute("session", sessionVo);
        }
        return sessionVo;
    }
}

package com.oreumio.james.rest.session;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jhonson choi (jhonsonchoi@gmail.com)
 */
public class SessionUtil {
    public static SessionVo getSession(HttpServletRequest request) {
        SessionVo sessionVo = (SessionVo) request.getSession().getAttribute("session");
        if (sessionVo == null) {
            System.out.println("세션 객체를 만듭니다.");
            sessionVo = new SessionVo("U1", "G1", "C1");
            request.getSession().setAttribute("session", sessionVo);
        }
        return sessionVo;
    }
}

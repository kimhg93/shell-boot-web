package com.boot.shell.Interceptor;


import com.boot.shell.web.vo.common.SessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;

        HttpSession session = request.getSession();
        SessionVo sessionVo = (SessionVo)session.getAttribute("session");

        if(sessionVo == null){
            logger.error("######### session null");
        } else {
            logger.error("######### session value : " + sessionVo.getId());
        }

        return result;
    }

}

package com.boot.shell.Interceptor;


import com.boot.shell.web.vo.common.SessionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public class SessionInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected List<Map<String, String>> excludeSessUrlList;
    protected List<Map<String, String>> excludeAuthUrlList;

    public void setExcludeSessUrlList(List<Map<String, String>> excludeSessUrlList) {
        this.excludeSessUrlList = excludeSessUrlList;
    }
    public void setExcludeAuthUrlList(List<Map<String, String>> excludeAuthUrlList) {
        this.excludeAuthUrlList = excludeAuthUrlList;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = false;

        HttpSession session = request.getSession();

        if(excludeSessUrl(request)) {
            result = true;
        } else {
            SessionVo sessionVo = (SessionVo)session.getAttribute("session");

            if(sessionVo == null){
                logger.error("######### session null");
            } else {
                logger.error("######### session value : " + sessionVo.getId());
            }
        }
        return result;
    }

    private boolean excludeSessUrl(HttpServletRequest request) {
        String url = request.getRequestURI().trim();
        for(Map<String, String> map : excludeSessUrlList){
            if(url.indexOf(map.get("excludeUrl")) > -1 ){
                return true;
            }
        }
        return false;
    }


}

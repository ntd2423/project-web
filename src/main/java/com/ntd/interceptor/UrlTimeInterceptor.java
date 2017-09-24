package com.ntd.interceptor;

import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by nongtiedan on 2016/8/3.
 */
public class UrlTimeInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(UrlTimeInterceptor.class);

    private int openingTime;
    private int closingTime;
    private String mappingURL;  //利用正则映射到需要拦截的路径
    public void setOpeningTime(int openingTime) {
        this.openingTime = openingTime;
    }
    public void setClosingTime(int closingTime) {
        this.closingTime = closingTime;
    }
    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //logger.debug("Before handling the request, handler=" + handler);
        String url = request.getRequestURL().toString();
        if(StringUtils.isBlank(mappingURL) || url.matches(mappingURL)){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            int now = c.get(Calendar.HOUR_OF_DAY);
            if(now < openingTime || now > closingTime) {    //操作只在9：00-12：00开放
                logger.info("UrlTimeInterceptor：跳转到userWarn页面！");
                request.getRequestDispatcher("/view/userWarn.jsp").forward(request, response);
                return false;
            }
        }

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        //logger.debug("After handling the request,handler=" + handler);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String currentPath = request.getRequestURI();
        //logger.debug("After rendering the view,handler=" + handler + ", url=" + currentPath);
    }

    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    }

}

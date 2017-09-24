package com.ntd.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by nongtiedan on 2016/8/3.
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

    private static final Log logger = LogFactory.getLog(UserInterceptor.class);

    /**
     * 这个方法在业务处理器处理请求之前被调用，SpringMVC中的Interceptor是链式的调用的，在一个请求中可以同时存在多个Interceptor。
     * 每个Interceptor的调用会依据它的声明顺序依次执行，而且最先执行的都是Interceptor中的preHandle方法，就是预处理，可以进行编码、安全控制等操作。
     * 当它返回为false时，请求结束，后续的Interceptor和Controller都不会再执行；当返回值为true时就会继续调用下一个Interceptor的preHandle方法，如果已经是最后一个Interceptor则会调用当前请求的Controller方法。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //logger.debug("Before handling the request, handler=" + handler);

        return true;
    }

    /**
     * 这个方法在当前请求进行处理之后，也就是Controller方法调用之后执行，但是它会在DispatcherServlet进行视图返回渲染之前被调用，所以我们可以在这个方法中对Controller处理之后的ModelAndView对象进行操作。
     * postHandle方法被调用的方向跟preHandle是相反的，也就是说先声明的Interceptor的postHandle方法反而会后执行。
     */
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {

        //logger.debug("After handling the request,handler=" + handler);
    }

    /**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求结束之后，也就是在DispatcherServlet渲染了对应的视图之后执行。
     * 这个方法的主要作用是用于进行资源清理工作的。
     */
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

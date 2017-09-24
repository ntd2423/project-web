package com.ntd.exception;

import com.ntd.common.Const;
import com.ntd.utils.JSONResponseBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class WebExceptionResolver extends AbstractHandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("Servlet Exception URI : {} {}", request.getRequestURI(), ex.getCause());
		/*if (ex instanceof ServiceException) {
			ServiceException e = (ServiceException)ex;
			JSONResponseBuilder.buildErrorResp(response, e.getCode(), e.getMessage(), e.getExtInfo());
			logger.info("code: {} message: ", e.getCode(), e.getMessage());
			//不能返回null，否则会执行其他HandlerExceptionResolver
			return new ModelAndView();
		} else {
			logger.error("path: {} handler: {}", request.getPathInfo(), handler == null ? "" : handler.getClass());
			logger.error(ex.getMessage(), ex);
			JSONResponseBuilder.buildErrorResp(response, ErrorCode.SERVER_ERROR.intValue(), "服务器内部错误");
		}*/

		if (request.getRequestURI().startsWith(Const.API_PREFIX)) {
			ServiceException e = (ServiceException)ex;
			JSONResponseBuilder.buildErrorResp(response, e.getCode(), e.getMessage(), e.getExtInfo());
			logger.info("code: {} message: ", e.getCode(), e.getMessage());
			//不能返回null，否则会执行其他HandlerExceptionResolver
			return new ModelAndView();
		} else {
			logger.error(ex.getMessage(), ex);
			return new ModelAndView("forward:/error-502.do");
		}

	}

}

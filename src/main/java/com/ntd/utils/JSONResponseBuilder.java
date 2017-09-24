package com.ntd.utils;

import com.ntd.common.Const;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONResponseBuilder {

	private static Logger logger = LoggerFactory.getLogger(JSONResponseBuilder.class);

	public static <T> String buildResp(HttpServletResponse response, T result) {
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader( "Cache-Control", "max-age=2592000" );
		try {
			PrintWriter writer = response.getWriter();
			JSONUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static <T> void buildRespVar(HttpServletResponse response, T result, String var){
		if (StringUtils.isNotBlank(var)) {
			response.setContentType("text/javascript;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				writer.append("var ").append(var).append("=").append(JSONUtil.toJson(result)).append(";");
				writer.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			response.setContentType("application/json;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				JSONUtil.writeJson(result, writer);
				writer.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public static <T> String buildResp(HttpServletResponse response, T result, String callback) {
		if (StringUtils.isNotBlank(callback)) {
			response.setContentType("text/javascript;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				writer.append(callback).append('(')
						.append(JSONUtil.toJson(result)).append(");");
				writer.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			response.setContentType("application/json;charset=UTF-8");
			try {
				PrintWriter writer = response.getWriter();
				JSONUtil.writeJson(result, writer);
				writer.flush();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public static String buildSuccResp(HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", Const.ErrorCode.SUCCEED);
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			JSONUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String buildErrorResp(HttpServletResponse response,
										Integer errorCode, String errorMessage) {
		return buildErrorResp(response, errorCode, errorMessage, null);
	}

	public static String buildErrorResp(HttpServletResponse response,
										Integer errorCode, String errorMessage,Map<String,Object> extInfo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", errorCode);
		result.put("error", errorMessage);
		if(extInfo!=null){
			result.putAll(extInfo);
		}
		response.setContentType("application/json;charset=UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			JSONUtil.writeJson(result, writer);
			writer.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}

/**
 * @(#)ServletUtils.java, 2010-3-22. Copyright 2010 Netease, Inc. All rights
 *                        reserved. NETEASE PROPRIETARY/CONFIDENTIAL. Use is
 *                        subject to license terms.
 */
package com.ntd.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * Servlet工具类
 */
public class ServletUtils {
	
	private static final Log logger = LogFactory.getLog(ServletUtils.class);

    public static final String CONTENT_TYPE_JSON = "application/json;charset=utf-8";
    
    public static final String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
    
    public static final String CONTENT_TYPE_JAVASCRIPT = "application/x-javascript;charset=utf-8";
    
    public static final String HTML_CONTENT = "<html><script type=\"text/javascript\">window.parent.callback(%s)</script></html>";

    /**
     * Get an int parameter, with a fallback value. Never throws an exception.
     * Can pass a distinguished value as default to enable checks of whether it
     * was supplied.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter
     * @param defaultVal
     *            the default value to use as fallback
     */
    public static int getInt(ServletRequest request, String name,
							 int defaultVal) {
        return ServletRequestUtils.getIntParameter(request, name, defaultVal);
    }

    /**
     * Get an array of int parameters, return an empty array if not found.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter with multiple possible values
     */
    public static int[] getInts(ServletRequest request, String name) {
        return ServletRequestUtils.getIntParameters(request, name);
    }

    /**
     * Get a long parameter, with a fallback value. Never throws an exception.
     * Can pass a distinguished value as default to enable checks of whether it
     * was supplied.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter
     * @param defaultVal
     *            the default value to use as fallback
     */
    public static long getLong(ServletRequest request, String name,
							   long defaultVal) {
        return ServletRequestUtils.getLongParameter(request, name, defaultVal);
    }

    /**
     * Get an array of long parameters, return an empty array if not found.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter with multiple possible values
     */
    public static long[] getLongs(ServletRequest request, String name) {
        return ServletRequestUtils.getLongParameters(request, name);
    }


    /**
     * Get a boolean parameter, with a fallback value. Never throws an
     * exception. Can pass a distinguished value as default to enable checks of
     * whether it was supplied.
     * <p>
     * Accepts "true", "on", "yes" (any case) and "1" as values for true; treats
     * every other non-empty value as false (i.e. parses leniently).
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter
     * @param defaultVal
     *            the default value to use as fallback
     */
    public static boolean getBool(ServletRequest request,
            String name, boolean defaultVal) {
        return ServletRequestUtils.getBooleanParameter(request, name, defaultVal);
    }

    /**
     * Get an array of boolean parameters, return an empty array if not found.
     * <p>
     * Accepts "true", "on", "yes" (any case) and "1" as values for true; treats
     * every other non-empty value as false (i.e. parses leniently).
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter with multiple possible values
     */
    public static boolean[] getBooleans(ServletRequest request,
            String name) {
        return ServletRequestUtils.getBooleanParameters(request, name);
    }

    /**
     * Get a String parameter, with a fallback value. Never throws an exception.
     * Can pass a distinguished value to default to enable checks of whether it
     * was supplied.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter
     * @param defaultVal
     *            the default value to use as fallback
     */
    public static String getStr(ServletRequest request,
            String name, String defaultVal) {
        return ServletRequestUtils.getStringParameter(request, name, defaultVal);
    }

    /**
     * Get an array of String parameters, return an empty array if not found.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter with multiple possible values
     */
    public static String[] getStrs(ServletRequest request,
            String name) {
        return ServletRequestUtils.getStringParameters(request, name);
    }
    
    
    
    /**
     * Get a long parameter, with a fallback value. Never throws an exception.
     * Can pass a distinguished value as default to enable checks of whether it
     * was supplied.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter
     * @param defaultVal
     *            the default value to use as fallback
     */
    public static double getDouble(ServletRequest request, String name,
								   long defaultVal) {
        return ServletRequestUtils.getDoubleParameter(request, name, defaultVal);
    }

    /**
     * Get an array of long parameters, return an empty array if not found.
     * 
     * @param request
     *            current HTTP request
     * @param name
     *            the name of the parameter with multiple possible values
     */
    public static double[] getDoubles(ServletRequest request, String name) {
        return ServletRequestUtils.getDoubleParameters(request, name);
    }
    
    public static void processReturn(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map, Object model, boolean result , String reason) {
    	if(result){
    		if(model ==null){
    			map.put("result", result);
    		}else{
    			map.put("result", model);
    		}
    		
    		map.put("reason", "");
    	}else{
    		map.put("result", result);
    		map.put("reason", reason);
    	}
    	processReturn(request, response, map);
	}
    
    public static void processReturn(HttpServletRequest request, HttpServletResponse response, Map model) {
		PrintWriter out = null;
		String callback = ServletRequestUtils.getStringParameter(request, "callback", "");
    	try {
	    	String contentType = CONTENT_TYPE_JSON;
			String resStr = VshowSerializer.json.writeValueAsString(model);
			if(StringUtils.isNotBlank(callback)) {
				contentType = CONTENT_TYPE_JAVASCRIPT;
				resStr = callback + "(" + resStr + ")";
			}
    		response.setContentType(contentType);
    		out = response.getWriter();
    		out.write(resStr);
    	} catch (IOException e) {
    		logger.error(e.getMessage());
    	} finally {
    		if(out!=null) {
    			out.close();
    		}
    	}
	}
    
    public static void processHtmlReturn(HttpServletRequest request, HttpServletResponse response, Map<String,Object> map, Object model, boolean result , String reason) {
    	if(result){
    		if(model ==null){
    			map.put("result", result);
    		}else{
    			map.put("result", model);
    		}
    		
    		map.put("reason", "");
    	}else{
    		map.put("result", result);
    		map.put("reason", reason);
    	}
    	processHtmlReturn(request, response, map);
	}
    
    public static void processHtmlReturn(HttpServletRequest request, HttpServletResponse response, Map model) {
		PrintWriter out = null;
		String callback = ServletRequestUtils.getStringParameter(request, "callback", "");
    	try {
	    	String contentType = CONTENT_TYPE_HTML;
			String resStr = VshowSerializer.json.writeValueAsString(model);
			if(StringUtils.isNotBlank(callback)) {
				resStr = callback + "(" + resStr + ")";
			}
    		response.setContentType(contentType);
    		resStr = String.format(HTML_CONTENT, resStr);
    		out = response.getWriter();
    		out.write(resStr);
    	} catch (IOException e) {
    		logger.error(e.getMessage());
    	} finally {
    		if(out!=null) {
    			out.close();
    		}
    	}    	
	}
}

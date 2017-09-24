package com.ntd.exception;

import com.ntd.common.Const.ErrorCode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class ServiceExceptionFactory extends Exception {

	private static Map<Integer,String> allErrors;

	static{

		Map<Integer,String> map=new HashMap<Integer,String>();
		map.put(ErrorCode.BAD_REQUEST.intValue(), "错误请求");
		map.put(ErrorCode.UNAUTHORIZED.intValue(), "未授权");
		map.put(ErrorCode.FORBIDDEN.intValue(), "操作不允许");
		map.put(ErrorCode.NOT_FOUND.intValue(), "未匹配");
		map.put(ErrorCode.SERVER_ERROR.intValue(), "服务器内部错误");

		allErrors = Collections.unmodifiableMap(map);
	}
	public static ServiceException build(int code, String ... params){
		return build(code, null, params);
	}

	public static ServiceException build(int code, Map<String,Object> extInfo, String ... params){
		String msg = allErrors.get(code);
		int count = StringUtils.countMatches(msg, "%s");
		Object[] values = new Object[count];
		for(int i=0; i<count; i++){
			if(i < params.length){
				values[i] = params[i];
			}else{
				values[i] = "";
			}
		}
		return new ServiceException(code, count == 0 ? msg : String.format(msg, values), extInfo);
	}
}

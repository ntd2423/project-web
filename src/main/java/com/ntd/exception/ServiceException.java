package com.ntd.exception;

import java.util.Map;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7552140848628743914L;

	private int code;
	private Map<String,Object> extInfo;

	public ServiceException(int code, String msg){
		super(msg);
		this.code=code;
	}

	public ServiceException(int code, String msg, Map<String,Object> extInfo){
		super(msg);
		this.code=code;
		this.extInfo=extInfo;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode(){
		return this.code;
	}

	public void setExtInfo(Map<String,Object> extInfo) {
		this.extInfo = extInfo;
	}

	public Map<String,Object> getExtInfo() {
		return extInfo;
	}

}

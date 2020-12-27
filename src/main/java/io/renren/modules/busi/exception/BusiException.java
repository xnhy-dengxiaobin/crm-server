package io.renren.modules.busi.exception;

import java.util.Map;

/**
 * @author Administrator
 * 自定义业务异常，用于自定义业务步骤中抛出自己定义的异常
 */
public class BusiException extends RuntimeException {
	private static final long serialVersionUID = -5061204523949949742L;
	private Map<String, Object> data;
	
	public BusiException(String message) {
		super(message);
	}
	public BusiException(String message, Map<String, Object> data, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.data = data;
	}
	public BusiException(String message, Map<String, Object> data, Throwable cause) {
		super(message, cause);
		this.data = data;
	}
	public BusiException(String message, Map<String, Object> data) {
		super(message);
		this.data = data;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}

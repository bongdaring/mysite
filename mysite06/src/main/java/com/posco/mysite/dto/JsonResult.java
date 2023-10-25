package com.posco.mysite.dto;


public class JsonResult {
	private String result; 		// "success" or "fail"
	private Object data;   		// if success, set!
	private String message;		// if failm set!
	
	private JsonResult() {	
	}
	
	public String getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	private JsonResult(Object data) {
		this.result = "success";
		this.data = data;
	}
	
	public static JsonResult success(Object data) {
		return new JsonResult(data);
	}	
	
	public static JsonResult fail(String message) {
		return new JsonResult(message);
	}	
}

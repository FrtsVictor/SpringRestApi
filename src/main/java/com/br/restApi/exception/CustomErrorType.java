package com.br.restApi.exception;

public class CustomErrorType extends Exception {

	private static final long serialVersionUID = 1L;
	String msg;
		

	public CustomErrorType(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}

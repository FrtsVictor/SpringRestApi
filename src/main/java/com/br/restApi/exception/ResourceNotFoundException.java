package com.br.restApi.exception;


public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 4097220015877199455L;
	String msg;

	public ResourceNotFoundException(String msg) {
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

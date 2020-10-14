package com.br.restApi.exception;


public class PropertyExceptions extends Exception {
	private static final long serialVersionUID = 6210744384457515378L;

	String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public PropertyExceptions(String msg) {
		super();
		this.msg = msg;
	}
	



}

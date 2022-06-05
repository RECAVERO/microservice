package com.spring.demo.dto;

public class RequestDto {
	private String uri;
	private String method;
	public RequestDto() {
		// TODO Auto-generated constructor stub
	}
	
	public RequestDto(String uri, String method) {
		this.uri = uri;
		this.method = method;
	}

	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	
}

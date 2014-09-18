package com.freeneo.survey.domain;
/**
 * 객체명이 SVLog인 이유는 Log 라이브러리들과 이름 충돌을 피하기 위해서지 특별한 의미는 없다.
 * @author freeneo
 */
public class SVLog {
	private String id;
	private String username;
	private String name;
	private String datetime;
	private String ip;
	private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}

package com.freeneo.survey.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Mms {
	
	private String msgkey;
	private String subject;
	private String phone;
	private String callback;
	private String status;
	private String reqdate;
	private String msg;
	private String type;
	
	public String getMsgkey() {
		return msgkey;
	}

	public void setMsgkey(String msgkey) {
		this.msgkey = msgkey;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReqdate() {
		return reqdate;
	}

	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		return Pojomatic.equals(this, o);
	}
	
	@Override
	public int hashCode() {
		return Pojomatic.hashCode(this);
	}
	
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}

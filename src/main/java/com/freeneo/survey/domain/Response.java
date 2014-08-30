package com.freeneo.survey.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Response {

	private Long id;
	private Long responseItemId;
	private String respondent;
	private String response;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResponseItemId() {
		return responseItemId;
	}

	public void setResponseItemId(Long responseItemId) {
		this.responseItemId = responseItemId;
	}

	public String getRespondent() {
		return respondent;
	}

	public void setRespondent(String respondent) {
		this.respondent = respondent;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
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

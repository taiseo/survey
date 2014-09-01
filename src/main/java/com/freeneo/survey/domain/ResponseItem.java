package com.freeneo.survey.domain;

import java.util.HashMap;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.gson.Gson;

@AutoProperty
public class ResponseItem {

	private Long id;
	private Long questionId;
	private String content;
	private int orderNo;
	private String datetime;
	private int min;
	private int max;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	public void setMinMax(){
		Gson gson = new Gson();
		@SuppressWarnings("unchecked")
		HashMap<String, String> minMax = gson.fromJson(this.content, HashMap.class);
		this.min = Integer.parseInt(minMax.get("min"));
		this.max = Integer.parseInt(minMax.get("max"));
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

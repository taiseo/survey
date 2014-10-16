package com.freeneo.survey.domain;

import java.util.HashMap;
import java.util.Map;

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
	private Long responseItemCount;
	
	/**
	 * 선호도 타입 응답 결과를 표시할 때 사용하는 필드.
	 * 순위 : 명수
	 * 1: 10
	 * 2: 5
	 * 3: 25
	 * 4: 11
	 * 5: 33
	 * key값은 그냥 숫자인데 처리하기 편하게 하려고 String 타입을 사용한 것뿐이다.
	 */
	private Map<String, Integer> preference;
	
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

	public Long getResponseItemCount() {
		return responseItemCount;
	}

	public void setResponseItemCount(Long responseItemCount) {
		this.responseItemCount = responseItemCount;
	}

	public Map<String, Integer> getPreference() {
		return preference;
	}

	public void setPreference(Map<String, Integer> preference) {
		this.preference = preference;
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

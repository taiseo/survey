package com.freeneo.survey.domain;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Question {

	private Long id;
	private Long surveyId;
	private String content;
	private String contentDetail;
	private String type;
	private int orderNo;
	private String datetime;
	private List<ResponseItem> responseItems;
	private Long questionRespondentCount;
	private String[] responses;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentDetail() {
		return contentDetail;
	}

	public void setContentDetail(String contentDetail) {
		this.contentDetail = contentDetail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public List<ResponseItem> getResponseItems() {
		return responseItems;
	}

	public void setResponseItems(List<ResponseItem> responseItems) {
		this.responseItems = responseItems;
	}

	public Long getQuestionRespondentCount() {
		return questionRespondentCount;
	}

	public void setQuestionRespondentCount(Long questionRespondentCount) {
		this.questionRespondentCount = questionRespondentCount;
	}

	public String[] getResponses() {
		return responses;
	}

	public void setResponses(String[] responses) {
		this.responses = responses;
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

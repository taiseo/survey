package com.freeneo.survey.domain;

import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Survey {

	private Long id;
	private String title;
	private String msgSubject;
	private String msg;
	private String description;
	private String startDate;
	private String endDate;
	private String targetCategory1;
	private String targetCategory2;
	private String targetBranches;
	private String writer;
	private String part;
	private String status;
	private String datetime;
	private List<Question> questions;
	private Long respondentCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgSubject() {
		return msgSubject;
	}

	public void setMsgSubject(String msgSubject) {
		this.msgSubject = msgSubject;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTargetCategory1() {
		return targetCategory1;
	}

	public void setTargetCategory1(String targetCategory1) {
		this.targetCategory1 = targetCategory1;
	}

	public String getTargetCategory2() {
		return targetCategory2;
	}

	public void setTargetCategory2(String targetCategory2) {
		this.targetCategory2 = targetCategory2;
	}

	public String getTargetBranches() {
		return targetBranches;
	}

	public void setTargetBranches(String targetBranches) {
		this.targetBranches = targetBranches;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Long getRespondentCount() {
		return respondentCount;
	}

	public void setRespondentCount(Long respondentCount) {
		this.respondentCount = respondentCount;
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

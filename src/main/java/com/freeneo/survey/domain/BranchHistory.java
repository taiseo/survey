package com.freeneo.survey.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class BranchHistory {
	
	private String branchName;
	private int sendCount;
	private int respondentCount;
	private double responseRatio;
	private int surveyCount;
	
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int getSendCount() {
		return sendCount;
	}
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}
	public int getRespondentCount() {
		return respondentCount;
	}
	public void setRespondentCount(int respondentCount) {
		this.respondentCount = respondentCount;
	}

	public double getResponseRatio() {
		return responseRatio;
	}
	public void setResponseRatio(double responseRatio) {
		this.responseRatio = responseRatio;
	}
	
	public int getSurveyCount() {
		return surveyCount;
	}
	public void setSurveyCount(int surveyCount) {
		this.surveyCount = surveyCount;
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

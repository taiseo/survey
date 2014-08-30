package com.freeneo.survey.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Survey {

	private Long id;
	private String title;
	private String description;
	private String startDate;
	private String endDate;
	private String target;
	private String writer;
	private String part;

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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
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

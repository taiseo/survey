package com.freeneo.survey.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class ConfigItem {
	
	private String keyName;
	private String value;

	
	public ConfigItem(){}
	
	public ConfigItem(String keyName, String value) {
		super();
		this.keyName = keyName;
		this.value = value;
	}
	public String getKeyname() {
		return keyName;
	}
	public void setKeyname(String keyName) {
		this.keyName = keyName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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

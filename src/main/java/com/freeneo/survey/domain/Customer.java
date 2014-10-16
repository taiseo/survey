package com.freeneo.survey.domain;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

@AutoProperty
public class Customer {
	private String cstNo;
	private String cstNm;
	private String hp;
	private String ptcpTit;
	private String ptcpDttm;
	private String etc01;
	
	public String getCstNo() {
		return cstNo;
	}
	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}
	public String getCstNm() {
		return cstNm;
	}
	public void setCstNm(String cstNm) {
		this.cstNm = cstNm;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	
	public String getPtcpTit() {
		return ptcpTit;
	}
	public void setPtcpTit(String ptcpTit) {
		this.ptcpTit = ptcpTit;
	}
	public String getPtcpDttm() {
		return ptcpDttm;
	}
	public void setPtcpDttm(String ptcpDttm) {
		this.ptcpDttm = ptcpDttm;
	}
	public String getEtc01() {
		return etc01;
	}
	public void setEtc01(String etc01) {
		this.etc01 = etc01;
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

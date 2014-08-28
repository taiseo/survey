package com.freeneo.survey.domain;

import org.apache.commons.codec.digest.DigestUtils;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AutoProperty
public class User {
	private Long id;
	private String username;
	private String password;
	private String name;
	private String part;
	private String tel;
	private String email;
	private String userLevel;
	
	static Logger logger = LoggerFactory.getLogger(User.class);

	public User() {
	};

	public User(Long id, String name, String username, String password, String part,
			String tel, String email, String userLevel) {
		this.id = id;
		this.name = name;
		this.username = username;
		setPassword(password);
		this.part = part;
		this.tel = tel;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		// !@#$asdf 는 salt.
		this.password = DigestUtils.sha1Hex(password + "!@#$asdf");
	}

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
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

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
	private String hp;
	private String email;
	private String userLevel;
	
	static Logger logger = LoggerFactory.getLogger(User.class);

	public User() {
	};

	public User(Long id, String username, String password, String name, String part,
			String tel, String hp, String email, String userLevel) {
		this.id = id;
		this.name = name;
		this.username = username;
		if( ! password.equals("")){
			setPassword(password);
		}
		this.part = part;
		this.tel = tel;
		this.hp = hp;
		this.email = email;
		this.userLevel = userLevel;
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
		this.password = password;
	}

	/**
	 * 평문으로 들어있는 암호를 해시값으로 바꾼다.
	 * 빈 값이 들어있는 경우엔 해시하지 않는다.
	 */
	public void setPasswordToHash() {
		if(this.password != null){
			// !@#$asdf 는 salt.
			this.password = DigestUtils.sha1Hex(this.password + "!@#$asdf");
		}
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
		
	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		User.logger = logger;
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

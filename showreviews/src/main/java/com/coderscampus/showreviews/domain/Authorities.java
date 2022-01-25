package com.coderscampus.showreviews.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Authorities implements GrantedAuthority{
	private static final long serialVersionUID = 4261507111361366025L;
	private Long id;
	private String authority;
	private User user;
	
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}
	
	public void setAuthority(String authority) {
		// TODO Auto-generated method stub
		 this.authority = authority;
	}
	
	
}

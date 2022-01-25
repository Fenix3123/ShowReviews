package com.coderscampus.showreviews.security;

import org.springframework.security.core.userdetails.UserDetails;

import com.coderscampus.showreviews.domain.User;

public class CustomSecurityUser extends User implements UserDetails{
	private static final long serialVersionUID = -8184653630362457755L;
	
	public CustomSecurityUser() {
		
	}

	public CustomSecurityUser(User user) {
		this.setAuthorities(user.getAuthorities());
		this.setId(user.getId());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}

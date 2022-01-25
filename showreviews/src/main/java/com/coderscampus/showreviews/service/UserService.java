package com.coderscampus.showreviews.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.showreviews.domain.User;
import com.coderscampus.showreviews.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	
	public User saveUser(User user) {
		return userRepo.save(user);
	}

	public User findById(Long id) {
		// TODO Auto-generated method stub
		Optional<User> userOpt = userRepo.findById(id);
		return userOpt.orElse(new User());
	}
	
}

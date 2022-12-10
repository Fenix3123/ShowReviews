package com.coderscampus.showreviews.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.showreviews.domain.Movies;
import com.coderscampus.showreviews.domain.User;
import com.coderscampus.showreviews.service.UserService;

@Controller
public class LoginController {
	@Autowired
	UserService userService;

	@GetMapping("/login")
	public String getLoginPage() {
		return "login";
	}
	
	@GetMapping("/")
	public String getLoginPage2() {
		return "login";
	}
	
	@GetMapping("/details")
	public String changeDetails(ModelMap model) {
		model.put("user", new User());
		return "details";
	}
	
	@PostMapping("/details")
	public String postChangeDetails(@AuthenticationPrincipal User user, User difUser, String newpass) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUsername(difUser.getUsername());
		user.setPassword(passwordEncoder.encode(difUser.getPassword()));
		userService.saveUser(user);
		
		if(!difUser.getPassword().equals(newpass)) {
			return "details";
		}
		return "login";
	}
}

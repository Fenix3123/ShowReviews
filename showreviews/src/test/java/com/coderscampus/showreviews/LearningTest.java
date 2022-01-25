package com.coderscampus.showreviews;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LearningTest {
	
	@Test
	public void encriptpassword() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String password = passwordEncoder.encode("Fenixxia1");
		System.out.println(password);
	}
}

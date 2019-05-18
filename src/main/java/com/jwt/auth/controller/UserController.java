package com.jwt.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.jwt.auth.domain.UserBean;
import com.jwt.auth.exception.UserAlreadyExistsException;
import com.jwt.auth.exception.UserNotFoundException;
import com.jwt.auth.service.UserService;
import com.jwt.auth.token.generator.SecurityTokenGenerator;

@CrossOrigin
@RestController
@RequestMapping("/user")
@EnableWebMvc
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityTokenGenerator tokenGenerator;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserBean bean) throws UserAlreadyExistsException{
		try {
			bean.setPassword(new BCryptPasswordEncoder().encode(bean.getPassword()));
			userService.saveUser(bean);
			return new ResponseEntity<String>("User registered Successfully", HttpStatus.CREATED);
		} catch(Exception e) {
			return new ResponseEntity<String>("message : "+ e, HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserBean userDetail) throws UserNotFoundException {
		try {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
			String userId = userDetail.getUserId();
			String password = userDetail.getPassword();
			
			if(userId == null || password == null) {
				throw new Exception ("Username or password cannot be empty!!");
			}
			
			UserBean  user = userService.findByUserId(userId);
			if(user == null) {
				throw new Exception("User id does not exists");
			}
			
			if(!(encoder.matches(password, user.getPassword()))) {
				throw new Exception("Invalid Credentials. Please try with correct username and password");
			}
			
			Map<String, String> map = tokenGenerator.generateToken(user);
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<String>("message : " + e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
}

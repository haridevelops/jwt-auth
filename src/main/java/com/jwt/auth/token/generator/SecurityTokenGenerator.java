package com.jwt.auth.token.generator;

import java.util.Map;

import com.jwt.auth.domain.UserBean;

public interface SecurityTokenGenerator {
	
	Map<String, String> generateToken(UserBean user);
}

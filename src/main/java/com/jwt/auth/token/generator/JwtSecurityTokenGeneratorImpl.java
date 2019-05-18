package com.jwt.auth.token.generator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jwt.auth.domain.UserBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator {

	private static final String Jwt_Secret = "secretkey";
	
	@Override
	public Map<String, String> generateToken(UserBean user) {
		HashMap<String, String> map = new HashMap<>();
		map.put("token",
				Jwts.builder()
					.setSubject(user.getUserId())
					.setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, Jwt_Secret.getBytes())
					.setExpiration(new Date((new Date()).getTime() + 3600))
					.compact());
		map.put("message", "User Log in Successful");
		
		return map;
	}

}

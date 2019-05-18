package com.jwt.auth.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.auth.domain.UserBean;

public interface UserRepository extends JpaRepository<UserBean, String> {

	UserBean findByUserIdAndPassword(String userId, String Password);
	
	UserBean findByUserId(String userId);
}

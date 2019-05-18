package com.jwt.auth.service;

import com.jwt.auth.domain.UserBean;
import com.jwt.auth.exception.UserAlreadyExistsException;
import com.jwt.auth.exception.UserNotFoundException;

public interface UserService {

	//to register the user
	boolean saveUser(UserBean user) throws UserAlreadyExistsException, UserNotFoundException;
	
	//to check before signin
	public UserBean findByUserIdAndPassword(String userId, String password) throws UserAlreadyExistsException, UserNotFoundException;
	
	public UserBean findByUserId(String userId) throws UserNotFoundException;
}

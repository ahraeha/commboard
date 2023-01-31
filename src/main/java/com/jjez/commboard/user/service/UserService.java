package com.jjez.commboard.user.service;

import com.jjez.commboard.user.model.UserInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  boolean register(UserInput parameter);

}

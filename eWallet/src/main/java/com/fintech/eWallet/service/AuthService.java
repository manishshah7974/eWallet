package com.fintech.eWallet.service;

import com.fintech.eWallet.dto.LoginCredentials;
import com.fintech.eWallet.dto.TokenResponse;
import com.fintech.eWallet.model.User;
import com.fintech.eWallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface AuthService {


     public User registerUser(User user) throws Exception;

     public TokenResponse login(LoginCredentials loginCredentials) throws Exception;
}

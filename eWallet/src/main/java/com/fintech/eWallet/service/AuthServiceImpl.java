package com.fintech.eWallet.service;

import com.fintech.eWallet.dto.LoginCredentials;
import com.fintech.eWallet.dto.TokenResponse;
import com.fintech.eWallet.model.User;
import com.fintech.eWallet.repository.UserRepository;
import com.fintech.eWallet.utils.Crypto.EncryptionUtil;
import com.fintech.eWallet.utils.ExceptionHandler.customExceptions.InvalidPasswordException;
import com.fintech.eWallet.utils.ExceptionHandler.customExceptions.UserNotFoundException;
import com.fintech.eWallet.utils.JwtTokenUtility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    public User registerUser(User user) throws Exception {
        User encrypUser = User.builder()
                .id(user.getId())
                .name(user.getName())
                .mobile(user.getMobile())
                .email(user.getEmail())
                .password(encryptionUtil.encrypt(user.getPassword()))
                .build();
        System.out.println("User saved "+encrypUser.toString());
        return userRepository.save(encrypUser);
    }

    @Override
    public TokenResponse login(LoginCredentials loginCredentials) throws Exception {
        String userEmail = loginCredentials.getEmail();
        User currentUser = userService.findUserByEmail(userEmail);
        if(currentUser==null)
            throw new UserNotFoundException("User Not Found");

        if(Objects.equals(loginCredentials.getPassword(), encryptionUtil.decrypt(currentUser.getPassword())))
        {
            String accessToken = jwtTokenUtil.generateAccessToken(userEmail);
            String refreshToken = jwtTokenUtil.generateRefreshToken(userEmail);
            return new TokenResponse(accessToken,refreshToken);
        }
        else
        {
            throw new InvalidPasswordException("Invalid Password");
        }
    }
}

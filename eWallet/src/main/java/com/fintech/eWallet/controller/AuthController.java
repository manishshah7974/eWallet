package com.fintech.eWallet.controller;

import com.fintech.eWallet.dto.LoginCredentials;
import com.fintech.eWallet.dto.TokenResponse;
import com.fintech.eWallet.model.User;
import com.fintech.eWallet.service.AuthService;
import com.fintech.eWallet.service.UserService;
import com.fintech.eWallet.utils.Crypto.EncryptionUtil;
import com.fintech.eWallet.utils.ExceptionHandler.customExceptions.UserAlreadyExistsException;
import com.fintech.eWallet.utils.JwtTokenUtility.JwtTokenUtil;
import com.fintech.eWallet.utils.JwtTokenUtility.TokenBlacklistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user) throws Exception {
        if (userService.checkIfUserAlreadyExists(user.getEmail())) {
            throw new UserAlreadyExistsException("User Already Exists for this Email Id");
        }
        User updatedUser = authService.registerUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginCredentials loginCredentials) throws Exception {
        TokenResponse tokens = authService.login(loginCredentials);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenResponse tokenResponse) {
        String refreshToken = tokenResponse.getRefreshToken();
        if (jwtTokenUtil.validateToken(refreshToken) && !jwtTokenUtil.isTokenExpired(refreshToken) && !tokenBlacklistService.isTokenBlacklisted(refreshToken)) {
            String email = jwtTokenUtil.getEmailFromToken(refreshToken);
            String newAccessToken = jwtTokenUtil.generateAccessToken(email);
            return ResponseEntity.ok(new TokenResponse(newAccessToken, refreshToken));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenResponse tokenResponse) {
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();

        // Invalidate the access token and refresh token
        tokenBlacklistService.blacklistToken(accessToken);
        tokenBlacklistService.blacklistToken(refreshToken);

        return ResponseEntity.ok("Logged out successfully");
    }
}

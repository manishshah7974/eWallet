package com.fintech.eWallet.aop;

import com.fintech.eWallet.utils.JwtTokenUtility.JwtTokenUtil;
import com.fintech.eWallet.utils.JwtTokenUtility.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
public class JwtAuthenticationAspect {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    //Need to make authentication for services which need to be made secured
    @Around("execution(* com.fintech.eWallet.controller.UserController.*(..))")
    public Object checkJwtToken(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Request attributes not found");
        }
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }
        System.out.println(header);
        String token = header.substring(7);
        if (!jwtTokenUtil.validateToken(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        if (tokenBlacklistService.isTokenBlacklisted(header)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please Login Again");
        }

        return joinPoint.proceed();
    }
}

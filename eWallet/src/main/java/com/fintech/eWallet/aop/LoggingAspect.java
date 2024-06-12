package com.fintech.eWallet.aop;
import com.fintech.eWallet.utils.Logger.LoggerSingleton;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    @Before("execution(* com.fintech.eWallet.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering API: /" + joinPoint.getSignature().getName() + " with arguments: " + Arrays.toString(joinPoint.getArgs()));}

    @AfterReturning(pointcut = "execution(* com.fintech.eWallet.controller.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("API: /" + joinPoint.getSignature().getName() + " executed successfully with result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.fintech.eWallet.controller.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logger.error("API: /" + joinPoint.getSignature().getName() + " threw an exception: " + error);
    }
}

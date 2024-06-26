package com.movies.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
	public static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

	@AfterThrowing(pointcut = "execution(* com.movies.service.MovieService.*(..))", throwing = "exception")
	public void logServiceException(JoinPoint joinPoint,Exception exception) {
		String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        String lineInfo = "Exception thrown in method: " + className + "." + methodName;
		LOGGER.error(lineInfo, exception);
	}
	
	@Before("execution(* com.movies.service.*.*(..))")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Entering method: " + methodName);
    }

    @After("execution(* com.movies.service.*.*(..)))")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Exiting method: " + methodName);
    }
    
    @Before("execution(* com.movies.controller.*.*(..))")
    public void logBeforeControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Entering method: " + methodName);
    }

    @After("execution(* com.movies.controller.*.*(..)))")
    public void logAfterControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Exiting method: " + methodName);
    }

}

	

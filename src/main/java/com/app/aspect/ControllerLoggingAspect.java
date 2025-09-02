package com.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLoggingAspect.class);

    @Pointcut("execution(* com.app.controller..*(..))")
    public void controllerMethods() {

    }

    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering Method: {}", joinPoint.getSignature());
        logger.info("Arguments: {}", joinPoint.getArgs());
    }

    @AfterReturning("controllerMethods()")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("Method {} executed successfully!", joinPoint.getSignature());
        logger.info("Return Result: {}", result);
    }

    @AfterThrowing("controllerMethods()")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        logger.error("Method {} threw exception: {}", joinPoint.getSignature(), exception.getMessage());
    }

    @Around("controllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Around start: {}", joinPoint.getSignature());
        Object result;
        try {
            result = joinPoint.proceed();
            return result;
        }finally {
            long duration = System.currentTimeMillis() -start;
            logger.info("Around end: {} excuted in {} ms",joinPoint.getSignature(),duration);
        }
    }

}

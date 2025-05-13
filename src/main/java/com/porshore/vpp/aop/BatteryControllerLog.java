package com.porshore.vpp.aop;

/**
 * @author <Pawan Gurung>
 */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logs execution details for controller methods.
 */
@Aspect
@Component
@Slf4j
public class BatteryControllerLog {

    @Around("execution(public * com.porshore.vpp.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("[Controller] Entered: {} with args: {}", methodName, Arrays.toString(args));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            log.info("[Controller] Exited: {} | Execution Time: {} ms", methodName, duration);
            return result;
        } catch (Throwable ex) {
            log.error(" [Controller] Exception in: {}", methodName, ex);
            throw ex;
        }
    }
}

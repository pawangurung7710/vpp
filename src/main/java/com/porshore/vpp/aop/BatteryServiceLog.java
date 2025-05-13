package com.porshore.vpp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author <Pawan Gurung>
 */
@Aspect
@Component
@Slf4j
public class BatteryServiceLog {

    @Around("execution(public * com.porshore.vpp.service.serviceimpl..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        log.info("Entered method: {} with args: {}", methodName, Arrays.toString(args));

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            log.info("[service] Exited method: {} | Execution time: {} ms", methodName, duration);
            return result;
        } catch (Throwable ex) {
            log.error("[service] Exception in method: {}", methodName, ex);
            throw ex;
        }
    }
}

package com.socialnetwork.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Created by santoshsharma on 22 May, 2022
 */

@Aspect
@Component
@Slf4j
public class LogTimer {
    /**
     * This method uses Around advice which ensures that an advice can run before and after the method execution,
     * to and log the execution time of the method.
     * This advice will be applied to all the method which are annotated with the
     * annotation @LogExecutionTime
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     * @LogExecutionTime public void methodTimeLogger();
     */
    @Around("@annotation(com.socialnetwork.helper.LogExecutionTime)")
    public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        // Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        // Measure method execution time
        StopWatch stopWatch = new StopWatch(className + "->" + methodName);
        stopWatch.start(methodName);
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        if (log.isInfoEnabled()) {
            log.info("->->->->" + stopWatch.getId() + " Completed in " + stopWatch.getLastTaskTimeMillis() + " ms " + "->->->->");
        }
        return result;
    }
}

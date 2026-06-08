package org.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class EmployeeCRUDAspect {

    @Before("execution(* EmployeeManager.getEmployeeById(..))")     //point-cut expression
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("EmployeeCRUDAspect logBefore() : " + joinPoint.getSignature().getName());
    }

    @After("execution(* EmployeeManager.getEmployeeByName(..))")     //point-cut expression
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("EmployeeCRUDAspect logAfter() : " + joinPoint.getSignature().getName());
    }

    @AfterReturning("execution(* EmployeeManager.updateEmployeeAge(..))")     //point-cut expression
    public void logAfterReturning(JoinPoint joinPoint) {
        System.out.println("EmployeeCRUDAspect logAfterReturning() : " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut="execution(* EmployeeManager.getEmployeeByName(..))", returning = "retVal")     //point-cut expression
    public void logAfterReturning(Object retVal) {
        System.out.println("EmployeeCRUDAspect log return value : " + retVal.toString());
    }

    @After("execution(* EmployeeManager.getEmployeesByAge(..)) && args(age)")
    public void logAfterReturningWithException(int age) {
        System.out.println("EmployeeCRUDAspect log argument value with exception : " + age);
    }

}

package com.example.firstdemo.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogProccess {

    @Pointcut(value = "execution (* com.example.firstdemo.Service.*.*(..))")
    public void pointCut() {
    }
    @Before(value = "pointCut()") //在切入点的方法run之前要干的
    public void before() {
        System.out.println("创建");
    }

}

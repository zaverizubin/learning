package com.playground.design_patterns.behavorial.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerProxy  implements InvocationHandler {

    private final Object instance;

    public InvocationHandlerProxy(Object instance) {
        this.instance = instance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            System.out.println("Before");
            result = method.invoke(this.instance, args);
            System.out.println("After");
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        } finally {
            System.out.println("finally");
        }
        return result;
    }
}

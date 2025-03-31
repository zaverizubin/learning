package com.playground.design_patterns.behavorial.dynamicproxy;

import java.lang.reflect.Proxy;

public class ProxyTest {

    public ProxyTest(){
        ISalutation instance = new Salutation();

        ISalutation proxy = (ISalutation) Proxy.newProxyInstance(ISalutation.class.getClassLoader(),
                new Class[] { ISalutation.class },
                new InvocationHandlerProxy(instance));

        proxy.hello("Mehmet");

        proxy.bye("Mehmet");

    }


}

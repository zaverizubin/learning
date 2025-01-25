package com.playground.design_patterns.behavorial.dynamicproxy;

public class Salutation implements ISalutation{
    @Override
    public void hello(String name) {
        System.out.println("Hello " + name);
    }

    @Override
    public void bye(String name) {
        System.out.println("Bye " + name);
    }
}

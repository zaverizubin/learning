package com.playground.liskov_principle.violation.type2;

public class Parrot extends Bird{

    @Override
    public void setLocation(double longitude, double latitude) {
        System.out.println("You can find me at longitude " + longitude + " and latitude " + latitude) ;
    }

    @Override
    public void setAltitude(int altitude) {
        System.out.println("I'm flying at altitude of " + altitude) ;
    }
}

package com.playground.liskov_principle.correct.type2;

public class BirdsWhoCanFly extends Bird {

    @Override
    public void setLocation(double longitude, double latitude) {
        System.out.println("You can find me at longitude " + longitude + " and latitude " + latitude) ;
    }

    public void setAltitude(int altitude){
            System.out.println("I'm flying at altitude of " + altitude) ;

    }
}

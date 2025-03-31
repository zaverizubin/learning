package com.playground.liskov_principle.violation.type2;


import org.apache.commons.lang3.NotImplementedException;

public class Penguin extends Bird {

    @Override
    public void setLocation(double longitude, double latitude) {
        System.out.println("You can find me at longitude " + longitude + " and latitude " + latitude);
    }

    @Override
    public void setAltitude(int altitude) {
        throw new NotImplementedException();
    }

    public static void main(String[] args) {
        /*Liskov principle violation.
            A not implemented method of a subclass.
        */

    }
}
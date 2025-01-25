package com.playground.liskov_principle.correct.type1;

import com.playground.liskov_principle.violation.type1.Rectangle;

public class Square extends Rectangle {

    private int length;

    public void setLength(int length){
        this.length = length;
    }

    @Override
    public int getArea(){
        return  this.length * length;
    }

}

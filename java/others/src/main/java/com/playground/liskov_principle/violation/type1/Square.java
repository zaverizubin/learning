package com.playground.liskov_principle.violation.type1;

public class Square extends Rectangle {

    public void setWidth(int width){
        this.width = width;
        this.height = width;
    }

    public void setHeight(int height){
        this.height = height;
        this.width = height;
    }

}

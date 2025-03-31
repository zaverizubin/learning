package com.playground.liskov_principle.correct.type1;

public class Rectangle  extends Shape{
    protected int width;
    protected int height;

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    @Override
    public int getArea(){
        return  this.width * this.height;
    }

}

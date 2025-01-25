package com.playground.liskov_principle.violation.type1;

public class Rectangle {

    protected int width;
    protected int height;

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getArea(){
        return  this.width * this.height;
    }

    public static void main(String[] args) {
        /*Liskov principle violation. Depending on order of setting width and height,
        the behavior of getArea() method returns different results for a square
        whereas in case of a rectangle the order does not matter - the result is always the same.

        A subclass method overrides a base class method to give it a different meaning -  setWidth or setHeight.
        */


        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(2);
        rectangle.setHeight(3);
        System.out.println(rectangle.getArea());

        rectangle = new Square();
        rectangle.setWidth(2);
        rectangle.setHeight(3);
        System.out.println(rectangle.getArea());

        rectangle = new Square();
        rectangle.setHeight(3);
        rectangle.setWidth(2);
        System.out.println(rectangle.getArea());



    }

}

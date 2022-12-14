package com.epam.DesignPattern.TemplateMethod2;

public abstract class Cake1 {

    final void make() {
        makeStart();//log
        makingCakeGerm();//做蛋糕胚
        makingCream();//制作奶油
        wipeCakeGerm();//抹蛋糕胚
        piping();//裱花
        makeFinish();//log
    }

    private void makeStart() {
        System.out.println("make start");
    }

    protected abstract String makingCakeGerm();

    protected abstract String makingCream();

    protected abstract String wipeCakeGerm();

    protected abstract String piping();

    private void makeFinish() {
        System.out.println("make finish");
    }
}

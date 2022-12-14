package com.epam.DesignPattern.TemplateMethod2;

/**
 * @program: epam
 * @description:
 * @author: 作者名字
 * @create: 2022-05-30 22:30
 **/
public abstract class Cake {

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

    private void makingCakeGerm() {
        System.out.println("制作蛋糕胚");
    }

    protected abstract void makingCream();//内容不共通的方法，留在子类实现

    private void wipeCakeGerm() {
        System.out.println("将奶油抹到蛋糕胚上");
    }

    protected abstract void piping();//内容不共通的方法，留在子类实现

    private void makeFinish() {
        System.out.println("make finish");
    }

}

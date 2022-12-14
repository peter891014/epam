package com.epam.DesignPattern.ChainPattern;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

public abstract class AbstractHandler {

    //责任链中的下一个对象
    private AbstractHandler nextHandler;

    /**
     * 责任链的下一个对象
     */
    public void setNextHandler(AbstractHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    /**
     * 具体参数拦截逻辑,给子类去实现
     */
    public void filter(HttpRequest request, HttpResponse response) {
        doFilter(request, response);
        if (getNextHandler() != null) {
            getNextHandler().filter(request, response);
        }
    }

    public AbstractHandler getNextHandler() {
        return nextHandler;
    }

     protected abstract void doFilter(HttpRequest request, HttpResponse response);

}

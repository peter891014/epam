package com.epam.DesignPattern.ChainPattern;


import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component("chainPatternComponent")
public class ChainPatternComponent {

    //自动注入各个责任链的对象
    @Autowired
    private List<AbstractHandler> abstractHandleList;

    private AbstractHandler abstractHandler;

    //spring注入后自动执行，责任链的对象连接起来
    @PostConstruct
    public void initializeChainFilter(){

        for(int i = 0;i<abstractHandleList.size();i++){
            if(i == 0){
                abstractHandler = abstractHandleList.get(0);
            }else{
                AbstractHandler currentHandler = abstractHandleList.get(i - 1);
                AbstractHandler nextHandler = abstractHandleList.get(i);
                currentHandler.setNextHandler(nextHandler);
            }
        }
    }

    //直接调用这个方法使用
    public HttpRequest exec(HttpRequest request, HttpResponse response) {
        abstractHandler.filter(request, response);
        return request;
    }

    public AbstractHandler getAbstractHandler() {
        return abstractHandler;
    }

    public void setAbstractHandler(AbstractHandler abstractHandler) {
        this.abstractHandler = abstractHandler;
    }
}

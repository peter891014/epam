package com.epam.secKill.exception;

import org.springframework.http.HttpStatus;


public class SellException extends RuntimeException{
    private Integer status ;

    public SellException(String msg){
        super(msg);
    }

    public SellException(Integer status, String msg){
        super(msg);
        this.status = status;
    }
}

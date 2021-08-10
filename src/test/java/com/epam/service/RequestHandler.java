package com.epam.service;

public interface RequestHandler<T> {

    public void handle(T result);
}
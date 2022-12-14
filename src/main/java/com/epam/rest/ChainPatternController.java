package com.epam.rest;

import com.epam.DesignPattern.ChainPattern.ChainPatternComponent;

import lombok.RequiredArgsConstructor;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 责任链模式 ：责任链模式为请求创建了一个接收者对象的链。
 * 执行链上有多个对象节点，每个对象节点都有机会（条件匹配）处理请求事务，
 * 如果某个对象节点处理完了，就可以根据实际业务需求传递给下一个节点继续处理或者返回处理完毕。
 * 这种模式给予请求的类型，对请求的发送者和接收者进行解耦。
 */
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ChainPatternController {

   @Autowired
   private ChainPatternComponent chainPatternComponent;
    @GetMapping(value = "/chainPattern")
    public ResponseEntity chainPattern(String str, HttpRequest request, HttpResponse response) {

        chainPatternComponent.exec(request,response);

        return new ResponseEntity( HttpStatus.OK);
    }


}

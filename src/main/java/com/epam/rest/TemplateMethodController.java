package com.epam.rest;




import com.epam.DesignPattern.TemplateMethod.CompanyBServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 模版方法
 * 定义一个操作中的算法的骨架流程，而将一些步骤延迟到子类中，
 * 使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
 * 它的核心思想就是：定义一个操作的一系列步骤，对于某些暂时确定不下来的步骤，
 * 就留给子类去实现，这样不同的子类就可以定义出不同的步骤。
 */

@RestController
@RequestMapping("api")
public class TemplateMethodController {

    @Autowired
    private CompanyBServiceImpl companyBServiceImpl;

    @GetMapping(value = "/handlerTempPlate")
    public ResponseEntity resolveFile(HttpRequest request) {

       return companyBServiceImpl.handier(request);
    }


}

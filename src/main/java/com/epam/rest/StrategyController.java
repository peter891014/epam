package com.epam.rest;




import com.epam.DesignPattern.Strategy.FileTypeResolveEnum;
import com.epam.DesignPattern.Strategy.StrategyUseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 策略模式
 * 根据不同类型采取不同的解析方式。
 * 我们借助spring的生命周期，使用ApplicationContextAware接口，把对用的策略，初始化到map里面。然后对外提供resolveFile方法即可
 */

@RestController
@RequestMapping("api")
public class StrategyController {

    @Autowired
    private StrategyUseService strategyUseService;

    @GetMapping(value = "/resolveFile")
    public ResponseEntity resolveFile(String fileType, String county) {
        FileTypeResolveEnum fileTypeResolveEnum;
        if(StringUtils.isNotBlank(fileType)) {
            fileTypeResolveEnum =FileTypeResolveEnum.valueOf(fileType);
        }else{
            fileTypeResolveEnum =FileTypeResolveEnum.File_DEFAULT_RESOLVE;
        }

        strategyUseService.resolveFile(fileTypeResolveEnum,county);
       return new ResponseEntity( HttpStatus.OK);
    }


}

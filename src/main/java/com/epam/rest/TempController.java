package com.epam.rest;


import com.ejlchina.http.internal.HttpException;
import com.epam.exception.ParamsException;
import com.epam.annotation.Limit;
import com.epam.service.TemperatureQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("api")
public class TempController {

    @Autowired
    private TemperatureQueryService temperatureQueryService;

   // @Limit(key = "test", period = 1, count = 100, name = "testLimit", prefix = "queryTemp")
    @GetMapping(value = "/queryTemp")
    public ResponseEntity queryTemp(String province, String city, String county) {
        Optional<Integer> optional = null;
        try {
            optional = temperatureQueryService.getTemperature(province,city,county);
        } catch (ParamsException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.OK);
        }catch (HttpException exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity(optional, HttpStatus.OK);
    }


}
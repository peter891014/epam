package com.epam.service.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.ejlchina.http.HTTP;
import com.ejlchina.http.internal.HttpException;
import com.epam.annotation.Limit;
import com.epam.exception.ParamsException;
import com.epam.service.TemperatureQueryService;
import com.epam.utils.JsonUtils;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Service;

@Service
public class TemperatureQueryImpl implements TemperatureQueryService {

    private static int RETRY_NUM = 0;
    private static int MAX_RETRY_NUM=3;
    private static String CHINA = "http://www.weather.com.cn/data/city3jdata/china.html";
    private static String PROVINCE = "http://www.weather.com.cn/data/city3jdata/provshi/";
    private static String STATION = "http://www.weather.com.cn/data/city3jdata/station/";
    private static String WETHER_INFO = "http://www.weather.com.cn/data/sk/";

    @Limit(key = "test", period = 1, count = 100, name = "testLimit", prefix = "queryTemp")
    @Override
    public Optional<Integer> getTemperature(String province, String city, String county) throws ParamsException {

        if (null == province || "".equals(province)) {
            throw new ParamsException("province 不能为空");
        } else if (null == city || "".equals(city)) {
            throw new ParamsException("city 不能为空");
        } else if (null == county || "".equals(county)) {
            throw new ParamsException("county 不能为空");
        }
        Integer result;

        Optional<Integer> optional;
        try {
            HTTP http = HTTP.builder().config(((OkHttpClient.Builder builder) -> {
                // 连接超时时间
                builder.connectTimeout(5, TimeUnit.SECONDS);
            }
            )).baseUrl(CHINA).build();
            JSONObject json = http.sync("").get().getBody().toJsonObject();
            String provinceId = JsonUtils.getKey(json, province);
            if (null != provinceId && !"".equals(provinceId)) {
                HTTP http1 = HTTP.builder().config(((OkHttpClient.Builder builder) -> {
                    builder.connectTimeout(5, TimeUnit.SECONDS);
                })).baseUrl(PROVINCE).build();
                JSONObject json1 = http1.sync(provinceId + ".html").get().getBody().toJsonObject();
                String cityId = JsonUtils.getKey(json1, city);
                if (null != cityId && !"".equals(cityId)) {
                    HTTP http2 = HTTP.builder().config(((OkHttpClient.Builder builder) -> {
                        builder.connectTimeout(5, TimeUnit.SECONDS);
                    })).baseUrl(STATION).build();
                    JSONObject json2 = http2.sync(provinceId + "" + cityId + ".html").get().getBody().toJsonObject();
                    String countyId = JsonUtils.getKey(json2, county);
                    if (countyId != null && !countyId.equals("")) {
                        HTTP http3 = HTTP.builder().config(((OkHttpClient.Builder builder) -> {
                            builder.connectTimeout(5, TimeUnit.SECONDS);
                        })).baseUrl(WETHER_INFO).build();
                        JSONObject json3 = http3.sync(provinceId + "" + cityId + countyId + ".html").get().getBody().toJsonObject();
                        JSONObject weatherinfo = json3.getJSONObject("weatherinfo");
                        String temp = weatherinfo.getString("temp");
                        Float tempf = new Float(temp);
                        result = Math.round(tempf);
                        optional = Optional.of(result);
                        return optional;
                    } else {
                        throw new ParamsException("county 参数有误");
                    }
                } else {
                    throw new ParamsException("city 参数有误");
                }
            } else {
                throw new ParamsException("province 参数有误");
            }
        } catch (HttpException httpException) {
            System.out.println("开始第"+(RETRY_NUM+1)+"次重试");
            RETRY_NUM = RETRY_NUM + 1;
            if (RETRY_NUM < MAX_RETRY_NUM) {
                return getTemperature(province, city, county);
            } else {
                throw new HttpException("网络连接异常");
            }
        }
    }

}


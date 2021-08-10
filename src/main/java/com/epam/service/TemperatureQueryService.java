package com.epam.service;

import com.epam.annotation.Limit;
import com.epam.exception.ParamsException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;


import java.util.Optional;

public interface TemperatureQueryService {
    //@Cacheable(value = "getTemperature", key="#p0+#p1+#p2")

    Optional<Integer> getTemperature(String province, String city, String county) throws ParamsException;
}

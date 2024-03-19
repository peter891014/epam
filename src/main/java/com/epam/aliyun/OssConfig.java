package com.epam.aliyun;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OssConfig {

    @Bean("ossConfigPro")
//    @ConfigurationProperties(prefix = "oss")
    public OssConfigProperties ossConfigProperties() {
        return new OssConfigProperties();
    }

    @Bean("OssUtils")
    public OssUtils ossOperationUtils(@Autowired OssConfigProperties ossConfigPro) {
        return new OssUtils(ossConfigPro);
    }

}

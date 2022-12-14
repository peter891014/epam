package com.epam.aspect;

import com.epam.exception.BadRequestException;
import com.epam.annotation.Limit;
import com.epam.utils.RequestHolder;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class LimitAspect {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);


    @Pointcut("@annotation(com.epam.annotation.Limit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
      //  HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        Limit limit = signatureMethod.getAnnotation(Limit.class);
        LimitType limitType = limit.limitType();
        String key = limit.key();
        if (StringUtils.isEmpty(key)) {
            switch (limitType) {
                case IP:
                   // key = StringUtils.getIP(request);
                    break;
                default:
                    key = signatureMethod.getName();
            }
        }
        ImmutableList keys = ImmutableList.of(StringUtils.join(limit.prefix(), "_", key));
        Integer num=(Integer) redisTemplate.opsForValue().get(keys.get(0));
        if(num!=null){
            if(num!=null&& num<= limit.count()){
                redisTemplate.opsForValue().set(keys.get(0),num+1,0);
                logger.info("第{}次访问key为 {}，描述为 [{}] 的接口", num, keys, limit.name());
                return joinPoint.proceed();
            }else {
                throw new BadRequestException("访问次数受限制");
            }
        }else{
            redisTemplate.opsForValue().set(keys.get(0),1,10l, TimeUnit.SECONDS);
            return joinPoint.proceed();
        }
    }
}

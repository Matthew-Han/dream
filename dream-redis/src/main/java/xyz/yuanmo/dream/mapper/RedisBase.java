package xyz.yuanmo.dream.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/15 17:38
 **/
@Slf4j
@Component
public class RedisBase implements BeanNameAware {

    private transient String beanName;

    public RedisTemplate<String, Object> redisTemplate;

    public ValueOperations<String, Object> valueOps;

    public ListOperations<String, Object> listOps;

    public SetOperations<String, Object> setOps;

    public HashOperations<String, String, Object> hashOps;

    public ZSetOperations<String, Object> zSetOps;

    public RedisBase(@Qualifier("jsonRedisTemplate") RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOps = redisTemplate.opsForValue();
        listOps = redisTemplate.opsForList();
        setOps = redisTemplate.opsForSet();
        hashOps = redisTemplate.opsForHash();
        zSetOps = redisTemplate.opsForZSet();
    }


    @PostConstruct
    public void init() {
        log.info("+++++++++ beanName : {} 注册成功 +++++++++", beanName);
    }

    @PreDestroy
    private void destroy() {
        log.info("+++++++++ beanName : {} 销毁成功 +++++++++", beanName);
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }


}

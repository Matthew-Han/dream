package xyz.yuanmo.dream.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/15 17:53
 **/
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig<K, V> implements BeanNameAware {

    /**
     * 不需要序列化 / 反序列化进行存储
     */
    private transient String beanName;

    /**
     * 采用阿里的FastJson
     * 目前用于RedisBase，大部分的缓存都用这个
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "jsonRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 全局开启AutoType，不建议使用
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("com.zrtg.");

        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * 用于Shiro的分布式session以byte[]形式序列化/反序列化存储和获取
     * 目前仅用于SessionRedisDao使用
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "byteRedisTemplate")
    public RedisTemplate<String, byte[]> byteRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        // 全局开启AutoType，不建议使用
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("com.zrtg.");

        // 设置值（value）的序列化采用jdkSerializationRedisSerializer。
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    @Primary
    public RedisProperties reSetRedisServer() {
        RedisProperties redisProperties = new RedisProperties();
        redisProperties.setPassword("123456");
        RedisProperties.Cluster cluster = new RedisProperties.Cluster();
        //cluster.setNodes(new ArrayList<>(Arrays.asList("172.20.2.105:6380", "172.20.2.105:6381")));
        //redisProperties.setCluster(cluster);
        redisProperties.setHost("172.26.59.202");
        return redisProperties;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @PostConstruct
    private void init() {
        log.info("+++++++++ beanName : {} 注册成功 +++++++++", beanName);
    }

    @PreDestroy
    private void destroy() {
        log.info("+++++++++ beanName : {} 销毁成功 +++++++++", beanName);
    }

}


package xyz.yuanmo.dream.mapper;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/18 13:01
 **/
@Component
public class RedisOperation {
    /**
     * 模拟10万数据
     */
    private final int DATA_COUNT = 100000;

    private final RedisBase redisBase;


    public RedisOperation(RedisBase redisBase) {
        this.redisBase = redisBase;
    }

    /**
     * 比较 String 字符串和 hash 集合对于10万级别数据存储的内存占用
     * 暂时关闭
     *
     * @return
     */
    @Bean(autowireCandidate = false)
    public Object hashRedisValue() {
        for (int i = 1; i <= DATA_COUNT; i++) {
            //redisBase.valueOps.set(packageId(i), "图片id：" + i);
            Map<String, String> map = new HashMap<>();
            map.put(packageId(i).substring(4, 6), "图片id：" + i);
            redisBase.hashOps.putAll(packageId(i).substring(0, 4), map);

        }
        return null;
    }

    /**
     * zSet test
     *
     * @return
     */
    @Bean(autowireCandidate = false)
    public Object zSetRedisValue() {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        redisBase.zSetOps.add("key1", "value11", 100);
        return null;
    }

    public static String packageId(int id) {
        int count = 0;
        int tmp = id;
        while (tmp > 0) {
            tmp /= 10;
            count++;
        }
        int diff = 6 - count;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < diff; i++) {
            res.append("0");
        }
        res.append(id);
        return res.toString();
    }

    public static void main(String[] args) {
        for (int i = 1000000 - 10; i <= 1000000; i++) {
            System.out.println(packageId(i));
        }
        System.out.println("abcde".substring(0, 2));
        System.out.println("abcde".substring(2, 5));

    }


}

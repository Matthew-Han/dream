package xyz.yuanmo.dream.config;

import org.springframework.context.annotation.Configuration;
import java.util.BitSet;

/**
 * @author Matthew Han
 * @version 1.0
 * @description 布隆过滤器，每次载入缓存也需要添加到布隆过滤器中
 * @date 2020/10/16 14:17
 **/
@Configuration
public class BloomFilter {

    /**
     * 布隆过滤器长度
     */
    private static final int SIZE = 2 << 10;
    /**
     * 模拟实现不同的哈希函数
     */
    private static final int[] NUM = new int[]{5, 19, 23, 31, 47, 71};
    /**
     * 初始化位数组
     */
    private final BitSet bits = new BitSet(SIZE);
    /**
     * 用于存储哈希函数
     */
    private final MyHash[] function = new MyHash[NUM.length];

    /**
     * 初始化哈希函数
     */
    public BloomFilter() {
        for (int i = 0; i < NUM.length; i++) {
            function[i] = new MyHash(SIZE, NUM[i]);
        }
    }

    /**
     * 存值Api
     *
     * @param value
     */
    public void add(String value) {
        // 对存入得值进行哈希计算
        for (MyHash f : function) {
            // 将为数组对应的哈希下标得位置得值改为1
            bits.set(f.hash(value), true);
        }
    }

    /**
     * 判断是否存在该值得Api
     *
     * @param value
     * @return
     */
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean result = true;
        for (MyHash f : function) {
            result = result && bits.get(f.hash(value));
        }
        return result;
    }

    public static class MyHash {
        private final int cap;
        private final int seed;

        // 初始化数据
        public MyHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        /**
         * 哈希函数
         *
         * @param value
         * @return
         */
        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }

    public static void main(String[] args) {
        BloomFilter bloomFilter = new BloomFilter();
        System.out.println(bloomFilter.contains("123"));
        bloomFilter.add("123");
        System.out.println(bloomFilter.contains("123"));
    }
}

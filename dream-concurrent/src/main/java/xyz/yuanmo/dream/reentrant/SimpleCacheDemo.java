package xyz.yuanmo.dream.reentrant;

import lombok.SneakyThrows;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/22 15:46
 **/
public class SimpleCacheDemo<K, V> {

    private final HashMap<K, V> cache;
    private final ReentrantReadWriteLock.ReadLock readLock;
    private final ReentrantReadWriteLock.WriteLock writeLock;

    /**
     * 读 - 读 共享
     * 读 - 写 分离
     * 写 - 写 分离
     *
     * @param capacity
     */
    public SimpleCacheDemo(int capacity) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
        cache = new HashMap<>(capacity * 4 / 3 + 1);
    }

    /**
     * 存放缓存
     *
     * @param key
     * @param val
     */
    @SneakyThrows
    public V setCache(K key, V val) {
        Thread thread = Thread.currentThread();
        writeLock.lock();
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(thread.getName() + " 正在写入val: " + val);
            return cache.put(key, val);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public V getCache(K key) {
        Thread thread = Thread.currentThread();
        readLock.lock();
        try {
            System.out.println(thread.getName() + " 正在获取val: " + cache.get(key));
            return cache.get(key);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 移除缓存
     *
     * @param key 键
     * @return 移除的值
     */
    public V remove(K key) {
        writeLock.lock();
        try {
            return cache.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {
            cache.clear();

        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 读写锁
     *
     * @param args
     */
    public static void main(String[] args) {
        SimpleCacheDemo<String, String> simpleCacheDemo = new SimpleCacheDemo<>(4);

        for (int i = 0; i < 4; i++) {
            int key = i;
            new Thread(() -> {
                simpleCacheDemo.setCache(String.valueOf(key), String.valueOf(key));
            }, "t1 - " + i).start();
        }

        for (int i = 0; i < 4; i++) {
            int key = i;
            new Thread(() -> {
                simpleCacheDemo.getCache(String.valueOf(key));
            }, "t1 - " + i).start();
        }


    }

}

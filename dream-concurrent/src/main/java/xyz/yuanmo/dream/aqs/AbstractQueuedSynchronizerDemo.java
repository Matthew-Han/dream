package xyz.yuanmo.dream.aqs;

import lombok.SneakyThrows;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2020/12/11 15:34
 **/
public class AbstractQueuedSynchronizerDemo {


    @SneakyThrows
    public static void main(String[] args) {
        Map<Long, Offer> map = new ConcurrentHashMap<>(500000 * 4 / 3 + 1);
        CountDownLatch countDownLatch = new CountDownLatch(500000);
        for (int i = 0; i < 500000; i++) {
            new Thread(() -> {
                map.put(UUID.randomUUID().getLeastSignificantBits(), new Offer());
                countDownLatch.countDown();

            }).start();
        }
        countDownLatch.await();
        System.out.println("map = " + map.size());
    }

    static class Offer {

    }
}

package xyz.yuanmo.dream.aqs;

import lombok.SneakyThrows;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/22 17:13
 **/
public class CountDownLatchDemo {

    private static final int COUNT = 6;

    @SneakyThrows
    public void simpleDemo() {
        CountDownLatch countDownLatch = new CountDownLatch(COUNT);
        for (int i = 0; i < 5; i++) {
            String no = String.valueOf(i);
            new Thread(() -> {
                System.out.println("NO. " + no + " 同事离开了办公室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println("countDownLatch.getCount() = " + countDownLatch.getCount());
        countDownLatch.await();
    }

    public static void main(String[] args) {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        demo.simpleDemo();
        new Thread(() -> {
            System.out.println("Matthew Han 关闭了教室");
        }, "t2").start();

    }

}

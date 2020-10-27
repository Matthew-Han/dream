package xyz.yuanmo.dream.java.aqs;

import lombok.SneakyThrows;
import java.util.concurrent.CountDownLatch;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/22 17:13
 **/
public class CountDownLatchDemo {


    @SneakyThrows
    public void simpleDemo() {
        final int count = 5;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < 5; i++) {
            String no = String.valueOf(i);
            new Thread(() -> {
                System.out.println("NO. " + no + " 同事离开了办公室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
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

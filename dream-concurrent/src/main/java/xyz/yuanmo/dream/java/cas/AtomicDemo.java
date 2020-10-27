package xyz.yuanmo.dream.java.cas;

import lombok.SneakyThrows;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/21 14:30
 **/
public class AtomicDemo {

    static AtomicInteger atomicInteger = new AtomicInteger(0);

    /**
     * case：未解决aba问题的cas
     */
    public static void atomicAba() {
        new Thread(() -> {
            atomicInteger.compareAndSet(0, 1);
            System.out.println("step1 = " + atomicInteger.get());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(1, 0);
            System.out.println("step2 = " + atomicInteger.get());
        }, "t1").start();

    }

    @SneakyThrows
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(0, 5);
            System.out.println("step3 = " + atomicInteger.get());
        }, "main").start();
        atomicAba();
        TimeUnit.SECONDS.sleep(8);
        System.out.println("res = " + atomicInteger.get());
    }
}

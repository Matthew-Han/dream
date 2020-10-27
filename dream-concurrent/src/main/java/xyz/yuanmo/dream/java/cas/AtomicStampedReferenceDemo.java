package xyz.yuanmo.dream.java.cas;

import lombok.SneakyThrows;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/21 14:46
 **/
public class AtomicStampedReferenceDemo {

    static AtomicStampedReference<Integer> afNumber;

    int timeStamp;

    public AtomicStampedReferenceDemo() {
        timeStamp = (int) System.currentTimeMillis();
        afNumber = new AtomicStampedReference<>(0, timeStamp);
        System.out.println("afNumber = " + afNumber.getReference());
        System.out.println("afNumber = " + afNumber.getStamp());
    }

    /**
     * case：未解决aba问题的cas
     */
    public void atomicAba() {
        new Thread(() -> {
            int newTimeStamp = (int) System.currentTimeMillis();
            afNumber.compareAndSet(0, 1, timeStamp, newTimeStamp);
            System.out.println("step1 = " + afNumber.getReference());
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeStamp = (int) System.currentTimeMillis();
            afNumber.compareAndSet(1, 0, newTimeStamp, timeStamp);

            System.out.println("step2 = " + afNumber.getReference());
        }, "t1").start();

    }

    @SneakyThrows
    public static void main(String[] args) {
        AtomicStampedReferenceDemo demo = new AtomicStampedReferenceDemo();
        int version = demo.timeStamp;
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            afNumber.compareAndSet(0, 5, version, (int) System.currentTimeMillis());
            System.out.println("step3 = " + afNumber.getReference());
            System.out.println("step3 = " + afNumber.getStamp());
        }, "main").start();
        demo.atomicAba();
        TimeUnit.SECONDS.sleep(8);
        System.out.println("res = " + afNumber.getReference());
    }
}

package xyz.yuanmo.dream.java.cas;

import lombok.SneakyThrows;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/22 11:00
 **/
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    @SneakyThrows
    public void lock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null, thread)) {
            //System.out.println("atomicReference.get() = " + atomicReference.get());
        }
        System.out.println(thread.getName() + " lock = " + atomicReference.get());
        TimeUnit.SECONDS.sleep(3);
    }

    @SneakyThrows
    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " unlock = " + atomicReference.get());
        TimeUnit.SECONDS.sleep(2);

    }

    /**
     * cas实现自旋锁
     * <p>
     * t1 lock = Thread[t1,5,main]
     * t1 unlock = Thread[t2,5,main]
     * t2 lock = Thread[t2,5,main]
     * t2 unlock = null
     * <p>
     * 为什么这里的t1 unlock 打印的是 Thread[t2,5,main] 呢？
     * 因为t2一直在循环，一旦他unlock了，t2马上执行<code>atomicReference.compareAndSet(null, thread)</code>
     * 将t2线程set，所以打印t1 unlock只能打印出t2线程，其实这里是已经先执行将t1 thread --> null了
     *
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {
        SpinLockDemo demo = new SpinLockDemo();
        new Thread(() -> {
            demo.lock();
            demo.unlock();
        }, "t1").start();

        TimeUnit.SECONDS.sleep(2);
        new Thread(() -> {
            demo.lock();
            demo.unlock();
        }, "t2").start();

    }
}

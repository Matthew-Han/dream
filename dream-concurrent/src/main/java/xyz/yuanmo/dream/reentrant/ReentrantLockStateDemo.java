package xyz.yuanmo.dream.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Matthew Han
 * @description
 * @date 2021/4/29 10:13
 * @since 1.0.0
 **/
public class ReentrantLockStateDemo {

    public static void main(String[] args) {
        lockOpsMethods();
    }

    private static void lockOpsMethods() {
        ReentrantLock lock = new ReentrantLock();
        int count = lock.getHoldCount();
        System.out.printf("在 lock() 方法调用之前的线程[%s]重进入数：%d\n", Thread.currentThread().getName(), count);
        lock(lock, 100);
    }

    private static void lock(ReentrantLock lock, int times) {

        if (times < 1) {
            return;
        }
        lock.lock();
        try {
            // times-- load, minus 1
            lock(lock, --times);
            System.out.printf("第%s次在 lock() 方法调用之后的线程[%s]重进入数：%d\n",
                    times + 1,
                    Thread.currentThread().getName(),
                    lock.getHoldCount());
        } finally {
            lock.unlock();
        }

    }

}

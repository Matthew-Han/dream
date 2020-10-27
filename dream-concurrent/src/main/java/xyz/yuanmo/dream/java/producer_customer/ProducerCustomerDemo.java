package xyz.yuanmo.dream.java.producer_customer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/26 11:32
 **/
public class ProducerCustomerDemo {

    private char flag = 'B';
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void autoIncrement() {
        lock.lock();
        try {
            while (flag != 'B') {
                condition.await();
            }
            flag = 'A';
            System.out.println(Thread.currentThread().getName() + " 改变了flag = " + flag);
            // 一通知，下面就开始工作了
            condition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void autoDecrement() {
        lock.lock();
        try {
            while (flag != 'A') {
                condition.await();
            }
            flag = 'B';
            System.out.println(Thread.currentThread().getName() + " 改变了flag = " + flag);
            // 一通知，下面就开始工作了
            condition.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 双线程相互唤醒
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ProducerCustomerDemo demo = new ProducerCustomerDemo();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.autoIncrement();
            }
        }, "producer").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.autoDecrement();
            }
        }, "customer").start();

    }

}

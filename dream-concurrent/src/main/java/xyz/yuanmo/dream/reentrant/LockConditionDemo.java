package xyz.yuanmo.dream.reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2020/10/27 11:14
 **/

public class LockConditionDemo {

    private final Lock lock;
    private final Condition[] conditions;
    private int number;

    public LockConditionDemo(int capacity) {
        lock = new ReentrantLock();
        conditions = new Condition[capacity];
        for (int i = 0; i < conditions.length; i++) {
            conditions[i] = lock.newCondition();
        }
        number = 0;
    }

    private void printNo(int no, int max) {
        lock.lock();
        try {
            while (number != no) {
                conditions[no].await();
            }
            for (int i = 0; i < no; i++) {
                System.out.println(Thread.currentThread().getName() + "\tfuck babe");
            }
            number++;
            conditions[number % max].signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int count = 5;
        LockConditionDemo demo = new LockConditionDemo(count);
        for (int i = 0; i < count; i++) {
            int no = i;
            new Thread(() -> {
                demo.printNo(no, count);
            }, "t" + i).start();
        }
    }


}
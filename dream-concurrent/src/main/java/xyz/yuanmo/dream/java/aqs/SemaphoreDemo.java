package xyz.yuanmo.dream.java.aqs;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/26 10:25
 **/
public class SemaphoreDemo {

    public static void main(String[] args) {
        int CDC_MC_COUNT = 20;
        int FUCKED = 5;
        Semaphore semaphore = new Semaphore(FUCKED);
        for (int i = 0; i < CDC_MC_COUNT; i++) {
            final int no = i;
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("NO. " + no + " 号正在成都MC中做菜，预计三秒结束...");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println("NO. " + no + " 号做菜完毕，有请下一位！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }

    }
}

package xyz.yuanmo.dream.aqs;

import lombok.SneakyThrows;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2020/11/5 17:36
 **/
public class LockSupportDemo {


    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println("来了,老弟");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            LockSupport.park();
            System.out.println("滑了,老弟");
        }, "t1");
        t1.start();

        new Thread(() -> {
            System.out.println("卍解");
            // 只有一个令牌
            LockSupport.unpark(t1);
            LockSupport.unpark(t1);
        }).start();
    }
}

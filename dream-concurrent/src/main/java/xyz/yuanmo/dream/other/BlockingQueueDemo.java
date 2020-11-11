package xyz.yuanmo.dream.other;

import lombok.SneakyThrows;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/26 10:38
 **/
public class BlockingQueueDemo {

    /**
     * 生产者 - 消费者模式，需要使用阻塞模式的话，要用put + take方法
     *
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.offer(1, 2L, TimeUnit.SECONDS);
        queue.offer(2, 2L, TimeUnit.SECONDS);
        System.out.println("queue = " + queue);
        queue.poll();
        queue.offer(3, 2L, TimeUnit.SECONDS);
        System.out.println("queue = " + queue);


        // 不存放数据，生产完如需消费直接消费（这样好像就没有队列效果了）
        SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                // 阻塞方法的话，这里要用put
                synchronousQueue.put(1);
                System.out.println(Thread.currentThread().getName() + "放入了：1");

                synchronousQueue.put(2);
                System.out.println(Thread.currentThread().getName() + "放入了：2");

                synchronousQueue.put(3);
                System.out.println(Thread.currentThread().getName() + "放入了：3");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "拿到了：" + synchronousQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "拿到了：" + synchronousQueue.take());

                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName() + "拿到了：" + synchronousQueue.take());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }, "t2").start();


    }
}

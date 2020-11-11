package xyz.yuanmo.dream.callable;

import lombok.SneakyThrows;
import java.util.concurrent.*;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @since 2020/10/27 15:04
 **/
public class CallableDemo {

    static int number = 0;

    static class CallableTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            TimeUnit.SECONDS.sleep(2L);
            number++;
            return number;
        }
    }

    @SneakyThrows
    public static void main(String[] args) {

        CallableTask task = new CallableTask();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        FutureTask<Integer> futureTask2 = new FutureTask<>(task);
        Thread thread = new Thread(futureTask, "t1");
        thread.start();
        Thread thread2 = new Thread(futureTask2, "t2");
        thread2.start();
        // get()方法会阻塞，不会出现拿不到返回值的情况
        System.out.println(futureTask.get());
        System.out.println(futureTask2.get());

    }

}

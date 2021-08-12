package xyz.yuanmo.dream.reentrant;

/**
 * @author Matthew Han
 * @description
 * @date 2021/4/26 15:54
 * @since 1.0.0
 **/
public class SynchronizedWaitDemo {

    public synchronized void echo1() throws InterruptedException {
        System.out.println("curr thread = " + Thread.currentThread().getName());
        wait();
        Thread.sleep(1000L);
        notify();
        //System.out.println("curr thread = " + Thread.currentThread().getName() + "溜了!");
    }

    public synchronized void echo2() throws InterruptedException {
        System.out.println("curr thread = " + Thread.currentThread().getName());
        wait();
        Thread.sleep(1000L);
        notify();
        //System.out.println("curr thread = " + Thread.currentThread().getName() + "溜了!");
    }

    public static void main(String[] args) {
        SynchronizedWaitDemo demo = new SynchronizedWaitDemo();

        new Thread(() -> {
            try {
                demo.echo1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                demo.echo2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

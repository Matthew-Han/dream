package xyz.dream.yuanmo.flow;

import java.util.concurrent.*;

/**
 * @author Matthew Han
 * @date 2021/5/11 10:48
 * @description
 * @since 1.0.0
 **/
public class FlowDemo {

    public SubmissionPublisher buildFlow() throws InterruptedException {
        SubmissionPublisher<String[]> sp = new SubmissionPublisher<>();
        sp.subscribe(new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(10000);
                System.out.println("订阅成功");
            }

            @Override
            public void onNext(String[] s) {
                for (String msg : s) {
                    if ("exit".equals(msg)) {
                        System.out.println("已退出");
                        subscription.cancel();
                        return;
                    }
                    System.out.println("msg = " + msg);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                // subscription.cancel();

            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });



        //ExecutorService executor = (ExecutorService) sp.getExecutor();
        //executor.awaitTermination(1, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(1);
        return sp;

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {

                SubmissionPublisher sp = new FlowDemo().buildFlow();
                sp.submit(new String[]{"先是这个1", "再是这个"});
                sp.submit(new String[]{"先是这个2", "再是这个"});
                sp.submit(new String[]{"先是这个3", "再是这个"});
                sp.submit(new String[]{"先是这个4", "再是这个"});
                sp.submit(new String[]{"test"});
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.setName("t1");
        t1.start();
        t1.join();

        Thread t2 = new Thread(() -> {
            System.out.println("t2");
        });
        t2.setName("t2");
        t2.start();

    }
}

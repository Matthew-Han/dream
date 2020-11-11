package xyz.yuanmo.dream.reentrant;

import java.util.concurrent.TimeUnit;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/22 10:17
 **/
public class ReentrantLockDemo {

    /**
     * 这里用reentrantLock结果也是一样的
     */
    static class Phone {

        public synchronized void sendMsg() {
            System.out.println(Thread.currentThread().getName() + " 短信发送成功");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendSms();
        }

        private synchronized void sendSms() {
            System.out.println(Thread.currentThread().getName() + " SMS发送成功");
        }
    }

    /**
     * 可重入锁（递归锁）
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(match("fred", "freh"));

        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendMsg();
        }, "t1").start();

        new Thread(() -> {
            phone.sendSms();
        }, "t2").start();

    }
    public static boolean match(String currWord, String targetWord) {
        int[] bucket = new int[26];
        char[] curr = currWord.toCharArray();
        char[] target = targetWord.toCharArray();
        for (int i = 0; i < currWord.length(); i++) {
            bucket[currWord.charAt(i) - 'a']++;
            bucket[targetWord.charAt(i) - 'a']--;
        }
        int count = 0;
        for (int n : bucket) {
            count += (n == 0 ? 0 : 1);
        }
        return count == 2;
    }
}

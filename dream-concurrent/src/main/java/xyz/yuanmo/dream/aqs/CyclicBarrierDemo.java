package xyz.yuanmo.dream.aqs;

import lombok.SneakyThrows;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Matthew Han
 * @version 1.0
 * @description
 * @date 2020/10/22 17:37
 **/
public class CyclicBarrierDemo {

    private static final int BYD_COUNT = 7;

    @SneakyThrows
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(BYD_COUNT, new Thread(() -> {
            System.out.println("李赣：BYD都到齐了，火速开会！");
        }, "main"));
        String[] byd = new String[BYD_COUNT];
        byd[0] = "黑狗";
        byd[1] = "强明星";
        byd[2] = "卷董";
        byd[3] = "嗨豹";
        byd[4] = "三狗";
        byd[5] = "冰帝";
        byd[6] = "牙牙早安";

        for (int i = 0; i < 7; i++) {
            int no = i;
            new Thread(() -> {
                System.out.println(byd[no] + " 到齐了");
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }


    }

}

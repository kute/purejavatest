package com.kute.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * created on 2018-07-02 15:58
 * 栅栏
 */
public class CyclicBarrier_Test {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> System.out.println("线程组执行结束"));
        for (int i = 0; i < 10; i++) {
            new Thread(new readNum(i, cyclicBarrier)).start();
        }
    }

    static class readNum implements Runnable {
        private int id;
        private CyclicBarrier cyc;

        public readNum(int id, CyclicBarrier cyc) {
            this.id = id;
            this.cyc = cyc;
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println("id:" + id);
                try {
                    cyc.await();
                    System.out.println("线程组任务" + id + "结束，其他任务继续");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

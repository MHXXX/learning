package com.xmh.juc;

import java.util.concurrent.CyclicBarrier;

/**
 * .
 *
 * @author 谢明辉
 * @date 2020-1-8
 */

public class CyclicBarrierTest {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            System.out.println("rush!rush!rush!");
        });

        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            new People(cyclicBarrier, String.valueOf(i)).start();
        }
    }


    static class People extends Thread {
        private final CyclicBarrier barrier;

        public People(CyclicBarrier barrier, String name) {
            super(name);
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("ready:" + Thread.currentThread().getName());
                Thread.sleep(1000);
                barrier.await();
                System.out.println("rush:" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}


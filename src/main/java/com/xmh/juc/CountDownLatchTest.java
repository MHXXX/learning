package com.xmh.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * .
 *
 * @author 谢明辉
 * @date 2020-1-8
 */

public class CountDownLatchTest {


    public static void main(String[] args) throws InterruptedException {
//        System.out.println("===================switch test begin============");
//        switchTest();
//        System.out.println("===================switch test finish===========");

        System.out.println("===================signal test begin============");
        signalTest();
        System.out.println("===================signal test finish===========");

    }

    private static void signalTest() {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Signal(countDownLatch, String.valueOf(i)).start();
        }
        System.out.println("main ready~~~~~~!!!!!!!");
        try {
            countDownLatch.await();
            System.out.println("main rush~~~~~~~~~~~!!!!!!!!!!!!!!!!!!!!!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static void switchTest() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Switch(countDownLatch, String.valueOf(i)).start();
        }

        System.out.println("main-------wait-----------wait");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("rush-------rush-----------rush");
        countDownLatch.countDown();
    }

    static class Signal extends Thread {
        private final CountDownLatch latch;

        Signal(CountDownLatch latch, String name) {
            super(name);
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                System.out.println("finish:" + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        }
    }

    static class Switch extends Thread {
        private final CountDownLatch latch;

        Switch(CountDownLatch latch, String name) {
            super(name);
            this.latch = latch;
        }


        @Override
        public void run() {
            try {
                System.out.println("ready:" + Thread.currentThread().getName());
                latch.await();
                System.out.println("finish:" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


package com.xmh.juc;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ThreadLocalRandom;

/**
 * .
 *
 * @author 谢明辉
 * @date 2020-1-8
 */

public class ExchangerTest {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Player("apple", exchanger, "apple tree").start();
        new Player("banana", exchanger, "banana tree").start();

    }

    static class Player extends Thread {
        private String message;
        private final Exchanger<String> exchanger;

        Player(String message, Exchanger<String> exchanger, String name) {
            super(name);
            this.message = message;
            this.exchanger = exchanger;
        }


        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + message);
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 10000));
                message = exchanger.exchange(message);
                System.out.println(Thread.currentThread().getName() + ":" + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

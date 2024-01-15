package com.xmh.juc;

import java.util.concurrent.Semaphore;

/**
 * .
 *
 * @author 谢明辉
 * @date 2020-1-8
 */

public class SemaphoreTest  {
    private static final SemaphoreTest INSTANT = new SemaphoreTest();

    private final static int MAX = 1;
    private final Semaphore semaphore = new Semaphore(MAX, true);
    private Object[] items = new Object[MAX];
    private boolean[] used = new boolean[MAX];

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                System.out.println("ready 1");
                Object item = SemaphoreTest.INSTANT.getItem();
                System.out.println("get 1");
                Thread.sleep(5000);
                SemaphoreTest.INSTANT.release(item);
                System.out.println("release 1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        thread.start();
        Thread.sleep(1000);
        System.out.println("ready 2");
        Object item = SemaphoreTest.INSTANT.getItem();
        System.out.println("get 2");
        Thread.sleep(5000);
        SemaphoreTest.INSTANT.release(item);
        System.out.println("release 2");
    }



    private Object getItem() throws InterruptedException {
        semaphore.acquire();
        return getNextItem();
    }

    private synchronized Object getNextItem() {
        for (int i = 0; i < MAX; i++) {
            if (!used[i]) {
                used[i] = true;
                return items[i];
            }
        }
        return null;
    }

    private void release(Object o) {
        if (markAsUnused(o)) {
            semaphore.release();
        }
    }

    private synchronized boolean markAsUnused(Object o) {
        for (int i = 0; i < MAX; i++) {
            if (o == items[i]) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}

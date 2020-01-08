package com.xmh;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * .
 *
 * @author 谢明辉
 * @date 2020-1-7
 */

public class Test2 implements Runnable {

    @Override
    public void run() {
        LockSupport.park();

        System.out.println(1);
    }

    public static void main(String[] args) {
        Thread t = new Thread(new Test2());
        t.start();

        Scanner s = new Scanner(System.in);
        s.next();
        LockSupport.unpark(t);


    }

}

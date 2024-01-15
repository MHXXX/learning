package com.xmh;

import cn.hutool.core.lang.Dict;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * .
 *
 * @author 谢明辉
 * @date 2020-1-7
 */

public class Test2 {

    public static void main(String[] args) throws Exception {


    }

    public static Integer small(Integer num) {
        return num * 2 + 1;
    }

    public static Integer big(Integer num) {
        return num * 3 + 1;
    }

    public static void run(int n) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(1);
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        int min = 0;
        while (n > min) {
            LinkedList<Integer> temp = new LinkedList<>();
            for (Integer item : list) {
                Integer small = small(item);
                Integer big = big(item);
                if (!queue.contains(small)) {
                    queue.add(small);
                    min++;
                }
                temp.add(small);
                if (!queue.contains(big)) {
                    queue.add(big);
                }
                temp.add(big);
            }
            list = temp;
        }

        for (int i = 0; i < n; i++) {
            System.out.println(queue.poll());
        }
        System.out.println(queue.poll());


    }


}

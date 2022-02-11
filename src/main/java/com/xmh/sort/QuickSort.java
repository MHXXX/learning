package com.xmh.sort;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * .
 *
 * @author 谢明辉
 * @date 2022/2/11
 */

public class QuickSort {

    public static void main(String[] args) {
        int[] ints = {5, 8, 9, 7, 3, 6, 11, 88, 44, 31, 22, 15, 88, 55, 132, 54};
        ints = sortArray(ints);
        System.out.println(Arrays.toString(ints));
    }

    public static int[] sortArray(int[] nums) {
        LinkedList<Integer> list = new LinkedList<>();
        list.push(0);
        list.push(nums.length - 1);

        while (list.size() > 0) {
            Integer right = list.pop();
            Integer left = list.pop();
            int index = partition(nums, left, right);
            if (left < index) {
                list.push(left);
                list.push(index);
            }
            if (index + 1 < right) {
                list.push(index + 1);
                list.push(right);
            }
        }
        return nums;
    }

    private static int partition(int[] nums, int start, int end) {
        int l = start;
        int r = end;

            int temp = nums[l];
            while (l < r) {
                while (l < r && nums[r] >= temp) {
                    r--;
                }
                nums[l] = nums[r];

                while (l < r && nums[l] < temp) {
                    l++;
                }
                nums[r] = nums[l];
            }
            nums[l] = temp;
            return l;
    }

}

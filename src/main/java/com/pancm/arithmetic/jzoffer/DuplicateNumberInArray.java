package com.pancm.arithmetic.jzoffer;

/**
 * https://www.nowcoder.com/practice/623a5ac0ea5b4e5f95552655361ae0a8?tpId=13&tqId=11203&tPage=1&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
 *
 * @description:
 * 3.数组中的重复数字
 * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。
 * 数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。
 * 请找出数组中任意一个重复的数字。
 *
 * @author: Zhoust
 * @date: 2019/04/28 10:58
 * @version: V1.0
 */
public class DuplicateNumberInArray {

    /**
     * 一种简单的实现思路，借助外部数组保存 numbers 中每个值的出现次数，即 numbers 中的值作为数组 array 的下标。
     * 题目中说了长度为 n 的数组中每个值都在 0 到 n-1 范围内，因此遍历数组 numbers 存入对应的 array 不会出现数组下标越界的问题
     * @param numbers
     * @param length        numbers 数组的长度
     * @param duplication   (Output) the duplicated number in the array number,length of duplication array is 1,so using duplication[0] = ? in implementation;
     *                      这里要特别注意~返回任意重复的一个，赋值duplication[0]
     * @return              true if the input is valid, and there are some duplications in the array number, otherwise false
     */
    public boolean duplicate(int[] numbers, int length, int[] duplication) {
        if (numbers == null) {
            return false;
        }
        boolean result = false;
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            if (++array[numbers[i]] > 1 && !result) {
                result = true;
                duplication[0] = numbers[i];
            }
        }
        return result;
    }

    /**
     * 先排序，然后找到排序后数组中第一个重复的数字（主要就是练习一下快排）
     * @param numbers
     * @param length
     * @param duplication
     * @return
     */
    public boolean sortThenFindFirstDuplicateNumber(int[] numbers, int length, int[] duplication) {
        if (numbers == null) {
            return false;
        }
        quickSort(numbers, 0, length-1);
        int temp = numbers[0];
        boolean result = false;

        for (int i = 1; i < length; i++) {
            if (numbers[i] == temp) {
                result = true;
                duplication[0] = temp;
                break;
            }
            temp = numbers[i];
        }
        return result;
    }

    /**
     * 将值为 i 的元素调整到下标为 i 的位置，如果该位置上的数已经等于 i 了，就说明 i 已经重复了
     * @param numbers
     * @param length
     * @param duplication
     * @return
     */
    public static boolean jz(int[] numbers, int length, int[] duplication) {
        for (int i = 0; i < length; i++) {
            //第 i 个位置上的数 numbers[i] 若不与下标 i 相等，就把 numbers[i] 放到下标为 numbers[i] 的位置
            while (numbers[i] != i) {
                //在交换位置之前还要检查 numbers[i] 位置上的数字是否已经是 numbers[i]，如果是就已经找到了重复的数字
                if (numbers[numbers[i]] == numbers[i]) {
                    duplication[0] = numbers[i];
                    //注意这里不是 break
                    return true;
                }

                //交换下标为 i、numbers[i] 两个位置上的数字（保证下标为 numbers[i] 的位置，放置的数字是 numbers[i]）
                swap(numbers, i, numbers[i]);
            }
        }
        return false;
    }

    /**
     * 交换下标为 i、j 的两个数
     * @param numbers
     * @param i
     * @param j
     */
    public static void swap(int[] numbers, int i, int j) {
        int swap = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = swap;
    }

    /**
     * 快排
     * @param array
     * @param length
     */
    @SuppressWarnings("all")
    public static void quickSort(int[] array, int low, int high) {
        //这里必须首先限制 low<high，否则会出现 ArrayIndexOutOfBoundsException，当
        if (low < high) {
            int temp = array[low];
            int l = low, h = high;
            while (l != h) {
                while (array[h] > temp && h > l) {
                    h--;
                }
                array[l] = array[h];
                while (array[l] <= temp && l < h) {
                    l++;
                }
                array[h] = array[l];
            }
            array[l] = temp;
            quickSort(array, low, h-1);
            quickSort(array, l+1, high);
        }
    }

    public static void main(String[] args) {
        int[] result = new int[1];
        jz(new int[]{2,4,2,1,4}, 5, result);
        System.out.println(result[0]);
//        int[] d = new int[1];
//        jz(new int[]{2,1,3,0,4}, 5, d);
//        int[] array = new int[]{1,4,4,4,12,78,8,45,67,39,20,90,65,34,34,6,7,88,9,1,-3,-88,-2};
        int[] array = new int[]{2,4,3,1,4};
        quickSort(array, 0, array.length-1);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

    }

}
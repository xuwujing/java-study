package com.pancm.question;

/**
 * @Title: QuestionTest1
 * @Description:
 * @Version:1.0.0
 * @author pancm
 * @date 2017年7月21日
 */
public class QuestionTest1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序， 每一列都按照从上到下递增的顺序排序。请完成一个函数，
		 * 输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
		 */
		int target = 4;
		int target1 = 5;
		int target2 = 6;
		int[][] array = { { 1, 2, 3, 4 }, { 1, 2, 3, 4, 5 } };
		System.out.println(Find(target, array));
		System.out.println(Find(target1, array));
		System.out.println(Find(target2, array));
		System.out.println(Find2(target, array));
		System.out.println(Find2(target1, array));
		System.out.println(Find2(target2, array));
		
		

	}

	/**
	 * 方法一：最笨的方法，利用双重循环找到
	 * 
	 * @param target
	 * @param array
	 * @return
	 */
	public static boolean Find(int target, int[][] array) {
		for (int[] i : array) {
			for (int j : i) {
				if (j == target) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 方法二: 最优解法。 思路：首先我们选择从左下角开始搜寻， (为什么不从左上角开始搜寻，左上角向右和向下都是递增，那么对于一个点，
	 * 对于向右和向下会产生一个岔路；如果我们选择从左下脚开始搜寻的话， 如果大于就向右，如果小于就向下)。
	 * 
	 * @param target
	 * @param array
	 * @return
	 */
	public static boolean Find2(int target, int[][] array) {
		int len = array.length - 1; // 得出二维数组的列长度
		int i = 0;
		while ((len > 0) && (i < array[0].length)) {
			if (array[len][i] > target) { // 如果左下角的数值大于要找的数字，则向右，否则向下
				len--;
			} else if (array[len][i] < target) {
				i++;
			} else {
				return true;
			}
		}
		return false;
	}

}

package com.pancm.arithmetic;

/**
* @Title: StockTest
* @Description: 一些常用的算法技巧
* 参考文章:https://www.cnblogs.com/kubidemanong/p/9887669.html
* @Version:1.0.0  
* @author pancm
* @date 2018年11月2日
*/
public class StockTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
			//乱序改有序
			test1();
			//利用取余方式防止数组下标越界
			test2();
			//对于可以递归的问题考虑状态保存
			test3();
			
			test4();
	}

	/**
	 *创建 n个无序的int整型数组arr，并且这些整数的取值范围都在1-5之间，
	 * 使用 O(n) 的时间复杂度中把这 n 个数按照从小到大的顺序打印出来。
	 */
	private static void test1() {
		System.out.println("----------");
		int arr[]= {2,3,5,4,1};
		 int temp[] = new int[6];
	       for (int i = 0; i < arr.length; i++) {
	           temp[arr[i]]++;
	       }
	       //顺序打印
	       for (int i = 0; i < 6; i++) {
	           for (int j = 0; j < temp[i]; j++) {
	               System.out.println(i);
	           }
	       }
	       System.out.println("----------");
	}

	/**
	 * 利用取余方式防止数组下标越界
	 */
	private static void test2() {
		System.out.println("----------");
		int arr[]= {2,3,5,4,1};
		int k= 0;
		int m=5;
		//传统方式
		for(int i=0;i<m;i++) {
			if(k<m) {
				//使用数组
				System.out.println(arr[k]);
			}else {
				//置为0再使用数组
				k=0;
			}
			k++;
		}
		System.out.println("---");
		//利用取模方式
		for(int i=0;i<m;i++) {
			  //需要注意的是 k<m
			  k = (k + 1) % m;
			  System.out.println(arr[k]);
		}
		System.out.println("----------");
	}

	/**
	 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
	 *  求该青蛙跳上一个n级的台阶总共有多少种跳法
	 */
	private static void test3() {
		System.out.println("----------");
		int n = 10;
		//方法一：最简单递归
		System.out.println(f(n));
		System.out.println("----");
		//方法二：状态保存
		int[] arr = new int[1000];
		System.out.println(f2(n,arr));
		System.out.println("----");
		//方法三：自底向上
		System.out.println(f3(n));
		System.out.println("----");
		System.out.println("----------");
		
		/*
		 * 总结
		当你在使用递归解决问题的时候，要考虑以下两个问题
		(1). 是否有状态重复计算的，可不可以使用备忘录法来优化。
		(2). 是否可以采取递推的方法来自底向上做，减少一味递归的开销。
				 
		 */
	}
	
	/**
	 * @param n
	 * @return
	 */
	private static int f3(int n) {
		 if(n <= 2) {
			 return n;
		 }
	       int f1 = 1;
	       int f2 = 2;
	       int sum = 0;
	       for (int i = 3; i <= n; i++) {
	           sum = f1 + f2;
	           f1 = f2;
	           f2 = sum;
	       }
	       return sum;
	}

	private static int f(int n) {
	       if (n <= 2) {
	           return n;
	       } else {
	           return f(n - 1) + f(n - 2);
	       }
	   }
	
	private static  int f2(int n,int[] arr ) {
	       if (n <= 2) {
	           return n;
	       } else {
	           if (arr[n] != 0) {
	               return arr[n];//已经计算过，直接返回
	           } else {
	               arr[n] = f(n-1) + f(n-2);
	               return arr[n];
	           }
	       }
	   }
	
	/**
	 * 
	 */
	private static void test4() {
		// TODO Auto-generated method stub
		
	}

	
}

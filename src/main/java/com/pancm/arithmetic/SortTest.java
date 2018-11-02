package com.pancm.arithmetic;

import java.util.Arrays;

/**
 * 
* @Title: SortTest
* @Description: 排序算法
* 主要包括插入、二分、冒泡和快排算法
* @Version:1.0.0  
* @author pancm
* @date 2017-5-31
 */
public class SortTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] t={1,44,55,22,34,88,3};
		for(int i=0,j=t.length;i<j;i++){
			System.out.println("排序之前:"+t[i]);
		}
//		long a=System.currentTimeMillis();
//		int[] k=ps(t);
//		for(int i=0,j=k.length;i<j;i++){
//			System.out.println("插入排序之后:"+k[i]);
//		}
//		
		int[] s=crps(t);
		for(int i=0,j=s.length;i<j;i++){
			System.out.println("插入排序倒叙之后:"+s[i]);
		}
		
		
//		System.out.println("\r<br>执行耗时 : "+(System.currentTimeMillis()-a)+"毫秒 ");
//	    int[] p=sort(t);
//	    for(int i=0,j=p.length;i<j;i++){
//			System.out.println("二分法排序之后:"+p[i]);
//		}
//	    
//		int[] l=mp(t);
//		for(int i=0,j=l.length;i<j;i++){
//			System.out.println("冒泡排序之后:"+l[i]);
//		}
//		
		int[] mpdx=mpdx(t);
		for(int i=0,j=mpdx.length;i<j;i++){
			System.out.println("冒泡排序倒叙之后:"+mpdx[i]);
		}
		
		 for (int i = 0; i < t.length; i++) {
	         System.out.print(t[i]+" ");
	     }
	     //快速排序
	     quick(t);
	     System.out.println();
	     System.out.println("快速排序之后：");
	     for (int i = 0; i < t.length; i++) {
	         System.out.print(t[i]+" ");
	     }
	     
		Arrays.sort(t);
//		for(int i:t){
//			System.out.println("快速排序之升序:"+i);
//		}
//	    for(int j=t.length-1;0<=j;j--){ 
//	    	System.out.println("快速排序之降序:"+t[j]);
//	    }
//	    System.out.println("\r<br>执行耗时 : "+(System.currentTimeMillis()-a)+"毫秒 ");
	}

	/**
	 * 插入排序  升序
	 * 插入排序是循环数组，然后将前一位的数字和后一位的进行比较，将数值大的向后移一位
	 * @param a
	 * @return
	 */
	public static int[] ps(int[] a){
		for(int i=1,j=a.length;i<j;i++){
			 int t=a[i];
			 int k;
			 for(k=i-1;k>=0;k--){
				 if(a[k]>t){
					a[k+1]=a[k]; 
				 }else{
					 break;
				 }				 
			 }
			a[k+1]=t;
		}
		return a; 
	}
   
	/**
	 * 插入排序  降序
	 * 插入排序是循环数组，然后将前一位的数字和后一位的进行比较，将数值大的向后移一位
	 * @param a
	 * @return
	 */
	public static int[] crps(int[] a){
		for(int i=1,j=a.length;i<j;i++){
			 int t=a[i];
			 int k;
			 for(k=i-1;k>=0;k--){
				 if(a[k]<t){
					a[k+1]=a[k]; 
				 }else{
					 break;
				 }				 
			 }
			a[k+1]=t;
		}
		return a; 
	}
	
	
	/**
	 * 冒泡排序 升序
	 * 冒泡排序 是双重循环数组，前一位和后一位进行比较，若前一位大于后一位，就交换位置
	 */
	public static int[] mp(int[] m){
		for(int i=0;i<m.length-1;i++){
			for(int j=i+1;j<m.length;j++){
				if(m[i]>m[j]){
					int tmp=m[i];
					m[i]=m[j];
					m[j]=tmp;
				}
			}
		}
		return m;	
	}
	
	/**
	 * 冒泡排序 倒序
	 * 冒泡排序 是双重循环数组，前一位和后一位进行比较，若前一位大于后一位，就交换位置
	 */
	public static int[] mpdx(int[] a){
		for(int i=0;i<a.length-1;i++){
			for(int j=i+1;j<a.length;j++){
				if(a[i]<a[j]){
					int tmp=a[i];
					a[i]=a[j];
					a[j]=tmp;
				}
			}
		}
		return a;	
	}
	
	/**
	 * 二分法排序  升序
	 * @param a
	 * @return
	 */
	public static int[] sort(int[] a) {
		for (int i = 0; i < a.length; i++) {
		  int temp = a[i];
		  int left = 0;
		  int right = i-1;
	      int mid = 0;
          while(left<=right){
		    mid = (left+right)/2;
	     	 if(temp<a[mid]){
		        right = mid-1;
              }else{
                  left = mid+1;
              }
          }
	      for (int j = i-1; j >= left; j--) {
	            a[j+1] = a[j];
	       }
	      if(left != i){
	            a[left] = temp;
	       }
	    }
		return a;
   }
			
 /**
  * 快速排序
  * @param a
  */
 private static void quick(int[] a) {
     if(a.length>0){
         quickSort(a,0,a.length-1);
     }
 }

 private static void quickSort(int[] a, int low, int high) {
     if(low<high){ //如果不加这个判断递归会无法退出导致堆栈溢出异常
         int middle = getMiddle(a,low,high);
         quickSort(a, 0, middle-1);
         quickSort(a, middle+1, high);
     }
 }

 private static int getMiddle(int[] a, int low, int high) {
     int temp = a[low];//基准元素
     while(low<high){
         //找到比基准元素小的元素位置
         while(low<high && a[high]>=temp){
             high--;
         }
         a[low] = a[high]; 
         while(low<high && a[low]<=temp){
             low++;
         }
         a[high] = a[low];
     }
     a[low] = temp;
     return low;
 }
	
}

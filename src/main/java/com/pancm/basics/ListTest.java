package com.pancm.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * 
* @Title: ListTest
* @Description:关于list测试 
* @Version:1.0.0  
* @author pancm
* @date 2017年10月13日
 */
public class ListTest {
	
	private final static int count=50000;
	
	private static ArrayList arrayList = new ArrayList<>();  
    private static LinkedList linkedList = new LinkedList<>();  
    private static Vector vector = new Vector<>();  
	 

	public static void main(String[] args) {
//		test1();
//		test2();
		test3();
//		test4();
//		test5();
		
		
		Vector<String> v=new Vector<>();
	}
	 
	
	/**
	 * 遍历方法
	 */
	private static void test1() {
	 List<String> list=new ArrayList<String>();
     list.add("a");
     list.add("b");
     list.add("c");
     
     System.out.println("list截取:"+list.subList(0, 1));
     System.out.println("list截取:"+list.subList(1, 3));
     
     
     //第一种使用
     for(int i=0;i<list.size();i++){
    	 System.out.println(list.get(i));
     }
     //第二种遍历方法使用foreach遍历List
     for (String str : list) {  
    	 System.out.println(str);
     }
     
     //第三种遍历 使用迭代器进行相关遍历
     Iterator<String> iterator=list.iterator();
     while(iterator.hasNext())//判断下一个元素之后有值
     {
         System.out.println(iterator.next());
     }
	}
	
	/**
	 * 数组变化遍历
	 */
	private static void test2() {
		
		List<String> list1 = new ArrayList<String>();
		 list1.add("1");
		 list1.add("2");
		System.out.println("list1遍历之前:"+list1);
		 Iterator<String> iterator = list1.iterator();
		 while (iterator.hasNext()) {
			 String item = iterator.next();
			 if ("2".equals(item)) {
				 iterator.remove();
			 }
		 }
		 System.out.println("list1遍历之后:"+list1);
		
		 List<String> list = new ArrayList<String>();
		 list.add("1");
		 list.add("2");
		 System.out.println("list遍历之前:"+list);
		 for (String item : list) {
		   if ("2".equals(item)) {
		    list.remove(item);
		    //如果这里不适用break的话，会直接报错的
		    break; 
		 }
	   } 
		System.out.println("list遍历之后:"+list);
		
		
	}
	
	
	/**
	 * 常用方法
	 */
	private static void test3() {
		List<Integer> list=initData(100);
		System.out.println("list:"+list);
		System.out.println("removeList:"+removeList(list,10,20));
		list.subList(10, 20).clear();
		System.out.println("subList:"+list);
		
		List<String> ls1=new ArrayList<String>();
		List<String> ls2=new ArrayList<String>();
		ls1.add("a");
		ls1.add("b");
		ls1.add("c");
		ls2.add("a");
		ls2.add("b");
		ls2.add("c");
		ls2.add("e");
//        System.out.println("合集:"+addAll(ls1,ls2));			
//        System.out.println("交集 :"+retainAll(ls1,ls2));		
        System.out.println("差集 :"+removeAll(ls2,ls1));		
//        System.out.println("并集 :"+andAll(ls1,ls2));		
		
	}
	
	private static  void test4() {
		LinkedList<Integer> list=new LinkedList<Integer>();	
		list.add(3);
		list.add(5);
		list.add(4);
		System.out.println(list);
		list.addFirst(2);
		list.addLast(4);
		System.out.println(list);
		
		LinkedList<Integer> list2=new LinkedList<Integer>();
		list2.add(1);
		list2.add(2);
		list2.add(4);
		list2.add(4);
		list2.add(6);
		list2.add(6);
		list2.add(5);
		System.out.println("去重之前:"+list2);
		//jdk1.8去重
		List<Integer> newList = list2.stream().distinct().collect(Collectors.toList()); 
		System.out.println("去重之后:"+newList);
		
	}
	
	private static  void test5() {
		insertList(arrayList);
		insertList(linkedList);
		insertList(vector);
		
		System.out.println("--------------------");
		
		readList(arrayList);
		readList(linkedList);
		readList(vector);
		
		System.out.println("--------------------");
		
		delList(arrayList);
		delList(linkedList);
		delList(vector);
		
	}
	
	
	private  static void insertList(List list){   
	     long start=System.currentTimeMillis();   
	     Object o = new Object();   
	     for(int i=0;i<count;i++){   
	         list.add(0, o);   
	     }
	    System.out.println(getName(list)+"插入"+count+"条数据，耗时:"+(System.currentTimeMillis()-start)+"ms");
	 }   
	
	private  static void readList(List list){   
	     long start=System.currentTimeMillis();   
	     Object o = new Object();   
	     for(int i = 0 ; i < count ; i++){  
	            list.get(i);  
	        }
	    System.out.println(getName(list)+"查询"+count+"条数据，耗时:"+(System.currentTimeMillis()-start)+"ms");
	 }  
	
	
	private  static void delList(List list){   
	     long start=System.currentTimeMillis();   
	     Object o = new Object();   
	     for(int i = 0 ; i < count ; i++){  
	    	 list.remove(0);   
	        }
	    System.out.println(getName(list)+"删除"+count+"条数据，耗时:"+(System.currentTimeMillis()-start)+"ms");
	 }  
	
	private static String getName(List list) {  
        String name = "";  
        if(list instanceof ArrayList){  
            name = "ArrayList";  
        }  
        else if(list instanceof LinkedList){  
            name = "LinkedList";  
        }  
        else if(list instanceof Vector){  
            name = "Vector";  
        }  
        return name;  
    }  
	
	/**
	 * for循环移除指定数据
	 * @param list 
	 * @param s  要移除的起始位置
	 * @param d  要移除的最后位置 
	 */
	private static List<Integer> removeList(List<Integer> list,int s,int d){
		for(int i=0,j=list.size();i<j;i++){
			if(i>=s&&i<d){
				list.remove(i);
			}
		}
		return list;
	}
    
	/**
	 * 获取数组数据
	 * @param j
	 * @return
	 */
	private static List<Integer> initData(int j){
		 List<Integer> list=new ArrayList<Integer>();  
		 for(int i=1;i<=j;i++){
			 list.add(i);
		 }
		return list;
	}
	
	/**
	 * 数组合集
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	private static List<String> addAll(List<String> ls1,List<String>ls2){
		ls1.addAll(ls2);
		return ls1;
	}
	
	/**
	 * 数组交集 （retainAll 会删除 ls1在ls2中没有的元素）
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	private static List<String> retainAll(List<String> ls1,List<String>ls2){
		System.out.println("ls1:"+ls1+";ls2:"+ls2);
		ls1.retainAll(ls2);
		return ls1;
	}
	
	/**
	 * 差集 (删除ls2中没有ls1中的元素)
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	private static List<String> removeAll(List<String> ls1,List<String>ls2){
		System.out.println("ls1:"+ls1+";ls2:"+ls2);
		ls1.removeAll(ls2);
		return ls1;
	}
	
	/**
	 * 无重复的并集 (ls1和ls2中并集，并无重复)
	 * @param ls1
	 * @param ls2
	 * @return
	 */
	private static List<String> andAll(List<String> ls1,List<String>ls2){
		//删除在ls1中出现的元素
		ls2.removeAll(ls1);
		//将剩余的ls2中的元素添加到ls1中
		ls1.addAll(ls2);
		System.out.println(ls1+";ls2:"+ls2);
		return ls1;
	}
} 

package com.pancm.thread.concurrent.liveLock;



/**
 * 
* Title: 
* Description: 
* 多线程共享测试
* Version:1.0.0  
* @author pancm
* @date 2018年3月8日
 */
public class test{
	 
	public static void main(String[] args) {
		     Drop drop=new Drop();
	        (new Thread(new Producer(drop))).start();
	        (new Thread(new Consumer(drop))).start();
	 }
}
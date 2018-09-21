package com.pancm.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
* Title: volatileTest1
* Description: 
* volatile关键字的测试
* Version:1.0.0  
* @author pancm
* @date 2017年9月21日
 */
public class VolatileTest2 {
	 public volatile long inv = 0; //使用volatile 保证了可见性，其他线程也可以查看更新之后的值
	 public  long ins = 0;  
	
	 public  long inl = 0;  
	 Lock lock = new ReentrantLock();
     
	    public void increase() {
	        inv++;
	    }
	    
	    public synchronized void insrease() { //使用synchronized，可以保证原子性
	        ins++;
	    }
	
	    public void inlrease(){    //使用loca 也可以保证
	    	 lock.lock();
	         try {
	             inl++;
	         } finally{
	             lock.unlock();
	         }
	    }
	    
	    
	public static void main(String[] args) {
		volatileTs();	
		synTs();
		lockTs();
	   }
	
	/**
	 * 使用 volatile 是无法保证 原子性的 
	 * 因为 自增操作不是原子性操作
	 */
	public static void volatileTs(){
		 final VolatileTest2 test = new VolatileTest2();
	        for(int i=0;i<10;i++){
	            new Thread(){
	                @Override
					public void run() {
	                    for(int j=0;j<1000;j++) {
							test.increase();
						}
	                };
	            }.start();
	        }
	        while(Thread.activeCount()>1) {
				Thread.yield();
			}
	        System.out.println(test.inv); //数据小于 10000  例如:9303,9068
	}
	
	/**
	 * 使用synchronized，可以保证原子性
	 */
	public static void synTs(){
		 final VolatileTest2 test = new VolatileTest2();
	        for(int i=0;i<10;i++){
	            new Thread(){
	                @Override
					public void run() {
	                    for(int j=0;j<1000;j++) {
							test.insrease();
						}
	                };
	            }.start();
	        }
	        while(Thread.activeCount()>1) {
				Thread.yield();
			}
	        System.out.println(test.ins); // 10000   保证了原子性
	}
	
	/**
	 * 使用lock，可以保证原子性
	 */
	public static void lockTs(){
		 final VolatileTest2 test = new VolatileTest2();
	        for(int i=0;i<10;i++){
	            new Thread(){
	                @Override
					public void run() {
	                    for(int j=0;j<1000;j++) {
							test.inlrease();
						}
	                };
	            }.start();
	        }
	        while(Thread.activeCount()>1) {
				Thread.yield();
			}
	        System.out.println(test.inl); //10000  保证了原子性
	}
	
	
	}


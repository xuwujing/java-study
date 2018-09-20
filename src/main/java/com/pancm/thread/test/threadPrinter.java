package com.pancm.thread.test;
/**
 * @author ZERO
 * @Data 2017-5-27 下午4:27:21
 * @Description 
 * 测试Object notify和wait方法
 */
public class threadPrinter implements Runnable {

	    private String name;     
	    private Object prev;     
	    private Object self;     
	    
	    private threadPrinter(String name, Object prev, Object self) {     
	        this.name = name;     
	        this.prev = prev;     
	        this.self = self;     
	    }     
	    
	    @Override    
	    public void run() {     
	        int count = 10;     
	        while (count > 0) {     
	            synchronized (prev) {     
	                synchronized (self) {     
	                    System.out.print(name);     
	                    count--;    
	                    self.notify();     
	                }     
	                try {     
	                    prev.wait();     
	                } catch (InterruptedException e) {     
	                    e.printStackTrace();     
	                }     
	            }     
	    
	        }     
	    }     
	    
	     /**
	      * 建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC。
	      * 思路:
	      * 同时运行这三个线程，打印A、B、C，确保线程的执行顺序是ABC,可以加上Thread.sleep()进行简单的控制
	      * 在运行A线程的时候，确保上一个线程不在运行，那么先唤醒本线程，然后 使上一个线程进行等待
	      * 
	      * @param args
	      * @throws Exception
	      */
	    public static void main(String[] args) throws Exception {     
	        Object a = new Object();     
	        Object b = new Object();     
	        Object c = new Object();     
	        threadPrinter pa = new threadPrinter("A", c, a);     
	        threadPrinter pb = new threadPrinter("B", a, b);     
	        threadPrinter pc = new threadPrinter("C", b, c);     
	             
	             
	        new Thread(pa).start();  
	        Thread.sleep(100);  //确保按顺序A、B、C执行  
	        new Thread(pb).start();  
	        Thread.sleep(100);    
	        new Thread(pc).start();     
	        Thread.sleep(100);    
	     }     
}

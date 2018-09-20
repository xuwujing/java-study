package com.pancm.thread.test;

import java.util.Random;

/**
 * @author ZERO
 * @Data 2017-5-24 下午5:15:23
 * @Description 
 * 线程优先级测试
 */
class Thread1 extends Thread{  
    public Thread1(String name) {  
        super(name);  
    }  
    public void run() {  
        System.out.println(this.getName() + " 线程运行开始!");  
        for (int i = 0; i < 5; i++) {  
            System.out.println("子线程"+this.getName() + "运行 : " + i);  
            try {  
                sleep(new Random().nextInt(10));  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println(this.getName() + " 线程运行结束!");  
    }
}
  
/**
 * 
* Title: ThreadYield
* Description: 
*  
 */
class ThreadYield extends Thread{  
    public ThreadYield(String name) {  
        super(name);  
    }  
   
	@Override  
    public void run() {  
        for (int i = 1; i <= 10; i++) {  
            System.out.println("" + this.getName() + "-----" + i);  
            // 当i为5时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）  
            if (i ==5) {  
            	this.yield();  
            }  
        }  
 }  
}
 

	public class threadMain {
	    
	 public static void main(String[] args) {  
//		    noJoinThread();
//		 	joinThread();
//		    yieldThread();
		    setPriorityThread();
		 
		 
	    
	 } 
	
	 public static void noJoinThread(){
		 System.out.println(Thread.currentThread().getName()+"主线程运行开始!");  
	     Thread1 mTh1=new Thread1("A");  
	     Thread1 mTh2=new Thread1("B");  
	     mTh1.start();  
	     mTh2.start();  
	     System.out.println(Thread.currentThread().getName()+ "主线程运行结束!");  
	   /**输出:
	    * main主线程运行开始!
			main主线程运行结束!
			A 线程运行开始!
			B 线程运行开始!
			子线程B运行 : 0
			子线程A运行 : 0
			子线程A运行 : 1
			子线程A运行 : 2
			子线程A运行 : 3
			子线程A运行 : 4
			A 线程运行结束!
			子线程B运行 : 1
			子线程B运行 : 2
			子线程B运行 : 3
			子线程B运行 : 4
			B 线程运行结束!
			
			主线程比子线程先结束。因为子线程花费的大量时间运算，如果防止这种情况发生，那么就需要使用join()方法。
			B线程先运行，但是A线程先结束。说明多线程程序是乱序执行。
	    * */ 
	 }
	 
	 //join指等待t线程终止
	 public static void joinThread(){
		 System.out.println(Thread.currentThread().getName()+"主线程运行开始!");  
	     Thread1 mTh1=new Thread1("A");  
	     Thread1 mTh2=new Thread1("B");  
	     mTh1.start();  
	     mTh2.start();  
	      try {  
	            mTh1.join();  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	        try {  
	            mTh2.join();  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }    
	     System.out.println(Thread.currentThread().getName()+ "主线程运行结束!");  
	   /**输出:
	    *   main主线程运行开始!
			A 线程运行开始!
			子线程A运行 : 0
			B 线程运行开始!
			子线程B运行 : 0
			子线程A运行 : 1
			子线程A运行 : 2
			子线程A运行 : 3
			子线程A运行 : 4
			A 线程运行结束!
			子线程B运行 : 1
			子线程B运行 : 2
			子线程B运行 : 3
			子线程B运行 : 4
			B 线程运行结束!
			main主线程运行结束!
			
			添加join方法，会使 主线程一定会等子线程都结束了才结束。
			B线程先运行，但是A线程先结束。说明多线程程序是乱序执行。
	    * */ 
	 }
	 
	 //yield():暂停当前正在执行的线程对象，并执行其他线程。
	 public static void yieldThread(){
		 ThreadYield yt1 = new ThreadYield("张三");  
	     ThreadYield yt2 = new ThreadYield("李四");  
	     yt1.start();  
	     yt2.start();    
	   /**输出:
	    *   张三-----1
			李四-----1
			李四-----2
			李四-----3
			李四-----4
			李四-----5
			张三-----2
			张三-----3
			张三-----4
			张三-----5
			李四-----6
			李四-----7
			李四-----8
			李四-----9
			李四-----10
			张三-----6
			张三-----7
			张三-----8
			张三-----9
			张三-----10
	       
	                       先乱序执行，当谁到达5的时候，让出CPU资源，让另一个执行。
	        
	    * */ 
	 }
	 
	 //setPriority(): 设置线程的优先级。数值在0-10之间, 数值越大，优先级越高 
	 public static void setPriorityThread(){
		 Thread1 t1=new Thread1("A");  
		 Thread1 t2=new Thread1("B");  
//		 t1.setPriority(Thread.MAX_PRIORITY);
//		 t2.setPriority(Thread.MIN_PRIORITY);
		 t1.setPriority(1);
		 t2.setPriority(2);
		 t1.start();
		 t2.start();
		 
		 /**
		  * A 线程运行开始!
			子线程A运行 : 0
			子线程A运行 : 1
			B 线程运行开始!
			子线程B运行 : 0
			子线程A运行 : 2
			子线程B运行 : 1
			子线程A运行 : 3
			子线程B运行 : 2
			子线程A运行 : 4
			子线程B运行 : 3
			A 线程运行结束!
			子线程B运行 : 4
			B 线程运行结束!
			
			A线程优先级比B线程高，因此优先执行A线程。所以A线程一定比B线程先执行完。
		  */
		 
	 }
	 
 
    }	


package com.pancm.thread.test;
/**
 * @author ZERO
 * @Data 2017-5-24 下午2:22:41
 * @Description 
 */
public class ThreadTest1 {

	  public static void main(String[] args) {
		  myThread();
		  System.out.println("\r\n");
		  myRunnable();
      }
   
	  
	  public static void myThread(){
		  for (int i = 0; i < 10; i++) {
              System.out.println("myThreadTest:"+Thread.currentThread().getName() + " " + i);
             if (i == 3) {
                  Thread myThread1 = new MyThread();     // 创建一个新的线程  myThread1  此线程进入新建状态
                  Thread myThread2 = new MyThread();     // 创建一个新的线程 myThread2 此线程进入新建状态
                  myThread1.start();                     // 调用start()方法使得线程进入就绪状态
                  myThread2.start();                     // 调用start()方法使得线程进入就绪状态
             }
         }
	  }
	  
	  public static void myRunnable(){
		  for (int i = 0; i < 10; i++) {
              System.out.println("myRunnableTest:"+Thread.currentThread().getName() + " " + i);
             if (i == 3) {
            	Runnable myRunnable = new MyRunnable(); // 创建一个Runnable实现类的对象
            	Thread thread1 = new Thread(myRunnable); // 将myRunnable作为Thread target创建新的线程
            	Thread thread2 = new Thread(myRunnable);
            	thread1.start(); // 调用start()方法使得线程进入就绪状态
                thread2.start();
             }
         }
	  }
	  
	  
}



package com.pancm.thread.test;
/**
 * @author ZERO
 * @Data 2017-5-24 下午2:20:29
 * @Description 
 */
public class MyThread extends Thread{

	private int i = 0;
	 
	 @Override
     public void run() {
        for (i = 0; i < 10; i++) {
             System.out.println("MyThread:"+Thread.currentThread().getName() + "第" + i+ "次");
         }
    }
	
	 
	 
	 
}

package com.pancm.thread.test;

import org.junit.Test;

/**
 * @author ZERO
 * @Data 2017-5-18 上午9:47:55
 * @Description  线程测试
 */
public class threadTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
       
		
	}
  
	@Test
    public void createThread1(){
        Thread t1 = new Thread(){
            public void run(){
                    System.out.println("创建线程的方式1");
            }
        };
        t1.start();
    }
	
	@Test
    public void createThread2(){
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("创建线程的方式2");
            }

        });
        t2.start();
    }
}

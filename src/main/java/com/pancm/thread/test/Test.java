/**
 * 
 */
package com.pancm.thread.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
* @Title: Test
* @Description: 
* @Version:1.0.0  
* @author pancm
* @date 2018年5月17日
*/
public class Test {

	public static void main(String[] args) {
		ThreadTest threadTest=new ThreadTest();
		threadTest.setPriority(1);
		threadTest.start();

		RunalbeTest runalbeTest=new RunalbeTest();
		Thread thread=new Thread(runalbeTest);
		thread.setPriority(10);
		thread.start();
		
		CallableTest callableTest=new CallableTest();
		FutureTask<Integer> ft = new FutureTask<Integer>(callableTest);  
		Thread thread2=new Thread(ft);
		thread2.setPriority(5);
		thread2.start();
		try {
			System.out.println("返回值:"+ft.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}


class ThreadTest extends Thread{
	 @Override
     public void run() {
		 for(int i=1;i<5;i++){
			 System.out.println("这是一个Thread的线程!"+i);
			 try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
		 System.out.println("Thread的线程执行完了!");
		 
    }
}


class RunalbeTest implements Runnable{
	 @Override
     public void run() {
		 for(int i=1;i<5;i++){
			 System.out.println("这是一个Runnable的线程!"+i);
			  try {
				 Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		 }
		 System.out.println("Runnable的线程执行完了!");

    }
}


class CallableTest implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		for(int i=1;i<5;i++){
			 System.out.println("这是一个Callable的线程!"+i);
			 try {
				 Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		 }
		 System.out.println("Callable的线程执行完了!");
		return 2;
	}
}

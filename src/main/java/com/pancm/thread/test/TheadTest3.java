package com.pancm.thread.test;

public class TheadTest3 {

	public static void main(String[] args) throws InterruptedException {
		MyRunnable myRunnable=new MyRunnable();
		for(int i=1;i<=5;i++){
			Thread thread=new Thread(myRunnable);
			thread.setName("myRunnable-"+i);
			thread.start();
		}
		Thread.sleep(2000);
		myRunnable.set(true);
		Thread.sleep(3000);
		myRunnable.set(false);
		for(int i=1;i<=5;i++){
			MyThread myThread=new MyThread();
			myThread.setName("myThread-"+i);
			myThread.start();
		}
		
		
		System.out.println("结束...");
	}
	
	

}

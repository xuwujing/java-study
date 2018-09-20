package com.pancm.thread.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 
* Title: executorTest
* Description:线程池 
* Version:1.0.0  
* @author pancm
* @date 2017年11月20日
 */
public class executorTest {

	public static void main(String[] args) {
		//创建一个其线程池具有 10 个线程的ScheduledExecutorService
		ScheduledExecutorService executor =Executors.newScheduledThreadPool(10);
		System.out.println("开始...");
		//创建一个 Runnable，以供调度稍后执行
		ScheduledFuture<?> future = executor.schedule(
				new Runnable() {
				@Override
				public void run() {
				System.out.println("30 seconds later");
				}
				}, 30, TimeUnit.SECONDS); //调度任务在从现在开始的 60 秒之后执行
				executor.shutdown(); //执行完毕，释放资源
	}

}

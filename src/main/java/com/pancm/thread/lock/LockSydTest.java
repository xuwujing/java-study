package com.pancm.thread.lock;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @Title: LockSydTest
* @Description: Lock(显示锁)和synchronized(内部锁) 测试
* @Version:1.0.0  
* @author pancm
* @date 2017年10月18日
 */
public class LockSydTest {

	public static void main(String[] args) {
		try {
			runTasks(lockTest.class);
			runTasks(synTest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 任务执行器
	 * @param cl 
	 * @throws Exception
	 */
	public static void runTasks(Class <? extends Runnable> cl) throws Exception{
		ExecutorService es=Executors.newCachedThreadPool(); //创建一个执行器
		System.out.println("---开始执行---"+cl.getSimpleName()+" 任务");
		//启动三个线程
		for(int i=0;i<3;i++){
			es.submit(cl.newInstance());
		}
		//等待足够长的时间，然后关闭执行器
		TimeUnit.SECONDS.sleep(10);
		System.out.println("---结束任务---"+cl.getSimpleName()+" 任务执行完毕 \n");
		es.shutdown();//关闭执行器
		
		
		/*
		 * 输出结果:
		 * ---开始执行---lockTest 任务
			线程名称:pool-1-thread-2,执行时间:35 s
			线程名称:pool-1-thread-3,执行时间:35 s
			线程名称:pool-1-thread-1,执行时间:35 s
			---结束任务---lockTest 任务执行完毕 
			
			---开始执行---synTest 任务
			线程名称:pool-2-thread-1,执行时间:45 s
			线程名称:pool-2-thread-3,执行时间:47 s
			线程名称:pool-2-thread-2,执行时间:49 s
			---结束任务---synTest 任务执行完毕 
		 * 
		 */
		
		/* 总结:
		 * lock(显示锁) 是对象级别的锁，而synchronized(内部锁)是类级别的锁，
		 * 也就是说显示锁是跟着对象的，而内部锁是跟随类的。
		 * 简单来说的话，把lock定义为多线程类的私有属性是起不到资源互斥作用的，
		 * 除非把lock定义为所有线程的共享变量。
		 * 
		 */
	}
}

class Task{
	public void doSomething(){
		try{
			Thread.sleep(2000); //等待2秒，此处线程状态转为WAITING
		}catch(Exception e){
			
		}
		StringBuffer sb=new StringBuffer();
		sb.append("线程名称:"+Thread.currentThread().getName());
		sb.append(",执行时间:"+Calendar.getInstance().get(13)+" s");
		System.out.println(sb);
	}
}

/**
 * 
* Title: lockTest
* Description:  Lock（显示锁）测试
* Version:1.0.0  
* @author pancm
* @date 2017年10月18日
 */
class lockTest extends Task implements Runnable{
	private final Lock lock=new ReentrantLock();
	@Override
	public void run() {
		try{
			lock.lock();
			doSomething();
		}catch(Exception e){
			
		}finally{
			lock.unlock(); //释放锁
		}
	}
}

/**
 * 
* Title: lockTest
* Description:  synchronized（内部锁）测试
* Version:1.0.0  
* @author pancm
* @date 2017年10月18日
 */
class synTest extends Task implements Runnable{
	@Override
	public void run() {
		synchronized("A"){
			doSomething();
		}
	}
}

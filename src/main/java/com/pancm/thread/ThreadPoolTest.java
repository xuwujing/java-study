package com.pancm.thread;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
* @Title: ThreadPoolTest
* @Description:
* 线程池测试 
* @Version:1.0.0  
* @author pancm
* @date 2018年3月1日
 */
public class ThreadPoolTest {

	public static void main(String[] args) {
//		cachedThreadPool();
//		fixedThreadPool();
//		newSingleThreadExecutor();
//		newScheduledThreadPool();
//		newScheduledThreadPool2();
		threadPoolExecutor();
	}
	
	/**
	 * 创建一个可缓存的线程池，如果当前线程池的规模超出了处理需求，将回收空的线程；当需求增加时，会增加线程数量；线程池规模无限制。
	 */
	private  static void cachedThreadPool() {
		ExecutorService exec=Executors.newCachedThreadPool();
		for(int i=0;i<10;i++){
			exec.execute(new MyThread(String.valueOf(i)));
		}
		//执行到此处并不会马上关闭线程池,执行完成之后才会关闭 但之后不能再往线程池中加线程，否则会报错  
		exec.shutdown(); 
		System.out.println("运行结束！");
		/**
		 * 1、主线程的执行与线程池里的线程分开，有可能主线程结束了，但是线程池还在运行
		 * 2、放入线程池的线程并不一定会按其放入的先后而顺序执行
		 * 
		 */
	}
	
	/**
	 * 创建一个固定长度的线程池，当到达线程最大数量时，线程池的规模将不再变化。
	 */
	private static void fixedThreadPool() {
		ExecutorService exec = Executors.newFixedThreadPool(5);     
	     for(int i = 0; i < 10; i++) {     
	            exec.execute(new MyThread(String.valueOf(i)));     
	     }     
	     exec.shutdown();  //执行到此处并不会马上关闭线程池  
	     System.out.println("运行结束！");
	     /**
	      * 1线程开始运行,时间2018-03-01 11:50:33 170
			4线程开始运行,时间2018-03-01 11:50:33 170
			3线程开始运行,时间2018-03-01 11:50:33 170
			2线程开始运行,时间2018-03-01 11:50:33 170
			0线程开始运行,时间2018-03-01 11:50:33 170
			4线程运行结束,时间2018-03-01 11:50:34 171
			3线程运行结束,时间2018-03-01 11:50:34 171
			2线程运行结束,时间2018-03-01 11:50:34 171
			5线程开始运行,时间2018-03-01 11:50:34 172
			6线程开始运行,时间2018-03-01 11:50:34 172
			7线程开始运行,时间2018-03-01 11:50:34 172
			1线程运行结束,时间2018-03-01 11:50:34 172
			0线程运行结束,时间2018-03-01 11:50:34 172
			8线程开始运行,时间2018-03-01 11:50:34 172
			9线程开始运行,时间2018-03-01 11:50:34 172
			8线程运行结束,时间2018-03-01 11:50:35 181
			6线程运行结束,时间2018-03-01 11:50:35 181
			9线程运行结束,时间2018-03-01 11:50:35 181
			7线程运行结束,时间2018-03-01 11:50:35 181
			5线程运行结束,时间2018-03-01 11:50:35 181
			
			结论:1,FixedThreadPool模式会使用一个优先固定数目的线程来处理若干数目的任务。
			    2,FixedThreadPool模式下最多 的线程数目是一定的。
			
	      */
	}
	
	/**
	 * 创建一个单线程的Executor，确保任务对了，串行执行
	 */
	private static void newSingleThreadExecutor() {
		 ExecutorService exec = Executors.newSingleThreadExecutor();   //创建大小为1的固定线程池  
	     for(int i = 0; i < 10; i++) {     
	            exec.execute(new MyThread(String.valueOf(i)));     
	     }     
	     exec.shutdown();  //执行到此处并不会马上关闭线程池  
	     System.out.println("运行结束！");
	}
	
	
	/**
	 * 创建一个固定长度的线程池，而且以延迟或者定时的方式来执行，类似Timer；
	 */
	private static void newScheduledThreadPool() {
		 ScheduledThreadPoolExecutor  exec = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);   //创建大小为10的线程池  
	     for(int i = 0; i < 10; i++) {     
	            exec.schedule(new MyThread(String.valueOf(i)), 2, TimeUnit.SECONDS);//延迟2秒执行  
	     }     
	     //如果任务都完成了则返回true
	     while(!exec.isTerminated()){  
	            //wait for all tasks to finish  
//	    	 System.out.println("正在运行中...");
	     }  
	     System.out.println("运行结束！");
	}
	
	/**
	 * 设置固定时间执行
	 */
	private static void newScheduledThreadPool2() {
		 ScheduledThreadPoolExecutor  exec = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(10);   //创建大小为10的线程池  
		 long oneDay = 24 * 60 * 60 * 1000;    
		 long initDelay  = getTimeMillis("14:08:00") - System.currentTimeMillis();    
		 initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;  
		 exec.scheduleAtFixedRate(new MyThread(String.valueOf(1)), initDelay, oneDay, TimeUnit.MILLISECONDS);
	     System.out.println("运行结束！");
	}
	
	/**  
	 * 获取指定时间对应的毫秒数  
	 * @param time "HH:mm:ss"  
	 * @return  
	 */    
	private static long getTimeMillis(String time) {    
	    try {    
	        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");    
	        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");    
	        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);    
	        return curDate.getTime();    
	    } catch (ParseException e) {    
	        e.printStackTrace();    
	    }    
	    return 0;    
	} 
	
	/**
	 * ThreadPoolExecutor线程池
	 */
	private  static void threadPoolExecutor() {
		int corePoolSize=5;
		int maximumPoolSize=10;
		long keepAliveTime=2L;
		// 线程核心数，最大线程数，线程缓存时间，时间格式，缓存队列 ，线程工厂，拒绝策略
		ThreadPoolExecutor tpx=new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, 
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3),
				Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardOldestPolicy());
		
		  for (int i = 1; i <= 10; i++) {  
	            try {  
	                // 产生一个任务，并将其加入到线程池  
	                String task = "task@ " + i;  
//	                System.out.println("put " + task);  
	                tpx.execute(new MyThread(task));  
	                // 便于观察，等待一段时间  
	                Thread.sleep(20);  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	}
	
	
	
}

class MyThread implements Runnable{
	private String name;
	public  MyThread(String name){
		this.name=name;
	}
	
	@Override
	public void run() {
		System.out.println(name+ "线程开始运行,时间:" +getNowTime());
		pause(1000);
		System.out.println(name+ "线程运行结束,时间:" +getNowTime());
	}
	
	private void pause(long lo) {
		 try {  
             Thread.sleep(lo);  
         } catch (InterruptedException e) {  
             e.printStackTrace();  
         }  

	}
	
	private String getNowTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date());
	}
	
}

package com.pancm.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
* @Title: LockTest1
* @Description: Lock(显示锁)和synchronized(内部锁) 测试
* @Version:1.0.0  
* @author pancm
* @date 2017年10月23日
 */
public class LockTest1 {

	public static void main(String[] args) {
		
	}

}

class Foo{
	/** 可重入的读写锁 */
	private final ReentrantReadWriteLock rwl=new ReentrantReadWriteLock();
	
	/** 读锁 */
	private final Lock r=rwl.readLock();
	
	/** 写锁 */
	private final Lock w=rwl.writeLock();
	
	
	//读操作，可并发执行
	public void read(){
		try{
			r.lock();
			Thread.sleep(1000);
			System.out.println("read......");
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			r.unlock();
		}
	}
	
	//写操作，同时值允许一个执行
	public void write(){
		try{
			w.lock();
			Thread.sleep(1000);
			System.out.println("write......");
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			w.unlock();
		}
	}
	
	/*
	 *  1.Lock支持更细粒度的锁控制;
	 *  2.Lock是无阻塞锁，synchronized是阻塞锁;
	 *  例:当线程A持有锁的时，线程B也期望获得锁，此时，如果线程是使用的显示锁，则B线程为等待状态（即阻塞）;
	 *    如果使用的是内部锁则为阻塞状态。
	 *    
	 *  3.Lock可以实现公平锁，而synchronized只能是非公平锁;
	 *  非公平锁:当一个线程A持有锁，而线程B、C处于阻塞状态时，   若线程A释放锁，JVM将从线程B、C随机选择一个线程持有锁并使其获得执行权，
	 *  	     这叫做非公平锁(因为它抛弃了先来后到的顺序); 
	 *  公平锁:若JVM选择了等待时间最长的一个线程持有锁，则为公平锁（保证每个线程的等待时间均衡）。
	 *  	   需要注意的是，即使是公平锁，JVM也无法做到准确的“公平”，在程序中不能以此作为计算。
	 *  显示锁默认是非公平锁，可以在构造函数中增加true来声明公平锁，而synchronized只能是实现非公平锁。
	 *  
	 *  4.Lock是代码级，synchronized是JVM级的
	 *   Lock是通过编码实现的，synchronized是在运行期由JVM解释的，相对来说synchronized的优化可能性更高，
	 *   毕竟是在最核心部分支持的，Lock的优化则需要用户自行考虑。
	 *   灵活、强大选择Lock;快捷、安全选择synchronized。
	 *  	
	 * 
	 * 
	 * 
	 * 
	 */
	
	
}
package com.pancm.thread.test;
/**
 * @author ZERO
 * @Data 2017-5-24 下午2:29:39
 * @Description 
 */
public class MyRunnable implements Runnable{
	private int i = 0;
	private boolean stop=false;
	public void set(boolean falg) throws InterruptedException{
		if(!falg){
			synchronized (this) {
				this.notify();
				stop=true;
				System.out.println("启动的线程:"+Thread.currentThread().getId());
			}
		}
		
	}
	
	@Override
	public void run() {
	  for (;;) {
		  synchronized (this) {
		       if(stop){
		    	  try {
					this.wait();
					System.out.println("暂停的线程:"+Thread.currentThread().getId());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		       }
		       i++;
			  System.out.println("MyRunnable:"+Thread.currentThread().getName() + "第" + i+ "次");
			  try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		  }
	   }	
	}
  
}

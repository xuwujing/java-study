package com.pancm.thread.test;

import java.util.Random;

/**
* @Title: JoinTest
* @Description:
* join方法测试 
* @Version:1.0.0  
* @author pancm
* @date 2018年5月22日
*/
public class JoinTest {

	public static void main(String[] args) {
		 System.out.println(Thread.currentThread().getName()+ "主线程开始运行!");  
		 Test2 t1=new Test2("A");  
		 Test2 t2=new Test2("B");  
		 t1.start();  
	     t2.start();  
	      try {  
	    	  t1.join();  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }  
	        try {  
	        	t2.join();  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        }    
	     System.out.println(Thread.currentThread().getName()+ "主线程运行结束!");  
	}

}

class Test2 extends Thread{  
    public Test2(String name) {  
        super(name);  
    }  
    public void run() {  
        System.out.println(this.getName() + " 线程运行开始!");  
        for (int i = 0; i < 5; i++) {  
            System.out.println("子线程"+this.getName() + "运行 : " + i);  
            try {  
                sleep(new Random().nextInt(10));  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        System.out.println(this.getName() + " 线程运行结束!");  
    }
}

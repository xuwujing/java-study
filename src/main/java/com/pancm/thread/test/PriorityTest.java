package com.pancm.thread.test;

import java.util.Random;

/**
* @Title: PriorityTest
* @Description:
* 线程优先级测试 
* @Version:1.0.0  
* @author pancm
* @date 2018年5月27日
*/
public class PriorityTest {

	public static void main(String[] args) {
		Test3 t1 = new Test3("张三");
		Test3 t2 = new Test3("李四");
		t1.setPriority(Thread.MIN_PRIORITY);
		t2.setPriority(Thread.MAX_PRIORITY);
		t1.start();
		t2.start();
	}
}

class Test3 extends Thread {
	public Test3(String name) {
		super(name);
	}
	@Override
	public void run() {
        System.out.println(this.getName() + " 线程运行开始!");  
		for (int i = 1; i <= 5; i++) {
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


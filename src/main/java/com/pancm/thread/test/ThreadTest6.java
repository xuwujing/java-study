package com.pancm.thread.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Title: ThreadTest6
* @Description: 
* @Version:1.0.0  
* @author pancm
* @date 2018年7月23日
*/
public class ThreadTest6 {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Object> map=new HashMap<String,Object>();	
		List<Integer> l=new ArrayList<>();
		Thread99 t9=new Thread99();
		Thread t=new Thread(t9);
		
	}
}




class Thread99 implements Runnable{

	@Override
	public void run() {
		System.out.println("====");	
	}
}




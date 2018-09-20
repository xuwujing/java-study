package com.pancm.thread.test;

import java.util.HashMap;
import java.util.Map;


public class Test22 {
	
	public static void main(String[] args) {
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		map.put(0, 0);
		map.put(1, 1);
		for(Integer type:map.keySet()){
			Thread3 t3=new Thread3(type);
			t3.start();
		}
	}
}

class Thread3 extends Thread{

	private int type;
	
	public Thread3(int type){
		this.type=type;
	}
	 
	 @Override
     public void run() {
		 if(type==0){
			 //连接mysql
			 System.out.println("线程ID:"+getId()+"连接mysql");
		 }else if(type==1){
			 //连接oracle
			 System.out.println("线程ID:"+getId()+"连接oracle");
		 }
    }
	
	 
	 
	 
}
package com.pancm.thread.concurrent.liveLock;

/**
 * 
* Title: Drop
* Description: 
* 数据协同
* Version:1.0.0  
* @author pancm
* @date 2018年3月8日
 */
public class Drop {
	  // 发送的消息
	  private String message;
	   //true 表示消费者应该等待生产者发送消息
	  // flase 表示生产者应该等待消费者获取消息
	  private boolean empty = true;
	
	  public synchronized String take() {
	      // 等待消息可用
	      while (empty) {
	          try {
	              wait();
	          } catch (InterruptedException e) {
	        	  e.printStackTrace();
	          }
	      }
	      // 改变状态
	      empty = true;
	      // 通知消费者状态改变
	      notifyAll();
	      return message;
	  }
	
	  public synchronized void put(String message) {
		   //等待消息被检索到 
	      while (!empty) {
	          try { 
	              wait();
	          } catch (InterruptedException e) {
	        	  e.printStackTrace();
	          }
	      }
	      // 改变状态
	      empty = false;
	      this.message = message;
	      // 通知消费者状态改变
	      notifyAll();
	  }
  }
package com.pancm.nio.netty.demo4;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
* Description: Netty 传输对象 
* Version:1.0.0  
* @author pancm
* @date 2017年9月24日
 */
public class NettySendBody  implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 创建集合  */
	private ConcurrentHashMap<String, String> data;
	/** 时间戳 */
	private long timestamp;
	
	public NettySendBody() {
		data = new ConcurrentHashMap<String, String>();
		timestamp = System.currentTimeMillis();  //取当前时间
	}


	public ConcurrentHashMap<String, String> getData() {
		return data;
	}

	public void setData(ConcurrentHashMap<String, String> data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public String get(String k) {
		return data.get(k);
	}

	public void put(String k, String v) {
		data.put(k, v);
	}


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buffer.append("<sent>");
		buffer.append("<timestamp>").append(timestamp).append("</timestamp>");
		buffer.append("<data>");
		for (String key : data.keySet()) {
			buffer.append("<" + key + ">").append(data.get(key)).append(
					"</" + key + ">");
		}
		buffer.append("</data>");
		buffer.append("</sent>");
		return buffer.toString();
	}
	

}

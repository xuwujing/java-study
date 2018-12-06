package com.pancm.code;

import java.util.HashMap;
import java.util.Map;

/**
* @Title: MapCodeTest
* @Description: Map源码学习相关类
* @Version:1.0.0  
* @author pancm
* @date 2018年11月29日
*/
public class MapCodeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test1();
	}
	
	
	/**
	 * 
	 */
	private static void test1() {
		Map<String, Object> map =new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
	}
}



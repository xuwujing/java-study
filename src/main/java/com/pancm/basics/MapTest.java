package com.pancm.basics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
* Title: MapTest
* Description:集合map测试 
* Version:1.0.0  
* @author pancm
* @date 2017年11月16日
 */
public class MapTest {
	private static Map<String,String> map = new HashMap<String,String>();
	
	public static Map<String,String> getHashMap(){
		if(map == null || map.isEmpty()){
			map = new HashMap<String,String>();
		}
		return  map;
	}
	
	public static void main(String[] args) {
		//普通的HashMap 
//		Map<String,String> map=mapTest.getHashMap();
//		map.put("a", "1");
//		map.put("b", "1");
//		map.put("c", "e");
//		System.out.println(map.toString());
//		Map<String,String> map1=mapTest.getHashMap();
//		System.out.println("-----"+map1.get("a"));
		
		//上锁的HashMap
//		Map<String,String> sMap=Collections.synchronizedMap(new HashMap());
		
//		getMap1();
		
		test1();
		test2();
	}

	
	
	private static void test1() {
		 Map<String, String> map = new HashMap<String, String>();
	      map.put("1", "value1");
	      map.put("2", "value2");
	      map.put("3", "value3");
	      //第一种：普遍使用，二次取值
	      System.out.println("通过Map.keySet遍历key和value：");
	      for (String key : map.keySet()) {
	       System.out.println("key= "+ key + " and value= " + map.get(key));
	      }
	      
	      //第二种 使用迭代器Iterator进行遍历
	      System.out.println("通过Map.entrySet使用iterator遍历key和value：");
	      Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
	      while (it.hasNext()) {
	       Map.Entry<String, String> entry = it.next();
	       System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
	      }
	      
	      //第三种：使用entrySet遍历 推荐，尤其是容量大时
	      System.out.println("通过Map.entrySet遍历key和value");
	      for (Map.Entry<String, String> entry : map.entrySet()) {
	       System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
	      }
	}
	
	private static void test2() {
		Map<String,Object> hashMap=new HashMap<String,Object>();
		hashMap.put("a", 1);
		hashMap.put("c", 3);
		hashMap.put("b", 2);
		System.out.println("HashMap:"+hashMap);
		
		Map<String,Object> treeMap=new TreeMap<String,Object>();
		treeMap.put("a", 1);
		treeMap.put("c", 3);
		treeMap.put("b", 2);
		System.out.println("TreeMap:"+treeMap);
		
	}
	
	
	
	
	
	@SuppressWarnings("unused")
	private static void getMap1(){
		Map<Integer,Integer> map1 =new HashMap<Integer,Integer>();
		Map<Integer,Integer> map2 =new HashMap<Integer,Integer>();
		map1.put(1, 1);
		map1.put(2, 2);
		map1.put(3, 3);
		map2.put(11, 11);
		map2.put(22, 22);
		map2.put(33, 33);
		Map<Integer,Map<Integer,Integer>> map3 =new HashMap<Integer,Map<Integer,Integer>>();
		map3.put(1, map1);
		map3.put(2, map2);
		
		System.out.println("map3:"+map3);
		StringBuffer sb=new StringBuffer();
		for(Entry<Integer,Map<Integer,Integer>> entry:map3.entrySet()){
			sb.append(entry.getKey());
			sb.append(":");
			Map<Integer,Integer> map4=entry.getValue();
				for(Entry<Integer,Integer> entry1:map4.entrySet()){
					sb.append(entry1.getKey());
					sb.append("_");
					sb.append(entry1.getValue());
					sb.append(",");
				}
			
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		System.out.println("sb:"+sb);
	}
}

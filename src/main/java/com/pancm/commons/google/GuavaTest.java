package com.pancm.commons.google;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Splitter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.primitives.Ints;

/**
 * 
* Title: guavaTest
* Description:谷歌 guava 工具包测试
* Version:1.0.0  
* @author pancm
* @date 2017年10月30日
 */
public class GuavaTest {

	public static void main(String[] args) {
		noChangeList();
		one2MoreMap();
		more2One();
		filtedMap();
		joiner();
		splitter();
		integer();
	}
	
	/**
	 * 不可变集合测试
	 */
	private static void noChangeList(){
		ImmutableList<String> list=ImmutableList.of("A","B","C");
		ImmutableMap<Integer,String> map=ImmutableMap.of(1,"壹",2,"贰",3,"叁");
		System.out.println("ImmutableList:"+list); //ImmutableList:[A, B, C]
		System.out.println("ImmutableMap:"+map); //ImmutableMap:{1=壹, 2=贰, 3=叁}
		
		//下面运行直接报错，因为这是不可变集合
//		list.add("D");  
//		map.put(4, "肆");
	}
	
	/**
	 * map中多个键的测试
	 * 例如:一个人多个电话
	 */
	private static void one2MoreMap(){
		Multimap<String,String> map= ArrayListMultimap.create();
		map.put("路人甲", "123");
		map.put("路人甲", "234");
		map.put("路人乙", "567");
		map.put("路人乙", "890");
		System.out.println("Multimap:"+map); //Multimap:{路人乙=[567, 890], 路人甲=[123, 234]}
		System.out.println("get:"+map.get("路人乙")); //get:[567, 890]
	}
	
	/**
	 * 多个键值对一个值
	 * 例如:坐标
	 */
	private static void more2One(){
		Table<Double, Double, String> table=HashBasedTable.create();
		table.put(22.54, 114.01, "深圳");
		table.put(39.96, 116.40, "北京");
		System.out.println("Table:"+table); //Table:{22.54={114.01=深圳}, 39.96={116.4=北京}}
		System.out.println("Table.get:"+table.get(22.54, 114.01));//Table.get:深圳
	}
	
	/**
	 * Map的过滤
	 * 例如:查找该集合中大于20岁的人
	 */
	private static void filtedMap(){
		Map<String,Integer> map=new HashMap<String,Integer>();
		map.put("张三", 19);
		map.put("李四", 20);
		map.put("王五", 21);
		Map<String,Integer> filtedmap =Maps.filterValues(map, 
				new Predicate<Integer>(){
					@Override
					public boolean apply(Integer age) {
						return age>20;
					}
		});
		System.out.println("Map:"+map);	//Map:{张三=19, 李四=20, 王五=21}
		System.out.println("filtedmap:"+filtedmap);//filtedmap:{王五=21}
	}
	
	/**
	 * Joiner连接测试
	 * 不局限于连接String,如果是null，会直接跳过
	 * 
	 */
	private static void joiner(){
		//设置连接符 
		//如:设置为 "和",拼接 “你”，“我” 就变成了“你和我”
		Joiner joiner=Joiner.on(",");
		String str=joiner.skipNulls().join("你好","java");
		Map<String,String> map=new HashMap<String,String>();
		map.put("张三", "你好");
		map.put("李四", "嗨");
		//设置键值的连接符以及键与值之间的连接符
		String str1=Joiner.on(",").withKeyValueSeparator(":").join(map);
		System.out.println("Joiner: "+str); //Joiner: 你好,java
		System.out.println("Joiner: "+str1); //Joiner: 张三:你好,李四:嗨
	}
	
	/**
	 * Splitter拆分测试
	 */
	private static void splitter(){
		String str="你好,java";
		//按字符分割
		for(String s:Splitter.on(",").split(str)){
			System.out.println("s:"+s);
		}
		//按固定长度分割
		for(String d:Splitter.fixedLength(2).split(str)){
			System.out.println("d:"+d);
		}
	}
	
	/**
	 * 基本类型测试
	 */
	private static void integer(){
		int []ints={1,4,3,2};
		//找到里面的最大值
		System.out.println("max:"+Ints.max(ints)); //max:4
		
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		list.add(6);
		//包装类型集合转变为基本类型集合
		int []arr=Ints.toArray(list);
		for(int i=0,j=arr.length;i<j;i++){
			System.out.println("arr:"+arr[i]);
		}
	}
	
	
}

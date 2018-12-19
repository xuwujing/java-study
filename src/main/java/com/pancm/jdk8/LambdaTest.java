package com.pancm.jdk8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * @Title: lambdaTest
 * @Description: 拉姆达表达式
 * 
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月28日
 */
public class LambdaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {

		Map<String, String> map = new HashMap<>();
		map.put("a", "a");
		map.put("b", "b");
		map.put("c", "c");
		map.put("d", "d");

		System.out.println("map普通方式遍历:");
		for (String key : map.keySet()) {
			System.out.println("k=" + key + "，v=" + map.get(key));
		}

		System.out.println("map拉姆达表达式遍历:");
		map.forEach((k, v) -> {
			System.out.println("k=" + k + "，v=" + v);
		});

		
		
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("bb");
		list.add("ccc");
		list.add("dddd");
		System.out.println("list拉姆达表达式遍历:");
		list.forEach(v -> {
			System.out.println(v);
		});
		System.out.println("list双冒号运算符遍历:");
		list.forEach(System.out::println);

	}

	private static void test2() {
		List<User> list = new ArrayList<User>();
		List<User> list2 = new ArrayList<User>();
		list.add(new User(1, "张三"));
		list.add(new User(2, "李四"));
		list.add(new User(3, "王五"));
		list.add(new User(4, "赵六"));
		System.out.println("list:" + list);
		list.forEach(v -> {
			if (v.getId() > 2) {
				list2.add(v);
			}
		});
		System.out.println("list2:" + list2);
	}
	
	//使用普通的方式创建
	Runnable r1 = new Runnable() {
		@Override
		public void run() {
			System.out.println("普通方式创建!");
		}
	};
	
	//使用拉姆达方式创建
	Runnable r2 = ()-> System.out.println("拉姆达方式创建!");
	

}

class User {

	/** 编号 */
	private int id;
	/** 姓名 */
	private String name;

	public User() {
	}

	/**
	 * 构造方法
	 * 
	 * @param id   编号
	 * @param name 姓名
	 */
	public User(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * 获取编号
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置编号
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 获取姓名
	 * 
	 * @return name
	 */
	public String getName() {
		System.out.println("姓名:" + name);
		return name;
	}

	/**
	 * 设置姓名
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("rawtypes")
	public Map toMap() {
		return JSON.parseObject(toString(), HashMap.class);
	}

	/** 
	 * 
	 */
	@Override
	public String toString() {
		return "{\"id\":\"" + id + "\",\"name\":\"" + name + "\"}";
	}
	
	
}

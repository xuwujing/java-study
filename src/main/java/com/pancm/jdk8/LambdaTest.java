package com.pancm.jdk8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pancm.pojo.User;

/**
* @Title: LambdaTest
* @Description: 拉姆达表达式
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

	private static void  test1(){
		Map<String , String> map = new HashMap<>();
        map.put("a","a");
        map.put("b","b");
        map.put("c","c");
        map.put("d","d");
        map.put("e","e");
        map.put("f","f");
 
        map.forEach((k,v)->{
            System.out.println("k="+k+"，v="+v);
        });
 
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbbbb");
        list.add("ccccccc");
        list.add("dddd");
        list.forEach(v->{
            System.out.println(v);
        });
	}
	
	private static void test2(){
		List<User> list = new ArrayList<User>();
		List<User> list2 = new ArrayList<User>();
        list.add(new User(1, "张三"));
        list.add(new User(2, "李四"));
        list.add(new User(3, "王五"));
        list.add(new User(4, "赵六"));
    	System.out.println("list:"+list);
        list.forEach(v->{
        	if(v.getId()>2){
        		list2.add(v);
        	}
        });
		System.out.println("list2:"+list2);
	}
}

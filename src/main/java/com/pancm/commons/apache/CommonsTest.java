package com.pancm.commons.apache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.HashBag;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * 
* Title: langTest
* Description: Apache commons工具包测试
* Version:1.0.0  
* @author pancm
* @date 2017年10月30日
 */
public class CommonsTest {

	public static void main(String[] args) {
		stringTest();
		otherTest();
	}
	
	/**
	 * StringUtils 相关测试
	 */
	private static void stringTest(){
		/*
		 * 空指针判断
		 */
		String str="";
		String str2=null;
		String str3=" ";
		System.out.println(":"+StringUtils.isEmpty(str));		//:true
		System.out.println("null:"+StringUtils.isEmpty(str2));  //null:true
		System.out.println(" :"+StringUtils.isEmpty(str3));		// :false
		
		/*
		 * 判断是否为数字
		 */
		String str4="123";
		String str5="12 3";
		String str6="123QD#";
		System.out.println("str4:"+StringUtils.isNumeric(str4));//str4:true
		System.out.println("str5:"+StringUtils.isNumeric(str5));//str5:false
		System.out.println("str6:"+StringUtils.isNumeric(str6));//str6:false
		
		/*
	 	 * 统计子字符串出现的次数
		 */
		String str7="abcdefgfedccfg";
		String str8="ac";
		String str9="c";
		System.out.println("count:"+StringUtils.countMatches(str7, str8));//count:0
		System.out.println("count:"+StringUtils.countMatches(str7, str9));//count:3
		
	}
	
	/**
	 * 其他的测试
	 */
	private static void otherTest(){
		System.out.println("十位数字随机数:"+RandomStringUtils.randomNumeric(10)); //0534110685
		System.out.println("十位字母随机数:"+RandomStringUtils.randomAlphabetic(10)); //jLWiHdQhHg
		System.out.println("十位ASCII随机数:"+RandomStringUtils.randomAscii(10));  //8&[bxy%h_-
		String str="hello world,why are you so happy";
		System.out.println("首字符大写:"+WordUtils.capitalize(str));  //:Hello World,why Are You So Happy
	}

	/**
	 * Bag 测试
	 * 主要测试重复元素的统计
	 */
	@SuppressWarnings("deprecation")
	private static void bagTest(){
		//定义4种球
		Bag box=new HashBag(Arrays.asList("red","blue","black","green"));
		System.out.println("box.getCount():"+box.getCount("red"));//box.getCount():1
		box.add("red", 5);//红色的球增加五个
		System.out.println("box.size():"+box.size());	//box.size():9
		System.out.println("box.getCount():"+box.getCount("red"));//box.getCount():6
	}
	
	/**
	 * Lazy测试
	 * 需要该元素的时候，才会生成
	 */
	@SuppressWarnings("unchecked")
	private static void lazyTest(){
		List<String> lazy=LazyList.decorate(new ArrayList<>(), new Factory() {
			@Override
			public Object create() {
				return "Hello";
			}
		}); 
		//访问了第三个元素，此时0和1位null
		//get几就增加了几加一 ， 输出依旧是 Hello 
		String str=lazy.get(2);
		System.out.println("str:"+str);//str:Hello 
		//加入的第四个元素
		lazy.add("world");
		//元素总个为4个
		System.out.println("lazy.size():"+lazy.size());//lazy.size():4
	}
	
	/**
	 * 双向Map
	 * 唯一的key和map，可以通过键或值来操作
	 * 比如删除、查询等
	 */
	private static void bidimapTest(){
		BidiMap map=new TreeBidiMap();
		map.put(1, "a");
		map.put(2, "b");
		map.put(3, "c");
		System.out.println("map:"+map);	//map:{1=a, 2=b, 3=c}
		System.out.println("map.get():"+map.get(2)); //map.get():b
		System.out.println("map.getKey():"+map.getKey("a")); //map.getKey():1
		System.out.println("map.removeValue():"+map.removeValue("c")); //map.removeValue():3
		System.out.println("map:"+map);	//map:{1=a, 2=b}
	}
	
	
}

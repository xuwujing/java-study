package com.pancm.design.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: FlyweightTest
 * @Description: 享元模式
 *               在有大量对象时，有可能会造成内存溢出，我们把其中共同的部分抽象出来，如果有相同的业务请求，直接返回在内存中已有的对象，
 *               避免重新创建。
 *                应用实例: 1、JAVA 中的 String，如果有则返回，如果没有则创建一个字符串保存在字符串缓存池里面。
 *               	     2、数据库的数据池。
 *                主要目的就是复用
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class FlyweightTest {

	public static void main(String[] args) {
		/*
		 * 享元模式的角色: Flyweight: 抽象享元类。所有具体享元类的超类或者接口，通过这个接口，Flyweight可以接受并作用于外部专题
		 * ConcreteFlyweight: 具体享元类。指定内部状态，为内部状态增加存储空间。
		 * UnsharedConcreteFlyweight: 非共享具体享元类。指出那些不需要共享的Flyweight子类。
		 * FlyweightFactory:
		 * 享元工厂类。用来创建并管理Flyweight对象，它主要用来确保合理地共享Flyweight，当用户请求一个Flyweight时，
		 * FlyweightFactory就会提供一个已经创建的Flyweight对象或者新建一个（如果不存在）。
		 * 
		 * 享元模式的核心在于享元工厂类，享元工厂类的作用在于提供一个用于存储享元对象的享元池，用户需要对象时，首先从享元池中获取，如果享元池中不存在
		 * ，则创建一个新的享元对象返回给用户，并在享元池中保存该新增对象。
		 * 
		 * 享元模式的目的是共享，避免多次创建耗费资源，单例模式的目的是限制创建多个对象以避免冲突等，所以即使都是一个对象，目的也不同。
		 */
		
		String names[] = { "张三", "李四", "王五", "虚无境" };
		for (int i = 0; i < 8; i++) {
			Penil penil = PenFactory.get(names[i>3?i-4:i]);
			penil.setSomething("画了一条鱼");
			penil.write();
		}
		/*
		 * 
		 * 张三 第:1次创建
			张三 用于铅笔  画了一条鱼
			李四 第:1次创建
			李四 用于铅笔  画了一条鱼
			王五 第:1次创建
			王五 用于铅笔  画了一条鱼
			虚无境 第:1次创建
			虚无境 用于铅笔  画了一条鱼
			张三 用于铅笔  画了一条鱼
			李四 用于铅笔  画了一条鱼
			王五 用于铅笔  画了一条鱼
			虚无境 用于铅笔  画了一条鱼

		 */
	}
}

/*
 * 创建一支笔的接口
 */
interface Pen {
	void write();
}

/*
 * 创建一支铅笔
 */
class Penil implements Pen {
	private String name;
	private String something; 
	private  int i;
	
	public Penil(String name) {
		this.name = name;
		i++;
		System.out.println(name+" 第:"+i+"次创建");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSomething() {
		return something;
	}
	
	public void setSomething(String something) {
		this.something = something;
	}
	
	@Override
	public void write() {
		System.out.println(name+" 用于铅笔  "+something);
	}
}

/*
 * 创建一个工厂
 * 核心
 */
class PenFactory {
	private static final Map<String, Penil> map = new HashMap<String, Penil>();

	public static Penil get(String name) {
		Penil penil = map.get(name);
		if (penil == null) {
			penil = new Penil(name);
			map.put(name, penil);
		}
		return penil;
	}
}

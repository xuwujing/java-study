package com.pancm.design.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: IteratorTest
 * @Description: 迭代器模式 迭代器模式（Iterator Pattern）是 Java 和 .Net
 *               编程环境中非常常用的设计模式。这种模式用于顺序访问集合对象的元素，不需要知道集合对象的底层表示。 迭代器模式属于行为型模式。
 *               提供一种方法顺序访问一个聚合对象中各个元素, 而又无须暴露该对象的内部表示。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class IteratorTest {

	public static void main(String[] args) {

		
		MyIterable myIterable = new ListContainer();
		myIterable.add("1");
		myIterable.add("zhangsan");
		myIterable.add("2");
		myIterable.add("lisi");
		myIterable.add("3");
		myIterable.add("xuwujing");
		
        MyIterator myIterator = myIterable.getIterator();
        while (myIterator.hasNext()){
            String str = myIterator.next();
            System.out.println(str);
        }
        
        /*
          输出结果:
		1
		zhangsan
		2
		lisi
		3
		xuwujing
         * 
         */

        
		/*
		 * 
		 * 优点： 1、它支持以不同的方式遍历一个聚合对象。 2、迭代器简化了聚合类。 3、在同一个聚合上可以有多个遍历。
		 * 4、在迭代器模式中，增加新的聚合类和迭代器类都很方便，无须修改原有代码。
		 * 
		 * 缺点：由于迭代器模式将存储数据和遍历数据的职责分离，增加新的聚合类需要对应增加新的迭代器类，类的个数成对增加，这在一定程度上增加了系统的复杂性。
		 * 
		 * 使用场景： 1、访问一个聚合对象的内容而无须暴露它的内部表示。 2、需要为聚合对象提供多种遍历方式。 3、为遍历不同的聚合结构提供一个统一的接口。
		 * 
		 * 注意事项：迭代器模式就是分离了集合对象的遍历行为，抽象出一个迭代器类来负责，这样既可以做到不暴露集合的内部结构，又可让外部代码透明地访问集合内部的数据。
		 * 
		 
		 适用
		访问一个聚集对象的内容而无需暴露它的内部表示;
		支持对聚集对象的多种遍历(如: 不光可以正向遍历, 还可以反向遍历容器元素.);
		为遍历不同的聚合结构提供一个统一的接口(即: 支持多态迭代).
		Iterator使用场景不必多言, 由于Java已经将其固化到语言中,因此开发中天天都在使用:
		
		当需要访问一个聚集对象, 且不需要了解其内部实现的时, 就应该考虑使用迭代器模式.
		当需要对聚集有多种方式遍历时, 可以考虑使用迭代器模式.
		 
		 */
	}

}


/*
 * 定义一个Iterator
 */
interface MyIterator {
	//判断是否还有下一个
	boolean hasNext();
	//返回信息
	String next();
}

/*
 *  定义一个Iterable
 */
interface MyIterable{
	MyIterator getIterator();
	
	void add(String str);
	
	String get(int index);
}



class ListContainer implements MyIterable {
	
	 private List<String> list =new ArrayList<>(); 

	 
	@Override
	public MyIterator getIterator() {
		return new ListIterator();
	}

	@Override
	public void add(String str) {
		list.add(str);
	}

	@Override
	public String get(int index) {
		return list.get(index);
	}
	
	
	class ListIterator implements MyIterator{
		int index;
		@Override
		public boolean hasNext() {
			return index < list.size();
		}

		@Override
		public String next() {
			if (this.hasNext()) {
				return list.get(index++);
			}
			return null;
		}
	}
	
}



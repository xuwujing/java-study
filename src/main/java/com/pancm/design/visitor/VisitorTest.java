package com.pancm.design.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: VisitorTest
 * @Description: 访问者模式
 *               访问者模式（VisitorPattern）是对象的行为模式。访问者模式的目的是封装一些施加于某种数据结构元素之上的操作。一旦这些操作需要修改的话，接受这个操作的数据结构则可以保持不变。
 *               核心:主要将数据结构与数据操作分离。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class VisitorTest {

	public static void main(String[] args) {

		/*
		 * 基本角色: 1 抽象访问者(Visitor)角色：声明了一个或者多个方法操作，形成所有的具体访问者角色必须实现的接口。
		 * 
		 * 2 具体访问者(ConcreteVisitor)角色：实现抽象访问者所声明的接口，也就是抽象访问者所声明的各个访问操作。
		 * 
		 * 3 抽象节点(Node)角色：声明一个接受操作，接受一个访问者对象作为一个参数。
		 * 
		 * 4 具体节点(ConcreteNode)角色：实现了抽象节点所规定的接受操作。
		 * 
		 * 5 结构对象(ObjectStructure)角色：有如下的责任，可以遍历结构中的所有元素；如果需要，提供一个高层次的接口让访问者对象可以访问每一个元素；
		 
		 * 
		 */

		// 创建一个结构对象
		ObjectStructure os = new ObjectStructure();
		// 给结构增加一个节点
		os.add(new Games());
		// 给结构增加一个节点
		os.add(new Photos());
		// 创建一个访问者
		Visitor visitor = new ZhangSan();
		os.action(visitor);

		/*
		 * 优点： 1、扩展性好，可以在不修改对象结构中的元素的情况下，为对象结构中的元素添加新的功能。
		 * 2、符合单一职责原则，通过访问者将无关的行为分离，使职责单一。
		 * 
		 * 缺点： 1、违反了迪米特原则，因为具体元素对访问者公布细节。 2、违反了依赖倒置原则，依赖了具体类，没有依赖抽象。
		 * 3、对象结构变化困难，若对象结构发生了改变，访问者的接口和访问者的实现也都要发生相应的改变。
		 * 
		 * 
		 * 使用场景： 1、对象结构中对象对应的类很少改变，但经常需要在此对象结构上定义新的操作。
		 * 2、需要对一个对象结构中的对象进行很多不同的并且不相关的操作，而需要避免让这些操作"污染"这些对象的类，也不希望在增加新操作时修改这些类。
		 * 注意事项：访问者可以对功能进行统一，可以做报表、UI、拦截器与过滤器。
		 * 
		 */
	}

}

/*
 * 定义一个抽象的角色(游客)
 */
interface Visitor {
	// 可以玩游戏
	void visit(Games games);

	// 可以查看图片
	void visit(Photos photos);
}

class ZhangSan implements Visitor {
	@Override
	public void visit(Games games) {
		games.play();
	}

	@Override
	public void visit(Photos photos) {
		photos.watch();
	}
}

/*
 * 
 */
class LiSi implements Visitor {
	@Override
	public void visit(Games games) {
		games.play();
	}

	@Override
	public void visit(Photos photos) {
		photos.watch();
	}
}

/*
 * 定义个接受者
 */
interface Computer {
	void accept(Visitor visitor);
}

class Games implements Computer {
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void play() {
		System.out.println("play lol");
	}
}

class Photos implements Computer {
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void watch() {
		System.out.println("watch scenery photo");
	}
}

/*
 * 结构对象角色
 */
class ObjectStructure {

	private List<Computer> computers = new ArrayList<Computer>();

	/**
	 * 执行方法操作
	 */
	public void action(Visitor visitor) {
		computers.forEach(c -> {
			c.accept(visitor);
		});

	}

	/**
	 * 添加一个新元素
	 */
	public void add(Computer computer) {
		computers.add(computer);
	}
}

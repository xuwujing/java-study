package com.pancm.basics;

/**
 * @author pancm
 * @Data 2017-6-1 
 * @Description abstract 抽象类测试
 */
public class AbstractTest {
	public static void main(String[] args) {
		// 这句会报错，因为抽象类不能实例化
		// Animal a=new Animal();
		Animal a = new Dog();
		a.show();

		E p = new F();
		p.show();
		E q = new G();
		q.show();

		/*
		 * 
		 * 1、抽象类和抽象方法都需要被abstract修饰。抽象方法一定要定义在抽象类中。
		 * 
		 * 2、抽象类不可以创建实例，原因：调用抽象方法没有意义。
		 * 
		 * 3、只有覆盖了抽象类中所有的抽象方法后，其子类才可以实例化。否则该子类还是一个抽象类。
		 * 
		 * 之所以继承，更多的是在思想，是面对共性类型操作会更简单。
		 * 
		 */
	}
}

abstract class Animal {
	abstract void show();

	public void print() {
		System.out.println("Animal");
	}
}

class Dog extends Animal {
	@Override
	void show() {
		System.out.println("This is Dog!");
	}
}

abstract class E {
	public abstract void show();
}

class F extends E {
	public void show() {
		System.out.print("test all FFFF \n");
	}
}

class G extends E {
	public void show() {
		System.out.print("test all GGGG \n");
	}
}

package com.pancm.basics;

/**
 * 
 * @Title: StaticTest
 * @Description: 构造块和静态方法测试
 * @Version:1.0.0
 * @author Administrator
 * @date 2017-8-14
 */
public class StaticTest {
	public static StaticTest t1 = new StaticTest();
	public static StaticTest t2 = new StaticTest();
	{
		System.out.println("构造块");
	}
	static {
		System.out.println("静态块");
	}

	public static void main(String[] args) {
		StaticTest t = new StaticTest(); // 构造块 构造块 静态块 构造块
		
		
		 //  new HelloA();  extends HelloA
    	new HelloB();   //3
        /*
         * 总结:创建对象时构造器的调用顺序是：先初始化静态成员，然后调用父类构造器，再初始化非静态成员，最后调用自身构造器。
         */

		/*
		 * 总结 开始时JVM加载B.class，对所有的静态成员进行声明，t1 t2被初始化为默认值，为null， 又因为t1
		 * t2需要被显式初始化，所以对t1进行显式初始化，初始化代码块→构造函数（没有就是调用默认的构造函数），
		 * 咦！静态代码块咋不初始化？因为在开始时已经对static部分进行了初始化，虽然只对static变量进行了初始化，
		 * 但在初始化t1时也不会再执行static块了，因为JVM认为这是第二次加载类B了，所以static会在t1初始化时被忽略掉，
		 * 所以直接初始化非static部分，也就是构造块部分（输出''构造块''）接着构造函数（无输出）。
		 * 接着对t2进行初始化过程同t1相同（输出'构造块'），此时就对所有的static变量都完成了初始化，
		 * 接着就执行static块部分（输出'静态块'），接着执行，main方法，同样也，new了对象， 调用构造函数输出（'构造块'）
		 * 
		 */

	}
	
}

class HelloA {
	private int i=10;
    public HelloA() {
        System.out.println("HelloA"); //5
    }
    
    { System.out.println("I'm A class"); } //4
    
    static { System.out.println("static A"); } //1

}
 class HelloB extends HelloA{
	private int j=12;
    public HelloB() {
        System.out.println("HelloB");  //7
    }
    
    { System.out.println("I'm B class"); }  //6
    
    static { System.out.println("static B"); }  //2
    
     
    	
  

}


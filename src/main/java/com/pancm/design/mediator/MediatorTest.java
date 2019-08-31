package com.pancm.design.mediator;

/**
 * @Title: MediatorTest
 * @Description: 中介者模式 中介者模式（Mediator Pattern）是用来降低多个对象和类之间的通信复杂性。
 *               这种模式提供了一个中介类，该类通常处理不同类之间的通信，并支持松耦合，使代码易于维护。中介者模式属于行为型模式。
 *               用一个中介对象来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class MediatorTest {

	public static void main(String[] args) {
		
	
		/*
		 * 基本角色
		 抽象中介者(Mediator): 。定义了同事对象到中介者对象之间的接口。
                      具体中介者(ConcreteMediator): 实现抽象中介者的方法，它需要知道所有的具体同事类，同时需要从具体的同事类那里接收信息，并且向具体的同事类发送信息。
                     抽象同事类(Colleague): 定义了中介者对象的接口，它只知道中介者而不知道其他的同事对象。
		具体同事类(ConcreteColleague) : 每个具体同事类都只需要知道自己的行为即可，但是他们都需要认识中介者。
		 * 
		 */
		
		JavaQQqun jq = new JavaQQqun();
        
		ZhangSan zs = new ZhangSan("张三", jq);
		XuWuJing xwj = new XuWuJing("xuwujing", jq);
		jq.setZs(zs);
		jq.setXwj(xwj);
		zs.exchange("大家好！我是张三!");
		xwj.exchange("欢迎你！张三！");
		
		
		/*
		 * 优点： 1、降低了类的复杂度，将一对多转化成了一对一。 2、各个类之间的解耦。 3、符合迪米特原则。
			缺点：中介者会庞大，变得复杂难以维护。
		 */
		
	}

}



//定义一个中介者 QQ群
interface QQqun {
	//提供一个交流的方法
	void exchange(Person person,String message);
}

//定义一个抽象同事类 
abstract class Person{
	protected String name;
    protected QQqun qun;
    
    Person(String name,QQqun qun){
        this.name = name;
        this.qun = qun;
    }
}

class ZhangSan extends Person{

	ZhangSan(String name, QQqun qun) {
		super(name, qun);
	}
	
	 void exchange(String message){
		qun.exchange(this,message);
    }
	
     void talk(String message){
        System.out.println(name +"说：" + message);
    }
}

class XuWuJing extends Person{

	XuWuJing(String name, QQqun qun) {
		super(name, qun);
	}
	
	 void exchange(String message){
		qun.exchange(this,message);
    }
	
     void talk(String message){
        System.out.println(name +"回应：" + message);
    }
}

//定义一个JavaQQ群
class JavaQQqun implements QQqun{
    private ZhangSan zs;
    private XuWuJing xwj;

    public ZhangSan getZs() {
		return zs;
	}

	public void setZs(ZhangSan zs) {
		this.zs = zs;
	}

	public XuWuJing getXwj() {
		return xwj;
	}


	public void setXwj(XuWuJing xwj) {
		this.xwj = xwj;
	}


	@Override
	public void exchange(Person person, String message) {
			if(zs.equals(person)){
				zs.talk(message);
			}else if(xwj.equals(person)){
				xwj.talk(message);
			}
	}
}

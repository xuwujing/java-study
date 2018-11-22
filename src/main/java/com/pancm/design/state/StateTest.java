package com.pancm.design.state;

/**
 * @Title: StateTest
 * @Description: 状态模式 在状态模式（State Pattern）中，类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。
 *               在状态模式中，我们创建表示各种状态的对象和一个行为随着状态对象改变而改变的 context 对象。
 *               允许对象在内部状态发生改变时改变它的行为，对象看起来好像修改了它的类。
 *      状态模式主要解决的是当控制一个对象状态转换的条件表达式过于复杂是的情况。把状态的判断逻辑转移到表示不同状态一系列类中，可以把复杂的判断简单化。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class StateTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		  
		  例子: 经典的Tcp的状态有 创建、监听、关闭这三个状态。
		  
		  基本角色:
		 环境角色（Context）：它定义了客户程序需要的接口并维护一个具体状态角色的实例，将与状态相关的操作委托给当前的Concrete State对象来处理。
　　		 抽象状态角色（State）：定义一个接口以封装使用上下文环境的的一个特定状态相关的行为。
　　		 具体状态角色（Concrete State）：实现抽象状态定义的接口。 
		 * 
		 */
		
		Headset hs = new Headset(new PlayState());
		//第一次播放音乐
		hs.press();
		//第二次暂停音乐
		hs.press();
		//第三次播放音乐
		hs.press();
		
		/*
		 * 优点： 
		 * 1、封装了转换规则。 
		 * 2、枚举可能的状态，在枚举状态之前需要确定状态种类。
		 * 3、将所有与某个状态有关的行为放到一个类中，并且可以方便地增加新的状态，只需要改变对象状态即可改变对象的行为。
		 * 4、允许状态转换逻辑与状态对象合成一体，而不是某一个巨大的条件语句块。 
		 * 5、可以让多个环境对象共享一个状态对象，从而减少系统中对象的个数。
		 * 缺点：
		 * 1、状态模式的使用必然会增加系统类和对象的个数。
		 * 2、状态模式的结构与实现都较为复杂，如果使用不当将导致程序结构和代码的混乱。
		 * 3、状态模式对"开闭原则"的支持并不太好，对于可以切换状态的状态模式，增加新的状态类需要修改那些负责状态转换的源代码，
		 * 否则无法切换到新增状态，而且修改某个状态类的行为也需修改对应类的源代码。 
		 * 使用场景：
		 * 1、行为随状态改变而改变的场景。 
		 * 2、条件、分支语句的代替者。
		  和策略模式比较:
		 相同:
			1.它们很容易添加新的状态或策略，而且不需要修改使用它们的Context对象。
			2.它们都符合OCP原则，在状态模式和策略模式中，Context对象对修改是关闭的，添加新的状态或策略，都不需要修改Context。
			3.它们都会初始化。
			4.它们都依赖子类去实现相关行为。 
	
		 区别: 
			 1.状态模式的行为是平行性的，不可相互替换的；
			 2.而策略模式的行为是平等性的，是可以相互替换的。
			 3.最重要的一个不同之处是，策略模式的改变由客户端完成；
			 4.而状态模式的改变，由环境角色或状态自己
		 
		 * 注意事项：在行为受状态约束的时候使用状态模式，而且状态不超过 5 个。
		 */
	}

}



//定义一个音乐状态
interface MusicState{
	void press();
}

class PlayState implements MusicState{

	@Override
	public void press() {
		System.out.println("播放音乐!");
	}
}

class PauseState implements MusicState{

	@Override
	public void press() {
		System.out.println("暂停音乐!");
	}
}




//定义一个耳机
class Headset{
	private MusicState state;
	private int i;
	public Headset(MusicState state){
		this.state=state;
	}
	public void press() {
		if((i&1)==0) {
			this.state=new PlayState();
		}else {
			this.state=new PauseState();
		}
		this.state.press();
		i++;
	}
	public MusicState getState() {
		return state;
	}
	public void setState(MusicState state) {
		this.state = state;
	}
	
}

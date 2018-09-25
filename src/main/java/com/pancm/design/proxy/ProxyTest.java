package com.pancm.design.proxy;

/**
* @Title: ProxyTest
* @Description:代理模式 
* 在代理模式中，一个类代表另一个类的功能。这种类型的设计模式属于结构型模式。
    在代理模式中，我们创建具有现有对象的对象，以便向外界提供功能接口。
    
  应用实例： 1、Windows 里面的快捷方式。 2、spring aop。  3、支票。
* @Version:1.0.0  
* @author pancm
* @date 2018年8月8日
*/
public class ProxyTest {

	public static void main(String[] args) {
		
		/*
		 * 
		 优点： 1、职责清晰。 2、高扩展性。 3、智能化。
		缺点： 1、由于在客户端和真实主题之间增加了代理对象，因此有些类型的代理模式可能会造成请求的处理速度变慢。 
			2、实现代理模式需要额外的工作，有些代理模式的实现非常复杂。
		
		注意事项： 1、和适配器模式的区别：适配器模式主要改变所考虑对象的接口，而代理模式不能改变所代理类的接口。 
			  2、和装饰器模式的区别：装饰器模式为了增强功能，而代理模式是为了加以控制。
		 */
		
		/*
		 * <大话设计模式>中的追求者、代理者
		 *  张三想买东西，可以自己买，但是此时李四正好在商场，于是便让李四帮忙(代理)买了。
		 */
		
		String name = "张三";
		String name2 = "李四";
		Shopping shopping = new ExecutePerson(name);
		Shopping shopping2 = new ExecutePerson(name2);
		shopping.buyFood();
		shopping2.buyFood();
		
		/*
		 *  核心就是在不影响之前的功能下进行扩展
		 */
	}
}

/*
 * 
 */
interface Shopping {
	void buyFood();
}

/*
 * 定义一个需要买东西的人
 */
class ExecutePerson implements Shopping{
	
	private  String name;
	
	public ExecutePerson(String name) {
		this.name = name;
	}
	
	@Override
	public void buyFood() {
		System.out.println(name+" 买东西");
	}
}

/*
 * 定义一个可以帮买东西的人
 */
class ProxyPerson implements Shopping{
	private String name;
	private ExecutePerson ep;
	
	public ProxyPerson(String name) {
		this.name = name;
	}

	@Override
	public void buyFood() {
		if(ep == null){
			ep = new ExecutePerson(name);
		}
		ep.buyFood();
	}
}



package com.pancm.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Title: ProxyTest
 * @Description:代理模式 在代理模式中，一个类代表另一个类的功能。这种类型的设计模式属于结构型模式。
 *                   在代理模式中，我们创建具有现有对象的对象，以便向外界提供功能接口。
 * 
 *                   应用实例： 1、Windows 里面的快捷方式。 2、spring aop。 3、支票。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class ProxyTest {

	public static void main(String[] args) {

		/*
		 * 
		 * 
		 * 一个是真正的你要访问的对象(目标类)，一个是代理对象,真正对象与代理 对象实现同一个接口,先访问代理类再访问真正要访问的对象。
		 * 代理模式分为静态代理、动态代理。
		 * 静态代理是由程序员创建或工具生成代理类的源码，再编译代理类。所谓静态也就是在程序运行前就已经存在代理类的字节码文件，
		 * 代理类和委托类的关系在运行前就确定了。 动态代理是在实现阶段不用关心代理类，而在运行阶段才指定哪一个对象。
		 * 
		 * 组成： 抽象角色：通过接口或抽象类声明真实角色实现的业务方法。
		 * 代理角色：实现抽象角色，是真实角色的代理，通过真实角色的业务逻辑方法来实现抽象方法，并可以附加自己的操作。
		 * 真实角色：实现抽象角色，定义真实角色所要实现的业务逻辑，供代理角色调用。
		  
		 * JDK对动态代理提供了以下支持:	
			java.lang.reflect.Proxy 动态生成代理类和对象
			java.lang.reflect.InvocationHandler  
			可以通过invoke方法实现对真实角色的代理访问;
			每次通过Proxy生成代理类对象时都要指定对象的处理器对象.

		 * 优点： 1、职责清晰。 2、高扩展性。 3、智能化。
		 *  缺点：
		 * 1、由于在客户端和真实主题之间增加了代理对象，因此有些类型的代理模式可能会造成请求的处理速度变慢。
		 * 2、实现代理模式需要额外的工作，有些代理模式的实现非常复杂。
		 * 
		 * 注意事项： 1、和适配器模式的区别：适配器模式主要改变所考虑对象的接口，而代理模式不能改变所代理类的接口。
		 * 2、和装饰器模式的区别：装饰器模式为了增强功能，而代理模式是为了加以控制。
		 */

		/*
		 * <大话设计模式>中的追求者、代理者 张三想买东西，可以自己买，但是此时李四正好在商场，于是便让李四帮忙(代理)买了。
		 */

		/*
		 * 静态代理
		 */

		String name = "李四";
		Shopping shopping = new ProxyPerson(new ExecutePerson(name));
		shopping.buyFood();

		/*
		 * 动态代理
		 */
		Shopping shopping2 = (Shopping) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Shopping.class}, new ProxyPerson2(new ExecutePerson(name)));
		shopping2.buyFood();

		/*
		 * 核心就是在不影响之前的功能下进行扩展
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
class ExecutePerson implements Shopping {

	private String name;

	public ExecutePerson(String name) {
		this.name = name;
	}

	@Override
	public void buyFood() {
		System.out.println(name + " 买东西");
	}
}

/*
 * 静态代理
 * 定义一个可以帮买东西的人
 */
class ProxyPerson implements Shopping {
	private ExecutePerson ep;

	public ProxyPerson(ExecutePerson ep) {
		this.ep = ep;
	}

	@Override
	public void buyFood() {
		ep.buyFood();
	}
}

/*
 * 动态代理
 * 定义一个可以帮买东西的人
 */
class ProxyPerson2 implements InvocationHandler {
	private Shopping shopping;

	private final String methodName = "buyFood";

	public ProxyPerson2(Shopping shopping) {
		this.shopping = shopping;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		if (methodName.equals(method.getName())) {
			result = method.invoke(shopping, args);
		}
		return result;
	}
}

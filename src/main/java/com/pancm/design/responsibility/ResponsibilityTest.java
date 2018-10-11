package com.pancm.design.responsibility;

/**
 * @Title: ResponsibilityTest
 * @Description: 责任链模式
  	顾名思义，责任链模式（Chain of Responsibility Pattern）为请求创建了一个接收者对象的链。
  	这种模式给予请求的类型，对请求的发送者和接收者进行解耦。这种类型的设计模式属于行为型模式。
	在这种模式中，通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class ResponsibilityTest {

	public static void main(String[] args) {
		
		
		/*
		 * 通过条件判断是否能够处理，符合就行处理，否则就转交给下一个进行处理
		 * 
		 */
		String name = "xuwujing";
		String something = "去聚餐";
		String something2 = "去旅游";
		Learder learder1 =new Supervisor(name, something);
		Learder learder2 =new BranchManager(name, something);
		Learder learder3 =new GeneralManager(name, something);
		learder1.setLearder(learder2);
		learder2.setLearder(learder3);
		learder1.handler(1);
		
		Learder learder4 =new Supervisor(name, something2);
		Learder learder5 =new BranchManager(name, something2);
		Learder learder6 =new GeneralManager(name, something2);
		learder4.setLearder(learder5);
		learder5.setLearder(learder6);
		learder4.handler(0);
		
		
		
	}

}

// 定义一个抽象类
abstract class Handler {
	protected Handler successor;

	/** * 示意处理请求的方法，虽然这个示意方法是没有传入参数的 但实际是可以传入参数的，根据具体需要来选择是否传递参数 */
	public abstract void handleRequest();

	/** * 取值方法 */
	public Handler getSuccessor() {
		return successor;
	}

	/** * 赋值方法，设置后继的责任对象 */
	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}

}

// 具体的业务
class ConcreteHandler extends Handler {
	@Override
	public void handleRequest() {
		/** * 判断是否有后继的责任对象 如果有，就转发请求给后继的责任对象 如果没有，则处理请求 */
		if (getSuccessor() != null) {
			System.out.println("放过请求");
			getSuccessor().handleRequest();
		} else {
			System.out.println("处理请求");
		}
	}
}


abstract class Learder{
	
	
	protected Learder learder;
	
	protected void setLearder(Learder learder){
		this.learder=learder;
	}
	
	protected Learder getLearder(){
		return learder;
	}
	
	abstract void handler(int  level);
}

//主管
class Supervisor extends Learder{
	 private String name;
	 private String something;
	 public Supervisor(String name,String something) {
		this.name=name;
		this.something=something;
	}
	
	@Override
	void handler(int level) {
		//如果级别在自己的处理范围之内
		if(level>1){
			System.out.println("主管处理了  "+name+"所述的<"+something+">事情!");
		}else{
			System.out.println("主管未能处理  "+name+"所述的<"+something+">事情!转交给上级!");
			getLearder().handler(level);
		}
	}
}

//部门经理
class BranchManager extends Learder{
	 private String name;
	 private String something;
	 public BranchManager(String name,String something) {
		this.name=name;
		this.something=something;
	}
	
	@Override
	void handler(int level) {
		//如果级别在自己的处理范围之内
		if(level>0){
			System.out.println("部门经理处理了  "+name+"所述的<"+something+">事情!");
		}else{
			System.out.println("部门经理未能处理  "+name+"所述的<"+something+">事情!转交给上级!");
			getLearder().handler(level);
		}
	}
}

//总经理
class GeneralManager extends Learder{
	 private String name;
	 private String something;
	 public GeneralManager(String name,String something) {
		this.name=name;
		this.something=something;
	}
	
	@Override
	void handler(int level) {
		//如果级别在自己的处理范围之内
		if(level>-1){
			System.out.println("总经理处理了  "+name+"所述的<"+something+">事情!");
		}else{
			System.out.println("总经理未能处理  "+name+"所述的<"+something+">事情!转交给上级!");
			getLearder().handler(level);
		}
	}
}


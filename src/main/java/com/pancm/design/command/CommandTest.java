package com.pancm.design.command;

import java.util.ArrayList;
import java.util.List;

/**
* @Title: CommandTest
* @Description:命令模式 
  命令模式（Command Pattern）是一种数据驱动的设计模式，它属于行为型模式。
  请求以命令的形式包裹在对象中，并传给调用对象。调用对象寻找可以处理该命令的合适的对象，并把该命令传给相应的对象，该对象执行命令.
  核心:将一个请求封装成一个对象，从而可以用不同的请求对客户进行参数化。
* @Version:1.0.0  
* @author pancm
* @date 2018年8月8日
*/
public class CommandTest {

	public static void main(String[] args) {
		
		/*
		 * 基本使用
		 * 所需的角色
		 * 1、received 真正的命令执行对象 ;
		 * 2、Command  ;
		 * 3、invoker 使用命令对象的入口;
		 */
		String name = "xuwujing";
		Student student = new  Student();
		Command command1 = new LiTeacher(student);
		Command command2 = new WangTeacher(student);
		Invoker invoker =new Invoker();
		invoker.setCommand(command1);
		invoker.setCommand(command2);
		invoker.executeCommand(name);
		
		/*
		 * 优点： 1、降低了系统耦合度。 2、新的命令可以很容易添加到系统中去。
		         缺点：使用命令模式可能会导致某些系统有过多的具体命令类。
		 */
	}

}

//定义一个学生
class Student{
	
	void cleanClassRoom(String name){
		System.out.println(name+" 开始打扫教室...");
	}
	void doHomeWork(String name){
		System.out.println(name+" 开始做作业...");
	}
}

//定义一个命令抽象类
abstract class Command{
	protected Student student;
	public Command(Student student){
		this.student = student;
	}
	//执行方法
	abstract void execute(String name);
}

//将一个接收者和动作进行绑定，调用接收者相应的操作
class LiTeacher extends Command{
	public LiTeacher(Student student) {
		super(student);
	}
	@Override
	void execute(String name) {
		student.cleanClassRoom(name);
	}
}

//将一个接收者和动作进行绑定，调用接收者相应的操作
class WangTeacher extends Command{
	public WangTeacher(Student student) {
		super(student);
	}
	@Override
	void execute(String name) {
		student.doHomeWork(name);
	}
}


//用于执行这个请求
class Invoker {
	private List<Command> commands = new ArrayList<Command>();
	
	
	//添加这个命令
	public void setCommand(Command command) {
		//设置执行命令的
		if(command.toString().indexOf("WangTeacher")>-1) {
			System.out.println("不执行 WangTeacher 的命令!");
		}else {
			commands.add(command);
		}
	}
	
	//执行这个命令
	public void executeCommand(String name) {
		commands.forEach(command->{
			command.execute(name);
		});
	}
	
	//撤销这个命令
	public void undoCommand(Command command) {
		commands.remove(command);
		System.out.println("撤销该命令!");
	}
	
}
package com.pancm.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: ObserverTest
 * @Description: 观察者模式 当对象间存在一对多关系时，则使用观察者模式（Observer
 *               Pattern）。比如，当一个对象被修改时，则会自动通知它的依赖对象。观察者模式属于行为型模式。
 观察者模式是对象的行为模式，又叫发布-订阅(Publish/Subscribe)模式、模型-视图(Model/View)模式、源-监听器(Source/Listener)模式或从属者(Dependents)模式。
观察者模式定义了一种一对多的依赖关系，让多个观察者对象同时监听某一个主题对象。这个主题对象在状态上发生变化时，会通知所有观察者对象，使它们能够自动更新自己。    
 *               核心:定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年8月8日
 */
public class ObserverTest {

	public static void main(String[] args) {

		/*
		 * 基本使用
		 * 
		 基本角色
		 1.抽象主题角色（Subject）：它把所有观察者对象的引用保存到一个聚集里，每个主题都可以有任何数量的观察者。抽象主题提供一个接口，可以增加和删除观察者对象。
		 2.具体主题角色（ConcreteSubject）：将有关状态存入具体观察者对象；在具体主题内部状态改变时，给所有登记过的观察者发出通知。
		 3.抽象观察者角色（Observer）：为所有的具体观察者定义一个接口，在得到主题通知时更新自己。
		 4.具体观察者角色（ConcreteObserver）：实现抽象观察者角色所要求的更新接口，以便使本身的状态与主题状态协调。  
		   
		 * 
		 */

		
		/*
		 比如，订阅番剧，番剧更新则通知订阅的人。
		 xuwujing订阅了 <冰菓>和<fate/zero>番剧，当番剧更新的时候，他们就会收到通知！
		 如果他们取消了该番剧的订阅，那么他就不会收到该番剧的通知了。
		 */
		String name1 ="张三";
		String name2 ="xuwujing";
		String	bingguo = "冰菓";
		String	fate = "fate/zero";
		BangumiSubject bs1 = new Bangumi(bingguo);
		BangumiSubject bs2 = new Bangumi(fate);
        
        UserObserver uo1 = new User(name1);
        UserObserver uo2 = new User(name2);
        
        //进行订阅
        bs1.toThem(uo1);
        bs1.toThem(uo2);
        bs2.toThem(uo1);
        bs2.toThem(uo2);
        //进行通知
        bs1.notifyUser();
        bs2.notifyUser();
        
        //取消订阅
        bs1.callOff(uo1);
        bs2.callOff(uo2);
        //进行通知
        bs1.notifyUser();
        bs2.notifyUser();
        
        /*
        	输出结果：
                    用户张三订阅了冰菓!
		用户xuwujing订阅了冰菓!
		用户张三订阅了fate/zero!
		用户xuwujing订阅了fate/zero!
		冰菓更新了！开始通知订阅该番剧的用户！
		张三订阅的番剧: 冰菓更新啦！
		xuwujing订阅的番剧: 冰菓更新啦！
		fate/zero更新了！开始通知订阅该番剧的用户！
		张三订阅的番剧: fate/zero更新啦！
		xuwujing订阅的番剧: fate/zero更新啦！
		用户张三取消订阅冰菓!
		用户xuwujing取消订阅fate/zero!
		冰菓更新了！开始通知订阅该番剧的用户！
		xuwujing订阅的番剧: 冰菓更新啦！
		fate/zero更新了！开始通知订阅该番剧的用户！
		张三订阅的番剧: fate/zero更新啦！
         */
        
		/*
		 * 
		 优点： 1、解除耦合，让耦合的双方都依赖于抽象，从而使得各自的变换都不会影响另一边的变换。
		 缺点： 1、如果一个被观察者对象有很多的直接和间接的观察者的话，将所有的观察者都通知到会花费很多时间。
		  	2、如果在观察者和观察目标之间有循环依赖的话，观察目标会触发它们之间进行循环调用，可能导致系统崩溃。
		  	3、观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化。
		 
		 使用场景：
		  
		 1. 需要关联行为的场景；
		 2. 事件需要创建一个触发链的场景，比如监控；
		 3.跨系统的消息交换场景，比如消息队列、事件总线的处理机制。
		
		  注意事项： 
		   1、JAVA中已经有了对观察者模式的支持类。
		   2、避免循环引用。 
		   3、如果顺序执行，某一观察者错误会导致系统卡壳，一般采用异步方式。
		 * 
		 */
	}

}



//定义一个抽象主题, 将观察者(订阅者)聚集起来,可以进行新增、删除和通知。
//这里就可以当做番剧
interface BangumiSubject{
	//追番
	void toThem(UserObserver user);
	//取消追番
	void callOff(UserObserver user);
	//通知
	void notifyUser();
}


//定义一个抽象观察者,在得到通知时进行更新
//这里就可以当做是用户
interface UserObserver{
	//更新通知
	void update(String bangumi);
	//得到用户名称
	String getName();
}

//定义一个具体主题,实现了抽象主题(BangumiSubject)接口的方法
//同时通过一个List集合保存观察者的信息，当需要通知观察者的时候，遍历通知即可。
class  Bangumi implements BangumiSubject {
    

    private List<UserObserver> list;
    private String  anime;
    public Bangumi(String anime) {
        this.anime = anime;
    	list = new ArrayList<UserObserver>();
    }
    
    @Override
    public void toThem(UserObserver user) {
        System.out.println("用户"+user.getName()+"订阅了"+anime+"!");
        list.add(user);
    }
    
    @Override
    public void callOff(UserObserver user) {
        if(!list.isEmpty()) {
        	System.out.println("用户"+user.getName()+"取消订阅"+anime+"!");
        	list.remove(user);
        }
    }

    @Override
    public void notifyUser() {
    	System.out.println(anime+"更新了！开始通知订阅该番剧的用户！");
    	list.forEach(user->
    		user.update(anime)
    	);
    }

}


//定义了一个具体观察者,实现抽象观察者(UserObserver)接口的方法
class  User implements UserObserver{
	private String name;
	public User(String name){
		this.name = name;
	}
	
	@Override
	public void update(String bangumi) {
		System.out.println(name+"订阅的番剧: " + bangumi+"更新啦！");
	}

	@Override
	public String getName() {
		return name;
	} 
}


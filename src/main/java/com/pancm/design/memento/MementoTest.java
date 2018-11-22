package com.pancm.design.memento;

/**
 * @Title: MementoTest
 * @Description: 备忘录模式 
  备忘录模式（Memento Pattern）保存一个对象的某个状态，以便在适当的时候恢复对象。备忘录模式属于行为型模式。
核心:  在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年10月29日
 */
public class MementoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * 基本使用
		 	基本角色：备忘录(Memento)角色、发起人(Originator)角色、负责人(Caretaker)角色。
		 	1.备忘录(Memento)：主要的功能是包含要被恢复的对象的状态。
		 	2.发起人(Originator)：在创建的时候，会在备忘录对象中存储状态。
		 	3.负责人(Caretaker)：主要是负责从备忘录对象中恢复对象的状态。
		 */
		/*
		 * 使用
		 * 常见场景就是游戏存档，电脑中的 Ctrl+Z撤销功能
		 */
		int level = 1;
		int life = 100;
		//创建一个玩家
		Player player =new Player(level, life);
		System.out.println("玩家xuwujing进入游戏!");
		//状态
		player.getStatus();
		//进行练级
		player.leveling();
		GameSavePage savePage =new GameSavePage();
		//状态
		player.getStatus();
		System.out.println("玩家xuwujing正在存档...");
		//第一次存档
		savePage.setSm(player.saveStateToMemento());
		System.out.println("玩家xuwujing存档成功!");
		System.out.println("玩家xuwujing挑战新手村的BOSS!");
		 boolean flag=player.challengeBOSS();
		if(flag) {
			System.out.println("玩家xuwujing挑战BOSS成功!");
			return;
		}
		System.out.println("玩家xuwujing挑战BOSS失败!游戏结束！开始读取存档...");
		savePage.getSm();
		System.out.println("玩家xuwujing读取存档成功!");
		//进行练级
		player.leveling();
		//状态
		player.getStatus();
		System.out.println("玩家xuwujing挑战新手村的BOSS!");
		flag=player.challengeBOSS();
		if(flag) {
			System.out.println("玩家xuwujing挑战BOSS成功!");
			return;
		}
		
		/*
		 
		 玩家xuwujing进入游戏!
		玩家xuwujing当前信息:
		人物等级:1,人物生命:100
		恭喜玩家xuwujing升级!等级提升了1,生命提升了10！
		玩家xuwujing当前信息:
		人物等级:2,人物生命:110
		玩家xuwujing正在存档...
		玩家xuwujing存档成功!
		玩家xuwujing挑战新手村的BOSS!
		玩家xuwujing挑战BOSS失败!游戏结束！开始读取存档...
		玩家xuwujing读取存档成功!
		恭喜玩家xuwujing升级!等级提升了1,生命提升了10！
		玩家xuwujing当前信息:
		人物等级:3,人物生命:120
		玩家xuwujing挑战新手村的BOSS!
		玩家xuwujing挑战BOSS成功!
		 
		 */
		
		
		
		/*
		 * 优点： 
		 1、给用户提供了一种可以恢复状态的机制，可以使用户能够比较方便地回到某个历史的状态。 
		 2、实现了信息的封装，使得用户不需要关心状态的保存细节。
		  缺点：
		   消耗资源。如果类的成员变量过多，势必会占用比较大的资源，而且每一次保存都会消耗一定的内存。
		  使用场景： 
		  1、需要保存/恢复数据的相关状态场景。 
		  2、提供一个可回滚的操作。
		  注意事项:为了节约内存，可使用原型模式+备忘录模式。
		 */
		
	}
}



//创建一个存档 信息(备忘录)
class SaveMsg{
	//存档等级
	private  int level;
	//存档时的生命值
	private int life;
	
	
	public SaveMsg( int level, int life) {
		super();
		this.level = level;
		this.life = life;
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
}

//设置一个玩家(发起者)
class Player {
	//等级
	private  int level;
	//生命值
	private int life;

	public Player( int level, int life) {
		super();
		this.level = level;
		this.life = life;
	}
	//保存信息
	public SaveMsg saveStateToMemento() {
		return new SaveMsg(level,life);
	}
	
	//恢复信息
	public void getStateFromMemento(SaveMsg sm) {
		this.level = sm.getLevel();
		this.life = sm.getLife();
	}
	
	//获取当前状态
	public void getStatus() {
		System.out.println("玩家xuwujing当前信息:");
		System.out.println("人物等级:"+level+",人物生命:"+life);
	}
	
	//练级
	public void leveling() {
		this.level = this.level+1;
		this.life = this.life+10;
		System.out.println("恭喜玩家xuwujing升级!等级提升了1,生命提升了10！");
	}
	
	//挑战BOSS
	public boolean challengeBOSS() {
		//设置条件
		return this.level>2&&this.life>100;
	}
}


//设置一个游戏存档页(负责人)
class GameSavePage{
	private SaveMsg sm;

	public SaveMsg getSm() {
		return sm;
	}
	public void setSm(SaveMsg sm) {
		this.sm = sm;
	}
	
}




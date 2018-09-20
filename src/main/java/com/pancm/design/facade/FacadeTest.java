package com.pancm.design.facade;

/**
* @Title: FacadeTest
* @Description: 
* 外观模式测试代码
* 
* 为子系统中的一组接口提供一个一致的界面，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
* 
* 比如windows开机:启动CPU、启动内存、启动硬盘
* windows关机:关闭硬盘、关闭内存、关闭CPU
* 
* @Version:1.0.0  
* @author pancm
* @date 2018年8月8日
*/
public class FacadeTest {

	public static void main(String[] args) {
		
		/*
		 * 对外提供 一个界面
		 * 游戏装在电脑上，想玩游戏就在电脑启动游戏就可以了
		 */
		Computer computer=new Computer();
		computer.playDNF();
		computer.playLOL();
		computer.playWOW();
	}
}

interface Game{
	void play();
}

class DNF implements Game{

	@Override
	public void play() {
		System.out.println("正在玩DNF...");
	}
}

class LOL implements Game{
	@Override
	public void play() {
		System.out.println("正在玩LOL...");
	}
}

class WOW implements Game{
	@Override
	public void play() {
		System.out.println("正在玩WOW...");
	}
}

class Computer{
	
	private Game dnf;
	private Game lol;
	private Game wow;
	
	public Computer() {
		dnf=new DNF();
		lol=new LOL();
		wow=new WOW();
	}
	
	public void playDNF(){
		dnf.play();
	}
	
	public void playLOL(){
		lol.play();
	}
	
	public void playWOW(){
		wow.play();
	}
	
	
}
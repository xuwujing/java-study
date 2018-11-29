package com.pancm.design.factory;

/**
* @Title: FactorymTest
* @Description: 
* 简单工厂模式测试
* @Version:1.0.0  
* @author pancm
* @date 2018年7月23日
*/
public class FactoryTest {
	private static final String LOL="LOL"; 
	private static final String DNF="DNF"; 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * 简单工厂模式
		 * 根据条件决定一个接口由哪个具体产品类来实现
		 * 优点:
		 * 缺点:扩展性差
		 */
		Game game= ComputerFactory.playGame(LOL);
		Game game2= ComputerFactory.playGame(DNF);
		game.play();
		game2.play();
		
		/**
		 * 工厂方法模式
		 * 
		 * 优点:扩展性高
		 * 缺点:增加了复杂度
		 */
		Game game3=new LOLFactory().playGame();
		Game game4=new DNFFactory().playGame();
		Game game5=new WOWFactory().playGame();
		game3.play();
		game4.play();
		game5.play();
		
		
		/**
		 * 抽象工厂模式
		 * 
		 * 优点:
		 * 
		 */
		ComputerFactory3 cf3=new PVPFactory();
		cf3.playGame().play();
		cf3.playGame2().play();
		ComputerFactory3 cf4=new PVEFactory();
		cf4.playGame().play();
		cf4.playGame2().play();
		
		
	}
	
	
	
	
}

/**
 * 定义一个接口
 */
interface Game{
	void play();
}

/**
 * 定义一个实现类
 */
class LOL implements Game{
	@Override
	public void play() {
		System.out.println("正在玩LOL...");
	}	
}

class DNF implements Game{
	@Override
	public void play() {
		System.out.println("正在玩DNF...");
	}	
}

class WOW  implements Game{
	@Override
	public void play() {
		System.out.println("正在玩WOW...");
	}	
}

/**
 * 定义一个电脑
 */
class ComputerFactory{
	private static final String LOL="LOL"; 
	private static final String DNF="DNF"; 
	//玩游戏
	 public static Game playGame(String game){
		 if(LOL.equalsIgnoreCase(game)){
			 return new LOL();
		 }else if(DNF.equalsIgnoreCase(game)){
			 return new DNF();
		 }
		 return null;
	 }	
}

interface ComputerFactory2{
	Game playGame();
}

class LOLFactory implements ComputerFactory2{
	@Override
	public Game playGame() {
		return new LOL();
	}
}

class DNFFactory implements ComputerFactory2{
	@Override
	public Game playGame() {
		return new DNF();
	}
}

class WOWFactory implements ComputerFactory2{
	@Override
	public Game playGame() {
		return new WOW();
	}
}

interface ComputerFactory3{
	Game playGame();
	
	Game playGame2();
}

class PVPFactory implements ComputerFactory3{

	@Override
	public Game playGame() {
		return new LOL();
	}

	@Override
	public Game playGame2() {
		return new WOW();
	}
	
}

class PVEFactory implements ComputerFactory3{

	@Override
	public Game playGame() {
		return new DNF();
	}

	@Override
	public Game playGame2() {
		return new WOW();
	}
	
}



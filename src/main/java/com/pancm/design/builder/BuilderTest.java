package com.pancm.design.builder;

/**
* @Title: BuilderTest
* @Description: 
* 建造者模式
* 将一个复杂的构建与其表示相分离，使得同样的构建过程可以创建不同的表示。
* @Version:1.0.0  
* @author pancm
* @date 2018年8月7日
*/
public class BuilderTest {

	public static void main(String[] args) {
		FoodStore foodStore=new FoodStore();
		Meal meal=foodStore.createBreakfast(new Breakfast());
		Meal meal2=foodStore.createBreakfast(new Lunch());
		System.out.println("小明早上吃的是:"+meal.getFood()+",喝的饮料是:"+meal.getDrinks());
		System.out.println("小明中午吃的是:"+meal2.getFood()+",喝的饮料是:"+meal2.getDrinks());
		
	}

}

/**
 * 
* @Title: Breakfast
* @Description: 
* 定义一份餐点
* 分为吃的和喝的
* @Version:1.0.0  
* @author pancm
* @date 2018年8月7日
 */
class Meal{
	private String food;
	private String drinks;
	
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	
	public String getDrinks() {
		return drinks;
	}
	public void setDrinks(String drinks) {
		this.drinks = drinks;
	}
}

/**
 * 
* @Title: IBuilderFood
* @Description: 
* 定义一个食物接口
* @Version:1.0.0  
* @author pancm
* @date 2018年8月7日
 */
interface IBuilderFood{
	void buildFood();
	void buildDrinks();
	Meal createMeal();
}

/**
 * 
* @Title: Breakfast
* @Description:定义一份早餐
* @Version:1.0.0  
* @author pancm
* @date 2018年8月7日
 */
class Breakfast implements IBuilderFood{
	Meal meal;

	public Breakfast(){
		meal=new Meal();
	}
	
	@Override
	public void buildFood() {
		meal.setFood("煎饼");
	}

	@Override
	public void buildDrinks() {
		meal.setDrinks("豆浆");	
	}
	
	@Override
	public Meal createMeal() {
		return meal;
	}
}

/**
 * 
* @Title: Lunch
* @Description: 定义一份午餐
* @Version:1.0.0  
* @author pancm
* @date 2018年8月15日
 */
class Lunch implements IBuilderFood{
	Meal meal;

	public Lunch(){
		meal=new Meal();
	}
	
	@Override
	public void buildFood() {
		meal.setFood("盒饭");
	}

	@Override
	public void buildDrinks() {
		meal.setDrinks("果汁");	
	}
	
	@Override
	public Meal createMeal() {
		return meal;
	}
}

/**
 * 
* @Title: FoodStore
* @Description: 
* 定义一个餐点
* 导演者
* @Version:1.0.0  
* @author pancm
* @date 2018年8月15日
 */
class FoodStore{
	public Meal createBreakfast(IBuilderFood bf){
		bf.buildDrinks();
		bf.buildFood();
		return bf.createMeal();
	}
}


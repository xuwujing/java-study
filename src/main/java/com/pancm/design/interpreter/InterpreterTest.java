package com.pancm.design.interpreter;

/**
* @Title: InterpreterTest
* @Description: 解释器模式
解释器模式（Interpreter Pattern）是类的行为模式。给定一个语言之后，解释器模式可以定义出其文法的一种表示，并同时提供一个解释器。客户端可以使用这个解释器来解释这个语言中的句子。
* 比如正则表达式
* @Version:1.0.0  
* @author pancm
* @date 2018年8月8日
*/
public class InterpreterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		String word = "好好学习，天天向上!";
		Expreeion expreeion =new  BaiduExpreeion();
		Expreeion expreeion2 =new  YouDaoExpreeion();
		Expreeion expreeion3 =new  XuWuJingExpreeion();
		expreeion.interpert(word);
		expreeion2.interpert(word);
		expreeion3.interpert(word);
		
		
		/*
		 输出结果：
		 百度翻译：好好学习，天天向上! 的英文是  Study hard and keep up!
		有道翻译：好好学习，天天向上! 的英文是  study hard and make progress every day！
		xuwujing翻译：好好学习，天天向上! 的英文是  Good good study, day day up！
		 
		 */
		
		/*
		 
		 应用实例：编译器、运算表达式计算。

		优点： 1、可扩展性比较好，灵活。 2、增加了新的解释表达式的方式。 3、易于实现简单文法。
		
		缺点： 1、可利用场景比较少。 2、对于复杂的文法比较难维护。 3、解释器模式会引起类膨胀。 4、解释器模式采用递归调用方法。
		
		使用场景： 1、可以将一个需要解释执行的语言中的句子表示为一个抽象语法树。 2、一些重复出现的问题可以用一种简单的语言来进行表达。 3、一个简单语法需要解释的场景。
		 
		 */
		
	}

}



/*
   * 定义一个表达式，有一个解释的方法
 */
interface Expreeion{
	void interpert(String word);
}

class  BaiduExpreeion implements Expreeion{
	String str ="好好学习，天天向上!";
	@Override
	public void interpert(String word) {
		//如果是这句就翻译
		if(str.equals(word)) {
			System.out.println("百度翻译："+word+" 的英文是  Study hard and keep up!");
		}
	}
}

class  YouDaoExpreeion implements Expreeion{
	String str ="好好学习，天天向上!";
	@Override
	public void interpert(String word) {
		//如果是这句就翻译
		if(str.equals(word)) {
			System.out.println("有道翻译："+word+" 的英文是  study hard and make progress every day！");
		}
	}
}

class  XuWuJingExpreeion implements Expreeion{
	String str ="好好学习，天天向上!";
	@Override
	public void interpert(String word) {
		//如果是这句就翻译
		if(str.equals(word)) {
			System.out.println("xuwujing翻译："+word+" 的英文是  Good good study, day day up！");
		}
	}
}

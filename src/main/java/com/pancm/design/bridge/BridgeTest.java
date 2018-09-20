package com.pancm.design.bridge;

/**
* @Title: BridgeTest
* @Description:桥接模式 
* 将抽象部分与实现部分分离，使它们都可以独立的变化。
* @Version:1.0.0  
* @author pancm
* @date 2018年8月8日
*/
public class BridgeTest {
	public static void main(String[] args) {
		Paper paper=new ExaminationPaper();
		paper.setPen(new RedPen());
		paper.writing();
		
		Paper paper2=new NewsPaper();
		paper2.setPen(new BlackPen());
		paper2.writing();
	}
}

/**
 * 
* @Title: ColourPen
* @Description: 
* 笔 
* @Version:1.0.0  
* @author pancm
* @date 2018年8月22日
 */
interface Pen{
	void write();
}

class RedPen implements Pen{
	@Override
	public void write() {
		System.out.println("红色的字");
	}
}

class BlackPen implements Pen{
	@Override
	public void write() {
		System.out.println("黑色的字");
	}
}


abstract class  Paper{
	protected  Pen pen;
	
	void setPen(Pen pen){
		this.pen=pen;
	}
	
	abstract void writing();
}

/**
 * 
* @Title: ExaminationPaper
* @Description:考试用的卷子 
* @Version:1.0.0  
* @author pancm
* @date 2018年8月22日
 */
class ExaminationPaper extends Paper{
	@Override
	void writing() {
		pen.write();
	}
}

class NewsPaper extends Paper{
	@Override
	void writing() {
		pen.write();
	}
}


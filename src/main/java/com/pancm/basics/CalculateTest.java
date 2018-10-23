package com.pancm.basics;

import java.math.BigInteger;

/**
 * @Title: CalculateTest
 * @Description: 运算符相关类
 * @Version:1.0.0
 * @author pancm
 * @date 2018年10月18日
 */
public class CalculateTest {
	
	public static void main(String[] args) {
		//自增运算符测试
		test1();
		//运算符优先级测试
		test2();
		//位运算符
		test3();
		//赋值运算符
		test4();
	}

	private static void test4() {
		int a = 4;
		a *= 5; 
		int b = 6;
		b %=3;
		int c = 9;
		c |= a;
		int d = 8;
		d <<=2;
		int e = 17;
		e &=9;
		int f = 16;
		f ^=2;
		System.out.println("赋值运算符测试开始");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println("赋值运算符测试结束");
		
		
		
		/*
		 
		 操作符	描述	例子
		=	简单的赋值运算符，将右操作数的值赋给左侧操作数	C = A + B将把A + B得到的值赋给C
		+ =	加和赋值操作符，它把左操作数和右操作数相加赋值给左操作数	C + = A等价于C = C + A
		- =	减和赋值操作符，它把左操作数和右操作数相减赋值给左操作数	C - = A等价于C = C -
		 A
		* =	乘和赋值操作符，它把左操作数和右操作数相乘赋值给左操作数	C * = A等价于C = C * A
		/ =	除和赋值操作符，它把左操作数和右操作数相除赋值给左操作数	C / = A等价于C = C / A
		（％）=	取模和赋值操作符，它把左操作数和右操作数取模后赋值给左操作数	C％= A等价于C = C％A
		<< =	左移位赋值运算符	C << = 2等价于C = C << 2
		>> =	右移位赋值运算符	C >> = 2等价于C = C >> 2
		＆=	按位与赋值运算符	C＆= 2等价于C = C＆2
		^ =	按位异或赋值操作符	C ^ = 2等价于C = C ^ 2
		| =	按位或赋值操作符	C | = 2等价于C = C | 2
		 
		 */
	}

	private static void test3() {
		int a = 64>>>2;
		int b = 2<<2;
		int c = a&b;
		int d = b|c;
		int e = ~a;
		int f = 60^13;
		System.out.println("位运算符测试开始");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
		System.out.println("位运算符测试结束");
		
		/*
		 * 
		 十进制
		 A = 60，B = 13
		 二进制
		 A = 0011 1100
		 B = 0000 1101
		 
		操作符	描述	例子
		＆	如果相对应位都是1，则结果为1，否则为0	（A＆B），得到12，即0000 1100
		|	如果相对应位都是0，则结果为0，否则为1	（A | B）得到61，即 0011 1101
		^	如果相对应位值相同，则结果为0，否则为1	（A ^ B）得到49，即 0011 0001
		〜	按位取反运算符翻转操作数的每一位，即0变成1，1变成0。	（〜A）得到-61，即1100 0011
		<< 	按位左移运算符。左操作数按位左移右操作数指定的位数。	A << 2得到240，即 1111 0000
		>> 	按位右移运算符。左操作数按位右移右操作数指定的位数。	A >> 2得到15即 1111
		>>> 按位右移补零操作符。左操作数的值按右操作数指定的位数右移，移动得到的空位以零填充。	A>>>2得到15即0000 1111
		 */
		
	}
	
	//运算符优先级测试
	private static void test2() {
		int a = 2+3*4/2 + (5+4)*2;
		int b = 2>>3+4/2-1;
		int c = a++*3-b--;
		int d = (a>c?4:5) + 4%2 << 3;
		System.out.println("运算符优先级测试开始");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println("运算符优先级测试结束");
		/*
		 * 最高优先级的运算符在的表的最上面，最低优先级的在表的底部。
		 类别	操作符	关联性
		后缀	() [] . (点操作符)	左到右
		一元	+ + - ！〜	从右到左
		乘性 	* /％	左到右
		加性 	+ -	左到右
		移位 	>> >>>  << 	左到右
		关系 	>> = << = 	左到右
		相等 	==  !=	左到右
		按位与	＆	左到右
		按位异或	^	左到右
		按位或	|	左到右
		逻辑与	&&	左到右
		逻辑或	| |	左到右
		条件	？：	从右到左
		赋值	= + = - = * = / =％= >> = << =＆= ^ = | =	从右到左
		逗号	，	左到右
		 */
		
	}

	private static void test1() {
		 int a=1,z=1;
		 int b = a++;
		 int c = ++a;
		 int x = 2*++a;
	     int y = 2*b++;
		System.out.println("自增运算符测试开始");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(z++);
		System.out.println(++z);
		System.out.println(x);
		System.out.println(y);
		System.out.println("自增加运算符测试结束");

	}

	
	
	
	private static void test10() {
		int i = 16;
		// 16转换的二进制数据
		int j = 10000;
		// 8转换的二进制数据
		int k = 1000;
		// 4转换的二进制数据
		int m = 100;
		// 32转换的二进制数据
		int n = 100000;
		System.out.println("--" + (j & i));
		System.out.println("--" + (k & i));
		System.out.println("--" + (m & i));
		System.out.println("--" + (n & i));
		System.out.println("--" + decimal2Binary(i));
		System.out.println("--" + biannary2Decimal(n));
		//十进制转二进制
		System.out.println("--" + Integer.toBinaryString(i));
	    /*
	     *  1. BigInteger的构造函数 
		    BigInteger(String src)默认参数字符串为10进制数值 
		    BigInteger(String src, int x)第2个参数x是指定第一个参数src的进制类型 
		    2. toString方法 
		    toString()默认把数值按10进制数值转化为字符串。 
		    toString(int x)把数值按参数x的进制转化为字符串
	     */
		System.out.println("--" + new BigInteger(String.valueOf(i)).toString(2));
		//二进制转十进制
		System.out.println("--" + new BigInteger(String.valueOf(j),2).toString());
	}
	
	/**
	 * 十进制转二进制
	 */
	public static String decimal2Binary(int de) {
		String numstr = "";
		while (de > 0) {
			int res = de % 2; // 除2 取余数作为二进制数
			numstr = res + numstr;
			de = de / 2;
		}
		return numstr;
	}

	/**
	 * 将二进制转换为10进制
	 * @param bi ：待转换的二进制
	 * @return
	 */
	public static Integer biannary2Decimal(int bi) {
		String binStr = bi + "";
		Integer sum = 0;
		int len = binStr.length();
		for (int i = 1; i <= len; i++) {
			// 第i位 的数字为：
			int dt = Integer.parseInt(binStr.substring(i - 1, i));
			sum += (int) Math.pow(2, len - i) * dt;
		}
		return sum;
	}

}

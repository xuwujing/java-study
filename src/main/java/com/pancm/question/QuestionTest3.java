package com.pancm.question;

/**
 * @Title: QuestionTest1
 * @Description:
 * @Version:1.0.0
 * @author pancm
 * @date 2017年7月21日
 */
public class QuestionTest3 {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * 请实现字符串反转，也就是 输入 abc，输出 cba
		 */
		String str="hello xuwujing";
		System.out.println(reverseString(str));
	}

	public static String reverseString(String str){
		if(str==null||str.length()<=1){
			return str;
		}	
		return reverseString(str.substring(1))+str.charAt(0);
	}
	
}

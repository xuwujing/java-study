/**
 * 
 */
package com.pancm.commons.apache;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @Title: LangTest
 * @Description:
 * @Version:1.0.0
 * @author pancm
 * @date 2018年5月14日
 */
public class LangTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 test();
	}

	public static void test() {
		// 1 合并两个数组: org.apache.commons.lang. ArrayUtils
		String[] s1 = new String[] { "1", "2", "3" };
		String[] s2 = new String[] { "a", "b", "c" };
		String[] s = (String[]) ArrayUtils.addAll(s1, s2);
		for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
		}
		String str = ArrayUtils.toString(s);
		str = str.substring(1, str.length() - 1);
		System.out.println(str + ">>" + str.length());

		System.out.println("测试截取:" + StringUtils.substringAfter("SELECT * FROM PERSON ", "FROM"));
		// 3 判断该字符串是不是为数字(0~9)组成，如果是，返回true 但该方法不识别有小数点和 请注意。
		System.out.println("数字判断:" + StringUtils.isNumeric("454534"));
		System.out.println("取得类名:" + ClassUtils.getShortClassName(LangTest.class));
		System.out.println("获取包名:" + ClassUtils.getPackageName(LangTest.class));

		System.out.println("是否是数字:" + NumberUtils.isCreatable("123"));
		System.out.println("随机数字和字母:" + RandomStringUtils.randomAlphanumeric(5));
		System.out.println("<>进行转义" + StringEscapeUtils.escapeHtml("<html>"));

		System.out.println("是否是null字符 :" + StringUtils.isBlank(null));
		System.out.println("是否是空字符 :" + StringUtils.isBlank(""));
		System.out.println("是否是空格字符 :" + StringUtils.isBlank("   "));
		System.out.println("分割数组:" + StringUtils.join(s1, ","));
		System.out.println("添加某个字符，使其长度等于所设置的:" + StringUtils.rightPad("abc", 6, 'T'));
		System.out.println("首字母大写:" + StringUtils.capitalize("abc"));
		System.out.println("去掉空格:" + StringUtils.deleteWhitespace("   ab  c  "));
		System.out.println("是否包含该字符:" + StringUtils.contains("abc", "ba"));
		System.out.println("表示左边的字符:" + StringUtils.left("abc", 2));
	}

	

}

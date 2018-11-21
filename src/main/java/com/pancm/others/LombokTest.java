package com.pancm.others;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Cleanup;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @Title: LombokTest
 * @Description: 学生信息表 里面的属性通过lombok实现
 * @Version:1.0.0
 * @author pancm
 * @date 2018年1月11日
 */
/*
 * @Data ：注解在类上；提供类所有属性的 getting 和 setting
 * 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 * 
 * @Setter：注解在属性上；为属性提供 setting 方法
 * 
 * @Getter：注解在属性上；为属性提供 getting 方法
 * 
 * @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 * 
 * @Slf4j ：注解在类上；为类提供一个 属性名为log 的 Slf4j 日志对象
 * 
 * @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
 * 
 * @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 * 
 * @ToString：注解在类上；为类提供一个toString的方法，可以指定属性进行排除
 * 
 * @Cleanup:注解在属性上：可以自动进行close
 * @NonNull:注解在属性上：可以避免空指针
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "id", "classId" })
public class LombokTest {
	/** 学生id */
	private int id;
	/** 学生姓名 */
	private String name;
	/** 班级ID */
	private int classId;

	public static void main(String[] args) {
		LombokTest student = new LombokTest();
		LombokTest student2 = new LombokTest(2, "zhangsan", 3);
		student.setId(1);
		student.setName("xuwujing");
		student.setClassId(1);
		log.info("id:{},姓名:{},班级:{}", student.getId(), student.getName(), student.getClassId());
		log.info("学生信息:{}", student2.toString());
		
		String name="xuwujing";
		String name2=null;
				
		try {
			test1();
			test2();
			test3(name);
			test4(name2);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NullPointerException e2) {
			System.out.println("出现了空指针！");
			e2.printStackTrace();
		}
		
		/*
 				INFO  com.pancm.test.pojoTest.Student - id:1,姓名:xuwujing,班级:1
				INFO  com.pancm.test.pojoTest.Student - 学生信息:Student(name=zhangsan)
				成功创建！
				成功创建！
				xuwujing
				出现了空指针！
				java.lang.NullPointerException: name is marked @NonNull but is null
				at com.pancm.test.pojoTest.Student.test4(Student.java:143)
				at com.pancm.test.pojoTest.Student.main(Student.java:81)
		 */
		
		
	}


	/**
	 * 普通的方法
	 * 
	 * @throws IOException
	 */
	private static  void test1() throws IOException {
		//创建要操作的文件路径和名称  
        String path ="E:/test/hello.txt";
        String str="你好!";
        FileWriter fw = new FileWriter(path);  
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(str);  
        bw.close();
        fw.close();  
        System.out.println("成功创建！");
	}

	/**
	 * 使用cleanup注解
	 * 
	 * @throws IOException
	 */
	private static void test2() throws IOException {
		//创建要操作的文件路径和名称  
        String path ="E:/test/hello.txt";
        String str="你好!";
        @Cleanup
        FileWriter fw = new FileWriter(path);  
        @Cleanup
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(str);  
        System.out.println("成功创建！");
	}
	
	


	/**
	 * @param name2
	 */
	private static void test3(String name) {
		if(null == name) {
			throw new NullPointerException("name is null");
		}
		System.out.println(name);
	}
	
	/**
	 * @param name2
	 */
	private static void test4(@NonNull String name) {
		System.out.println(name);
	}
}

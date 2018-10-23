package com.pancm.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

/**
 * 
* Title: Class
* Description: 
* 学生信息表
* Version:1.0.0  
* @author pancm
* @date 2018年1月11日
 */
/*@Data   ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
@Setter：注解在属性上；为属性提供 setting 方法
@Getter：注解在属性上；为属性提供 getting 方法
@Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
@NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
@AllArgsConstructor：注解在类上；为类提供一个全参的构造方法*/
@Data
@Log4j
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id","name"})
@SuppressWarnings("unused")
public class Student {
    /** 学生id */
	private int id;
	/** 学生姓名 */
	private String name;
	/** 班级ID  */
	private int  classId;
}

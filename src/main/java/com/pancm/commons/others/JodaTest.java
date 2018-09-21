package com.pancm.commons.others;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * 
* Title: JodaTest
* Description: Joda 时间工具包测试
* Version:1.0.0  
* @author pancm
* @date 2017年11月1日
 */
public class JodaTest {

	public static void main(String[] args) {
		jodaTest();
	}
	
	/**
	 * 日期方法
	 */
	private static void jodaTest(){
		//当前时间戳
		DateTime datetime =new DateTime();
		//当前的英文星期几
		System.out.println("Week:"+datetime.dayOfWeek().getAsText(Locale.ENGLISH));  //Week:Wednesday
		//本地的日期格式
		System.out.println("LocalDate:"+datetime.toLocalDate()); //LocalDate:2017-11-02
		//本地的当前时间 包含毫秒
		System.out.println("LocalDateTime:"+datetime.toLocalDateTime()); //LocalDateTime:2017-11-02T08:40:04.529
		//格式化日期
		System.out.println("时间:"+datetime.toString(DateTimeFormat.forPattern("yyyy年M月d日")));//时间:2017年11月2日
		
		//加上100小时之后是星期几(中文)
		System.out.println("dayOfWeek:"+datetime.plusHours(100).dayOfWeek().getAsText(Locale.CHINA));//dayOfWeek:星期一
		//加100天的日期
		System.out.println("toLocalDate():"+datetime.plusDays(100).toLocalDate()); //toLocalDate():2018-02-10
		//十年前的今天是星期几(默认中文)
		System.out.println("minusYears():"+datetime.minusYears(10).dayOfWeek().getAsText()); //minusYears():星期五
		
		//离双11还有多少小时
		System.out.println("离双11的时间:"+Hours.hoursBetween(datetime,new DateTime("2017-11-11")).getHours()); //离双11的时间:207
		
		//伦敦的时间:2017-11-02T01:24:15.139Z
		System.out.println("伦敦的时间:"+datetime.withZone(DateTimeZone.forID("Europe/London")));//伦敦的时间:2017-11-02T01:24:15.139Z
		
		//标准时间
		System.out.println("标准时间:"+datetime.withZone(DateTimeZone.UTC));
		
		//当前可变的时间
		MutableDateTime mdt=new MutableDateTime();
		//10年后的时间
		DateTime dt=datetime.plusYears(10);
		
		System.out.println("十年之后:"+dt); //2027-11-02T09:06:36.883+08:00
		
		while(mdt.isBefore(dt)){
			//循环一次加一天
			mdt.addDays(1);
			//是13号，并且是星期五
			if(mdt.getDayOfMonth()==13&&mdt.getDayOfWeek()==5){
				//打印出十年内所有的黑色星期五
				System.out.println("黑色星期五:"+mdt);
				/*
				 *  星期五:2018-04-13T09:13:40.551+08:00
					星期五:2018-07-13T09:13:40.551+08:00
					星期五:2019-09-13T09:13:40.551+08:00
					星期五:2019-12-13T09:13:40.551+08:00
					星期五:2020-03-13T09:13:40.551+08:00
					星期五:2020-11-13T09:13:40.551+08:00
					星期五:2021-08-13T09:13:40.551+08:00
					星期五:2022-05-13T09:13:40.551+08:00
					星期五:2023-01-13T09:13:40.551+08:00
					星期五:2023-10-13T09:13:40.551+08:00
					星期五:2024-09-13T09:13:40.551+08:00
					星期五:2024-12-13T09:13:40.551+08:00
					星期五:2025-06-13T09:13:40.551+08:00
					星期五:2026-02-13T09:13:40.551+08:00
					星期五:2026-03-13T09:13:40.551+08:00
					星期五:2026-11-13T09:13:40.551+08:00
					星期五:2027-08-13T09:13:40.551+08:00
				 */
			}
		}
		
		//转换成jdk的Date格式
		Date jdkDate=datetime.toDate();
		System.out.println("jdkDate:"+jdkDate);  //jdkDate:Thu Nov 02 09:51:13 CST 2017
		//jdk的Date转换成Joda的Date
		datetime=new DateTime(jdkDate);
		System.out.println("JodaDate:"+datetime);//JodaDate:2017-11-02T09:51:13.691+08:00
	}
	
}

package com.pancm.jdk8;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
* @Title: TimeTest
* @Description: 
* 时间测试
* @Version:1.0.0  
* @author pancm
* @date 2018年6月21日
*/
public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test();
		test2();
		test3();
		test4();
	}
	
	/**
	 * 时间测试
	 */
	private static void test(){
		//获取当前的时间，包括毫秒
		 LocalDateTime ldt = LocalDateTime.now();
//		 String time="2018-06-29 09:19:45.498";
		 String time2="2018-01-04T09:19:29.499";
		 //格式化时间
		 LocalDateTime ldt2=LocalDateTime.parse(time2);
		 System.out.println("当前年:"+ldt.getYear());   //2018
		 System.out.println("当前年份天数:"+ldt.getDayOfYear());//172 
		 System.out.println("当前月:"+ldt.getMonthValue());
		 System.out.println("当前时:"+ldt.getHour());
		 System.out.println("当前分:"+ldt.getMinute());
		 System.out.println("当前时间:"+ldt.toString());
		 System.out.println("格式化时间:"+ ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		 System.out.println("前5天时间:"+ldt.minusDays(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); //2018-06-16
		 System.out.println("后5天时间:"+ldt.plusDays(5));
		 System.out.println("指定2099年的当前时间:"+ldt.withYear(2099)); //2099-06-21T15:07:39.506

		 System.out.println("前一个月的时间:"+ldt2.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM"))); //2018-06-16
		 System.out.println("后一个月的时间:"+ldt2.plusMonths(1)); //2018-06-16
		 System.out.println("得到的时间:"+ldt2.toString());
		 System.out.println("格式化时间:"+ldt2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	}
	
	
	/**
	 * 
	 */
	private static void test2(){
		LocalDate ld=LocalDate.parse("2017-11-17");
		LocalDate ld2=LocalDate.parse("2018-01-05");
		
		LocalDate ld3=LocalDate.of(2017, 11, 01);
		LocalDate ld4=LocalDate.of(2018, 01, 01);
		//jdk1.8的类，用于比较时间
		//可以得到相差年、月、日
		Period p=Period.between(ld, ld2);
		System.out.println("相差年: "+p.getYears()+" 相差月 :"+p.getMonths() +" 相差天:"+p.getDays());
		System.out.println("增加2个月: "+ld.plusMonths(2));
		// 相差年: 0 相差月 :1 相差天:19
		
		Period p2=Period.between(ld3, ld4);
		System.out.println("相差年: "+p2.getYears()+" 相差月 :"+p2.getMonths() +" 相差天:"+p2.getDays()+"--"+p2.toTotalMonths());
		//相差年: 0 相差月 :2 相差天:0
	}
	
	
	/**
	 * 
	 */
	private static void   test3(){
		Instant inst1 = Instant.now();
        System.out.println("Inst1 : " + inst1);
        Instant inst2 = inst1.plus(Duration.ofSeconds(10));
        System.out.println("Inst2 : " + inst2);

        System.out.println("相差毫秒 : " + Duration.between(inst1, inst2).toMillis());

        System.out.println("相毫秒 : " + Duration.between(inst1, inst2).getSeconds());
	}
	
	
	/**
	 * 单个时间单位内测量一段时间
	 */
	private static void test4(){
		  //2017-11-19
		  LocalDate startDate = LocalDate.of(2017, Month.NOVEMBER, 01);
	        System.out.println("开始时间  : " + startDate);
	        
	        //2018-1-16
	        LocalDate endDate = LocalDate.of(2018, Month.JANUARY, 01);
	        System.out.println("结束时间 : " + endDate);
	        
	        System.out.println("相差月份:"+ChronoUnit.MONTHS.between(startDate, endDate));
	        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
	        System.out.println("两天之间的差在天数   : " + daysDiff);

	    }
	
	
}

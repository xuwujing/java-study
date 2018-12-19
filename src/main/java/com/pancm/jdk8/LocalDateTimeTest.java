package com.pancm.jdk8;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
* @Title: timeTest
* @Description: 
* 时间测试
* @Version:1.0.0  
* @author pancm
* @date 2018年6月21日
*/
public class LocalDateTimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		/*
		   
		   新版API中java.time包里的一些关键类：

			Instant：瞬时实例。
			LocalDate：本地日期，不包含具体时间 例如：2014-01-14 可以用来记录生日、纪念日、加盟日等。
			LocalTime：本地时间，不包含日期。
			LocalDateTime：组合了日期和时间，但不包含时差和时区信息。
			ZonedDateTime：最完整的日期时间，包含时区和相对UTC或格林威治的时差。
		   
		   
		   Java 8日期时间API 使用说明:
		   
		    1）提供了javax.time.ZoneId 获取时区。

			2）提供了LocalDate和LocalTime类。
			
			3）Java 8 的所有日期和时间API都是不可变类并且线程安全，而现有的Date和Calendar API中的java.util.Date和SimpleDateFormat是非线程安全的。
			
			4）主包是 java.time,包含了表示日期、时间、时间间隔的一些类。里面有两个子包java.time.format用于格式化， java.time.temporal用于更底层的操作。
			
			5）时区代表了地球上某个区域内普遍使用的标准时间。每个时区都有一个代号，格式通常由区域/城市构成（Asia/Tokyo），在加上与格林威治或 UTC的时差。例如：东京的时差是+09:00。
			
			6）OffsetDateTime类实际上组合了LocalDateTime类和ZoneOffset类。用来表示包含和格林威治或UTC时差的完整日期（年、月、日）和时间（时、分、秒、纳秒）信息。
			
			7）DateTimeFormatter 类用来格式化和解析时间。与SimpleDateFormat不同，这个类不可变并且线程安全，需要时可以给静态常量赋值。 DateTimeFormatter类提供了大量的内置格式化工具，同时也允许你自定义。在转换方面也提供了parse()将字符串解析成日期，如果解析出错会抛出DateTimeParseException。DateTimeFormatter类同时还有format()用来格式化日期，如果出错会抛出DateTimeException异常。
			
			8）再补充一点，日期格式“MMM d yyyy”和“MMM dd yyyy”有一些微妙的不同，第一个格式可以解析“Jan 2 2014”和“Jan 14 2014”，而第二个在解析“Jan 2 2014”就会抛异常，因为第二个格式里要求日必须是两位的。如果想修正，你必须在日期只有个位数时在前面补零，就是说“Jan 2 2014”应该写成 “Jan 02 2014”。
					 
		 */
		
		
		test1();
		test2();
		test3();
		test4();
	}
	
	
	
	
	/**
	 * 
	 */
	private static void test1(){
		
		/*
		 * 获取当前时间
		 */
		//本地日期,不包括时分秒
		LocalDate nowDate = LocalDate.now();
		//本地日期,包括时分秒
		LocalDateTime nowDateTime = LocalDateTime.now();
		System.out.println("当前时间:"+nowDate);
		System.out.println("当前时间:"+nowDateTime);
		//  当前时间:2018-12-19
		//  当前时间:2018-12-19T15:24:35.822
		
		/*
		 * 格式化时间
		 */
		LocalDate ld=LocalDate.parse("2017-11-17");
		LocalDate ld2=LocalDate.parse("2018-01-05");
		
		/**
		 * 创建指定日期
		 */
		LocalDate ld3=LocalDate.of(2017, Month.NOVEMBER, 17);
		LocalDate ld4=LocalDate.of(2018, 02, 11);
		//jdk1.8的类，用于比较时间
		//可以得到相差年、月、日
		Period p=Period.between(ld, ld2);
		System.out.println("相差年: "+p.getYears()+" 相差月 :"+p.getMonths() +" 相差天:"+p.getDays());
		// 相差年: 0 相差月 :1 相差天:19
		
		System.out.println("增加2个月: "+ld.plusMonths(2));
		Period p2=Period.between(ld3, ld4);
		System.out.println("相差年: "+p2.getYears()+" 相差月 :"+p2.getMonths() +" 相差天:"+p2.getDays()+"--"+p2.toTotalMonths());
		//相差年: 0 相差月 :2 相差天:25
	}
	
	/**
	 * 时间测试
	 */
	private static void test2(){
//		 String time="2018-06-29 09:19:45.498";
		 String time2="2018-01-04T09:19:29.499";
		 //格式化时间
		 LocalDateTime ldt2=LocalDateTime.parse(time2);
		 //获取当前的时间，包括毫秒
		 LocalDateTime ldt = LocalDateTime.now();
		 System.out.println("当前年:"+ldt.getYear());   //2018
		 System.out.println("当前年份天数:"+ldt.getDayOfYear());//172 
		 System.out.println("当前月:"+ldt.getMonthValue());
		 System.out.println("当前时:"+ldt.getHour());
		 System.out.println("当前分:"+ldt.getMinute());
		 System.out.println("当前时间:"+ldt.toString());
		//		 当前年:2018
		//		 当前年份天数:353
		//		 当前月:12
		//		 当前时:15
		//		 当前分:24
		//		 当前时间:2018-12-19T15:24:35.833
		 
		 
		 System.out.println("格式化时间: "+ ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		 //格式化时间:2018-12-19 15:37:47.119
		 
		 System.out.println("后5天时间:"+ldt.plusDays(5));
		 System.out.println("前5天时间并格式化:"+ldt.minusDays(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); //2018-06-16
		 System.out.println("前一个月的时间:"+ldt2.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyyMM"))); //2018-06-16
		 System.out.println("后一个月的时间:"+ldt2.plusMonths(1)); //2018-06-16
		 System.out.println("指定2099年的当前时间:"+ldt.withYear(2099)); //2099-06-21T15:07:39.506
		//		后5天时间:2018-12-24T15:50:37.508
		//		前5天时间并格式化:2018-12-14
		//		前一个月的时间:201712
		//		后一个月的时间:2018-02-04T09:19:29.499
		//		指定2099年的当前时间:2099-12-19T15:50:37.508
		 
		 System.out.println("得到的时间:"+ldt2.toString());
		 System.out.println("格式化时间:"+ldt2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		 
		 
		 /*
		  * 
		 通过  Clock时钟类用于获取当时的时间戳，或当前时区下的日期时间信息。
		  */
		 Clock clock = Clock.systemUTC();
		 System.out.println("当前时间戳 : " + clock.millis());
		 Clock clock2 = Clock.system(ZoneId.of("Asia/Shanghai"));
		 System.out.println("亚洲上海此时的时间戳:"+clock2.millis());
		 Clock clock3 = Clock.system(ZoneId.of("America/New_York"));
		 System.out.println("美国纽约此时的时间戳:"+clock3.millis());
		//	当前时间戳 : 1545209277657
		//	 亚洲上海此时的时间戳:1545209277657
		//	 美国纽约此时的时间戳:1545209277658
		 
		 /*
		  * 时区计算
		  */
		 ZoneId zoneId= ZoneId.of("America/New_York");
		 ZonedDateTime dateTime=ZonedDateTime.now(zoneId);
		 System.out.println("美国纽约此时的时间 : " + dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		 System.out.println("美国纽约此时的时间 和时区: " + dateTime);
		 //	 美国纽约此时的时间 : 2018-12-19 03:52:22.494
		 //	美国纽约此时的时间 和时区: 2018-12-19T03:52:22.494-05:00[America/New_York]
				 
		 /**
		  *
		  * 时间比较
		  */
		 LocalDateTime ldt4 = LocalDateTime.now();
		 LocalDateTime ldt5 = ldt4.plusMinutes(10);
		 System.out.println("当前时间是否大于:"+ldt4.isAfter(ldt5));
		 System.out.println("当前时间是否小于"+ldt4.isBefore(ldt5));
		 // false
		 // true
	}
	
	
	/**
	 * 
	 */
	private static void   test3(){
		/*
		 *   Duration 这个类以秒和纳秒为单位建模时间的数量或数量
		 */
		Instant inst1 = Instant.now();
        System.out.println("当前时间戳: " + inst1);
        Instant inst2 = inst1.plus(Duration.ofSeconds(10));
        System.out.println("增加之后的时间 : " + inst2);
        System.out.println("相差毫秒 : " + Duration.between(inst1, inst2).toMillis());
        System.out.println("相毫秒 : " + Duration.between(inst1, inst2).getSeconds());
		//	 当前时间戳 : 2018-12-19T08:14:21.675Z
		//	增加之后的时间 : 2018-12-19T08:14:31.675Z
		//	相差毫秒 : 10000
		//	 相毫秒 : 10
        
	}
	
	
	/**
	 * 单个时间单位内测量一段时间
	 */
	private static void test4(){
			/*
			 *  ChronoUnit 日期周期单位的标准集合。
			 */
		  	LocalDate startDate = LocalDate.of(2017, 11, 17);
	        LocalDate endDate = LocalDate.of(2018, 01, 05);
	        System.out.println("相差月份:"+ChronoUnit.MONTHS.between(startDate, endDate));
	        System.out.println("两月之间的相差的天数   : " + ChronoUnit.DAYS.between(startDate, endDate));
			//	       相差月份:1
			//	       两天之间的差在天数   : 49
	    }
	
	
}

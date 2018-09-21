package com.pancm.others;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
* @Title: logbackTest
* @Description:
* logback日志测试 
* @Version:1.0.0  
* @author pancm
* @date 2018年1月24日
 */
public class LogbackTest {
	//主程序的日志打印
	private static Logger LOG = LoggerFactory.getLogger(LogbackTest.class);
	//自定义的日志打印
	private static Logger LOG2 = LoggerFactory.getLogger("oneInfo");
	//自定义的日志打印
	private static Logger LOG3 = LoggerFactory.getLogger("twoInfo");
	
	public static void main(String[] args) {
		test();
	}
	
	
	private static void test(){
		/*  
		 * 因为设置打印的级别是info，所以debug级别的不会打印 
		 * */
		LOG.debug("主程序的debug");
		LOG.info("主程序的info");
		LOG.warn("主程序的warn");
		LOG.error("主程序的error");
		
		/* 
		 * 因为自定义配置设定的是  additivity="false"  不在控制台打印
		 * 所以一条都不会打印，但是debug级别以上的日志可以在logs/pcm/oneInfo中查看
		 */
		LOG2.debug("oneInfo的debug");
		LOG2.info("oneInfo的info");
		LOG2.warn("oneInfo的warn");
		LOG2.error("oneInfo的error");
		
		/* 
		 * 因为自定义配置设定的是 additivity="true"  可以在控制台打印
		 * 所以回打印两条warn级别的日志，日志也可以在logs/pcm/oneInfo中查看
		 */
		LOG3.debug("twoInfo的debug");
		LOG3.info("twoInfo的info");
		LOG3.warn("twoInfo的warn");
		LOG3.error("twoInfo的error");
	}
}

package com.pancm.bigdata.hbase;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
/**
 * 
* Title: hbaseTest
* Description: HBase 相关测试
* Version:1.0.0  
* @author pancm
* @date 2017年11月23日
 */
public class HbaseTest {
	
	public static void main(String[] args) {
		test();
	}

	/**
	 * 一些测试
	 */
	private static void test() {
		String tableName1="t_student",tableName2="t_student_info";
		String []columnFamily1={"st1","st2"};
		String []columnFamily2={"stf1","stf2"};
		HBaseUtil.creatTable(tableName1, columnFamily1);
		HBaseUtil.creatTable(tableName2, columnFamily2);
		
		HBaseUtil.insert(tableName1, "1001", columnFamily1[0], "name", "zhangsan");
		HBaseUtil.insert(tableName1, "1002", columnFamily1[0], "name", "lisi");
		HBaseUtil.insert(tableName1, "1001", columnFamily1[1], "age", "18");
		HBaseUtil.insert(tableName1, "1002", columnFamily1[1], "age", "20");
		
		HBaseUtil.insert(tableName2, "1001", columnFamily2[0], "phone", "123456");
		HBaseUtil.insert(tableName2, "1002", columnFamily2[0], "phone", "234567");
		HBaseUtil.insert(tableName2, "1001", columnFamily2[1], "mail", "123@163.com");
		HBaseUtil.insert(tableName2, "1002", columnFamily2[1], "mail", "234@163.com");
		
		HBaseUtil.select(tableName1); //查询该表所有数据
		HBaseUtil.select(tableName1, "1001"); //根据表名和行健查询
		HBaseUtil.select(tableName2, "1002",columnFamily2[0]); //根据表名、行健和列族查询
		HBaseUtil.select(tableName2, "1002",columnFamily2[1],"mail"); //根据表名、行健、列族、和列查询
		
		HBaseUtil.select(tableName1, "1002"); //根据表名和行健查询
		HBaseUtil.delete(tableName1, "1002", columnFamily1[0]);//删除数据
		HBaseUtil.select(tableName1, "1002"); //根据表名和行健查询
		
	}
	

  
	
	
	
	/**
    * 批量测试方法
    * @param tableName  表名
    * @param family  列族
    * @param qualifier  列
    * @param value  值
    * @param k		           次数
    */
   public static void insterTest(String tableName,String family,String qualifier,String value, int k){
	   List<JSONObject> list =new ArrayList<>();
		for(int i=1;i<=k;i++){
			JSONObject json =new JSONObject();
			json.put("rowKey", i);              //行健
			json.put("family", family);	 		 //列族
			json.put("qualifier", qualifier);		 //列
			if("t_student".equals(tableName)){ //如果是t_student 姓名则加上编号
				json.put("value", value+i);	//值
			}else if("".equals(value)){		 //如果为空，则是年龄	
				json.put("value", i);	//值
			}else{							  //否则就是性别
				json.put("value", value);	//值
			}
		
			list.add(json);
		}
		HBaseUtil.insertBatch(tableName,list);
   }
	
	
}

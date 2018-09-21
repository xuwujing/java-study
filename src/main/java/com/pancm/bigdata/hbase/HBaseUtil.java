package com.pancm.bigdata.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * Title: HBaseUtil
 * Description: HBase工具类 
 * Version:1.0.0
 * @author pancm
 * @date 2017年12月6日
 */
public class HBaseUtil {
	/** hadoop 连接 */
	private static Configuration conf = null;
	/** hbase 连接 */
	private static Connection con = null;
	/** 会话 */
	private static Admin admin = null;

	private static String ip ="master";
    private static String port ="2181";
    private static String port1 ="9001";
	   
   // 初始化连接
   static {
	   // 获得配制文件对象
       conf = HBaseConfiguration.create(); 
       // 设置配置参数
		conf.set("hbase.zookeeper.quorum", ip);
		conf.set("hbase.zookeeper.property.clientPort", port);  
		//如果hbase是集群，这个必须加上 
		//这个ip和端口是在hadoop/mapred-site.xml配置文件配置的
		conf.set("hbase.master", ip+":"+port1); 
   }
		

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public synchronized static Connection getConnection() {
		try {
			if (null == con || con.isClosed()) {
				// 获得连接对象
				con = ConnectionFactory.createConnection(conf);
			}
		} catch (IOException e) {
			System.out.println("获取连接失败!");
			e.printStackTrace();
		}

		return con;
	}

	/**
	 * 连接关闭
	 */
	public static void close() {
		try {
			if (admin != null) {
				admin.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (IOException e) {
			System.out.println("连接关闭失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 创建表
	 * 
	 * @param tableName
	 *            表名
	 * @param columnFamily
	 *            列族
	 */
	public static void creatTable(String tableName, String[] columnFamily) {
		if(null==tableName||tableName.length()==0){
			return;
		}
		if(null==columnFamily||columnFamily.length==0){
			return;
		}
		// 创建表名对象
		TableName tn = TableName.valueOf(tableName);
		// a.判断数据库是否存在
		try {
			// 获取会话
			admin = getConnection().getAdmin();
			if (admin.tableExists(tn)) {
				System.out.println(tableName + " 表存在，删除表....");
				// 先使表设置为不可编辑
				admin.disableTable(tn);
				// 删除表
				admin.deleteTable(tn);
				System.out.println("表删除成功.....");
			}
			// 创建表结构对象
			HTableDescriptor htd = new HTableDescriptor(tn);
			for (String str : columnFamily) {
				// 创建列族结构对象
				HColumnDescriptor hcd = new HColumnDescriptor(str);
				htd.addFamily(hcd);
			}
			// 创建表
			admin.createTable(htd);
			System.out.println(tableName + " 表创建成功！");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * 数据单条插入或更新
	 * 
	 * @param tableName
	 *            表名
	 * @param rowKey
	 *            行健 (主键)
	 * @param family
	 *            列族
	 * @param qualifier
	 *            列
	 * @param value
	 *            存入的值
	 * @return
	 */
	public static void insert(String tableName, String rowKey, String family,
			String qualifier, String value) {
		Table t = null;
		try {
			t = getConnection().getTable(TableName.valueOf(tableName));
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier),
					Bytes.toBytes(value));
			t.put(put);
			System.out.println(tableName + " 更新成功!");
		} catch (IOException e) {
			System.out.println(tableName + " 更新失败!");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * 数据批量插入或更新
	 * 
	 * @param tableName
	 *            表名
	 * @param list
	 *            hbase的数据 
	 * @return
	 */
	public static void insertBatch(String tableName, List<?> list) {
		if (null == tableName ||tableName.length()==0) {
			return;
		}
		if( null == list || list.size() == 0){
			return;
		}
		Table t = null;
		Put put = null;
		JSONObject json = null;
		List<Put> puts = new ArrayList<Put>();
		try {
			t = getConnection().getTable(TableName.valueOf(tableName));
			for (int i = 0, j = list.size(); i < j; i++) {
				json = (JSONObject) list.get(i);
				put = new Put(Bytes.toBytes(json.getString("rowKey")));
				put.addColumn(Bytes.toBytes(json.getString("family")),
						Bytes.toBytes(json.getString("qualifier")),
						Bytes.toBytes(json.getString("value")));
				puts.add(put);
			}
			t.put(puts);
			System.out.println(tableName + " 更新成功!");
		} catch (IOException e) {
			System.out.println(tableName + " 更新失败!");
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	/**
	 * 数据删除 
	 * @param tableName 表名
	 * @param rowKey	行健
	 * @return
	 */
    public static void delete(String tableName, String rowKey) {
    	delete(tableName,rowKey,"","");
    }
	
	/**
	 * 数据删除 
	 * @param tableName 表名
	 * @param rowKey	行健
	 * @param family	列族
	 * @return
	 */
    public static void delete(String tableName, String rowKey, String family) {
    	delete(tableName,rowKey,family,"");
    }
	
	/**
	 * 数据删除 
	 * @param tableName 表名
	 * @param rowKey	行健
	 * @param family	列族
	 * @param qualifier 列
	 * @return
	 */
    public static void delete(String tableName, String rowKey, String family,
            String qualifier) {
    	if (null == tableName ||tableName.length()==0) {
			return;
		}
		if( null == rowKey || rowKey.length() == 0){
			return;
		}
    	Table t = null;
        try {
            t = getConnection().getTable(TableName.valueOf(tableName));
            Delete del = new Delete(Bytes.toBytes(rowKey));
            // 如果列族不为空
 			if (null != family && family.length() > 0) {
 				// 如果列不为空
 				if (null != qualifier && qualifier.length() > 0) {
 					del.addColumn(Bytes.toBytes(family),
 							Bytes.toBytes(qualifier));
 				} else {
 					del.addFamily(Bytes.toBytes(family));
 				}
 			}      
            t.delete(del);    
        } catch (IOException e) {
        	System.out.println("删除失败!");
            e.printStackTrace();
        } finally {
          close();
        }
    }
	
	/**
	 * 查询该表中的所有数据
	 * 
	 * @param tableName
	 *            表名
	 */
	public static void select(String tableName) {
		if(null==tableName||tableName.length()==0){
			return;
		}
		Table t = null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			t = getConnection().getTable(TableName.valueOf(tableName));
			// 读取操作
			Scan scan = new Scan();
			// 得到扫描的结果集
			ResultScanner rs = t.getScanner(scan);
			if (null == rs ) {
				return;
			}
			for (Result result : rs) {
				// 得到单元格集合
				List<Cell> cs = result.listCells();
				if (null == cs || cs.size() == 0) {
					continue;
				}
				for (Cell cell : cs) {
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("rowKey", Bytes.toString(CellUtil.cloneRow(cell)));// 取行健
					map.put("timestamp", cell.getTimestamp());// 取到时间戳
					map.put("family", Bytes.toString(CellUtil.cloneFamily(cell)));// 取到列族
					map.put("qualifier", Bytes.toString(CellUtil.cloneQualifier(cell)));// 取到列
					map.put("value", Bytes.toString(CellUtil.cloneValue(cell)));// 取到值
					list.add(map);
				}
			}
			System.out.println("查询的数据:"+list);
		} catch (IOException e) {
			System.out.println("查询失败!");
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * 根据表名和行健查询
	 * @param tableName
	 * @param rowKey
	 */
	public static void select(String tableName, String rowKey) {
		select(tableName,rowKey,"","");
	}
	
	/**
	 * 根据表名、行健和列族查询
	 * @param tableName
	 * @param rowKey
	 * @param family
	 */
	public static void select(String tableName, String rowKey, String family) {
		select(tableName,rowKey,family,"");
	}
	
	/**
	 * 根据条件明细查询
	 * 
	 * @param tableName
	 *            表名
	 * @param rowKey
	 *            行健 (主键)
	 * @param family
	 *            列族
	 * @param qualifier
	 *            列
	 */
	public static void select(String tableName, String rowKey, String family,
			String qualifier) {
		Table t = null;
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try {
			t = getConnection().getTable(TableName.valueOf(tableName));
			// 通过HBase中的 get来进行查询
			Get get = new Get(Bytes.toBytes(rowKey));
			// 如果列族不为空
			if (null != family && family.length() > 0) {
				// 如果列不为空
				if (null != qualifier && qualifier.length() > 0) {
					get.addColumn(Bytes.toBytes(family),
							Bytes.toBytes(qualifier));
				} else {
					get.addFamily(Bytes.toBytes(family));
				}
			}
			Result r = t.get(get);
			List<Cell> cs = r.listCells();
			if (null == cs || cs.size() == 0) {
				return;
			}
			for (Cell cell : cs) {
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("rowKey", Bytes.toString(CellUtil.cloneRow(cell)));// 取行健
				map.put("timestamp", cell.getTimestamp());// 取到时间戳
				map.put("family", Bytes.toString(CellUtil.cloneFamily(cell)));// 取到列族
				map.put("qualifier", Bytes.toString(CellUtil.cloneQualifier(cell)));// 取到列
				map.put("value", Bytes.toString(CellUtil.cloneValue(cell)));// 取到值
				list.add(map);
			}
			System.out.println("查询的数据:"+list);
		} catch (IOException e) {
			System.out.println("查询失败!");
			e.printStackTrace();
		} finally {
			close();
		}
	}

}

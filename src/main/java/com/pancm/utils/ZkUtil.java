package com.pancm.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher.Event;

/**
 * 
* @Title: ZookeeperUtils
* @Description: 
* zookeeper的工具类
* @Version:1.0.0  
* @author pancm
* @date 2018年5月2日
 */
public class ZkUtil {
	
	/**
	 * 斜杠
	 */
	private static final String SPRIT ="/";
	/**
	 * 创建目录并添加参数
	 * @param zk
	 * @param path
	 * @param data
	 * @return
	 */
	public static void create(ZkClient zk,String path,String data) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException{
		if(!zk.exists(path)){
			zk.create(path,data,CreateMode.PERSISTENT);
		}
	}
	
	/**
	 * 创建目录不添加参数
	 * 也可以创建层级目录，格式: /test/test1/test1-1
	 * @param zk
	 * @param path
	 * @return
	 */
	public static void create(ZkClient zk,String path) throws ZkInterruptedException, IllegalArgumentException, ZkException, RuntimeException{
		if(!zk.exists(path)){
			zk.createPersistent(path,true);
		}
	}
	
	/**
	 * 更新参数，没有就新增
	 * @param zk
	 * @param path
	 * @param data
	 * @return
	 */
	public static boolean setData(ZkClient zk,String path,String data){
		boolean falg=false;
		if(zk.exists(path)){
		   zk.writeData(path, data);
		   falg=true;
		}
		return falg;
	}
	
	/**
	 * 获取数据
	 * @param zk
	 * @param path
	 * @return
	 */
	public static <T extends Object> T getData(ZkClient zk,String path){
		T t=null;
		if(zk.exists(path)){
		   t=zk.readData(path);
		}
		return t;
	}
	
	
	/**
	 * 获取子节点
	 * @param zk
	 * @param path
	 * @return
	 */
	public static List<String> getChildNode(ZkClient zk,String path){
		List<String> list=null;
		if(zk.exists(path)){
			list=zk.getChildren(path);
		}
		return list;
	}
	
	
	/**
	 * 获取父级目录下的所有数据
	 * 注:仅限一级
	 * @param zk
	 * @param path
	 * @return
	 */
	public static Map<String, String> getAll(ZkClient zk,String path){
		List<String> yardList =getChildNode(zk,path);
		Map<String, String> map = new HashMap<String, String>();
		for(String key : yardList){
			String value = getData(zk,path.concat(SPRIT).concat(key));
			map.put(key, value);
		}
		return map;
	}
	
	/**
	 * 删除节点
	 * 注:如果是该节点包含子节点，也会一并删除
	 * @param zk
	 * @param path
	 * @return
	 */
	public static boolean delete(ZkClient zk,String path){
		return zk.deleteRecursive(path);
	}
	
	public static void close(ZkClient zk){
		if(zk!=null){
			zk.close();
		}
	}
	
	
	public void process(WatchedEvent watchedEvent) {
	    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) { //与zk服务器处于连接状态
	    	//如果没有就创建
	        if(watchedEvent.getType() == Event.EventType.None && null == watchedEvent.getPath()) {
	        	
	        }else if(watchedEvent.getType() == Event.EventType.NodeCreated) { 
	            System.out.println("监控到了该节点被创建");
	        } else if(watchedEvent.getType() == Event.EventType.NodeDataChanged) {
	            // 节点的子节点列表发生变化
	        	System.out.println("监控到了该节点更新");
	        } else if(watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
	            // 节点的数据内容发生变化
	        	System.out.println("监控到了该节点的子节点更新");
	        }else if(watchedEvent.getType() == Event.EventType.NodeDeleted) {
	            System.out.println("监控到了该节点删除");
	        }
	    }
	}
	
}

package com.pancm.bigdata.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
* @Title: ZookeeperTest
* @Description: 
* zookeeper测试
* @Version:1.0.0  
* @author pancm
* @date 2018年4月28日
 */
public class ZookeeperTest {
	private static String url="master:2181";
	private static  ZooKeeper zk;
	private static  int  CONNECTION_TIMEOUT=30000;
	
	public static void main(String[] args) throws Exception {
		// 创建一个与服务器的连接
		 zk = new ZooKeeper(url , 
				 CONNECTION_TIMEOUT, new Watcher() { 
		            // 监控所有被触发的事件
		            public void process(WatchedEvent event) {
		                System.out.println(event.getPath()+"已经触发了" + event.getType() + "事件！"); 
		            } 
		        }); 
		 
		 /*
		  * 创建一个给定的目录节点 path, 并给它设置数据，
		  * CreateMode 标识有四种形式的目录节点，分别是
		  *  PERSISTENT：持久化目录节点，这个目录节点存储的数据不会丢失；
		  *  PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点，这种目录节点会根据当前已近存在的节点数自动加 1，
		  *  然后返回给客户端已经成功创建的目录节点名；
		  *  EPHEMERAL：临时目录节点，一旦创建这个节点的客户端与服务器端口也就是 session 超时，这种节点会被自动删除；
		  *  EPHEMERAL_SEQUENTIAL：临时自动编号节点
		  */
		 
		 // 创建一个父级目录节点
		 if(zk.exists("/test", true)==null){
			 //参数说明:目录，参数，参数权限，节点类型
			 zk.create("/test", "data1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT); 
		 }
		 if(zk.exists("/test/test1", true)==null){
			 // 创建一个子目录节点
			 zk.create("/test/test1", "data2".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		 }
		 
		 System.out.println("="+new String(zk.getData("/test",false,null))); 
		 // 取出子目录节点列表
		 System.out.println("=="+zk.getChildren("/test",true)); 
		
		 if(zk.exists("/test/test1", true)!=null){
			 // 修改子目录节点数据
			 zk.setData("/test/test1","testOne".getBytes(),-1); 
		 }
		 System.out.println("目录节点状态：["+zk.exists("/test",true)+"]"); 
		 if(zk.exists("/test/test1", true)!=null){
		  // 创建另外一个子目录节点
		  zk.create("/test/test2", "test2".getBytes(), Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
		 }
		 System.out.println("==="+new String(zk.getData("/test/test2",true,null))); 

		 /*
		  * 删除 path 对应的目录节点，version 为 -1 可以匹配任何版本，也就删除了这个目录节点所有数据
		  */
		 // 删除子目录节点
		 zk.delete("/test/test2",-1); 
		 zk.delete("/test/test1",-1); 
		 
		 // 删除父目录节点
		 zk.delete("/test",-1); 
		 // 关闭连接
		 zk.close();
	}
	

	
	
}

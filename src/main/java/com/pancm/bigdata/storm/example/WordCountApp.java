package com.pancm.bigdata.storm.example;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

 

/**
 * 
* Title: WordCountApp
* Description: 测试storm本地模式 统计words单次个数
* 源代码地址:http://www.tianshouzhi.com/api/tutorials/storm/54
* Version:1.0.0  
* @author pancm
* @date 2017年12月28日
 */
public class WordCountApp {
    public static void main(String[] args) throws InterruptedException, AlreadyAliveException, InvalidTopologyException {
    	//定义拓扑
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("word-reader" , new WordReader());
        builder.setBolt("word-normalizer" , new WordNormalizer()).shuffleGrouping("word-reader" );
        builder.setBolt("word-counter" , new WordCounter()).fieldsGrouping("word-normalizer" , new Fields("word"));
        StormTopology topology = builder.createTopology();
        //配置
        
        Config conf = new Config();
        String fileName ="words.txt" ;
        conf.put("fileName" , fileName );
        conf.setDebug(false);
 
         //运行拓扑
         System.out.println("开始...");
         if(args !=null&&args.length>0){ //有参数时，表示向集群提交作业，并把第一个参数当做topology名称
        	 System.out.println("远程模式");
             try {
				StormSubmitter.submitTopology(args[0], conf, topology);
			} catch (AuthorizationException e) {
				e.printStackTrace();
			}
       } else{//没有参数时，本地提交
         //启动本地模式
    	 System.out.println("本地模式");
         LocalCluster cluster = new LocalCluster();
         cluster.submitTopology("Getting-Started-Topologie" , conf , topology );
         Thread.sleep(5000);
         //关闭本地集群
         cluster.shutdown();
       }
         System.out.println("结束");
       
    }
}

package com.pancm.bigdata.storm.example;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

/**
 * 
* Title: WordCounter
* Description: 该类主要用于统计
* Version:1.0.0  
* @author pancm
* @date 2017年12月28日
 */
public class WordCounter implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer id;
	String name;
	Map<String, Integer> counters;
	private OutputCollector collector;

	/**
	 * 当Bolt销毁时，我们会显示单词数量
	 */
	@Override
	public void cleanup() {
		 System.out.println("开始显示单词数量...");
		for (Map.Entry<String, Integer> entry : counters.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("WordCounter.cleanup()");
	}

	/**
	 * 为每个单词计数
	 */
	@Override
	public void execute(Tuple input) {
		System.out.println("WordCounter.execute()");
		String str = input.getString(0);
		/**
		 * 如果单词尚不存在于map，我们就创建一个，如果已在，我们就为它加1
		 */
		if (!counters.containsKey(str)) {
			counters.put(str, 1);
		} else {
			Integer c = counters.get(str) + 1;
			counters.put(str, c);
		}
		// 对元组作为应答
		collector.ack(input);
	}

	/**
	 * 初始化
	 */
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.counters = new HashMap<String, Integer>();
		this.collector = collector;
		this.name = context.getThisComponentId();
		this.id = context.getThisTaskId();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println("WordCounter.declareOutputFields()");
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		System.out.println("WordCounter.getComponentConfiguration()");
		return null;
	}
}

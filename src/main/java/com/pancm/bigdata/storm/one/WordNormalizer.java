package com.pancm.bigdata.storm.one;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;


/**
 * 
* Title: WordNormalizer
* Description:该类主要用于格式化数据 
* Version:1.0.0  
* @author pancm
* @date 2017年12月28日
 */
public class WordNormalizer implements IRichBolt {
	/**
       *
       */
	private static final long serialVersionUID = 3644849073824009317L;
	private OutputCollector collector;

	/**
	 * *bolt*从单词文件接收到文本行，并标准化它。 文本行会全部转化成小写，并切分它，从中得到所有单词。
	 */
	public void execute(Tuple input) {
		System.out.println("WordNormalizer.execute()");
		String sentence = input.getString(0);
		String[] words = sentence.split(" ");
		for (String word : words) {
			word = word.trim();
			if (!word.isEmpty()) {
				word = word.toLowerCase();
				/* //发布这个单词 */
				collector.emit(input, new Values(word));
			}
		}
		// 对元组做出应答
		collector.ack(input);
	}

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		System.out.println("WordNormalizer.prepare()");
		this.collector = collector;
	}

	/**
	 * 这个*bolt*只会发布“word”域
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out.println("WordNormalizer.declareOutputFields()");
		declarer.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		System.out.println("WordNormalizer.getComponentConfiguration()");
		return null;
	}

	public void cleanup() {
		System.out.println("WordNormalizer.cleanup()");
	}
}

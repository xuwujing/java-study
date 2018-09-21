package com.pancm.bigdata.storm.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;


/**
 * 
* Title: WordReader
* Description: 该类主要用于从外部数据源words.txt中获取数据
* Version:1.0.0  
* @author pancm
* @date 2017年12月28日
 */
public class WordReader implements IRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6146631397258548505L;
	private SpoutOutputCollector collector;
	private FileReader fileReader;
	BufferedReader reader;
	private boolean completed = false;

	/**
	 * 这个方法做的惟一一件事情就是分发文件中的文本行
	 */
	public void nextTuple() {
		/**
		 * 这个方法会不断的被调用，直到整个文件都读完了，我们将等待并返回。
		 */
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// 什么也不做
			}
			return;
		}
		String str;

		try {
			int i = 0;
			// 读所有文本行
			while ((str = reader.readLine()) != null) {
				System.out.println("WordReader.nextTuple(),emits time:" + i++);
				/**
				 * 按行发布一个新值
				 */
				this.collector.emit(new Values(str), str);
			}
		} catch (Exception e) {
			throw new RuntimeException("Error reading tuple", e);
		} finally {
			completed = true;
		}
	}

	/**
	 * 
	 * 当Spout被创建之后，这个方法会被掉用
	 */
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {

		System.out.println("WordReader.open(Map conf, TopologyContext context, SpoutOutputCollector collector)");
		String fileName = conf.get("fileName").toString();
		InputStream inputStream = WordReader.class.getClassLoader()
				.getResourceAsStream(fileName);
		reader = new BufferedReader(new InputStreamReader(inputStream));
		this.collector = collector;
	}

	/**
	 * 声明数据格式，即输出的一个Tuple中，包含几个字段
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		System.out
				.println("WordReader.declareOutputFields(OutputFieldsDeclarer declarer)");
		declarer.declare(new Fields("line"));
	}

	@Override
	public void activate() {
		System.out.println("WordReader.activate()");
	}

	@Override
	public void deactivate() {
		System.out.println("WordReader.deactivate()");
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		System.out.println("WordReader.getComponentConfiguration()");
		return null;
	}

	/**
	 * 当一个Tuple处理成功时，会调用这个方法
	 */
	public void ack(Object msgId) {
		System.out.println("WordReader.ack(Object msgId):" + msgId);
	}

	/**
	 * 当Topology停止时，会调用这个方法
	 */
	public void close() {
		System.out.println("WordReader.close()");
	}

	/**
	 * 当一个Tuple处理失败时，会调用这个方法
	 */
	public void fail(Object msgId) {
		System.out.println("WordReader.fail(Object msgId):" + msgId);
	}
}

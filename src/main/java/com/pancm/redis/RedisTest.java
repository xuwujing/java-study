package com.pancm.redis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * 
 * @Title: RedisTest 
 * @Description: redis测试代码
 * @Version:1.0.0
 * @author pancm
 * @date 2017-8-19
 */
public class RedisTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 连接到本地的 redis服务
		Jedis jedis = new Jedis("127.0.0.1");
		System.out.println("连接成功");
		// 查看服务是否运行
		System.out.println("服务正在运行: " + jedis.ping());

		// 存储到列表中
		jedis.lpush("list", "redis");
		jedis.lpush("list", "java");
		jedis.lpush("list", "mysql");
		// 获取存储的数据并输出
		List<String> list = jedis.lrange("list", 0, 2);
		for (int i = 0, j = list.size(); i < j; i++) {
			System.out.println("list的输出结果:" + list.get(i));
		}

		// 设置 redis 字符串数据
		jedis.set("rst", "redisStringTest");
		// 获取存储的数据并输出
		System.out.println("redis 存储的字符串为: " + jedis.get("rst"));

		// 存储数据
		jedis.sadd("setTest1", "abc");
		jedis.sadd("setTest1", "abcd");
		jedis.sadd("setTest1", "abcde");
		// 获取数据并输出
		Set<String> keys = jedis.keys("*");
		// Set<String> keys=jedis.smembers("setTest1");
		// 定义迭代器输出
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
		}

	}

}

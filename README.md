<div align="center">

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://github.com/hhyo/archery/blob/master/LICENSE)
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

</div>

## java-study

**介绍**

[java-study](https://github.com/xuwujing/java-study) 是本人学习Java过程中记录的一些代码！从Java基础的数据类型、jdk1.8的使用、IO、集合、线程等等技术以及一些常用框架，netty、mina、springboot、kafka、storm、zookeeper、es、redis、hbase、hive等等。

**使用**

下载：

    git clone https://github.com/xuwujing/java-study

然后使用maven方式导入IDE中运行main方法即可。

## 项目结构

    com.pancm.arithmetic - 一些算法相关类 
    com.pancm.basics - 一些Java基础相关类 主要是三大特性、修饰符、io、集合、反射、克隆等等相关代码
    com.pancm.bigdata - 大数据相关的类 主要是hbase、storm、zookeeper等等相关的代码
    com.pancm.commons - 一些第三方工具类的测试用例 主要是apache commons、apache lang、google common、google guava、joda等等一些工具包测试使用代码
    com.pancm.design -  设计模式相关的示例类 包含常用的23种设计模式
    com.pancm.elasticsearch -  elasticsearch相关使用的测试用例，包括索引mapping的创建、全文检索、聚合查询等等
    com.pancm.jdk8 -    jdk1.8相关的类 主要是lambda、stream以及LocalDateTime等等测试代码
    com.pancm.mq - 一些消息中间件的类，主要包含kafka、rabbitmq相关的测试代码
    com.pancm.nio - 一些nio框架，主要是netty和mina
    com.pancm.others - 一些不知道怎么定义的测试类，Jsoup(爬虫)、logback、lombok等等测试代码
    com.pancm.pojo -  实体相关类
    com.pancm.question - 一些面试可能会问的问题的类
    com.pancm.redis - redis相关使用的类
    com.pancm.sql -   一些数据库相关的类
    com.pancm.thread - 一些线程相关的类 从基本的使用到各种并发的测试类
    com.pancm.utils - 一些常用的工具类 主要是Json数据转换，日期转换，二维码图片生成工具类，常用的AES、MD5、BASE64等等编码解码工具类，redis、kafka、zookeeper等等工具类

## 相关文章

这里介绍的文章主要是本人写的一些博客。博客主要发布在[个人博客](http://www.panchengming.com)、[CSDN](https://blog.csdn.net/qazwsxpcm)、[博客园](https://www.cnblogs.com/xuwujing/)等，但是由于个人博客在github上，访问可能较慢，CSDN目前观感体验不好，所以以下链接主要就在博客园中了。

**Java基础相关:**

- [基本数据类型](https://www.cnblogs.com/xuwujing/p/8597557.html)
- [修饰符和String](https://www.cnblogs.com/xuwujing/p/8638329.html)
- [封装、继承和多态](https://www.cnblogs.com/xuwujing/p/8681123.html)
- [集合List、Map和Set](https://www.cnblogs.com/xuwujing/p/8886821.html)
- [多线程](https://www.cnblogs.com/xuwujing/p/9102870.html)
- [IO流](https://www.cnblogs.com/xuwujing/p/9191546.html)
- [总结篇](https://www.cnblogs.com/xuwujing/p/9236376.html)

**设计模式:**

- [单例模式](https://www.cnblogs.com/xuwujing/p/9277266.html)
- [工厂方法和抽象工厂模式](https://www.cnblogs.com/xuwujing/p/9363142.html)
- [建造者模式和原型模式](https://www.cnblogs.com/xuwujing/p/9496346.html)
- [适配器模式和桥接模式](https://www.cnblogs.com/xuwujing/p/9520851.html)
- [外观模式和装饰器模式](https://www.cnblogs.com/xuwujing/p/9545272.html)
- [组合模式和过滤器模式](https://www.cnblogs.com/xuwujing/p/9630850.html)
- [享元模式和代理模式](https://www.cnblogs.com/xuwujing/p/9704228.html)
- [责任链模式和命令模式](https://www.cnblogs.com/xuwujing/p/9794886.html)
- [解释器模式和迭代器模式](https://www.cnblogs.com/xuwujing/p/9873514.html)
- [访问者模式和中介者模式](https://www.cnblogs.com/xuwujing/p/9911997.html)
- [策略模式和模板方法模式](https://www.cnblogs.com/xuwujing/p/9954263.html)
- [观察者模式和空对象模式](https://www.cnblogs.com/xuwujing/p/10036204.html)
- [总结篇](https://www.cnblogs.com/xuwujing/p/10134494.html)

**JAVA进阶相关:**

- [JDK1.8的Lambda、Stream和日期的使用详解](https://www.cnblogs.com/xuwujing/p/10145691.html)


**大数据相关:**
- [大数据学习系列之三 ----- HBase Java Api 图文详解](https://www.cnblogs.com/xuwujing/p/8039175.html)
- [Kafka 使用Java实现数据的生产和消费demo](https://www.cnblogs.com/xuwujing/p/8371127.html)
- [关于Kafka 的 consumer 消费者手动提交详解](https://www.cnblogs.com/xuwujing/p/8432984.html)
- [Storm 入门的Demo教程](https://www.cnblogs.com/xuwujing/p/8584684.html)


**ElasticSearch相关:**
- [ElasticSearch实战系列一: ElasticSearch集群+Kinaba安装教程](https://www.cnblogs.com/xuwujing/p/11385255.html)
- [ElasticSearch实战系列二: ElasticSearch的DSL语句使用教程---图文详解](https://www.cnblogs.com/xuwujing/p/11567053.html)
- [ElasticSearch实战系列三: ElasticSearch的JAVA API使用教程](https://www.cnblogs.com/xuwujing/p/11645630.html)



**其他博客:**

- [两年JAVA程序员的面试总结](https://www.cnblogs.com/xuwujing/p/7613084.html)
- [一个两年java程序猿的2017个人总结](https://www.cnblogs.com/xuwujing/p/8158716.html)
- [写了一年的博客，我收获了什么](https://www.cnblogs.com/xuwujing/p/8747769.html)
- [给刚工作不久的程序猿同学的一封信](https://www.cnblogs.com/xuwujing/p/9665966.html)
- [一个平凡但不平庸的程序猿2018个人总结](https://www.cnblogs.com/xuwujing/p/9665966.html)
- [个人收集的资源分享](https://www.cnblogs.com/xuwujing/p/10393111.html)
- [一个毕业三年的程序猿对于提升自我的一些建议](https://www.cnblogs.com/xuwujing/p/11735726.html)

## 其他

在这些代码中，虽然大部分都是自己写的，但是也有不少是在学习过程中从网上或书上直接摘抄的，当时有些并未标明出处，现在由于忘了出处，有些代码并未标明，若有冒犯，请见谅！

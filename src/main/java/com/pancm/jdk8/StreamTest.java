package com.pancm.jdk8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;



/**
 * @Title: StreamTest
 * @Description: Stream测试用例 流的操作类型分为两种：
 * 
 *               Intermediate：一个流可以后面跟随零个或多个 intermediate
 *               操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。
 *               这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。 Terminal：一个流只能有一个
 *               terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。 所以这必定是流的最后一个操作。 Terminal
 *               操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
 * @Version:1.0.0
 * @author pancm
 * @date 2018年9月3日
 */
public class StreamTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
		
		/*
		 * Stream 的特性可以归纳为：
			不是数据结构
			它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
			它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
			所有 Stream 的操作必须以 lambda 表达式为参数
			不支持索引访问
			你可以请求第一个元素，但无法请求第二个，第三个，或最后一个。不过请参阅下一项。
			很容易生成数组或者 List
			惰性化
			很多 Stream 操作是向后延迟的，一直到它弄清楚了最后需要多少数据才会开始。
			Intermediate 操作永远是惰性化的。
			并行能力
			当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
			可以是无限的
			集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成。
		 */
	}

	/**
	 * 简单实用
	 */
	private static void test1() {
		/*
		 * 普通的方式过滤
		 */
		List<String> list = Arrays.asList("张三", "李四", "王五", "xuwujing");
		System.out.println("过滤之前:" + list);
		List<String> result = new ArrayList<>();
		for (String str : list) {
			if (!"李四".equals(str)) {
				result.add(str);
			}
		}
		System.out.println("过滤之后:" + result);

		/*
		 * stream 过滤
		 */
		List<String> result2 = list.stream().filter(str -> !"李四".equals(str)).collect(Collectors.toList());
		System.out.println("stream 过滤之后:" + result2);
		// 另一种方式输出
		result2.forEach(System.out::println);

		// 使用stream.filter ()过滤一列表，并.findAny().orElse
		// 遍历该list，查询数据，如果查不到，就返回 找不到!
		String result3 = list.stream().filter(str -> "李四".equals(str)).findAny().orElse("找不到!");
		String result4 = list.stream().filter(str -> "李二".equals(str)).findAny().orElse("找不到!");

		System.out.println("stream 过滤之后 2:" + result3);
		System.out.println("stream 过滤之后 3:" + result4);
		//stream 过滤之后 2:李四
		//stream 过滤之后 3:找不到!
	}

	/**
	 * 基本使用
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private static void test2() {

		/*
		 * 构造流的几种方式
		 */
		Stream stream = Stream.of("a", "b", "c");
		String[] strArray = new String[] { "a", "b", "c" };
		stream = Stream.of(strArray);
		stream = Arrays.stream(strArray);
		List<String> list = Arrays.asList(strArray);
		stream = list.stream();

		/*
		 * 流之间的相互转化 一个 Stream 只可以使用一次，这段代码为了简洁而重复使用了数次，因此会抛出异常
		 */
		try {
			Stream<String> stream2 = Stream.of("a", "b", "c");
			// 转换成 Array
			String[] strArray1 = stream2.toArray(String[]::new);

			// 转换成 Collection
			List<String> list1 = stream2.collect(Collectors.toList());
			List<String> list2 = stream2.collect(Collectors.toCollection(ArrayList::new));			
			Set set1 = stream2.collect(Collectors.toSet());
			Stack stack1 = stream2.collect(Collectors.toCollection(Stack::new));

			// 转换成 String
			String str = stream.collect(Collectors.joining()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * 汇总操作
		 */
		List<User> lists = new ArrayList<User>();
		lists.add(new User(6, "张三"));
		lists.add(new User(2, "李四"));
		lists.add(new User(3, "王五"));
		lists.add(new User(1, "张三"));
		// 计算这个list中出现 "张三" id的值
		int sum = lists.stream().filter(u -> "张三".equals(u.getName())).mapToInt(u -> u.getId()).sum();

		System.out.println("计算结果:" + sum); 
		// 7

		/*
		 * 数值类型的流 包括IntStream, LongStream和DoubleStream
		 */
		System.out.println("遍历输出该数组的数据:");
		IntStream.of(new int[] { 1, 2, 3, 4 }).forEach(System.out::println);
		System.out.println("查询范围在 2-3(2<=i<3)之间的数据:");
		IntStream.range(2, 3).forEach(System.out::println);
		System.out.println("查询范围在2-3(2<=i<=3)之间的数据:");
		IntStream.rangeClosed(2, 3).forEach(System.out::println);

		/* stream中的 map使用 */

		/*
		 * 转换大写
		 */
		List<String> list3 = Arrays.asList("zhangSan", "liSi", "wangWu");
		System.out.println("转换之前的数据:" + list3);
		List<String> list4 = list3.stream().map(String::toUpperCase).collect(Collectors.toList());
		System.out.println("转换之后的数据:" + list4); 
		// 转换之后的数据:[ZHANGSAN, LISI,WANGWU]
		
		/*
		 * 转换数据类型
		 */
		List<String> list31 = Arrays.asList("1", "2", "3");
		System.out.println("转换之前的数据:" + list31);
		List<Integer> list41 = list31.stream().map(Integer::valueOf).collect(Collectors.toList());
		System.out.println("转换之后的数据:" + list41); 
		// [1, 2, 3]
		
		
		/*
		 *  转换数据类型
		 *  对象转map
		 */
		List<User> list32 = new ArrayList<User>();
		for(int i=1;i<=10;i++){
			list32.add(new User(i,"张三"+i));
		}
		
		System.out.println("转换之前的数据:" + list32);// 转换之前的数据:[1, 2, 3]
		List<Map> list42 = list32.stream().map(User::toMap).collect(Collectors.toList());
		System.out.println("转换之后的数据:" + list42); // [1, 2, 3]
		
		
		/*
		 * 获取平方
		 */
		List<Integer> list5 = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5 });
		List<Integer> list6 = list5.stream().map(n -> n * n).collect(Collectors.toList());
		System.out.println("平方的数据:" + list6);
		// [1, 4, 9, 16, 25]

		/*
		 * flatMap 一对多 得到多个数组里面的数字
		 */
		Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
		Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
		System.out.println("打印 stream中的数字:");
		outputStream.forEach(System.out::println);

		/*
		 * 得到一段句子中的单词
		 */
		String worlds = "The way of the future";
		List<String> list7 = new ArrayList<>();
		list7.add(worlds);
		List<String> list8 = list7.stream().flatMap(str -> Stream.of(str.split(" ")))
				.filter(world -> world.length() > 0).collect(Collectors.toList());
		System.out.println("单词:");
		list8.forEach(System.out::println);
		// 单词:
		// The 
		// way 
		// of 
		// the 
		// future
		
		/*
		 * peek 对每个元素执行操作并返回一个新的 Stream
		 */
		System.out.println("peek使用:");
		Stream.of("one", "two", "three", "four").filter(e -> e.length() > 3).peek(e -> System.out.println("转换之前: " + e))
				.map(String::toUpperCase).peek(e -> System.out.println("转换之后: " + e)).collect(Collectors.toList());
		
		//	转换之前: three
		//	转换之后: THREE
		//	转换之前: four
		//	转换之后: FOUR
		
		
		
		/*
		 * limit 和 skip limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素（它是由一个叫
		 * subStream 的方法改名而来）。
		 */
		
		//limit 简单使用
		Random rd = new Random();
		System.out.println("取到的前三条数据:");
		rd.ints().limit(3).forEach(System.out::println);
		//	取到的前三条数据:
		//	1167267754
		//	-1164558977
		//	1977868798
		
		List<User> list9 = new ArrayList<User>();
		for (int i = 1; i < 4; i++) {
			User user = new User(i, "pancm" + i);
			list9.add(user);
		}
		System.out.println("截取之前的数据:");
		// 取前3条数据，但是扔掉了前面的2条，可以理解为拿到的数据为 2<=i<3 (i 是数值下标)
		List<String> list10 = list9.stream().map(User::getName).limit(3).skip(2).collect(Collectors.toList());
		System.out.println("截取之后的数据:" + list10);
		//		截取之前的数据:
		//		姓名:pancm1
		//		姓名:pancm2
		//		姓名:pancm3
		//		截取之后的数据:[pancm3]
		
		
		/*
		 * sort 进行排序 先获取在排序效率更高
		 */
		
		Random rd2 = new Random();
		System.out.println("取到的前三条数据然后进行排序:");
		rd2.ints().limit(3).sorted().forEach(System.out::println);
		//	取到的前三条数据然后进行排序:
		//	-2043456377
		//	-1778595703
		//	1013369565
		
		//普通的排序取值
		List<User> list11 = list9.stream().sorted((u1, u2) -> u1.getName().compareTo(u2.getName())).limit(3)
				.collect(Collectors.toList());
		System.out.println("排序之后的数据:" + list11);
		//优化排序取值
		List<User> list12 = list9.stream().limit(3).sorted((u1, u2) -> u1.getName().compareTo(u2.getName()))
				.collect(Collectors.toList());
		System.out.println("优化排序之后的数据:" + list12);
		//排序之后的数据:[{"id":1,"name":"pancm1"}, {"id":2,"name":"pancm2"}, {"id":3,"name":"pancm3"}]
		//优化排序之后的数据:[{"id":1,"name":"pancm1"}, {"id":2,"name":"pancm2"}, {"id":3,"name":"pancm3"}]
		
		/*
		 * min/max/distinct
		 * 最大，最小和去重
		 */
		
		List<String> list13 = Arrays.asList("zhangsan","lisi","wangwu","xuwujing");
		int maxLines = list13.stream().mapToInt(String::length).max().getAsInt();
		int minLines = list13.stream().mapToInt(String::length).min().getAsInt();
		System.out.println("最长字符的长度:" + maxLines+",最短字符的长度:"+minLines);
		//最长字符的长度:8,最短字符的长度:4
		
		String lines = "good good study day day up";
		List<String> list14 = new ArrayList<String>();
		list14.add(lines);
		List<String> words = list14.stream().flatMap(line -> Stream.of(line.split(" "))).filter(word -> word.length() > 0)
				.map(String::toLowerCase).distinct().sorted().collect(Collectors.toList());
		System.out.println("去重复之后:" + words);
		//去重复之后:[day, good, study, up]

		/*
		 * Match 匹配
		 * 
		 * allMatch：Stream 中全部元素符合则返回 true ;
		 * anyMatch：Stream 中只要有一个元素符合则返回 true; 
		 * noneMatch：Stream 中没有一个元素符合则返回 true。
		 */

		boolean all = lists.stream().allMatch(u -> u.getId() > 3);
		System.out.println("是否都大于3:" + all);
		boolean any = lists.stream().anyMatch(u -> u.getId() > 3);
		System.out.println("是否有一个大于3:" + any);
		boolean none = lists.stream().noneMatch(u -> u.getId() > 3);
		System.out.println("是否没有一个大于3的:" + none);		
		//	是否都大于3:false
		//	是否有一个大于3:true
		//	是否没有一个大于3的:false
		
		/*
		 * 生成随机数 通过实现 Supplier 接口，你可以自己来控制流的生成。这种情形通常用于随机数、常量的
		 * Stream，或者需要前后元素间维持着某种状态信息的 Stream。 把 Supplier 实例传递给 Stream.generate()
		 * 生成的 Stream，默认是串行（相对 parallel 而言）但无序的（相对 ordered 而言）。
		 * 由于它是无限的，在管道中，必须利用 limit 之类的操作限制 Stream 大小。
		 */
		Random seed = new Random();
		seed.ints().limit(3).forEach(System.out::println);
		Supplier<Integer> random = seed::nextInt;
		System.out.println("生成5个随机数:");
		Stream.generate(random).limit(3).forEach(System.out::println);
		System.out.println("生成5正整数的随机数:");
		IntStream.generate(() -> (int) (System.nanoTime() % 100)).limit(3).forEach(System.out::println);
		System.out.println("生成5个随机数:");
	
		
		/*
		 并行（parallel）程序
		parallelStream 是流并行处理程序的代替方法。
		 */
		List<String> strings = Arrays.asList("a", "", "c", "", "e","", " ");
		// 获取空字符串的数量
		long count =  strings.parallelStream().filter(string -> string.isEmpty()).count();
		System.out.println("空字符串的个数:"+count);
		
		
		/*
		 * 合并字符串
		 */
		List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		System.out.println("筛选列表: " + filtered);
		String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
		System.out.println("合并字符串: " + mergedString);
		//	筛选列表: [a, c, e,  ]
		//	合并字符串: a, c, e,  
		
	}

	/**
	 * 一些关联使用
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void test3() {

		/*
		 * Optional
		 */
		String strA = " abcd ", strB = null;
		System.out.println("数据校验开始...");
		print(strA);
		print("");
		print(strB);
		getLength(strA);
		getLength("");
		getLength(strB);
		System.out.println("数据校验结束...");

		/*
		 * reduce 主要作用是把 Stream 元素组合起来。
		 */
		// 字符串连接，concat = "ABCD"
		String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
		System.out.println("字符串拼接:" + concat);
		//字符串拼接:ABCD
		// 求最小值
		double minValue = Stream.of(-4.0, 1.0, 3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
		System.out.println("最小值:" + minValue);
		//最小值:-4.0
		
		
		// 求和, 无起始值
		int sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
		System.out.println("有无起始值求和:" + sumValue);
		// 求和, 有起始值
		 sumValue = Stream.of(1, 2, 3, 4).reduce(1, Integer::sum);
		System.out.println("有起始值求和:" + sumValue);
		//	有无起始值求和:10
		//	有起始值求和:11
		
		
		// 过滤，字符串连接，concat = "ace"
		concat = Stream.of("a", "B", "c", "D", "e", "F").filter(x -> x.compareTo("Z") > 0).reduce("", String::concat);
		System.out.println("过滤和字符串连接:" + concat);
		//过滤和字符串连接:ace
		
		
		
		/*
		 * iterate iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如 f）。
		 * 然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推。 在 iterate
		 * 时候管道必须有 limit 这样的操作来限制 Stream 大小。
		 */
		System.out.println("从2开始生成一个等差队列:");
		Stream.iterate(2, n -> n + 2).limit(5).forEach(x -> System.out.print(x + " "));
		// 从2开始生成一个等差队列:
		// 2 4 6 8 10
		
		
		System.out.println("\n");
		/*
		 * 分组排序 groupingBy/partitioningBy
		 */
		// 通过id进行排序
		System.out.println("通过id进行分组排序:");
		Map<Integer, List<User>> personGroups = Stream.generate(new UserSupplier2()).limit(5)
				.collect(Collectors.groupingBy(User::getId));
		Iterator it = personGroups.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, List<User>> persons = (Map.Entry) it.next();
			System.out.println("id " + persons.getKey() + " = " + persons.getValue());
		}
		
		//	通过id进行分组排序:
		//	id 10 = [{"id":10,"name":"pancm1"}]	
		//	id 11 = [{"id":11,"name":"pancm3"}, {"id":11,"name":"pancm6"}, {"id":11,"name":"pancm4"}, {"id":11,"name":"pancm7"}]

		
		
		//通过年龄排序
		System.out.println("通过年龄进行分区排序:");
		Map<Boolean, List<User>> children = Stream.generate(new UserSupplier3()).limit(5)
				.collect(Collectors.partitioningBy(p -> p.getId() < 18));

		System.out.println("小孩: " + children.get(true));
		System.out.println("成年人: " + children.get(false));
		
		// 通过年龄进行分区排序:
		// 小孩: [{"id":16,"name":"pancm7"}, {"id":17,"name":"pancm2"}]
		// 成年人: [{"id":18,"name":"pancm4"}, {"id":19,"name":"pancm9"}, {"id":20,"name":"pancm6"}]
		
		
		
		/*
		 *  IntSummaryStatistics 用于收集统计信息(如count、min、max、sum和average)的状态对象。
		 */
		List<Integer> numbers = Arrays.asList(1, 5, 7, 3, 9);
		IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
		 
		System.out.println("列表中最大的数 : " + stats.getMax());
		System.out.println("列表中最小的数 : " + stats.getMin());
		System.out.println("所有数之和 : " + stats.getSum());
		System.out.println("平均数 : " + stats.getAverage());
		
		//	列表中最大的数 : 9
		//	列表中最小的数 : 1
		//	所有数之和 : 25
		//	平均数 : 5.0
		
	}

	/**
	 * 自定义流
	 */
	private static void test4() {

		/*
		 * 自定义一个流 然后进行输出
		 */
		System.out.println("自定义一个流进行计算输出:");
		Stream.generate(new UserSupplier()).limit(2).forEach(u -> System.out.println(u.getId() + ", " + u.getName()));
		
		//第一次:
		//自定义一个流进行计算输出:
		//10, pancm7
		//11, pancm6
		
		//第二次:
		//自定义一个流进行计算输出:
		//10, pancm4
		//11, pancm2
		
		//第三次:
		//自定义一个流进行计算输出:
		//10, pancm4
		//11, pancm8
	}

	public static void print(String text) {
		// jdk1.8之前的写法
		// if (text != null) {
		// System.out.println(text);
		// }
		// jdk1.8的写法
		Optional.ofNullable(text).ifPresent(System.out::println);
	}

	public static void getLength(String text) {
		// jdk1.8之前的写法
		// return if (text != null) ? text.length() : -1;
		// jdk1.8的写法
		int i = Optional.ofNullable(text).map(String::length).orElse(-1);
		System.out.println("数据:" + i);
	};
}

class UserSupplier implements Supplier<User> {
	private int index = 10;
	private Random random = new Random();

	@Override
	public User get() {
		return new User(index++, "pancm" + random.nextInt(10));
	}
}

class UserSupplier2 implements Supplier<User> {
	private int index = 10;
	private Random random = new Random();

	@Override
	public User get() {
		return new User(index % 2 == 0 ? index++ : index, "pancm" + random.nextInt(10));
	}
}

class UserSupplier3 implements Supplier<User> {
	private int index = 16;
	private Random random = new Random();

	@Override
	public User get() {
		return new User(index++, "pancm" + random.nextInt(10));
	}
}





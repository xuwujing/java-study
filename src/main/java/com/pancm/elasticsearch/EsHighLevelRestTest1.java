package com.pancm.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @Title: EsHighLevelRestTest1
 * @Description: Java High Level REST Client Es高级客户端使用教程一 (基本CRUD使用) 官方文档地址:
 *               https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high.html
 * @since jdk 1.8
 * @Version:1.0.0
 * @author pancm
 * @date 2019年3月5日
 */
public class EsHighLevelRestTest1 {

	private static String elasticIp = "192.169.0.23";
	private static int elasticPort = 9200;

	private static Logger logger = LoggerFactory.getLogger(EsHighLevelRestTest1.class);

	private static RestHighLevelClient client = null;

	public static void main(String[] args) {
		try {
			init();
			createIndex();
			insert();
			queryById();
			exists();
			update();
//			deleteByQuery();
//			deleteIndex();
//			delete();
//			bulk();
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void insert() throws IOException {
		String index = "test1";
		String type = "_doc";
		// 唯一编号
		String id = "1";
		IndexRequest request = new IndexRequest(index, type, id);
		/*
		 * 第一种方式，通过jsonString进行创建
		 */
		// json
		String jsonString = "{" + "\"uid\":\"1234\","+ "\"phone\":\"12345678909\","+ "\"msgcode\":\"1\"," + "\"sendtime\":\"2019-03-14 01:57:04\","
				+ "\"message\":\"xuwujing study Elasticsearch\"" + "}";
		request.source(jsonString, XContentType.JSON);

		/*
		 * 第二种方式，通过map创建,，会自动转换成json的数据
		 */
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("uid", 1234);
		jsonMap.put("phone", 12345678909L);
		jsonMap.put("msgcode", 1);
		jsonMap.put("sendtime", "2019-03-14 01:57:04");
		jsonMap.put("message", "xuwujing study Elasticsearch");
		request.source(jsonMap);

		/*
		 * 第三种方式 : 通过XContentBuilder对象进行创建
		 */

		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			builder.field("uid", 1234);
			builder.field("phone", 12345678909L);
			builder.field("msgcode", 1);
			builder.timeField("sendtime", "2019-03-14 01:57:04");
			builder.field("message", "xuwujing study Elasticsearch");
		}
		builder.endObject();
		request.source(builder);

		IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);

		//如果是200则表示成功，否则就是失败
		if(200 == indexResponse.status().getStatus()){

		}

		// 对响应结果进行处理
		String index1 = indexResponse.getIndex();
		String type1 = indexResponse.getType();
		String id1 = indexResponse.getId();
		long version = indexResponse.getVersion();
		// 如果是新增/修改的话的话
		if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {

		} else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {

		}
		ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

		}
		if (shardInfo.getFailed() > 0) {
			for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
				String reason = failure.reason();
			}
		}
	}

	/*
	 * 初始化服务
	 */
	private static void init() {
		
		client = new RestHighLevelClient(RestClient.builder(new HttpHost(elasticIp, elasticPort)));

	}

	/*
	 * 关闭服务
	 */
	private static void close() {
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建索引
	 * 
	 * @throws IOException
	 */
	private static void createIndex() throws IOException {

		// 类型
		String type = "_doc";
		String index = "test1";
		// setting 的值
		Map<String, Object> setmapping = new HashMap<>();
		// 分区数、副本数、缓存刷新时间
		setmapping.put("number_of_shards", 10);
		setmapping.put("number_of_replicas", 1);
		setmapping.put("refresh_interval", "5s");
		Map<String, Object> keyword = new HashMap<>();
		//设置类型
		keyword.put("type", "keyword");
		Map<String, Object> lon = new HashMap<>();
		//设置类型
		lon.put("type", "long");
		Map<String, Object> date = new HashMap<>();
		//设置类型
		date.put("type", "date");
		date.put("format", "yyyy-MM-dd HH:mm:ss");

		Map<String, Object> jsonMap2 = new HashMap<>();
		Map<String, Object> properties = new HashMap<>();
		//设置字段message信息
		properties.put("uid", lon);
		properties.put("phone", lon);
		properties.put("msgcode", lon);
		properties.put("message", keyword);
		properties.put("sendtime", date);
		Map<String, Object> mapping = new HashMap<>();
		mapping.put("properties", properties);
		jsonMap2.put(type, mapping);

		GetIndexRequest getRequest = new GetIndexRequest();
		getRequest.indices(index);
		getRequest.types(type);
		getRequest.local(false);
		getRequest.humanReadable(true);
		boolean exists2 = client.indices().exists(getRequest, RequestOptions.DEFAULT);
		//如果存在就不创建了
		if(exists2) {
			System.out.println(index+"索引库已经存在!");
			return;
		}
		// 开始创建库
		CreateIndexRequest request = new CreateIndexRequest(index);
		try {
			// 加载数据类型
			request.settings(setmapping);
			//设置mapping参数
			request.mapping(type, jsonMap2);
			//设置别名
			request.alias(new Alias("pancm_alias"));
			CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
			boolean falg = createIndexResponse.isAcknowledged();
			if(falg){
				System.out.println("创建索引库:"+index+"成功！" );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * 删除索引
	 *
	 * @throws IOException
	 */
	private static void deleteIndex() throws IOException {
		String index = "userindex";
		DeleteIndexRequest  request = new DeleteIndexRequest(index);
		// 同步删除
		client.indices().delete(request,RequestOptions.DEFAULT);
		System.out.println("删除索引库成功！"+index);

	}

	/**
	 * 查询数据
	 * 
	 * @throws IOException
	 */
	private static void queryById() {
		String type = "_doc";
		String index = "test1";
		// 唯一编号
		String id = "1";
		// 创建查询请求
		GetRequest getRequest = new GetRequest(index, type, id);

		GetResponse getResponse = null;
		try {
			getResponse = client.get(getRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ElasticsearchException e) {
			// 如果是索引不存在
			if (e.status() == RestStatus.NOT_FOUND) {
				System.out.println("该索引库不存在！" + index);
			}

		}
		// 如果存在该数据则返回对应的结果
		if (getResponse.isExists()) {
			long version = getResponse.getVersion();
			String sourceAsString = getResponse.getSourceAsString();
			Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
			byte[] sourceAsBytes = getResponse.getSourceAsBytes();
			System.out.println("查询返回结果String:" + sourceAsString);
			System.out.println("查询返回结果Map:" + sourceAsMap);
		} else {
			System.out.println("没有找到该数据！");
		}
	}

	/**
	 * 是否存在
	 * 
	 * @throws IOException
	 */
	private static void exists() throws IOException {
		String type = "_doc";
		String index = "test1";
		// 唯一编号
		String id = "1";
		// 创建查询请求
		GetRequest getRequest = new GetRequest(index, type, id);

		
		boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);

		ActionListener<Boolean> listener = new ActionListener<Boolean>() {
			@Override
			public void onResponse(Boolean exists) {
				System.out.println("==" + exists);
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println("失败的原因：" + e.getMessage());
			}
		};
		// 进行异步监听
//		client.existsAsync(getRequest, RequestOptions.DEFAULT, listener);

		System.out.println("数据是否存在：" + exists);
	}

	/**
	 * 更新操作
	 * 
	 * @throws IOException
	 */
	private static void update() throws IOException {
		String type = "_doc";
		String index = "test1";
		// 唯一编号
		String id = "1";
		UpdateRequest upateRequest = new UpdateRequest();
		upateRequest.id(id);
		upateRequest.index(index);
		upateRequest.type(type);

		// 依旧可以使用Map这种集合作为更新条件
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("uid", 12345);
		jsonMap.put("phone", 123456789019L);
		jsonMap.put("msgcode", 2);
		jsonMap.put("sendtime", "2019-03-14 01:57:04");
		jsonMap.put("message", "xuwujing study Elasticsearch");
		upateRequest.doc(jsonMap);
		// upsert 方法表示如果数据不存在，那么就新增一条
		upateRequest.docAsUpsert(true);
		client.update(upateRequest, RequestOptions.DEFAULT);
		System.out.println("更新成功！");

	}



	/**
	 * 根据查询条件更新
	 *
	 * @throws IOException
	 */
	private static void updateByQuery() throws IOException {
		String type = "_doc";
		String index = "test1";
		//
		UpdateByQueryRequest request = new UpdateByQueryRequest(index,type);
		// 设置查询条件
		request.setQuery(new TermQueryBuilder("user", "pancm"));
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

		// 设置复制文档的数量
		request.setSize(10);
		// 设置一次批量处理的条数，默认是1000
		request.setBatchSize(100);
		//设置超时时间
		request.setTimeout(TimeValue.timeValueMinutes(2));
		//索引选项
		request.setIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);

		// 同步执行
		BulkByScrollResponse bulkResponse = client.updateByQuery(request, RequestOptions.DEFAULT);

		// 异步执行
//		client.updateByQueryAsync(request, RequestOptions.DEFAULT, listener);

		// 返回结果
		TimeValue timeTaken = bulkResponse.getTook();
		boolean timedOut = bulkResponse.isTimedOut();
		long totalDocs = bulkResponse.getTotal();
		long updatedDocs = bulkResponse.getUpdated();
		long deletedDocs = bulkResponse.getDeleted();
		long batches = bulkResponse.getBatches();
		long noops = bulkResponse.getNoops();
		long versionConflicts = bulkResponse.getVersionConflicts();
		long bulkRetries = bulkResponse.getBulkRetries();
		long searchRetries = bulkResponse.getSearchRetries();
		TimeValue throttledMillis = bulkResponse.getStatus().getThrottled();
		TimeValue throttledUntilMillis = bulkResponse.getStatus().getThrottledUntil();
		List<ScrollableHitSource.SearchFailure> searchFailures = bulkResponse.getSearchFailures();
		List<BulkItemResponse.Failure> bulkFailures = bulkResponse.getBulkFailures();
		System.out.println("查询更新总共花费了:" + timeTaken.getMillis() + " 毫秒，总条数:" + totalDocs + ",更新数:" + updatedDocs);

	}

	/**
	 * 删除
	 * 
	 * @throws IOException
	 * 
	 */
	private static void delete() throws IOException {

		String type = "_doc";
		String index = "test1";
		// 唯一编号
		String id = "1";
		DeleteRequest deleteRequest = new DeleteRequest();
		deleteRequest.id(id);
		deleteRequest.index(index);
		deleteRequest.type(type);
		// 设置超时时间
		deleteRequest.timeout(TimeValue.timeValueMinutes(2));
		// 设置刷新策略"wait_for"
		// 保持此请求打开，直到刷新使此请求的内容可以搜索为止。此刷新策略与高索引和搜索吞吐量兼容，但它会导致请求等待响应，直到发生刷新
		deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
		// 同步删除
		DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);

		/*
		 * 异步删除操作
		 */

		// 进行监听
		ActionListener<DeleteResponse> listener = new ActionListener<DeleteResponse>() {
			@Override
			public void onResponse(DeleteResponse deleteResponse) {
				System.out.println("响应:" + deleteResponse);
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println("删除监听异常:" + e.getMessage());
			}
		};

		// 异步删除
//		 client.deleteAsync(deleteRequest, RequestOptions.DEFAULT, listener);

		ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
		// 如果处理成功碎片的数量少于总碎片的情况,说明还在处理或者处理发生异常
		if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
			System.out.println("需要处理的碎片总量:" + shardInfo.getTotal());
			System.out.println("处理成功的碎片总量:" + shardInfo.getSuccessful());
		}

		if (shardInfo.getFailed() > 0) {
			for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
				String reason = failure.reason();
			}
		}
		System.out.println("删除成功!");
	}


	/**
	 * 根据查询条件删除
	 *
	 * @throws IOException
	 */
	private static void deleteByQuery() throws IOException {
		String type = "_doc";
		String index = "test1";
		DeleteByQueryRequest request = new DeleteByQueryRequest(index,type);
		// 设置查询条件
		request.setQuery(QueryBuilders.termQuery("uid",1234));
		// 同步执行
		BulkByScrollResponse bulkResponse = client.deleteByQuery(request, RequestOptions.DEFAULT);

		// 异步执行
//		client.updateByQueryAsync(request, RequestOptions.DEFAULT, listener);

		// 返回结果
		TimeValue timeTaken = bulkResponse.getTook();
		boolean timedOut = bulkResponse.isTimedOut();
		long totalDocs = bulkResponse.getTotal();
		long updatedDocs = bulkResponse.getUpdated();
		long deletedDocs = bulkResponse.getDeleted();
		long batches = bulkResponse.getBatches();
		long noops = bulkResponse.getNoops();
		long versionConflicts = bulkResponse.getVersionConflicts();
		long bulkRetries = bulkResponse.getBulkRetries();
		long searchRetries = bulkResponse.getSearchRetries();
		TimeValue throttledMillis = bulkResponse.getStatus().getThrottled();
		TimeValue throttledUntilMillis = bulkResponse.getStatus().getThrottledUntil();
		List<ScrollableHitSource.SearchFailure> searchFailures = bulkResponse.getSearchFailures();
		List<BulkItemResponse.Failure> bulkFailures = bulkResponse.getBulkFailures();
		System.out.println("查询更新总共花费了:" + timeTaken.getMillis() + " 毫秒，总条数:" + totalDocs + ",更新数:" + updatedDocs);

	}

	/**
	 * 批量操作示例
	 * 
	 * @throws InterruptedException
	 */
	private static void bulk() throws IOException, InterruptedException {
		String index = "estest";
		String type = "estest";

		BulkRequest request = new BulkRequest();
		// 批量新增,存在会直接覆盖
		request.add(new IndexRequest(index, type, "1").source(XContentType.JSON, "field", "foo"));
		request.add(new IndexRequest(index, type, "2").source(XContentType.JSON, "field", "bar"));
		request.add(new IndexRequest(index, type, "3").source(XContentType.JSON, "field", "baz"));

		// 可以进行修改/删除/新增 操作
		//docAsUpsert 为true表示存在更新，不存在插入，为false表示不存在就是不做更新
		request.add(new UpdateRequest(index, type, "2").doc(XContentType.JSON, "field", "test").docAsUpsert(true));
		request.add(new DeleteRequest(index, type, "3"));
		request.add(new IndexRequest(index, type, "4").source(XContentType.JSON, "field", "baz"));

		BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);


		ActionListener<BulkResponse> listener3 = new ActionListener<BulkResponse>() {
			@Override
			public void onResponse(BulkResponse response) {
				System.out.println("===="+response.buildFailureMessage());
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println("====---"+e.getMessage());
			}
		};

		client.bulkAsync(request, RequestOptions.DEFAULT,listener3);

		// 可以快速检查一个或多个操作是否失败 true是有至少一个失败！
		if (bulkResponse.hasFailures()) {
			System.out.println("有一个操作失败!");
		}

		// 对处理结果进行遍历操作并根据不同的操作进行处理
		for (BulkItemResponse bulkItemResponse : bulkResponse) {
			DocWriteResponse itemResponse = bulkItemResponse.getResponse();

			// 操作失败的进行处理
			if (bulkItemResponse.isFailed()) {
				BulkItemResponse.Failure failure = bulkItemResponse.getFailure();

			}

			if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX
					|| bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
				IndexResponse indexResponse = (IndexResponse) itemResponse;
				System.out.println("新增失败!"+indexResponse.toString());

			} else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
				UpdateResponse updateResponse = (UpdateResponse) itemResponse;
				System.out.println("更新失败!"+updateResponse.toString());

			} else if (bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
				DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
				System.out.println("删除失败!"+deleteResponse.toString());

			}
		}

		System.out.println("批量执行成功！");

		/*
		 * 批量执行处理器相关示例代码
		 */

		// 批量处理器的监听器设置

		BulkProcessor.Listener listener = new BulkProcessor.Listener() {

			// 在执行BulkRequest的每次执行之前调用，这个方法允许知道将要在BulkRequest中执行的操作的数量
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				int numberOfActions = request.numberOfActions();
				logger.debug("Executing bulk [{}] with {} requests", executionId, numberOfActions);
			}

			// 在每次执行BulkRequest之后调用，这个方法允许知道BulkResponse是否包含错误
			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				if (response.hasFailures()) {
					logger.warn("Bulk [{}] executed with failures", executionId);
				} else {
					logger.debug("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
				}
			}

			// 如果BulkRequest失败，则调用该方法，该方法允许知道失败
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				logger.error("Failed to execute bulk", failure);
			}
		};

		BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer = (request2, bulkListener) -> client
				.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
		// 创建一个批量执行的处理器
		BulkProcessor bulkProcessor = BulkProcessor.builder(bulkConsumer, listener).build();
		BulkProcessor.Builder builder = BulkProcessor.builder(bulkConsumer, listener);
		// 根据当前添加的操作数量设置刷新新批量请求的时间(默认为1000，使用-1禁用它)
		builder.setBulkActions(500);
		// 根据当前添加的操作大小设置刷新新批量请求的时间(默认为5Mb，使用-1禁用)
		builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
		// 设置允许执行的并发请求数量(默认为1，使用0只允许执行单个请求)
		builder.setConcurrentRequests(0);
		// 设置刷新间隔如果间隔通过，则刷新任何挂起的BulkRequest(默认为未设置)
		builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
		// 设置一个常量后退策略，该策略最初等待1秒并重试最多3次。
		builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));

		IndexRequest one = new IndexRequest(index, type, "1").source(XContentType.JSON, "title",
				"In which order are my Elasticsearch queries executed?");
		IndexRequest two = new IndexRequest(index, type, "2").source(XContentType.JSON, "title",
				"Current status and upcoming changes in Elasticsearch");
		IndexRequest three = new IndexRequest(index, type, "3").source(XContentType.JSON, "title",
				"The Future of Federated Search in Elasticsearch");
		bulkProcessor.add(one);
		bulkProcessor.add(two);
		bulkProcessor.add(three);

		// 如果所有大容量请求都已完成，则该方法返回true;如果在所有大容量请求完成之前的等待时间已经过去，则返回false
		boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);

		System.out.println("请求的响应结果:" + terminated);

	}

}

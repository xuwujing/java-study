package com.pancm.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RethrottleRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.ScrollableHitSource;
import org.elasticsearch.tasks.TaskId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNull;

/**
 * @Title: EsHighLevelRestTest2
 * @Description: Java High Level REST Client Es高级客户端使用教程二 (关于组合使用)
 * @since jdk 1.8
 * @Version:1.0.0
 * @author pancm
 * @date 2019年3月11日
 */
public class EsHighLevelRestTest2 {

	private static String elasticIp = "192.169.0.23";
	private static int elasticPort = 9200;

	private static Logger logger = LoggerFactory.getLogger(EsHighLevelRestTest2.class);

	private static RestHighLevelClient client = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			init();
			multiGet();
			reindex();
			deleteByQuery();
			rethrottleByQuery();
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 初始化服务
	 */
	private static void init() {
		client = new RestHighLevelClient(RestClient.builder(new HttpHost(elasticIp, elasticPort, "http")));

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
	 * 多查询使用
	 * 
	 * @throws IOException
	 */
	private static void multiGet() throws IOException {

		MultiGetRequest request = new MultiGetRequest();
		request.add(new MultiGetRequest.Item("estest", "estest", "1"));
		request.add(new MultiGetRequest.Item("user", "userindex", "2"));
		// 禁用源检索，默认启用
//		request.add(new MultiGetRequest.Item("user", "userindex", "2").fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));

		// 同步构建
		MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);

		// 异步构建
//		MultiGetResponse response2 = client.mgetAsync(request, RequestOptions.DEFAULT, listener);

		/*
		 * 返回的MultiGetResponse包含在' getResponses中的MultiGetItemResponse的列表，其顺序与请求它们的顺序相同。
		 * 如果成功，MultiGetItemResponse包含GetResponse或MultiGetResponse。如果失败了就失败。
		 * 成功看起来就像一个正常的GetResponse
		 */

		for (MultiGetItemResponse item : response.getResponses()) {
			assertNull(item.getFailure());
			GetResponse get = item.getResponse();
			String index = item.getIndex();
			String type = item.getType();
			String id = item.getId();
			// 如果请求存在
			if (get.isExists()) {
				long version = get.getVersion();
				String sourceAsString = get.getSourceAsString();
				Map<String, Object> sourceAsMap = get.getSourceAsMap();
				byte[] sourceAsBytes = get.getSourceAsBytes();
				System.out.println("查询的结果:" + sourceAsMap);
			} else {
				System.out.println("没有找到该文档!");
			}

		}

	}

	/**
	 * 索引复制
	 * 
	 * @throws IOException
	 */
	private static void reindex() throws IOException {
		// 创建索引复制请求并进行索引复制
		ReindexRequest request = new ReindexRequest();
		// 需要复制的索引
		request.setSourceIndices("user");
		// 复制的目标索引
		request.setDestIndex("dest_test");

		// 表示如果在复制索引的时候有缺失的文档的话会进行创建,默认是index
		request.setDestOpType("create");
		// 如果在复制的过程中发现版本冲突，那么会继续进行复制
		request.setConflicts("proceed");

		// 只复制文档类型为 userindex 的数据
		request.setSourceDocTypes("userindex");
		// 只复制 pancm 用户的数据
		request.setSourceQuery(new TermQueryBuilder("user", "pancm"));
		// 设置复制文档的数量
		request.setSize(10);
		// 设置一次批量处理的条数，默认是1000
		request.setSourceBatchSize(100);

		// 进行排序
//		request.addSortField("postDate", SortOrder.DESC);

		// 指定切片大小
		request.setSlices(2);
		
		//设置超时时间
		request.setTimeout(TimeValue.timeValueMinutes(2));
		//允许刷新
		request.setRefresh(true);
		
		// 同步执行
		BulkByScrollResponse bulkResponse = client.reindex(request, RequestOptions.DEFAULT);

		// 异步执行
//		client.reindexAsync(request, RequestOptions.DEFAULT, listener);

		// 响应结果处理

		TimeValue timeTaken = bulkResponse.getTook();
		boolean timedOut = bulkResponse.isTimedOut();
		long totalDocs = bulkResponse.getTotal();
		long updatedDocs = bulkResponse.getUpdated();
		long createdDocs = bulkResponse.getCreated();
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

		System.out.println("索引复制总共花费了:" + timeTaken.getMillis() + " 毫秒，总条数:" + totalDocs + ",创建数:" + createdDocs
				+ ",更新数:" + updatedDocs);
	}

	

	
	
	/**
	 * 根据查询条件删除
	 * @throws IOException
	 */
	private static void deleteByQuery() throws IOException {
		//
		DeleteByQueryRequest request = new DeleteByQueryRequest("user");

		// 设置查询条件
		request.setQuery(new TermQueryBuilder("user", "pancm"));

		// 设置复制文档的数量
		request.setSize(10);
		// 设置一次批量处理的条数，默认是1000
		request.setBatchSize(100);
		//设置路由
		request.setRouting("=cat");
		//设置超时时间
		request.setTimeout(TimeValue.timeValueMinutes(2));
		//允许刷新
		request.setRefresh(true);
		//索引选项
		request.setIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
		
		// 同步执行
		BulkByScrollResponse bulkResponse = client.deleteByQuery(request, RequestOptions.DEFAULT);

		// 异步执行
//		client.updateByQueryAsync(request, RequestOptions.DEFAULT, listener); 

		// 返回结果
		TimeValue timeTaken = bulkResponse.getTook();
		boolean timedOut = bulkResponse.isTimedOut();
		long totalDocs = bulkResponse.getTotal();
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
		System.out.println("查询更新总共花费了:" + timeTaken.getMillis() + " 毫秒，总条数:" + totalDocs + ",删除数:" + deletedDocs);

	}
	
	
	/**
	 * 用于更改正在运行的重索引、逐查询更新或逐查询删除任务的当前节流，或完全禁用任务的节流。
	 * @throws IOException
	 */
	private static void rethrottleByQuery() throws IOException {
		TaskId taskId=new TaskId("1");
		//用于更改正在运行的重索引、逐查询更新或逐查询删除任务的当前节流，或完全禁用任务的节流。
		//并且将请求将任务的节流更改为每秒100个请求
		RethrottleRequest request = new RethrottleRequest(taskId,100.0f);

		// 同步设置需要更改的流
//		client.reindexRethrottle(request, RequestOptions.DEFAULT);       
//		client.updateByQueryRethrottle(request, RequestOptions.DEFAULT); 
//		client.deleteByQueryRethrottle(request, RequestOptions.DEFAULT); 
		
		
		ActionListener<ListTasksResponse> listener = new ActionListener<ListTasksResponse>() {
		    @Override
		    public void onResponse(ListTasksResponse response) {
		        System.out.println("===="+response.getTasks().toString());
		    }

		    @Override
		    public void onFailure(Exception e) {
		    	 System.out.println("====---"+e.getMessage());
		    }
		};
		
		// 异步设置要更改的流
		client.reindexRethrottleAsync(request, RequestOptions.DEFAULT, listener);       
		client.updateByQueryRethrottleAsync(request, RequestOptions.DEFAULT, listener); 
		client.deleteByQueryRethrottleAsync(request, RequestOptions.DEFAULT, listener);

		System.out.println("已成功设置!");

	}
	
	
	
	
}

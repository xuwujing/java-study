package com.pancm.sql.easticsearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Title: EsHighLevelRestSearchTest
 * @Description: Java High Level REST Client Es高级客户端查询使用使用教程 (Search查询使用教程)
 *               官方文档地址:
 *               https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high.html
 * @Version:1.0.0
 * @author pancm
 * @date 2019年3月12日
 */
public class EsHighLevelRestSearchTest {

	private static String elasticIp = "192.169.0.23";
	private static int elasticPort = 9200;

	private static Logger logger = LoggerFactory.getLogger(EsHighLevelRestSearchTest.class);

	private static RestHighLevelClient client = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			init();
			search();
			

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
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
	 * search查询使用示例
	 * 
	 * @throws IOException
	 */
	private static void search() throws IOException {

		/*
		 * 查询集群所有的索引
		 * 
		 */
		SearchRequest searchRequestAll = new SearchRequest();

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchRequestAll.source(searchSourceBuilder);

		// 同步查询
		SearchResponse searchResponseAll = client.search(searchRequestAll, RequestOptions.DEFAULT);

		System.out.println("所有查询总数:" + searchResponseAll.getHits().getTotalHits());

		// 查询指定的索引库
		SearchRequest searchRequest = new SearchRequest("user");
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		// 设置查询条件
		sourceBuilder.query(QueryBuilders.termQuery("user", "pancm"));
		// 设置起止和结束
		sourceBuilder.from(0);
		sourceBuilder.size(5);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 设置路由
//		searchRequest.routing("routing");
		// 设置索引库表达式
		searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
		// 查询选择本地分片，默认是集群分片
		searchRequest.preference("_local");

		// 排序
		// 根据默认值进行降序排序
//		sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC)); 
		// 根据字段进行升序排序
//		sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));  

		// 关闭suorce查询
//		sourceBuilder.fetchSource(false);

		String[] includeFields = new String[] { "title", "user", "innerObject.*" };
		String[] excludeFields = new String[] { "_type" };
		// 包含或排除字段
//		sourceBuilder.fetchSource(includeFields, excludeFields);

		searchRequest.source(sourceBuilder);

		// 同步查询
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

		// HTTP状态代码、执行时间或请求是否提前终止或超时
		RestStatus status = searchResponse.status();
		TimeValue took = searchResponse.getTook();
		Boolean terminatedEarly = searchResponse.isTerminatedEarly();
		boolean timedOut = searchResponse.isTimedOut();

		// 供关于受搜索影响的切分总数的统计信息，以及成功和失败的切分
		int totalShards = searchResponse.getTotalShards();
		int successfulShards = searchResponse.getSuccessfulShards();
		int failedShards = searchResponse.getFailedShards();
		// 失败的原因
		for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
			// failures should be handled here
		}

		// 结果
		searchResponse.getHits().forEach(hit -> {
			Map<String, Object> map = hit.getSourceAsMap();
			String string = hit.getSourceAsString();
			System.out.println("普通查询的Map结果:" + map);
			System.out.println("普通查询的String结果:" + string);
		});

		
		System.out.println("\n=================\n");

		/*
		 * 全文查询使用示例
		 */

		// 搜索字段user为pancm的数据
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("user", "pancm");

		// 设置模糊查询
		matchQueryBuilder.fuzziness(Fuzziness.AUTO);
		// 设置前缀长度
		matchQueryBuilder.prefixLength(3);
		// 设置最大扩展选项来控制查询的模糊过程
		matchQueryBuilder.maxExpansions(10);

		/*
		 * QueryBuilder也可以
		 */

//		QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
//                .fuzziness(Fuzziness.AUTO)
//                .prefixLength(3)
//                .maxExpansions(10);

		SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
		searchSourceBuilder2.query(matchQueryBuilder);

		SearchRequest searchRequest2 = new SearchRequest();
		searchRequest2.source(searchSourceBuilder2);
		// 同步查询
		SearchResponse searchResponse2 = client.search(searchRequest, RequestOptions.DEFAULT);
		
		SearchHits hits = searchResponse.getHits();
		//总条数和分值
		long totalHits = hits.getTotalHits();
		float maxScore = hits.getMaxScore();
		
	
		
		hits.forEach(hit -> {
			
			String index = hit.getIndex();
			String type = hit.getType();
			String id = hit.getId();
			float score = hit.getScore();
			
			
			
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			String string = hit.getSourceAsString();
			System.out.println("Match查询的Map结果:" + sourceAsMap);
			System.out.println("Match查询的String结果:" + string);
			
			String documentTitle = (String) sourceAsMap.get("title");
//			List<Object> users = (List<Object>) sourceAsMap.get("user");
			Map<String, Object> innerObject =
			        (Map<String, Object>) sourceAsMap.get("innerObject");
		});

		
		
		
		System.out.println("\n=================\n");

		/*
		 * 高亮查询
		 */

		SearchSourceBuilder searchSourceBuilder3 = new SearchSourceBuilder();
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
		// 设置字段高亮字体
		highlightTitle.highlighterType("user");
		highlightBuilder.field(highlightTitle);
		HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
		highlightBuilder.field(highlightUser);
		searchSourceBuilder3.highlighter(highlightBuilder);

		SearchRequest searchRequest3 = new SearchRequest();
		searchRequest3.source(searchSourceBuilder3);

		// 同步查询
		SearchResponse searchResponse3 = client.search(searchRequest3, RequestOptions.DEFAULT);

		searchResponse3.getHits().forEach(hit -> {

			Map<String, Object> map = hit.getSourceAsMap();
			String string = hit.getSourceAsString();
			System.out.println("Highlight查询的Map结果:" + map);
			System.out.println("Highlight查询的String结果:" + string);
		});

		System.out.println("\n=================\n");

		/**
		 * 聚合查询
		 */

		SearchSourceBuilder searchSourceBuilder4 = new SearchSourceBuilder();
		
		//terms 就是分组统计 根据user进行分组并创建一个新的聚合user_
		TermsAggregationBuilder aggregation = AggregationBuilders.terms("user_").field("user");
		aggregation.subAggregation(AggregationBuilders.avg("average_age").field("age"));
		searchSourceBuilder4.aggregation(aggregation);

		SearchRequest searchRequest4 = new SearchRequest();
		searchRequest4.source(searchSourceBuilder4);

		// 同步查询
		SearchResponse searchResponse4 = client.search(searchRequest4, RequestOptions.DEFAULT);
		//聚合查询返回条件
		Aggregations aggregations = searchResponse4.getAggregations();
		List<Aggregation> aggregationList = aggregations.asList();
		
		for (Aggregation agg : aggregations) {
		    String type = agg.getType();
		    if (type.equals(TermsAggregationBuilder.NAME)) {
		        Bucket elasticBucket = ((Terms) agg).getBucketByKey("Elastic");
		        long numberOfDocs = elasticBucket.getDocCount();
		        System.out.println("条数:"+numberOfDocs);
		    }
		}
		
		
//		Terms byCompanyAggregation = aggregations.get("user_"); 
//		Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic"); 
//		Avg averageAge = elasticBucket.getAggregations().get("average_age"); 
//		double avg = averageAge.getValue();
//		
//		System.out.println("聚合查询返回的结果:"+avg);
		
		
		/*
		 * 建议查询
		 */
		SearchSourceBuilder searchSourceBuilder5 = new SearchSourceBuilder();
		SuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion("user").text("pancm");
		SuggestBuilder suggestBuilder = new SuggestBuilder();
		suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
		searchSourceBuilder5.suggest(suggestBuilder);
		
		SearchRequest searchRequest5 = new SearchRequest();
		searchRequest5.source(searchSourceBuilder5);

		// 同步查询
		SearchResponse searchResponse5= client.search(searchRequest5, RequestOptions.DEFAULT);
		
		Suggest suggest = searchResponse5.getSuggest(); 
		TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user"); 
		for (TermSuggestion.Entry entry : termSuggestion.getEntries()) { 
		    for (TermSuggestion.Entry.Option option : entry) { 
		        String suggestText = option.getText().string();
		        System.out.println("返回结果:"+suggestText);
		    }
		}
		
		
		
		
		
		
		
		
		
	}

}

package com.pancm.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author pancm
 * @Title: pancm_project
 * @Description:ES聚合查询测试用例
 * @Version:1.0.0
 * @Since:jdk1.8
 * @date 2019/4/2
 */
public class EsAggregationSearchTest {



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
            avgSearch();
            maxSearch();
            sumSearch();
            avgGroupSearch();
            maxGroupSearch();
            sumGroupSearch();
            topSearch();
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
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(elasticIp, elasticPort));
        client = new RestHighLevelClient(restClientBuilder);

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
            }finally{
                client=null;
            }
        }
    }

    /**
     * @Author pancm
     * @Description 平均聚合查询测试用例
     * @Date  2019/4/1
     * @Param []
     * @return void
     **/
    private static  void avgSearch() throws IOException {

        String buk="t_grade_avg";
        //直接求平均数
        AggregationBuilder aggregation = AggregationBuilders.avg(buk).field("grade");
        logger.info("求班级的平均分数:");
        agg(aggregation,buk);

    }

    private static  void maxSearch() throws  IOException{
        String buk="t_grade";
        AggregationBuilder aggregation = AggregationBuilders.max(buk).field("grade");
        logger.info("求班级的最分数:");
        agg(aggregation,buk);
    }

    private static  void sumSearch() throws  IOException{
        String buk="t_grade";
        AggregationBuilder aggregation = AggregationBuilders.sum(buk).field("grade");
        logger.info("求班级的总分数:");
        agg(aggregation,buk);
    }

    /**
     * @Author pancm
     * @Description 平均聚合查询测试用例
     * @Date  2019/4/1
     * @Param []
     * @return void
     **/
    private static  void avgGroupSearch() throws IOException {


        String agg="t_class_avg";
        String buk="t_grade";
        //terms 就是分组统计 根据student的grade成绩进行分组并创建一个新的聚合
        TermsAggregationBuilder aggregation = AggregationBuilders.terms(agg).field("class");
        aggregation.subAggregation(AggregationBuilders.avg(buk).field("grade"));

        logger.info("根据班级求平均分数:");
        agg(aggregation,agg,buk);

    }


    private static  void maxGroupSearch() throws  IOException{

        String agg="t_class_max";
        String buk="t_grade";
        //terms 就是分组统计 根据student的grade成绩进行分组并创建一个新的聚合
        TermsAggregationBuilder aggregation = AggregationBuilders.terms(agg).field("class");
        aggregation.subAggregation(AggregationBuilders.max(buk).field("grade"));
        logger.info("根据班级求最大分数:");
        agg(aggregation,agg,buk);
    }


    private static  void sumGroupSearch() throws  IOException{
        String agg="t_class_sum";
        String buk="t_grade";
        //terms 就是分组统计 根据student的grade成绩进行分组并创建一个新的聚合
        TermsAggregationBuilder aggregation = AggregationBuilders.terms(agg).field("class");
        aggregation.subAggregation(AggregationBuilders.sum(buk).field("grade"));

        logger.info("根据班级求总分:");
        agg(aggregation,agg,buk);
    }


    protected  static  void agg(AggregationBuilder aggregation, String buk) throws  IOException{
        SearchResponse searchResponse = search(aggregation);
        if(RestStatus.OK.equals(searchResponse.status())) {
            // 获取聚合结果
            Aggregations aggregations = searchResponse.getAggregations();

            if(buk.contains("avg")){
                //取子聚合
                Avg ba = aggregations.get(buk);
                logger.info(buk+":" + ba.getValue());
                logger.info("------------------------------------");
            }else if(buk.contains("max")){
                //取子聚合
                Max ba = aggregations.get(buk);
                logger.info(buk+":" + ba.getValue());
                logger.info("------------------------------------");

            }else if(buk.contains("min")){
                //取子聚合
                Min ba = aggregations.get(buk);
                logger.info(buk+":" + ba.getValue());
                logger.info("------------------------------------");
            }else if(buk.contains("sum")){
                //取子聚合
                Sum ba = aggregations.get(buk);
                logger.info(buk+":" + ba.getValue());
                logger.info("------------------------------------");
            }else{
                //取子聚合
                TopHits ba = aggregations.get(buk);
                logger.info(buk+":" + ba.getHits().totalHits);
                logger.info("------------------------------------");
            }


        }
    }

    private static SearchResponse search(AggregationBuilder aggregation) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("student");
        searchRequest.types("_doc");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //不需要解释
        searchSourceBuilder.explain(false);
        //不需要原始数据
        searchSourceBuilder.fetchSource(false);
        //不需要版本号
        searchSourceBuilder.version(false);
        searchSourceBuilder.aggregation(aggregation);
        logger.info("查询的语句:"+searchSourceBuilder.toString());
        searchRequest.source(searchSourceBuilder);
        // 同步查询
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return  searchResponse;
    }

    /**
     * @Author pancm
     * @Description 进行聚合
     * @Date  2019/4/2
     * @Param []
     * @return void
     **/
    protected  static  void agg(AggregationBuilder aggregation, String agg, String buk) throws  IOException{

        // 同步查询
        SearchResponse searchResponse = search(aggregation);

        //4、处理响应
        //搜索结果状态信息
        if(RestStatus.OK.equals(searchResponse.status())) {
            // 获取聚合结果
            Aggregations aggregations = searchResponse.getAggregations();


            //分组
            Terms byAgeAggregation = aggregations.get(agg);
            logger.info(agg+" 结果");
            logger.info("name: " + byAgeAggregation.getName());
            logger.info("type: " + byAgeAggregation.getType());
            logger.info("sumOfOtherDocCounts: " + byAgeAggregation.getSumOfOtherDocCounts());

            logger.info("------------------------------------");
            for(Terms.Bucket buck : byAgeAggregation.getBuckets()) {
                logger.info("key: " + buck.getKeyAsNumber());
                logger.info("docCount: " + buck.getDocCount());
                logger.info("docCountError: " + buck.getDocCountError());


                if(agg.contains("avg")){
                    //取子聚合
                    Avg ba = buck.getAggregations().get(buk);
                    logger.info(buk+":" + ba.getValue());
                    logger.info("------------------------------------");
                }else if(agg.contains("max")){
                    //取子聚合
                    Max ba = buck.getAggregations().get(buk);
                    logger.info(buk+":" + ba.getValue());
                    logger.info("------------------------------------");

                }else if(agg.contains("min")){
                    //取子聚合
                    Min ba = buck.getAggregations().get(buk);
                    logger.info(buk+":" + ba.getValue());
                    logger.info("------------------------------------");
                }else if(agg.contains("sum")){
                    //取子聚合
                    Sum ba = buck.getAggregations().get(buk);
                    logger.info(buk+":" + ba.getValue());
                    logger.info("------------------------------------");
                }
            }
        }
    }

    private static  void topSearch() throws  IOException{


    }


}

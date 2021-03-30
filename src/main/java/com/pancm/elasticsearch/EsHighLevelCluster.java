package com.pancm.elasticsearch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.routing.allocation.decider.EnableAllocationDecider;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.indices.recovery.RecoverySettings;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author pancm
 * @Title: pancm_project
 * @Description: ES的集群相关配置
 * @Version:1.0.0
 * @Since:jdk1.8
 * @date 2020/1/3
 */
public class EsHighLevelCluster {


  	private static String elasticIp = "192.169.0.1";
    private static int elasticPort = 9200;

    private static Logger logger = LoggerFactory.getLogger(EsHighLevelRestTest2.class);

    private static RestHighLevelClient client = null;

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            init();
//            clusterUpdateSetting();
//            catHealth();
//            clusterGetSetting();
            clearCache();
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
     * @return void
     * @Author pancm
     * @Description 设置该节点为冷节点
     * @Date 2020/1/2
     * @Param [index]
     **/
    public static void setCool(String index) throws IOException {
        RestClient restClient = null;
        try {
            Objects.requireNonNull(index, "index is not null");
            restClient = client.getLowLevelClient();
            String source = "{\"index.routing.allocation.require.box_type\": \"%s\"}";
            source = String.format(source, "cool");
            HttpEntity entity = new NStringEntity(source, ContentType.APPLICATION_JSON);
            restClient.performRequest("PUT", "/" + index + "/_settings", Collections.<String, String>emptyMap(), entity);
        } catch (IOException e) {
            throw e;
        } finally {
            if (restClient != null) {
                restClient.close();
            }
        }
    }


    public void setAlias(String index, String aliasIndex) throws IOException {
        RestClient restClient = null;
        try {
            Objects.requireNonNull(index, "index is not null");
            restClient = client.getLowLevelClient();
            String msg = "/" + index + "/_alias" + "/" + aliasIndex;
            restClient.performRequest("PUT", msg);
        } finally {
            if (restClient != null) {
                restClient.close();
            }
        }
    }


    /**
     * @return void
     * @Author pancm
     * @Description
     * @Date 2020/1/2
     * @Param [index]
     **/
    public static void clearCache() throws IOException {

        ClearIndicesCacheRequest request = new ClearIndicesCacheRequest();
        ClearIndicesCacheResponse response = client.indices().clearCache(request,RequestOptions.DEFAULT);
        System.out.println(""+response.getTotalShards());
        System.out.println(""+response.getStatus());

    }

    /**
     * @return void
     * @Author pancm
     * @Description 设置集群的配置
     * @Date 2020/1/2
     * @Param [index]
     **/
    public static void clusterUpdateSetting() throws IOException {

        ClusterUpdateSettingsRequest request = new ClusterUpdateSettingsRequest();

        String transientSettingKey =
                RecoverySettings.INDICES_RECOVERY_MAX_BYTES_PER_SEC_SETTING.getKey();
        int transientSettingValue = 10;
        Settings transientSettings =
                Settings.builder()
                        .put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES)
                        .build();

        String persistentSettingKey =
                EnableAllocationDecider.CLUSTER_ROUTING_ALLOCATION_ENABLE_SETTING.getKey();
        String persistentSettingValue =
                EnableAllocationDecider.Allocation.NONE.name();
        Settings persistentSettings =
                Settings.builder()
                        .put(persistentSettingKey, persistentSettingValue)
                        .build();
        /** 方式一 */
        Settings.Builder transientSettingsBuilder =
                Settings.builder()
                        .put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES);
        request.transientSettings(transientSettingsBuilder);

        /** 方式二 */
        request.transientSettings(
                "{\"indices.recovery.max_bytes_per_sec\": \"10b\"}"
                , XContentType.JSON);

        /** 方式三 */
        Map<String, Object> map = new HashMap<>();
        map.put(transientSettingKey
                , transientSettingValue + ByteSizeUnit.BYTES.getSuffix());
        request.transientSettings(map);


        ClusterUpdateSettingsResponse response = client.cluster().putSettings(request, RequestOptions.DEFAULT);

        Settings setting = response.getPersistentSettings();
        Settings setting2 = response.getTransientSettings();
        logger.info("setting:{}", setting);
        logger.info("setting2:{}", setting2);
    }


    /**
     * @return void
     * @Author pancm
     * @Description 设获取集群的健康情况
     * @Date 2020/1/2
     * @Param [index]
     **/
    public static void catHealth() throws IOException {


        ClusterHealthRequest request = new ClusterHealthRequest();
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        String clusterName = response.getClusterName();
        ClusterHealthStatus status = response.getStatus();
        boolean timedOut = response.isTimedOut();
        RestStatus restStatus = response.status();

        int numberOfNodes = response.getNumberOfNodes();
        int numberOfDataNodes = response.getNumberOfDataNodes();
        int activeShards = response.getActiveShards();
        int activePrimaryShards = response.getActivePrimaryShards();
        int relocatingShards = response.getRelocatingShards();
        int initializingShards = response.getInitializingShards();
        int unassignedShards = response.getUnassignedShards();
        int delayedUnassignedShards = response.getDelayedUnassignedShards();
        double activeShardsPercent = response.getActiveShardsPercent();
        logger.info("clusterName:{},status:{},timedOut:{},restStatus:{}", clusterName, status, timedOut, restStatus.getStatus());

        List<Map<String, Object>> mapList = new ArrayList<>();
        response.getIndices().forEach((k, v) -> {
            Map<String, Object> map = new HashMap<>();
            String index = v.getIndex();
            int replicas = v.getNumberOfReplicas();
            int allShards = v.getActiveShards();
            int shards = v.getActivePrimaryShards();
            int status2 = v.getStatus().value();
            map.put("index", index);
            map.put("replicas", replicas);
            map.put("shards", shards);
            map.put("status", status2);
            System.out.println(map);
        });

    }


    /**
     * @return void
     * @Author pancm
     * @Description 设获取集群的设置情况
     * @Date 2020/1/2
     * @Param [index]
     **/
    public static void clusterGetSetting() throws IOException {

        ClusterGetSettingsRequest request = new ClusterGetSettingsRequest();
        ClusterGetSettingsResponse response = client.cluster().getSettings(request, RequestOptions.DEFAULT);
        Settings setting = response.getPersistentSettings();
        Settings setting2 = response.getTransientSettings();
        logger.info("setting:{}", setting);
        logger.info("setting2:{}", setting2);
    }








}

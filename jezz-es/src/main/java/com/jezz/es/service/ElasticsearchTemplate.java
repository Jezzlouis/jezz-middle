package com.jezz.es.service;

import com.jezz.es.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class ElasticsearchTemplate {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private IndexRequest initRequest(String indexName,String indexId,Map<String, Object> source){
        IndexRequest request = new IndexRequest(indexName)
                .id(indexId)
                .source(source)
                .opType(DocWriteRequest.OpType.CREATE);
        return request;
    }

    public ReturnT synCreateOrUpdateIndex(String indexName, String indexId, Map<String, Object> source) {
        IndexRequest request = initRequest(indexName,indexId,source);
        try {
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            String index = indexResponse.getIndex();
            String id = indexResponse.getId();
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                return ReturnT.SUCCESS;
            } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                return ReturnT.SUCCESS;
            }
            ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                return ReturnT.FAIL;
            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure :
                        shardInfo.getFailures()) {
                    String reason = failure.reason();
                    return new ReturnT(reason);
                }
            }
        } catch (IOException e) {
            log.error("create index fail",e);
        }
        return ReturnT.FAIL;
    }

    public void asyCreateOrUpdateIndex(String indexName,String indexId,Map<String, Object> source){
        IndexRequest request = initRequest(indexName,indexId,source);

        restHighLevelClient.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {


            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public String synGet(String indexName,String indexId,String field){
        GetRequest request = new GetRequest(indexName,indexId);
        request.storedFields(field);
        GetResponse getResponse = null;
        String message = "";
        try {
            getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            message = getResponse.getField(field).getValue();
            String index = getResponse.getIndex();
            String id = getResponse.getId();
            if (getResponse.isExists()) {
                long version = getResponse.getVersion();
                String sourceAsString = getResponse.getSourceAsString();
                Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                byte[] sourceAsBytes = getResponse.getSourceAsBytes();
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  message;
    }

    public void asyGet(String indexName,String indexId,String field){
        GetRequest request = new GetRequest(indexName,indexId);
        request.storedFields(field);
        restHighLevelClient.getAsync(request, RequestOptions.DEFAULT, new ActionListener<GetResponse>() {
            @Override
            public void onResponse(GetResponse documentFields) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void synExists(String indexName,String indexId){
        GetRequest request = new GetRequest(indexName,indexId);
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        try {
            boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asyExists(String indexName,String indexId){
        GetRequest request = new GetRequest(indexName,indexId);
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        restHighLevelClient.existsAsync(request, RequestOptions.DEFAULT, new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean aBoolean) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void synDelete(String indexName,String indexId){
        DeleteRequest request = new DeleteRequest(indexName,indexId);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(
                    request, RequestOptions.DEFAULT);
            if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {

            }
            String index = deleteResponse.getIndex();
            String id = deleteResponse.getId();
            long version = deleteResponse.getVersion();
            ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo();
            if (shardInfo.getTotal() != shardInfo.getSuccessful()) {

            }
            if (shardInfo.getFailed() > 0) {
                for (ReplicationResponse.ShardInfo.Failure failure :
                        shardInfo.getFailures()) {
                    String reason = failure.reason();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void asyDelete(String indexName,String indexId){
        DeleteRequest request = new DeleteRequest(indexName,indexId);
        restHighLevelClient.deleteAsync(request, RequestOptions.DEFAULT, new ActionListener<DeleteResponse>() {
            @Override
            public void onResponse(DeleteResponse deleteResponse) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}

import com.example.ESApplication;


import java.io.IOException;
import java.util.ArrayList;

import com.example.dto.TUser;
import com.example.util.JsonUtil;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


import java.io.IOException;

@SpringBootTest(classes = ESApplication.class)
public class ESTest {

    private static final String index_name = "user_info";


    @Autowired
    private RestHighLevelClient restHighLevelClient;

//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 创建一个索引库
     *
     * @throws IOException
     */
    @Test
    void createIndex() throws IOException {

   CreateIndexRequest createIndexRequest = new CreateIndexRequest(index_name);
    CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    //打印创建结果
    System.out.println(createIndexResponse.isAcknowledged());
    }

    /**
     * 判断索引库是否存在
     */
    @Test
    void searchIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(index_name);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 删除索引库
     */
    @Test
    void removeIndex() throws IOException {

        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index_name);
        AcknowledgedResponse response = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        //打印删除结果
        System.out.println(response.isAcknowledged());
    }

    /**
     * 创建文档
     */
    @Test
    void createDoc() throws IOException {
        TUser user = new TUser(1, "jason", "123456");
        IndexRequest indexRequest = new IndexRequest(index_name);
        indexRequest.id(String.valueOf(user.getId()));
        IndexRequest request = indexRequest.source(JsonUtil.toJson(user), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 查询文档
     */
    @Test
    void queryDoc() throws IOException {
        GetRequest getRequest = new GetRequest(index_name, "1");
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(response.getSource());
    }

    /**
     * 修改文档
     */
    @Test
    void updateDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index_name, "1");
        TUser user = new TUser(1, "majian", "654321");
        updateRequest.doc(JsonUtil.toJson(user), XContentType.JSON);
        updateRequest.timeout("1s");
        UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 删除指定文档
     */
    @Test
    void removeDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index_name, "1");
        deleteRequest.timeout("1s");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 批量插入文档
     */
    @Test
    void bulkCreateDoc() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        ArrayList<TUser> userList = new ArrayList<>();
        for (int i = 1; i <=100 ; i++) {
            userList.add(new TUser(i, "甜甜" + i, "123456-" + i));
        }
        for (int i = 0; i < userList.size(); i++) {
            bulkRequest.add(new IndexRequest(index_name)
                    .id(String.valueOf(i))
                    .source(JsonUtil.toJson(userList.get(i)), XContentType.JSON));
        }
        BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    /**
     * 批量查询文档
     */
    @Test
    void queryDocList() throws IOException {
        SearchRequest searchRequest = new SearchRequest(index_name);
        //2.创建 SearchSourceBuilder条件构造。
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //条件创造器
    //指定条件查询
        //1.指定需要返回或者排除的字段
        String[] includes = {
                "id", "name"};
        String[] excludes = {
                "pass"
        };
        searchSourceBuilder.fetchSource(includes, excludes);  //select id,name from t_user


        //2.查询id为25的index_info索引库中的数据
//    TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("id", 25);  //select id,name from t_user where id=25
//    searchSourceBuilder.query(termQueryBuilder);


        //设置分页---注意如果要用条件检查，请对于from size要设定刚好在此区间才能查出来
        //from 关键字：用来指定起始返回位置，和size关键字连用可实现分页效果
        searchSourceBuilder.from(20);
        //size 关键字：指定查询结果中返回指定条数。 默认返回值10条。
        searchSourceBuilder.size(10);

        //3.将 SearchSourceBuilder 添加到 SearchRequest中
        searchRequest.source(searchSourceBuilder);
        //查询索引库中所有的文档数据
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("花费的时长：" + response.getTook());

        SearchHits hits = response.getHits();
        System.out.println("总命中数:" + hits.getTotalHits());
        System.out.println("符合条件的总文档数量：" + hits.getTotalHits().value);
        hits.forEach(p -> System.out.println("文档原生信息：" + p.getSourceAsString()));

    }

}

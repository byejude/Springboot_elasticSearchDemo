package com.tulip.springbootelasticsearchdemo.dao.Impl;

import com.tulip.springbootelasticsearchdemo.dao.PersonDao;
import com.tulip.springbootelasticsearchdemo.domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
@Component
public class PersonDaoImpl implements PersonDao {

    @Autowired
    private TransportClient transportClient;

    private String index = "test";

    private String type = "person";

    private Logger log = Logger.getLogger(getClass());

    @Override
    public String save(Person person) {
        try{
            XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
            contentBuilder.field("name",person.getName());
            contentBuilder.field("age",person.getAge());
            contentBuilder.field("sex",person.getSex());
            contentBuilder.field("birthday",person.getBirthday());
            contentBuilder.field("introduce",person.getIntroduce());
            contentBuilder.endObject();
            IndexResponse response = transportClient.prepareIndex().
                    setSource(contentBuilder).get();
            return response.getId();
        }catch (IOException e){
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public String update(Person person) {
        UpdateRequest request = new UpdateRequest(index,type,person.getId());
        try{
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            if(person.getName() != null){
                builder.field("name",person.getName());
            }
            if(person.getSex() != null){
                builder.field("sex",person.getSex());
            }
            if(person.getIntroduce() != null){
                builder.field("introduce",person.getIntroduce());
            }
            if(person.getBirthday() != null){
                builder.field("birthday",person.getBirthday());
            }
            if(person.getAge() > 0){
                builder.field("age",person.getAge());
            }

            builder.endObject();
            request.doc(builder);
            UpdateResponse response = transportClient.update(request).get();
            return response.getId();
        }catch (IOException | InterruptedException | ExecutionException e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    @Override
    public String delete(String id) {
        DeleteResponse response = transportClient.prepareDelete(index,type,id).get();
        return response.getId();
    }

    @Override
    public Object find(String id) {
        GetResponse response = transportClient.prepareGet(index,type,id).get();
        System.out.print("result"+response);
        Map<String,Object> result = response.getSource();
        if(result != null){
            result.put("_id",response.getId());
        }
        return result;
    }

    @Override
    public Object query(Person person) {
        List<Map<String,Object>> result = new ArrayList<>();
        try {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            if(person.getName()!=null){
                boolQueryBuilder.should(QueryBuilders.matchQuery("name",person.getName()));
            }

            if(person.getIntroduce() != null){
                boolQueryBuilder.should(QueryBuilders.matchQuery("introduce",person.getIntroduce()));
            }

            if(person.getAge() > 0){
                RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
                rangeQueryBuilder.from(person.getAge());
                rangeQueryBuilder.to(person.getAge()+10);
                boolQueryBuilder.filter(rangeQueryBuilder);
            }

            SearchRequestBuilder builder = transportClient.prepareSearch(index)
                    .setTypes(type)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setQuery(boolQueryBuilder)
                    .setFrom(0)
                    .setSize(10);
            log.info("builder is"+String.valueOf(builder));
            SearchResponse response = builder.get();
            response.getHits().forEach((s)->result.add(s.getSource()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

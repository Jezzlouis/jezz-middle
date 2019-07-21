package com.jezz.es;

import com.jezz.es.service.ElasticsearchTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
public class EsApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void contextLoads() {
        //elasticsearchTemplate.synCreateOrUpdateIndex("zengzhitest","1",new HashMap<>());
        elasticsearchTemplate.synGet("zengzhitest","1","");
    }

}

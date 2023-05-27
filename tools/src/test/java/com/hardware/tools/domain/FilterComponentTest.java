package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.exceptions.GraphQLToolsException;
import com.hardware.tools.domain.inputs.FilterInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
public class FilterComponentTest {


    @Autowired
    FilterComponent filterComponent;


    @Test
    void testFilter() {


        // Try to run this code snippet as a Java application.
        ArrayList<FilterInput> filters = new ArrayList<>();
        filters.add(new FilterInput("name", new ArrayList<>(Arrays.asList("hammer", "pipe", "set"))));
        filters.add(new FilterInput("brand_id", new ArrayList<>(Arrays.asList("644f1f7f9bff546e8d6651bd\"]}, name;:{ $in: [/(?i)hammer/,/(?i)pipe/,'a"))));
//        filters.add(new FilterInput("price", new ArrayList<>(Arrays.asList("10", "20"))));

        Query query1 = new Query();
        query1.addCriteria(new Criteria(filters.get(0).field).in(List.of(Pattern.compile("(?i)hammer"), Pattern.compile("(?i)pipe"))));
//        query1.addCriteria(new Criteria(filters.get(1).field).in(filters.get(1).values));
        System.out.println(query1);
        try {
            System.out.println(filterComponent.filter(filters, Tool.class));
        }catch (GraphQLToolsException e){
            System.out.println(e.getMessage());
        }

    }
}

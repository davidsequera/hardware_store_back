package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.exceptions.GraphQLToolsException;
import com.hardware.tools.domain.inputs.FilterInput;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.util.Pair;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class FilterComponent {
    final HashMap<String, Pair<String,String> > typeMap = new HashMap<>();

    private final static String STRING = String.class.getName();
    private final static String INTEGER = int.class.getName();
    private final static String DOUBLE = double.class.getName();
    private final static String BOOLEAN = boolean.class.getName();
    private final static String OBJECT_ID = ObjectId.class.getName();

    final String OPEN  = "{";
    final String CLOSE  = "}";
    final String OPERATOR = "$in";

    final Pair<String, String> REGEX_PAIR = Pair.of("{ \"$regularExpression\" : { \"pattern\" : \"", "\", \"options\" : \"i\"}}");
    final Pair<String, String> OBJECT_ID_PAIR = Pair.of("'", "'");
    final Pair<String, String> NUMBER_PAIR = Pair.of("", "");


    public FilterComponent() {
        typeMap.put(STRING, REGEX_PAIR);
        typeMap.put(INTEGER, NUMBER_PAIR);
        typeMap.put(DOUBLE, NUMBER_PAIR);
        typeMap.put(BOOLEAN, NUMBER_PAIR);
        typeMap.put(OBJECT_ID, OBJECT_ID_PAIR);
    }

    public String filter(List<FilterInput> filters, Class<?> clazz) throws GraphQLToolsException{
        StringBuilder query = new StringBuilder(OPEN);
        for (FilterInput filter : filters) {
            checkFields(filter, clazz);
        }

        for (FilterInput filter : filters) {
            query.append(query(filter, clazz));
            query.append(",");
        }
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append(CLOSE);
//        List<String> fields =Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).toList();
//        for (String field : fields) {
//            System.out.println(field+":"+getMongoType(clazz, field).getName());
//        }
        return query.toString();
    }


    private void checkFields(FilterInput filterInput, Class<?> clazz) throws GraphQLToolsException{
        if( filterInput.field == null ||  Arrays.stream(clazz.getDeclaredFields()).map(Field::getName).noneMatch(filterInput.field::equals)){
            throw new GraphQLToolsException("Invalid filter field: " + filterInput.field);
        }
        for(String value : filterInput.values){
            if(value == null || value.isEmpty()){
                throw new GraphQLToolsException("Invalid filter value: " + value);
            }
        }
    }


    private String query(FilterInput filterInput, Class<?> clazz){
        StringBuilder query = new StringBuilder();
        String type = getMongoType(clazz, filterInput.field).getName();
        Pair<String, String> pair = typeMap.get(type) == null ? REGEX_PAIR : typeMap.get(type);


        query.append(filterInput.field).append(": { ").append(OPERATOR).append(": [");
        //
        for (var value : filterInput.values) {
            query.append(pair.getFirst()).append(sanatize(value)).append(pair.getSecond()).append(",");
        }
        //
        query = new StringBuilder(query.substring(0, query.length() - 1));
        query.append("]}");
        return query.toString();
    }

    private Class<?> getMongoType(Class<?> clazz, String fieldName) throws NullPointerException {
        MongoMappingContext mappingContext = new MongoMappingContext();
        MongoPersistentEntity<?> entity = mappingContext.getPersistentEntity(clazz);
        MongoPersistentProperty property = entity.getPersistentProperty(fieldName);
        return property.getFieldType();
    }

    private Class<?> getJavaType(Class<?> clazz, String fieldName) {
        MongoMappingContext mappingContext = new MongoMappingContext();
        TypeInformation<?> typeInformation = mappingContext.getPersistentEntity(clazz)
                .getTypeInformation().getProperty(fieldName);
        return typeInformation.getType();
    }

    private String sanatize(String value){
        String a = "\'";
        return value.replaceAll(a, "\\\\\'");
    }
}

package com.ntd.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json工具类
 */
public class JSONUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final JsonFactory jsonFactory = new JsonFactory();

    private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

    static {
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static class DateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
        }
    }

    public static class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

    public static List<Object> fromJsonList(String jsonAsString, Class<?> generic) {
        try {
            new DateDeserializer();
            GsonBuilder gb = new GsonBuilder();
            gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
            Gson gson = gb.create();
            // 创建一个JsonParser
            JsonParser parser = new JsonParser();

            List<Object> olist = new ArrayList<Object>();
            // 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
            JsonElement el = parser.parse(jsonAsString);
            JsonArray jsonArray = null;
            if (el.isJsonArray()) {
                jsonArray = el.getAsJsonArray();
                Object field = generic.newInstance();
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    JsonElement e = (JsonElement) it.next();
                    // JsonElement转换为JavaBean对象
                    field = gson.fromJson(e, generic);
                    olist.add(field);
                }
                return olist;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fromJson(String jsonAsString, Class<T> pojoClass) {
        try {
            return  (T)mapper.readValue(jsonAsString, pojoClass);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T fromJson(String jsonAsString, TypeReference<T> typeRef){
        try {
            return (T)mapper.readValue(jsonAsString, typeRef);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T parseResponse(String jsonAsString, TypeReference<T> typeRef){
        try {
            JsonNode node=mapper.readTree(jsonAsString);
            int code=node.path("success").getValueAsInt();
            if(code==200){
                return (T)mapper.readValue(node.path("data"), typeRef);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String toJson(Object pojo){
        return toJson(pojo,false);
    }

    public static String toJson(Object pojo, boolean prettyPrint) {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator jg = jsonFactory.createJsonGenerator(sw);
            if(prettyPrint) {
                jg.useDefaultPrettyPrinter();
            }
            mapper.writeValue(jg, pojo);
            return sw.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static void writeJson(Object pojo,Writer writer){
        try {
            JsonGenerator jg = jsonFactory.createJsonGenerator(writer);
            mapper.writeValue(jg, pojo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        //http://blog.csdn.net/nomousewch/article/details/8955796
    }

}

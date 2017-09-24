package com.ntd.aop.annotation.db;

import org.springframework.util.Assert;

public class MyContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    private static final ThreadLocal<Object> datasourceKeyHolder = new ThreadLocal<Object>();

    public static void setCustomerType(String myDbType) {
        Assert.notNull(myDbType, "myDbType cannot be null");
        contextHolder.set(myDbType);
    }

    public static String getCustomerType() {
        return (String) contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }
    
    public static void setDatasourceKey(Object key){
    	Assert.notNull(key, "datasource key cannot be null");
    	datasourceKeyHolder.set(key);
    }
    
    public static Object getDatasourceKey(){
    	return datasourceKeyHolder.get();
    }
    
    public static void clearDatasourceKey(){
    	datasourceKeyHolder.remove();
    }
}

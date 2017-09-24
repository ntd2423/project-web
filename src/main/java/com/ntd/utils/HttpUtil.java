package com.ntd.utils;

import com.ntd.common.Const.ErrorCode;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http工具类
 */
public class HttpUtil {

    private static final int MAX_TOTAL_CONNECTIONS = 500;
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int CONNECTION_MANAGER_TIMEOUT = 2000;
    private static final int HTTPS_PROXY_PORT = 443;

    private static CloseableHttpClient client;
    private static RequestConfig requestConfig;
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static ExecutorService executorService;

    static {
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setCharset(Consts.UTF_8).build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_MANAGER_TIMEOUT)
                .build();
        client = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig).build();
        executorService = new ThreadPoolExecutor(0, 2, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    //海外请求走专线代理
    public static String getUrlByProxy(String url) {
        url = checkUrlForProxy(url);
        return getUrl(url);
    }

    private static String getUrl(String url) {
        HttpGet method = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(method);
            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity());
                }
                logger.warn("http get:" + response.getStatusLine());
            } finally {
                response.close();
            }
        } catch (IOException e) {
            logger.warn("HTTP GET请求发生异常:" + e.getMessage(), e);
        } finally {
            method.releaseConnection();
        }
        return ErrorCode.SERVER_ERROR + "";
    }

    //海外请求走专线代理
    public static String postUrlByProxy(String url, NameValuePair... params) {
        url = checkUrlForProxy(url);
        return postUrl(url, params);
    }

    public static String postUrl(String url, NameValuePair... params) {
        return postUrl(url, null, params);
    }

    public static String postUrl(String url, Header header, NameValuePair... params) {
        HttpPost method = new HttpPost(url);
        try {
            if (params != null) {
                method.setEntity(new UrlEncodedFormEntity(
                        Arrays.asList(params), Consts.UTF_8));
            }
            if(header != null){
                method.addHeader(header);
            }
            CloseableHttpResponse response = client.execute(method);
            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity());
                }
            } finally {
                response.close();
            }
            logger.warn("http post:" + response.getStatusLine());
        } catch (IOException e) {
            logger.warn("HTTP POST请求发生异常:" + e.getMessage(), e);
        } finally {
            method.releaseConnection();
        }
        return ErrorCode.SERVER_ERROR + "";
    }

    public static Future<String> postUrlAsyn(final String url, final NameValuePair... params) {
        return executorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                return postUrl(url, params);
            }
        });
    }

    public static String postXML(String url, String body) {
        HttpPost method = new HttpPost(url);
        try {
            method.addHeader("Content-Type", "text/xml");
            method.setEntity(new StringEntity(body, Consts.UTF_8));
            CloseableHttpResponse response = client.execute(method);
            try {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    return EntityUtils.toString(response.getEntity());
                }
            } finally {
                response.close();
            }
            logger.warn("http post:" + response.getStatusLine());
        } catch (IOException e) {
            logger.warn("HTTP POST请求发生异常:" + e.getMessage(), e);
        } finally {
            method.releaseConnection();
        }
        return ErrorCode.SERVER_ERROR + "";
    }

    public static String post(String url, String body, String invoker, String sign) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("MGLIVE-CHAT-OUTSIDE-INVOKER", invoker);
        httppost.addHeader("MGLIVE-CHAT-OUTSIDE-SIG", sign);
        httppost.addHeader("Content-Type", "application/json");

        try {
            HttpEntity entity1 = new StringEntity(body, "UTF-8");
            httppost.setEntity(entity1);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ErrorCode.SERVER_ERROR + "";
    }

    private static String checkUrlForProxy(String url) {
        if (url != null && url.startsWith("https://")) {
            int paramIndex = url.indexOf('/', 10);
            if (paramIndex > 0) {
                url = "http" + url.substring(5, paramIndex) + ":" + HTTPS_PROXY_PORT + url.substring(paramIndex);
            } else {
                url = "http" + url.substring(5) + ":" + HTTPS_PROXY_PORT;
            }
        }
        return url;
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        String res = postUrl(
                "http://114.113.202.254:8280/broadcast/cCurrency.do",
                new BasicNameValuePair("userId", "1980626999114966373"));
        Future<String> future = postUrlAsyn("http://114.113.202.254:8280/broadcast/cCurrency.do",
                new BasicNameValuePair("userId", "4"));
        System.out.println(res);
        System.out.println(future.get());
    }

}

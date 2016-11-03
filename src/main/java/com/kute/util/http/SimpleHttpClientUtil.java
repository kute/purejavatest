package com.kute.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

/**
 * @author longbai
 */
public class SimpleHttpClientUtil {

    private static Log log = LogFactory.getLog(SimpleHttpClientUtil.class);

    /**
     * Send get to URL.
     * 
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        HttpClient client = new HttpClient();
        HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒)
        managerParams.setConnectionTimeout(5000);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(5000);
        GetMethod getmethod = null;
        String response = null;
        try {
            getmethod = new GetMethod(url);
            client.executeMethod(getmethod);
            response = getmethod.getResponseBodyAsString();
        }catch(Throwable e){
        }finally {
            if (null != getmethod) {
                getmethod.releaseConnection();
            }
        }
        return response;
    }
    
    public static String sendPut(String url) {
        HttpClient client = new HttpClient();
        HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
        // 设置连接超时时间(单位毫秒)
        managerParams.setConnectionTimeout(700);
        // 设置读数据超时时间(单位毫秒)
        managerParams.setSoTimeout(2000);
        PutMethod putMethod = null;
        String response = null;
        try {
            putMethod = new PutMethod(url);
            client.executeMethod(putMethod);
            response = putMethod.getResponseBodyAsString();
        }catch(Throwable e){
        }finally {
            if (null != putMethod) {
                putMethod.releaseConnection();
            }
        }
        return response;
    }
    
    public static String sendPut(String url, Map<String, String> parameterMap,
            String encoding) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        if (parameterMap != null && !parameterMap.isEmpty()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Iterator<Map.Entry<String, String>> it = parameterMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, String> entry = it.next();
                params.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));
            }
            try {
                if (encoding == null) {
                    httpPut.setEntity(new UrlEncodedFormEntity(params));
                } else {
                    httpPut.setEntity(new UrlEncodedFormEntity(params,
                            encoding));
                }
            } catch (UnsupportedEncodingException e) {
                log.error("Encode the parameter failed!", e);
            }
        }

        String content = null;
        try {
            content = httpClient.execute(httpPut, new BasicResponseHandler());
        } catch (IOException e) {
            log.error("put url faild, url: " + url, e);
            content = null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return content;
    }
    
    public static String sendPutJson(String url, JSONObject paramJsonObj) {
        return sendPutJson(url, paramJsonObj, "utf-8");
    }
    
    public static String sendPutJson(String url, JSONObject paramJsonObj,
            String encoding) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);
        httpPut.setHeader("Content-Type", "application/json");
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        
        if(null != paramJsonObj && paramJsonObj.size() > 0) {
            try {
                if(StringUtils.isNotEmpty(encoding)) {
                    httpPut.setEntity(new StringEntity(paramJsonObj.toString(), encoding));
                } else {
                    httpPut.setEntity(new StringEntity(paramJsonObj.toString()));
                }
            } catch (Exception e) {
                log.error("http post set params error", e);
            }
        }
        String content = null;
        try {
            content = httpClient.execute(httpPut, new BasicResponseHandler());
        } catch (IOException e) {
            log.error("Put json url faild, url: " + url, e);
            content = null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return content;
    }
    
    public static String sendDelete(String url) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        String content = null;
        try {
            content = httpClient.execute(httpDelete, new BasicResponseHandler());
        } catch (IOException e) {
            log.error("Delete url faild, url: " + url, e);
            content = null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return content;
    }
    
    /**
     * Send post to URL.
     * 
     * @param url
     * @return
     */
    public static String sendPost(String url) {
        return sendPost(url, null, "utf-8");
    }

    /**
     * Send post to URL with parameters.
     * 
     * @param url
     * @param parameterMap
     * @return
     */
    public static String sendPost(String url, Map<String, Object> parameterMap) {
        return sendPost(url, parameterMap, "utf-8");
    }

    /**
     * Send post to URL with parameters by given encoding.
     * 
     * @param url
     * @param parameterMap
     * @param encoding
     * @return
     */
    public static String sendPost(String url, Map<String, Object> parameterMap,
            String encoding) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, encoding);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 8000);
        
        HttpPost httpPost = new HttpPost(url);
        httpPost.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);
        httpPost.getParams().setParameter("http.protocol.content-charset",encoding); 
        if (parameterMap != null && !parameterMap.isEmpty()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Iterator<Map.Entry<String, Object>> it = parameterMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = it.next();
                params.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry
                        .getValue())));
            }
            try {
                if (encoding == null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                } else {
                    httpPost.setEntity(new UrlEncodedFormEntity(params,
                            encoding));
                }
            } catch (UnsupportedEncodingException e) {
                log.error("Encode the parameter failed!", e);
            }
        }

        String content = null;
        try {
            content = httpClient.execute(httpPost, new BasicResponseHandler());
        } catch (IOException e) {
            log.error("Post url faild, url: " + url, e);
            content = null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return content;
    }
    
    public static String sendPostJson(String url, JSONObject paramJsonObj) {
        return sendPostJson(url, paramJsonObj, "utf-8");
    }
    
    public static String sendPostJson(String url, JSONObject paramJsonObj,
            String encoding) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);
        httpPost.setHeader("Content-Type", "application/json");
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        
        if(null != paramJsonObj && paramJsonObj.size() > 0) {
            try {
                if(StringUtils.isNotEmpty(encoding)) {
                    httpPost.setEntity(new StringEntity(paramJsonObj.toString(), encoding));
                } else {
                    httpPost.setEntity(new StringEntity(paramJsonObj.toString()));
                }
            } catch (Exception e) {
                log.error("http post set params error", e);
            }
        }
        String content = null;
        try {
            content = httpClient.execute(httpPost, new BasicResponseHandler());
        } catch (IOException e) {
            log.error("Post json url faild, url: " + url, e);
            content = null;
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return content;
    }
    
    public static void sendPostWithNoResponse(String url) {
        HttpClient httpClient = new HttpClient();
        HttpMethod httpPost = new PostMethod(url);
        httpPost.getParams().setBooleanParameter(
                "http.protocol.expect-continue", false);
        try {
            log.info("Post WithNoResponse begin post");
            httpClient.executeMethod(httpPost);
            log.info("Post WithNoResponse end post");
        } catch (IOException e) {
            log.error("Post WithNoResponse url faild, url: " + url, e);
        } finally {
            httpPost.releaseConnection();
        }
    }
    
    public static String sendPostCurl(String cmd) {
        StringBuffer buffer = new StringBuffer();
        try {
            String[] cmdArray = { "/bin/sh", "-c", cmd };
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmdArray);
          //  InputStreamReader reader = new InputStreamReader(process.getInputStream());
            
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
           // LineNumberReader line = new LineNumberReader(reader);
            String str = null;
            while(StringUtils.isNotEmpty((str=in.readLine()))){
                buffer.append(str);
            }
        } catch (Exception e) {
            log.error("sendpost curl url error:" + cmd, e);
        }
        return buffer.toString();
    }

}

package com.kute.util.http;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.nio.charset.CodingErrorAction;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTPS
 * @author bl
 *
 */
public class HttpsClientUtil {

  
	private static final Logger logger = LoggerFactory.getLogger(HttpsClientUtil.class);
	
	private static PoolingHttpClientConnectionManager connManager = null;
	private static CloseableHttpClient httpClient = null;
	private static final int SOCKET_TIMEOUT = 10000;//read timeout
	private static final int CONNECT_TIMEOUT = 5000;//connect timeout
	private static final int CONNECTION_REQUEST_TIMEOUT = 5000;//fetch connection from connect-pool timeout
	private static final int MAX_CONNECTION = 2000;
	private static final int MAX_PER_ROUTE = 1000;//current only one route,so let max_connection=max_per_route
	
	static {
		try {
			SSLContext sslContext = SSLContexts.custom().useTLS().build();
			sslContext.init(null, new TrustManager[]{
					new X509TrustManager() {
						@Override
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						
						@Override
						public void checkServerTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
						}
						
						@Override
						public void checkClientTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
						}
					}
			}, null);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
                    .build();
			connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            httpClient = HttpClients.custom().setConnectionManager(connManager).build();
            // Create socket configuration
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            connManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(200)
                .setMaxLineLength(2000)
                .build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints)
                .build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(MAX_CONNECTION);
            connManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String httpGet(String url, String encoding) {
		String responseString = null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).build();
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if(entity != null){
                        responseString = EntityUtils.toString(entity, encoding);
                    }
                } finally {
                    if(entity != null){
                        entity.getContent().close();
                    }
                }
            } catch (Exception e) {
                logger.error(String.format("[HttpsClientUtil httpGet]get response error, url:%s", url), e);
                return responseString;
            } finally {
                if(response != null){
                    response.close();
                }
            }
        } catch (SocketTimeoutException e) {
            logger.error(String.format("[HttpsClientUtil httpGet]invoke get timout error, url:%s", url), e);
            return responseString;
        } catch (Exception e) {
            logger.error(String.format("[HttpsClientUtil httpGet]invoke get error, url:%s", url), e);
        } finally {
        	if(get != null)
        		get.releaseConnection();
        }
        return responseString;
	}
	
	public static String httpPost(String url, String encoding, Map<String, String> paramsMap) {
        String responseString = null;
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        
        if(null != paramsMap && !paramsMap.isEmpty()) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Iterator<Map.Entry<String, String>> it = paramsMap
                    .entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, String> entry = it.next();
                params.add(new BasicNameValuePair(entry.getKey(), entry
                        .getValue()));
            }
            logger.info(params.isEmpty() + "");
            try {
                if (encoding == null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                } else {
                    httpPost.setEntity(new UrlEncodedFormEntity(params,
                            encoding));
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("Encode the parameter failed!", e);
            }
        }
        
        
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                logger.info(response.toString());
                HttpEntity entity = response.getEntity();
                try {
                    if(entity != null){
                        responseString = EntityUtils.toString(entity, encoding);
                    }
                } finally {
                    if(entity != null){
                        entity.getContent().close();
                    }
                }
            } catch (Exception e) {
                logger.error(String.format("[HttpsClientUtil httpGet]get response error, url:%s", url), e);
                System.out.println(e);
                return responseString;
            } finally {
                if(response != null){
                    response.close();
                }
            }
        } catch (SocketTimeoutException e) {
            logger.error(String.format("[HttpsClientUtil httpGet]invoke get timout error, url:%s", url), e);
            System.out.println(e);
            return responseString;
        } catch (Exception e) {
            logger.error(String.format("[HttpsClientUtil httpGet]invoke get error, url:%s", url), e);
            System.out.println(e);
        } finally {
            if(httpPost != null)
                httpPost.releaseConnection();
        }
        return responseString;
    }
	
	
}

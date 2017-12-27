package com.kute.util.http.okhttp;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by longbai on 2017/12/22.
 */
public class OKHttpClientUtil {
    
    private static final OkHttpClient CLIENT = new OkHttpClient();
    
    static {
        
    }
    
    public String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = CLIENT.newCall(request).execute();
        if(response.isSuccessful()) {
            return response.body().toString();
        }
        return null;
    }
    
    public void get(String url, Callback callback) throws IOException {
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(callback);
    }
    
    public void get(HttpUrl url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
            }
            
            @Override
            public void onFailure(Call arg0, IOException arg1) {
            }
        });
    }
    
}

package com.kute.util.http.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by longbai on 2017年12月25日下午6:52:42
 *
 **/
public class MockTest {
    
    public static void main(String[] args) {
        Callback callback = new Callback() {
            
            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onFailure(Call arg0, IOException arg1) {
                // TODO Auto-generated method stub
                
            }
        };
    }

}

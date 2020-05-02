package com.csj.util;

import com.alibaba.fastjson.JSON;
import com.csj.domain.AccessToken;
import com.csj.domain.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class OkHttp {
    /**
     * 利用okhttp3发送post请求得到token码
     */
    public static String getAccessToken(AccessToken accessToken){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client;
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        Call call=null;
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        Response response = null;
        try{
            call = client.newCall(request);
            response = call.execute();
            String result = response.body().string();
            //打印结果
            //System.out.println(result);
            //截取access_token
            String filedOne = result.split("&")[0];
            String token = filedOne.split("=")[1];
            return token;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }finally {
            call.cancel();
            if(response!=null){
                response.close();
            }
        }
    }

    /**
     * 发送post请求获得github的user
     * @return
     */
    public static GithubUser getUser(String accessToken){
        OkHttpClient client;
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Call call=null;
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        Response response = null;
        try{
            call = client.newCall(request);
            response = call.execute();
            String result = response.body().string();
            System.out.println(result);
            //System.out.println(result);
            GithubUser githubUser = JSON.parseObject(result, GithubUser.class);
            return githubUser;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            call.cancel();
            if(response!=null){
                response.close();
            }
        }
    }

}

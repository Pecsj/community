package com.csj.util;

import com.alibaba.fastjson.JSON;
import com.csj.domain.AccessToken;
import com.csj.domain.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OkHttp {
    /**
     * 利用okhttp3发送post请求得到token码
     */
    public static String getAccessToken(AccessToken accessToken){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        //如果try出现异常，（）里连接会自动释放
        Response response = null;
        try{
            response = client.newCall(request).execute();
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
            response.close();
        }
    }

    /**
     * 发送post请求获得github的user
     * @return
     */
    public static GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            String result = response.body().string();
            //System.out.println(result);
            GithubUser githubUser = JSON.parseObject(result, GithubUser.class);
            githubUser.setToken();//使用uuid随机设置唯一token值，先写着
            return githubUser;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            response.close();
        }
    }

}

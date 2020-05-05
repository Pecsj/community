package com.csj.service.impl;

import com.baidu.aip.face.AipFace;
import com.csj.service.IBaidu;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Baidu implements IBaidu {
    //设置APPID/AK/SK
    public static final String APP_ID = "19421391";
    public static final String API_KEY = "mxLDBcbfdHFfdFVZ6ZLyQ6L0";
    public static final String SECRET_KEY = "oDmgib1UiRYS5oe1mxXwvVDVdGZXjpkR";

    private AipFace aipFace = new AipFace(APP_ID,API_KEY,SECRET_KEY);

    @Override
    public String addFace(int id,String name, String baseImg) {
        System.out.println(APP_ID);
        System.out.println(API_KEY);
        System.out.println(SECRET_KEY);
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", name);//用户额外信息
        options.put("quality_control", "NORMAL");//照片质量控制
        options.put("liveness_control", "LOW");//活体检测
        options.put("action_type", "APPEND");//追加添加人脸

        //图片编码设置
        String image = baseImg;
        String imageType = "BASE64";
        String groupId = "groupUser";
        String userId = String.valueOf(id);

        // 人脸注册
        JSONObject res = aipFace.addUser(image, imageType, groupId, userId, options);
        System.out.println(res.toString(2));

        return null;
    }

    @Override
    public int faceSearch(String image) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "LOW");//图片质量控制
        //options.put("liveness_control", "LOW");//活体检测，默认关闭
        //options.put("user_id", "1");//对指定用户对比，即人脸认证（不指定则是1:N人脸搜索）
        options.put("max_user_num", "1");//查找后返回用户数量

        String imageType = "BASE64";
        String groupIdList = "groupUser";//从指定的人脸库列表中查询，用逗号隔开

        // 人脸搜索
        JSONObject res = aipFace.search(image, imageType, groupIdList, options);
        System.out.println(res.toString(2));
        int score=-1;
        String userId="";
        //从返回的json数据中获取id
        if(res.has("result")){
            JSONObject result = res.getJSONObject("result");
            System.out.println(result);
            if(result.has("user_list")){
                JSONArray userList = result.getJSONArray("user_list");
                JSONObject user = userList.getJSONObject(0);
                score = user.getInt("score");
                userId = user.getString("user_id");
            }
        }
        //当匹配值达到90分时成功
        if (score>90){
            return Integer.parseInt(userId);
        }else{
            return -1;
        }
    }
}

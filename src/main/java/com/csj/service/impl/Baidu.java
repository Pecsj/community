package com.csj.service.impl;

import com.baidu.aip.face.AipFace;
import com.csj.service.IBaidu;
import com.csj.util.Base64Util;
import com.csj.util.FileUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
}

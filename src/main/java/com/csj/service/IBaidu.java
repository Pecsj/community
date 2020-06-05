package com.csj.service;

import org.json.JSONObject;

public interface IBaidu {
    /**
     * 向百度人脸库添加用户
     * @param id
     * @param baseImg
     * @return
     */
    JSONObject addFace(int id, String name, String baseImg);

    /**
     * 向人脸库中匹配用户并返回id
     * @param image
     * @return
     */
    int faceSearch(String image);

}

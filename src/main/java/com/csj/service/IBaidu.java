package com.csj.service;

public interface IBaidu {
    /**
     * 向百度人脸库添加用户
     * @param id
     * @param baseImg
     * @return
     */
    String addFace(int id,String name,String baseImg);

}

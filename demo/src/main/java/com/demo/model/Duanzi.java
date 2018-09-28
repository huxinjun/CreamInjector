package com.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * Created by xinjun on 2018/9/28 15:53
 */
public class Duanzi {

    public int code;
    public String msg;

    public List<Data> data;

    @Override
    public String toString() {
        return "Duanzi{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {

        public enum DuanziType{
            TEXT,
            IMAGE,
            VIDEO
        }

        public String type;//10图片 41视频 29文字
        public String text;
        public String name;
        public String profile_image;
        public String cdn_img;
        public String width;
        public String height;


        public String bimageuri;
        public String videouri;


        public DuanziType getType(){
            switch (type){
                case "10":
                    return DuanziType.IMAGE;
                case "29":
                    return DuanziType.TEXT;
                case "41":
                    return DuanziType.VIDEO;

            }
            return null;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "type='" + type + '\'' +
                    ", text='" + text + '\'' +
                    ", name='" + name + '\'' +
                    ", profile_image='" + profile_image + '\'' +
                    ", cdn_img='" + cdn_img + '\'' +
                    ", width='" + width + '\'' +
                    ", height='" + height + '\'' +
                    ", bimageuri='" + bimageuri + '\'' +
                    ", videouri='" + videouri + '\'' +
                    '}';
        }
    }
}

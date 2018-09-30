package com.demo.model;

import java.util.List;

/**
 * Created by xinjun on 2018/9/28 15:53
 */
public class ImageList {

    public int code;
    public String msg;

    public List<Data> data;

    @Override
    public String toString() {
        return "ImageList{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data {

        public String url;

        @Override
        public String toString() {
            return "Data{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }
}

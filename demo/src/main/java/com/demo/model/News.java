package com.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/9/21.
 */

public class News {

    public int code;
    public String msg;

    public Map<String, ArrayList<Item>> data;

    public List<String> titles;
    public List<List<Item>> pagers;

    private Map<String, String> map = new HashMap() {
        {
            put("tech", "教育");
            put("auto", "汽车");
            put("money", "金融");
            put("sports", "运动");
            put("dy", "科学");
            put("war", "军事");
            put("ent", "娱乐");
            put("toutiao", "头条");
        }
    };

    public void convertDataMapToList() {
        if (data == null || data.size() == 0)
            return;

        titles = new ArrayList<>();
        pagers = new ArrayList<>();
        Set<String> strings = data.keySet();
        for (String str : strings)
            titles.add(map.get(str));

        Collection<ArrayList<Item>> values = data.values();
        for (List list : values)
            pagers.add(list);
    }

    @Override
    public String toString() {
        return "News{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", titles=" + titles +
                ", pagers=" + pagers +
                '}';
    }

    public class Item {
        public String source;
        public String title;
        public String digest;
        public String link;
        public List<PicInfo> picInfo;

        @Override
        public String toString() {
            return "Item{" +
                    "source='" + source + '\'' +
                    ", title='" + title + '\'' +
                    ", digest='" + digest + '\'' +
                    ", link='" + link + '\'' +
                    ", picInfo='" + picInfo + '\'' +
                    '}';
        }
    }

    public class PicInfo{
        public String url;

        @Override
        public String toString() {
            return "PicInfo{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }
}

package com.demo.net;

import com.demo.model.Duanzi;
import com.demo.model.ImageList;
import com.demo.model.News;

import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2018/9/21.
 */

public interface API {

    @POST
    Observable<News> getNews(@Url String url);

    @POST
    Observable<Duanzi> getDuanzi(@Url String url);

    @POST
    Observable<ImageList> getImageList(@Url String url);
}

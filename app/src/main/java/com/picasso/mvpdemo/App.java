package com.picasso.mvpdemo;

import android.app.Application;

import com.picasso.mvpdemo.retrofit.ApiService;
import com.picasso.mvpdemo.util.FileUtil;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * User: Picasso
 * Date: 2016-01-12
 * Time: 14:43
 * FIXME
 */
public class App extends Application {
    public static App app;
    public static ApiService service;
    public static final String URL_BASE = "http://www.weather.com.cn/";
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //设置缓存 10M
        File httpCacheDirectory = new File(FileUtil.getAvailableCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, 20 * 1024 * 1024);

        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
                .cache(cache).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiService.class);
    }

}

package com.picasso.mvpdemo.retrofit;

import com.picasso.mvpdemo.WeatherBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 作者：Picasso on 2016/7/21 13:16
 * 详情：
 */
public interface ApiService {

    @GET("adat/sk/{cityId}.html")
    Call<WeatherBean> getWeather(@Path("cityId") String cityId);

}

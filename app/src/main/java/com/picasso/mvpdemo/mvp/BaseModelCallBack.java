package com.picasso.mvpdemo.mvp;

import com.picasso.mvpdemo.WeatherBean;

import retrofit2.Response;

/**
 * 作者：Picasso on 2016/7/21 11:50
 * 详情：model层逻辑结束回调
 */
public interface BaseModelCallBack {

    void onResponse(Response<WeatherBean> response);

    void onFailure(Throwable t);
}

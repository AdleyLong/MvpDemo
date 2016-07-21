package com.picasso.mvpdemo.mvp.model;

import com.picasso.mvpdemo.App;
import com.picasso.mvpdemo.WeatherBean;
import com.picasso.mvpdemo.mvp.BaseModelCallBack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：Picasso on 2016/7/21 11:29
 * 详情：Model层，实现逻辑
 */
public class TestModel {

    /**
     * 网络请求操作
     * @param baseModelCallBack 从Presenter中传来的回调对象
     */
    public void getData(final BaseModelCallBack baseModelCallBack) {
        Call<WeatherBean> call = App.service.getWeather("101010100");
        call.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Response<WeatherBean> response) {
                //成功的内容回调给presenter
                baseModelCallBack.onResponse(response);
            }

            @Override
            public void onFailure(Throwable t) {
                baseModelCallBack.onFailure(t);
            }
        });
    }
}

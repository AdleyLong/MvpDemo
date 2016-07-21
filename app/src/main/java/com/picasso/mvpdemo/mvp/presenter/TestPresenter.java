package com.picasso.mvpdemo.mvp.presenter;

import com.picasso.mvpdemo.WeatherBean;
import com.picasso.mvpdemo.mvp.BaseModelCallBack;
import com.picasso.mvpdemo.mvp.model.TestModel;
import com.picasso.mvpdemo.mvp.view.TestContract;

import retrofit2.Response;

/**
 * 作者：Picasso on 2016/7/21 11:23
 * 详情：Presenter，主要作为一个桥梁，Model 去访问一个网站数据，解析回来，Presenter通过View 接口回调给界面显示
 */
public class TestPresenter implements TestContract.Presenter {

    private TestModel mModel;

    private TestContract.View mView;

    public TestPresenter(TestContract.View view) {
        mView = view;
//        mView.setPresenter(this);
        mModel = new TestModel();
    }


    /**
     * 异步获取数据
     */
    @Override
    public void getHttpData() {

        mModel.getData(new BaseModelCallBack() {
            //Model获取数据后的回调方法onResponse
            @Override
            public void onResponse(Response<WeatherBean> response) {
                mView.updateUI(response);
            }

            //Model获取数据后的回调方法onFailure
            @Override
            public void onFailure(Throwable t) {
                mView.updateFail(t);
            }
        });
    }

    /**
     * 默认的方法
     */
    @Override
    public void start() {

    }
}

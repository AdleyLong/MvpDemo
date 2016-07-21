package com.picasso.mvpdemo.mvp.view;

import com.picasso.mvpdemo.WeatherBean;
import com.picasso.mvpdemo.mvp.BasePresenter;
import com.picasso.mvpdemo.mvp.BaseView;

import retrofit2.Response;

/**
 * 作者：Picasso on 2016/7/21 11:19
 * 详情：契约类
 * 契约类来统一管理view与presenter的所有的接口，
 * 这种方式使得view与presenter中有哪些功能，一目了然，维护起来也方便
 */
public class TestContract {

    //mvp中的view层回调函数
    public interface View extends BaseView<Presenter> {

        void updateUI(Response<WeatherBean> response);
        void updateFail(Throwable t);

    }

    //mvp中的presenter层的回调函数
    public interface Presenter extends BasePresenter {

        void getHttpData();

    }
}

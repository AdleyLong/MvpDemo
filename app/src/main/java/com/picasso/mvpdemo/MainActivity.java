package com.picasso.mvpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.picasso.mvpdemo.mvp.presenter.TestPresenter;
import com.picasso.mvpdemo.mvp.view.TestContract;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TestContract.View{

    private TestContract.Presenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("shenlong","onCreate()");

        //初始化Presenter
        mPresenter = new TestPresenter(this);
        //调用数据请求接口
        mPresenter.getHttpData();
    }


    /**
     * 数据请求结束后更新UI界面回调方法
     */
    @Override
    public void updateUI(Response<WeatherBean> response) {
        Log.i("shenlong","updateUI success");
    }

    /**
     * 数据请求失败回调方法
     */
    @Override
    public void updateFail(Throwable t) {
        Log.i("shenlong","updateFail t="+t);
    }

    /**
     * 设置presenter
     * 注意：这里的setPresenter看似没用，在google推荐的demo里面，是因为要吧presenter从activity传过来才加的
     */
//    @Override
//    public void setPresenter(TestContract.Presenter presenter) {
//        Log.i("shenlong","setPresenter()");
//    }
}

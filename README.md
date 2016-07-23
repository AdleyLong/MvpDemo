# MvpDemo
>最近关于MVC、MVP的架构被越来越多的人讨论和使用。确实，随着UI创建技术的功能日益增强，UI层也履行着越来越多的职责。为了更好地细分视图(View)与模型(Model)的功能，让View专注于处理数据的可视化以及与用户的交互，同时让Model只关系数据的处理，基于MVC概念的MVP(Model-View-Presenter)模式应运而生。

#MVP介绍

*之前对于MVP的理解有错误 之前的理解：将数据的获取（http）数据的保存等操作放在presenter里面处理，model层相当于bean（其实就是没有model），其实这样的结构只能说是MVC架构。 正确的mvp：将数据的获取等操作放在model里面，取完后回调给presenter，presenter其实应该是View和Model的桥梁，将model返回的数据回调给View。这样子才能真正实现model层和view层的解耦。*


**1、在MVP模式里通常包含4个要素**

     (1)View:负责绘制UI元素、与用户进行交互(在Android中体现为Activity或者Fragment);
     (2)View interface:需要View实现的接口，View通过View interface与Presenter进行交互，降低耦合，方便进行单元测试;
     (3)Model:负责存储、检索、操纵数据(有时也实现一个Model interface用来降低耦合);
     (4)Presenter:主要作为一个桥梁，Model 去访问一个网站数据，解析回来，通过View 接口提供给界面显示
     
![这里写图片描述](http://img.blog.csdn.net/20160114104042668)

**2、MVP的优点**

界面和逻辑业务通过接口关联，将业务实现细节隐藏，如此一来，当我们将交互之间的接口定义好之后，内部的实现细节修改，保证模块独立性。


----------


#MVP结构分析
主要代码文件如下：
![这里写图片描述](http://img.blog.csdn.net/20160721155043764)


**1、契约类 XXXXContract.java**

契约类来统一管理view与presenter的所有的接口， 这种方式使得view与presenter中有哪些功能，一目了然，维护起来也方便。

```java
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
```
**2、Model层 XXXXModel.java**

Model实现逻辑，比如http请求，并存储在某个载体内，有时也实现一个Model interface用来降低耦合（这里就不写Model interface了）

```java
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
```
**3、Presenter类 XXXXPresenter.java**

Presenter，主要作为一个桥梁，Model 去访问一个网站数据，解析回来，Presenter通过View 接口回调给界面显示。这里Presenter继承了契约类里面的presenter接口。

```java
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
```
**4、Activity或者Fragment**

实现契约类内部的view接口，初始化presenter，并且调用网络请求接口

```java
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
```

到这里，MVP的框架demo就算结束了，从代码中就可以看出，model层的业务逻辑和view层一点关系都没有，都是通过presenter层来做沟通的。

**说到解耦，这里还要啰嗦一下，为什么要解耦？**

解耦的好处是：

一、 提高可维护性。在UI逻辑发生变化甚至整个端掉都不会影响到处理逻辑。

二、 提高可复用性。复用通常只数据的复用，数据逻辑不受UI的左右，由此可以服务于多个UI视图。

三、 可读性。层次分清之后按照统一的架构思路去理解代码，当然还得靠开发人员良好的编程素养和代码规范。


**另外简要讲下MVC、MVVM框架的特点**

**MVC模式**

视图（View）：用户界面。
控制器（Controller）：业务逻辑
模型（Model）：数据保存
View 传送指令到 Controller
Controller 完成业务逻辑后，要求 Model 改变状态
Model 将新的数据发送到 View，用户得到反馈

**MVVM模式**

将 Presenter 改名为 ViewModel，基本上与 MVP 模式完全一致。唯一的区别是，它采用双向绑定（data-binding）：View的变动，自动反映在 ViewModel，反之亦然。

参考文献：

[MVC和MVP在app中的对比分析以及实际应用](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0313/2599.html)

[MVP模式在Android开发中的应用](http://blog.csdn.net/vector_yi/article/details/24719873)

[Android开发中的MVP架构](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2016/0105/3832.html)

[MVC，MVP 和 MVVM 的图示](http://www.ruanyifeng.com/blog/2015/02/mvcmvp_mvvm.html)

[android MVP 架构思路](http://mp.weixin.qq.com/s?__biz=MzI1MjMyOTU2Ng==&mid=2247483865&idx=1&sn=28d3c2f12138e5db0b0245efb1825d4f#rd)

[google官方demo](https://github.com/googlesamples/android-architecture/tree/todo-mvp/)

package cn.sm.framework.net.api;

import android.content.Context;


import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.sm.framework.JConfig;
import cn.sm.framework.net.callback.ApiCallback;
import cn.sm.framework.net.callback.ApiCallbackSubscriber;
import cn.sm.framework.net.interceptor.MyInterceptor;
import cn.sm.framework.net.parsing.ApiErrFunc;
import cn.sm.framework.net.parsing.ApiParsingFunc;
import cn.sm.framework.util.ClassUtil;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description: 网络操作入口
 * Created by lishimin 2017/4/19
 */
public class ViseApi {
    private static Context context;
    private static ApiService apiService;


    private ViseApi() {

    }


    /**
     * 普通Get方式请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> get(String url, Map<String, String> maps, Class<T> clazz) {
        return apiService.get(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 普通Get方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription get(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.get(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }


    /**
     * 普通POST方式请求，需传入实体类
     *
     * @param url
     * @param parameters
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> post(final String url, final Map<String, String> parameters, Class<T> clazz) {
        return apiService.post(url, parameters).compose(this.norTransformer(clazz));
    }

    /**
     * 普通POST方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription post(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.post(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }


    /**
     * 提交表单方式请求，需传入实体类
     *
     * @param url
     * @param fields
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> form(final String url, final @FieldMap(encoded = true) Map<String, Object> fields, Class<T> clazz) {
        return apiService.postForm(url, fields).compose(this.norTransformer(clazz));
    }

    /**
     * 提交表单方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param fields
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription form(final String url, final @FieldMap(encoded = true) Map<String, Object> fields, ApiCallback<T> callback) {
        return this.form(url, fields, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 提交Body方式请求，需传入实体类
     *
     * @param url
     * @param body
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> body(final String url, final Object body, Class<T> clazz) {
        return apiService.postBody(url, body).compose(this.norTransformer(clazz));
    }

    /**
     * 提交Body方式请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param body
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription body(final String url, final Object body, ApiCallback<T> callback) {
        return this.body(url, body, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 删除信息请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> delete(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.delete(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 删除信息请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription delete(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.delete(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 修改信息请求，需传入实体类
     *
     * @param url
     * @param maps
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> put(final String url, final Map<String, String> maps, Class<T> clazz) {
        return apiService.put(url, maps).compose(this.norTransformer(clazz));
    }

    /**
     * 修改信息请求，无需订阅，只需传入Callback回调
     *
     * @param url
     * @param maps
     * @param callback
     * @param <T>
     * @return
     */
    public <T> Subscription put(String url, Map<String, String> maps, ApiCallback<T> callback) {
        return this.put(url, maps, ClassUtil.getTClass(callback)).subscribe(new ApiCallbackSubscriber(context, callback));
    }

    /**
     * 上传图片，需传入请求body和实体类
     *
     * @param url
     * @param requestBody
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadImage(String url, RequestBody requestBody, Class<T> clazz) {
        return apiService.uploadImage(url, requestBody).compose(this.norTransformer(clazz));
    }

    /**
     * 上传图片，需传入图片文件和实体类
     *
     * @param url
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadImage(String url, File file, Class<T> clazz) {
        return apiService.uploadImage(url, RequestBody.create(okhttp3.MediaType.parse("image/jpg; " + "charset=utf-8"), file)).compose
                (this.norTransformer(clazz));
    }

    /**
     * 上传文件
     *
     * @param url
     * @param requestBody
     * @param file
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadFile(String url, RequestBody requestBody, MultipartBody.Part file, Class<T> clazz) {
        return apiService.uploadFile(url, requestBody, file).compose(this.norTransformer(clazz));
    }

    /**
     * 上传多文件
     *
     * @param url
     * @param files
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Observable<T> uploadFlies(String url, Map<String, RequestBody> files, Class<T> clazz) {
        return apiService.uploadFiles(url, files).compose(this.norTransformer(clazz));
    }


    /**
     * 创建ViseApi.Builder
     *
     * @param context
     * @return
     */
    public ViseApi.Builder newBuilder(Context context) {
        return new ViseApi.Builder(context);
    }

    /**
     * 设置请求线程和回调线程的自由切换 并将结果解析成对应的bean
     * @param <T>
     * @return
     */
    private <T> Observable.Transformer<ResponseBody,T> norTransformer (final Class<T> clazz){
        return new Observable.Transformer<ResponseBody, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBody> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiParsingFunc<T>(clazz)).onErrorResumeNext(new ApiErrFunc<T>());
            }
        };
    }



    /**
     * 非空判断
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    private static <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }

    /**
     * ViseApi的所有配置都通过建造者方式创建
     */
    public static final class Builder {
        private Cache cache;
        private String baseUrl;
        private Boolean isCache = false;
        private int connectTimeout = JConfig.DEFAULT_CONNECT_TIMEOUT;
        private TimeUnit connectTimeUnit = TimeUnit.SECONDS;
        private int readTimeout = JConfig.DEFAULT_READ_TIMEOUT;
        private TimeUnit readTimeUnit = TimeUnit.SECONDS;
        private Retrofit.Builder retrofitBuilder;
        private OkHttpClient.Builder okHttpBuilder;

        public Builder(Context mContext) {
            context = mContext;
            retrofitBuilder = new Retrofit.Builder();
            okHttpBuilder = new OkHttpClient.Builder();
        }


        public ViseApi build() {

            okHttpBuilder
                    .connectTimeout(connectTimeout,connectTimeUnit)
                    .writeTimeout(readTimeout,readTimeUnit)
                    .readTimeout(readTimeout,readTimeUnit);
            if (isCache) okHttpBuilder.cache(new Cache(context.getCacheDir(),JConfig.CACHE_MAX_SIZE));


            Retrofit retrofit = retrofitBuilder
                    .baseUrl(baseUrl)
                    .client(okHttpBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);

            return new ViseApi();
        }


        /**
         * 设置连接超时时间（秒）
         *
         * @param seconds
         * @return
         */
        public ViseApi.Builder connectTimeout(int seconds) {
            this.connectTimeout = seconds;
            this.connectTimeUnit = TimeUnit.SECONDS;
            return this;
        }


        /**
         * 设置连接超时时间
         *
         * @param timeout
         * @param unit
         * @return
         */
        public ViseApi.Builder connectTimeout(int timeout, TimeUnit unit) {
            this.connectTimeout = timeout;
            this.connectTimeUnit = unit;
            return this;
        }


        /**
         * 设置读取和写入超时时间（默认单位是: 秒）
         *
         * @param timeout
         * @return
         */
        public ViseApi.Builder setReadAndWriteTimeOut(int timeout) {
            this.readTimeout = timeout;
            readTimeUnit = TimeUnit.SECONDS;
            return this;
        }


        /**
         * 设置读取和写入超时时间 (可接受自定义的时间单位)
         *
         * @param timeout
         * @param unit
         * @return
         */
        public ViseApi.Builder setReadAndWriteTimeOut(int timeout, TimeUnit unit) {
            if (timeout > -1) {
                readTimeout = timeout;
                readTimeUnit = unit;
            }
            return this;
        }


        /**
         * 设置是否添加缓存
         *
         * @param isCache
         * @return
         */
        public ViseApi.Builder setCacheEnable(boolean isCache) {
            this.isCache = isCache;
            return this;
        }


        /**
         * 设置请求BaseURL
         *
         * @param baseUrl
         * @return
         */
        public ViseApi.Builder baseUrl(String baseUrl) {
            this.baseUrl = checkNotNull(baseUrl, "baseUrl == null");
            return this;
        }



        /**
         * 设置请求头部
         *
         * @param headers
         * @return
         */
        public ViseApi.Builder headers(Map<String, String> headers) {
            okHttpBuilder.addInterceptor(new MyInterceptor(headers));
            return this;
        }


        /**
         * 设置请求参数
         *
         * @param parameters
         * @return
         */
        public ViseApi.Builder parameters(Map<String, String> parameters) {
            okHttpBuilder.addInterceptor(new MyInterceptor(parameters));
            return this;
        }


        /**
         * 设置拦截器
         *
         * @param interceptor
         * @return
         */
        public ViseApi.Builder interceptor(Interceptor interceptor) {
            okHttpBuilder.addInterceptor(checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

        /**
         * 设置网络拦截器
         *
         * @param interceptor
         * @return
         */
        public ViseApi.Builder networkInterceptor(Interceptor interceptor) {
            okHttpBuilder.addNetworkInterceptor(checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

    }
}

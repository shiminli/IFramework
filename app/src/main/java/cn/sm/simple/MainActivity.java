package cn.sm.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import cn.sm.framework.image.GlideLoader;
import cn.sm.framework.image.GlideLoaderFactory;
import cn.sm.framework.image.MyLoader;
import cn.sm.framework.net.api.ViseApi;
import cn.sm.framework.net.callback.ApiCallback;
import cn.sm.framework.net.exception.ApiException;
import cn.sm.framework.net.util.JLog;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test1();
        test2();

        ImageView imageView1 = (ImageView) findViewById(R.id.iv1);
        ImageView imageView2 = (ImageView) findViewById(R.id.iv2);

        GlideLoaderFactory.getLoader().loadResource(imageView1,R.mipmap.datouerzi, MyLoader.Options.defaultOptions());
        GlideLoaderFactory.getLoader().loadResource(imageView2,R.mipmap.donghuawu_pic, MyLoader.Options.defaultOptions());



    }

    private void test2() {
        ViseApi viseApi = new ViseApi.Builder(this)
                .baseUrl("http://www.tngou.net")
                .connectTimeout(5)
                .build();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", "1");
        viseApi.get("/api/food/list", hashMap, TestBean.class)
                .subscribe(new Subscriber<TestBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(TestBean testBean) {
//                        Log.d(TAG, "onNext: " + testBean.isStatus() + " totla: " + testBean.getTotal());
                        JLog.d("onNext: " + testBean.isStatus() + " totla: " + testBean.getTotal());
                        TextView textView = (TextView) findViewById(R.id.tv2);
                        textView.setText(testBean.getTngou().get(2).getDescription());
                    }
                });
    }


    void test1() {
        ViseApi viseApi = new ViseApi.Builder(this)
                .baseUrl("http://www.tngou.net")
                .connectTimeout(5)
                .build();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", "1");
        viseApi.get("/api/food/list", hashMap, new ApiCallback<TestBean>() {
            @Override
            public void onStart() {
                Log.e(TAG, "k开始  了了 : ");
            }

            @Override
            public void onError(ApiException e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onNext(TestBean testBean) {
//                Log.d(TAG, "onNext: " + testBean.isStatus() + " totla: " + testBean.getTotal());
                JLog.d("onNext: " + testBean.isStatus() + " totla: " + testBean.getTotal());
                TextView textView = (TextView) findViewById(R.id.tv1);
                textView.setText(testBean.getTngou().get(0).getDescription());
            }
        });
    }
}

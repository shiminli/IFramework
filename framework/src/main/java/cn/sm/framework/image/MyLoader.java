package cn.sm.framework.image;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

import cn.sm.framework.JConfig;

/**
 * Created by lishiming on 17/4/19.
 */

public interface MyLoader {

    /**
     * 加载网络图片
     * @param target 显示图片的控件
     * @param url 图片地址
     * @param options 配置加载错误和加载中的占位图
     */
    void loadNet(ImageView target, String url , Options options);

    /**
     * 加载资源文件里的图片
     * @param target 显示图片的控件
     * @param resId 图片地址
     * @param options 配置加载错误和加载中的占位图
     */
    void loadResource(ImageView target, int resId , Options options);

    /**
     * 加载外部资源文件里的图片
     * @param target 显示图片的控件
     * @param assetName 图片地址
     * @param options 配置加载错误和加载中的占位图
     */
    void loadAssets(ImageView target, String assetName , Options options);

    /**
     * 加载指定路径里的图片
     * @param target 显示图片的控件
     * @param file 图片地址
     * @param options 配置加载错误和加载中的占位图
     */
    void loadFile(ImageView target, File file, Options options);

    /**
     * 清除当前内存中的图片缓存
     * @param context 上下文
     */
    void clearMemoryCache(Context context);


    /**
     * 清除本地中的图片缓存
     * @param context 上下文
     */
    void clearDiskCache(Context context);


        class Options {
            public static final int NO_RES = -1;
            public int loadingResId = NO_RES;
            public int loadErrorResId = NO_RES;

            public static Options defaultOptions(){
                return new Options(JConfig.IL_LOADING_RES,JConfig.IL_ERROR_RES);
            }

            public Options(int loadingResId, int loadErrorResId) {
                this.loadingResId = loadingResId;
                this.loadErrorResId = loadErrorResId;
            }
        }
}

package cn.sm.framework.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.File;

/**
 * Created by lishiming on 17/4/19.
 */

public class GlideLoader implements MyLoader {

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        loadImg(getRequest(target.getContext()).load(url),target,options);
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        loadImg(getRequest(target.getContext()).load(resId),target,options);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        loadImg(getRequest(target.getContext()).load("file:///android_asset/" + assetName),target,options);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        loadImg(getRequest(target.getContext()).load(file),target,options);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    private RequestManager getRequest(Context context){
        RequestManager with = Glide.with(context);
        DrawableTypeRequest<String> aaa = with.load("aaa");

        return Glide.with(context);
    }

    private void loadImg(DrawableTypeRequest request , ImageView target , Options options){
        if (options == null) options = Options.defaultOptions();

        if (options.loadingResId != Options.NO_RES)
            request.placeholder(options.loadingResId);
        if (options.loadErrorResId != Options.NO_RES)
            request.error(options.loadErrorResId);
        request.crossFade().into(target);
    }
}

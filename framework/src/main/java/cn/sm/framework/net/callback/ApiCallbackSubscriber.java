package cn.sm.framework.net.callback;

import android.content.Context;

import java.lang.ref.WeakReference;

import cn.sm.framework.net.exception.ApiCode;
import cn.sm.framework.net.exception.ApiException;
import rx.Subscriber;

/**
 * Created by lishiming on 17/4/18.
 */

public class ApiCallbackSubscriber<T> extends Subscriber<T> {

    public WeakReference<Context> contextWeakReference;
    protected ApiCallback<T> callBack;

    public ApiCallbackSubscriber(Context context , ApiCallback<T> callBack) {
        contextWeakReference = new WeakReference<Context>(context);
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    @Override
    public void onCompleted() {
        callBack.onCompleted();
    }

    @Override
    public void onStart() {
        super.onStart();
        callBack.onStart();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException){
            callBack.onError((ApiException)e);
        }else {
            callBack.onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }

    @Override
    public void onNext(T o) {
        callBack.onNext(o);
    }
}

package cn.sm.framework.net.callback;

import cn.sm.framework.net.exception.ApiException;

/**
 * Created by lishiming on 17/4/18.
 */

public abstract class ApiCallback<T> {
    public abstract void onStart();

    public abstract void onError(ApiException e);

    public abstract void onCompleted();

    public abstract void onNext(T t);
}

package cn.sm.framework.net.parsing;

import cn.sm.framework.net.exception.ApiException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lishiming on 17/4/18.
 */

public class ApiErrFunc <T> implements Func1<Throwable, Observable<T>>  {

    @Override
    public Observable<T> call(Throwable throwable) {
        return Observable.error(ApiException.handleException(throwable));
    }
}

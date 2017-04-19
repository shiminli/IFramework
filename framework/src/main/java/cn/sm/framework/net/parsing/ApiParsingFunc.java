package cn.sm.framework.net.parsing;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.functions.Func1;

/**
 * Created by lishiming on 17/4/18.
 */

public class ApiParsingFunc<T> implements Func1<ResponseBody, T> {


    protected Class<T> clazz;

    public ApiParsingFunc(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T call(ResponseBody responseBody) {
        Gson gson = new Gson();
        String json = null;
        try {
            json = responseBody.string();
            if (clazz.equals(String.class)) {
                return (T) json;
            } else {
                return gson.fromJson(json, clazz);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            responseBody.close();
        }
        return (T) json;
    }
}

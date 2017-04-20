package cn.sm.framework.util;

import android.content.Context;
import android.content.SharedPreferences;

import cn.sm.framework.JConfig;
import cn.sm.framework.cipher.BASE64;
import cn.sm.framework.convert.ByteUtil;
import cn.sm.framework.convert.ToHex;


/**
 * Created by lishimin 2017/4/19
 * @Description: SharedPreferences存储，支持对象加密存储
 */
public class SpCacheUtil {
    private SharedPreferences sp;

    public SpCacheUtil(Context context) {
        this(context, JConfig.CACHE_SP_NAME);
    }

    public SpCacheUtil(Context context, String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSp() {
        return sp;
    }


    public void put(String key, Object ser) {
        try {
            JLog.i(key + " put: " + ser);
            if (ser == null) {
                sp.edit().remove(key).commit();
            } else {
                byte[] bytes = ByteUtil.objectToByte(ser);
                bytes = BASE64.encode(bytes);
                put(key, ToHex.encodeHexStr(bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Object get(String key) {
        try {
            String hex = get(key, (String) null);
            if (hex == null) return null;
            byte[] bytes = ToHex.decodeHex(hex.toCharArray());
            bytes = BASE64.decode(bytes);
            Object obj = ByteUtil.byteToObject(bytes);
            JLog.i(key + " get: " + obj);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean contains(String key) {
        return sp.contains(key);
    }


    public void remove(String key) {
        sp.edit().remove(key).apply();
    }


    public void clear() {
        sp.edit().clear().apply();
    }

    public void put(String key, String value) {
        if (value == null) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().putString(key, value).commit();
        }
    }

    public void put(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public void put(String key, float value) {
        sp.edit().putFloat(key, value).commit();
    }

    public void put(String key, long value) {
        sp.edit().putLong(key, value).commit();
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public float get(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return sp.getLong(key, defValue);
    }
}

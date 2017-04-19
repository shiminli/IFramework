package cn.sm.framework.image;

/**
 * Created by lishiming on 17/4/19.
 */

public class GlideLoaderFactory {

    private static MyLoader loader;

    /**
     * 获取加载图片单例对象
     * @return
     */
    public static MyLoader getLoader(){
        if (loader == null){
            synchronized (GlideLoaderFactory.class){
                if (loader == null){
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }
}

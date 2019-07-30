package com.example.huodai.widget.myGlide;

import android.content.Context;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.signature.EmptySignature;
import com.example.baselib.utils.MyLog;

import java.io.File;
import java.io.IOException;

/**
 * createBy ${huanghao}
 * on 2019/7/27
 */
public class GlideUtil {
    public static File getCacheFile2(String url, Context context) {
        DataCacheKey dataCacheKey = new DataCacheKey(new GlideUrl(url), EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(dataCacheKey);
        try {
            int cacheSize = 100 * 1000 * 1000;
            File file = new File(context.getApplicationContext().getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR);
            MyLog.i("我找到了GlideUtil file: "+file.exists());
            DiskLruCache diskLruCache = DiskLruCache.open(file, 1, 1, cacheSize);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}

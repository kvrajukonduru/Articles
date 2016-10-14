package com.venkat.articles.utils;

import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;


public class ImageUtil {

    /**
     * 5 MB mem cache
     */
    static final int MAX_MEMORY_CACHE_SIZE = 5 * 1024 * 1024;

    /**
     * custom cache for fresco library used to provide custom cache sizes for disk and mem.
     *
     * @param context app context
     */
    public static void initFresco(Context context) {

        int maxHeapSize = (int) Runtime.getRuntime().maxMemory();
        int use = ((maxHeapSize / 4) / 1024 / 1024);

        String diskCachePath = context.getCacheDir() + "/Fresco/";
        File diskCacheDir = new File(diskCachePath);

        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size
        builder.setBitmapMemoryCacheParamsSupplier(
                new Supplier<MemoryCacheParams>() {
                    public MemoryCacheParams get() {
                        return bitmapCacheParams;
                    }
                })
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryPath(diskCacheDir)
                                .setBaseDirectoryName("tqtest")
                                .setMaxCacheSize(40 * ByteConstants.MB)
                                .build());
        ImagePipelineConfig config = builder.build();

        Fresco.initialize(context, config);
    }
}

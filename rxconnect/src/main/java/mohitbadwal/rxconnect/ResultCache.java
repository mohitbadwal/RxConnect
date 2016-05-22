package mohitbadwal.rxconnect;

import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Created by JohnConnor on 22-May-16.
 */
public class ResultCache {
    private static LruCache<String,String> mResultCache;
   static
    {    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mResultCache=new LruCache<>(cacheSize);
    }
    public void putResultToCache(String key,String value)
    {
        if(getResultCache(key)==null)
        {
            mResultCache.put(key, value);
            Log.d("LRU","Added to Cache");
        }
    }
    public String getResultCache(String key)
    {
        return mResultCache.get(key);
    }
    public void removeCachePut(String key,String value)
    {   if(getResultCache(key)!=null)
    {
        mResultCache.remove(key);
        Log.d("LRU","Cache Removed");
        putResultToCache(key, value);
    }

    }
}

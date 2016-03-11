package application.buzzmovieselector.Service;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import application.buzzmovieselector.Activity.WelcomeScreen;

/**
 * This class represents a Volley object
 * @author Harmeet S. Bindra
 * @version 1.0
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    /**
     * Makes a User object that creates a request queue
     */
    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(WelcomeScreen.getAppContext()); // need application context
        mImageLoader =new ImageLoader(mRequestQueue,new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }
    /**
     * returns the instance of the class
     * @return instance
     */
    public static VolleySingleton getInstance() {
        if(sInstance == null) {
            sInstance = new VolleySingleton();
        }
        return sInstance;
    }
    /**
     * returns the request queue
     * @return requestQueue
     */
    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}

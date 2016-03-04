package application.buzzmovieselector.Service;

import com.android.volley.RequestQueue;
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
    /**
     * Makes a User object that creates a request queue
     */
    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(WelcomeScreen.getAppContext()); // need application context
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
}

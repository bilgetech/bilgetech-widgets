package com.bilgetech.widgets.helper;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by damra on 09/08/2017.
 */

@SuppressWarnings({"all"})
public abstract class ThreadSafeSingleton<T> {

    private static final Map<Class<? extends ThreadSafeSingleton>, ThreadSafeSingleton> INSTANCES = new HashMap<>();
    private static final String TAG = ThreadSafeSingleton.class.getSimpleName();

    private static class SingletonHolder<T> {
        private static <T> T getInstance(Class<T> clazz) {
            Constructor<T> constructor = (Constructor<T>) clazz.getDeclaredConstructors()[0];
            constructor.setAccessible(true);

            try {
                return constructor.newInstance(null);
            } catch (Exception e) {
                Log.d(TAG, "getInstance: " + e.getLocalizedMessage());
                return null;
            }
        }
    }

    protected ThreadSafeSingleton() {
        Log.d(TAG, "Initializing ThreadSafeSingleton");
    }

    protected static <T extends ThreadSafeSingleton<T>> T getInstance(Class<T> clazz) {
        if (INSTANCES.containsKey(clazz)) {
            Log.d(TAG, "getInstance: " + ((T) INSTANCES.get(clazz)).hashCode());
            return (T) INSTANCES.get(clazz);
        } else {

            try {
                T instance = SingletonHolder.getInstance(clazz);
                INSTANCES.put(clazz, instance);
                Log.d(TAG, "putInstance & getInstance: " + instance.hashCode());
                return instance;
            } catch (Exception e) {
                Log.d(TAG, "getInstance: " + e.getLocalizedMessage());
                return null;
            }
        }
    }

    protected static <T extends ThreadSafeSingleton<T>> void putInstance(Class<T> clazz, T instance) {
        if (!INSTANCES.containsKey(clazz)) {
            INSTANCES.put(clazz, instance);
            Log.d(TAG, "putInstance: " + instance.hashCode());
        }
    }
}
package com.bilgetech.libraries.sample;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Optional;

/**
 * Created by damra on 26/01/2018.
 */

public class JSONUtils {

    private static final String TAG = JSONUtils.class.getSimpleName();

    /**
     * Open a json file from raw and construct as class using Gson.
     *
     * @param resources
     * @param resId
     * @param classType
     * @param <T>
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static <T> Optional<T> getJsonFileAsClass(final Resources resources, final int resId, final Class<T> classType) {

        InputStream resourceReader = resources.openRawResource(resId);
        Writer writer = new StringWriter();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceReader, "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                line = reader.readLine();
            }
            return Optional.ofNullable(constructUsingGson(classType, writer.toString()));
        } catch (Exception e) {
            Log.e(TAG, "Unhandled exception while using JSONResourceReader", e);
            e.printStackTrace();
        } finally {
            try {
                resourceReader.close();
            } catch (Exception e) {
                Log.e(TAG, "Unhandled exception while using JSONResourceReader", e);
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * Build an object from the specified JSON resource using Gson.
     *
     * @param type The type of the object to build.
     * @return An object of type T, with member fields populated using Gson.
     */
    private static <T> T constructUsingGson(final Class<T> type, final String jsonString) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, type);
    }
}

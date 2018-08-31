package me.mrzhang.music.http;

import android.util.Log;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;
import java.io.IOException;
import okhttp3.Response;

public abstract class JsonCallback<T> extends Callback<T>{

    private Class<T> clazz;
    private Gson gson;
    private static final String TAG = "JsonCallbackError";

    public JsonCallback(Class<T> clazz){
        this.clazz = clazz;
        gson = new Gson();
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException{
        try {
            String jsonString = response.body().string();
            return gson.fromJson(jsonString, clazz);
        } catch (Exception error){
            Log.e(TAG, error.getLocalizedMessage());
            error.printStackTrace();
        }
        return null;
    }
}

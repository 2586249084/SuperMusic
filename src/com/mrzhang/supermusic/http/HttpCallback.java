package com.mrzhang.supermusic.http;

public abstract class HttpCallback<T> {
    public abstract void onSuccess(T t);

    public abstract void onFail(Exception error);

    public void onFinish(){
        
    }
}

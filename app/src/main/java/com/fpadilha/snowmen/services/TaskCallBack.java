package com.fpadilha.snowmen.services;

public interface TaskCallBack {

    void onSuccess(String message);
    void onFailed(String error);

}

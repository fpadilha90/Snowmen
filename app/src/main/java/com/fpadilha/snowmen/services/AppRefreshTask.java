package com.fpadilha.snowmen.services;

import android.content.Context;
import android.util.Log;

import com.fpadilha.snowmen.R;
import com.fpadilha.snowmen.helpers.SnowmanHelper;
import com.fpadilha.snowmen.models.Snowman;
import com.fpadilha.snowmen.models.response.GetSnowmenResponse;
import com.fpadilha.snowmen.utils.Utils;
import com.fpadilha.snowmen.ws.RestClient;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestClientException;

@EBean
public class AppRefreshTask implements TaskCallBack {

    @RootContext
    Context context;

    @RestService
    RestClient restClient;


    TaskCallBack callBack;
    double latitude;
    double longitude;

    public void start(TaskCallBack callBack, double latitude, double longitude) {
        this.callBack = callBack;
        this.latitude = latitude;
        this.longitude = longitude;
        doInBackground();
    }

    @Background
    void doInBackground() {
        GetSnowmenResponse getSnowmenResponse;
        try {

            if (Utils.isOnline(context)) {
                getSnowmenResponse = restClient.getSnowmen(latitude, longitude, 1000);

                Log.d("Snowmen", String.valueOf(getSnowmenResponse.getCount()));

                for (Snowman snowman : getSnowmenResponse.getResults()) {
                    SnowmanHelper.insertOrUpdate(context, snowman);

                }

                callBack.onSuccess(String.valueOf(getSnowmenResponse.getCount()));
            } else {
                callBack.onFailed(context.getString(R.string.err_without_connection));
            }
        } catch (RestClientException | HttpMessageNotReadableException e) {
            Log.e("AppRefreshTask", e.getMessage(), e);
            callBack.onFailed(context.getString(R.string.err_server_unreachable));
        }

    }

    @Override
    public void onSuccess(String message) {
        callBack.onSuccess(message);
    }

    @Override
    public void onFailed(String error) {
        callBack.onFailed(error);
    }

}

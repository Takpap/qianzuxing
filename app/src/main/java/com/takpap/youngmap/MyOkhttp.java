package com.takpap.youngmap;

import android.os.Build;
import android.util.Log;

import com.takpap.youngmap.utils.httpConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyOkhttp {
    private String url = httpConfig.getServerUrl()+"locations";
    private Double longitude;
    private Double latitude;
    private Double direction;
    private int accuracy;
    private OkHttpClient okHttpClient;
    private String mesResponse;
    private String tel;
    public String getMesResponse() {
        return mesResponse;
    }

    public MyOkhttp(int accuracy,Double longitude, Double latitude, Double direction,String tel) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
        this.direction = direction;
        this.tel = tel;
    }

    public void SendAndRespon() {
        okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("tel", String.valueOf(tel))
                .add("longitude", String.valueOf(longitude))
                .add("latitude", String.valueOf(latitude))
                .add("accuracy", String.valueOf(accuracy))
                .add("direction", String.valueOf(direction))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("receivelocationFailed", String.valueOf(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mesResponse = Objects.requireNonNull(response.body()).string();
                Log.d("receivelocationSuccess",mesResponse);
            }
        });
    }

}

package id.agristreet.agristreetapp.data.remote;

/**
 * Created by RyMey on 12/10/17.
 */


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import id.agristreet.agristreetapp.data.model.Pebisnis;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public class RestApi {
    private static RestApi instance;

    private RestApi(Context context) {

    }

    public static RestApi getInstance(Context context) {
        if (instance == null) {
            instance = new RestApi(context);
        }
        return instance;
    }

    private interface Api {

        @FormUrlEncoded
        @POST("/agristreetAPI/pebisnis/verifyPhone")
        Observable<JsonElement> verifyPhone(@Field("phone_number") String phoneNumber);

    }
}

package id.agristreet.agristreetapp.data.remote;

/**
 * Created by RyMey on 12/10/17.
 */


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Akun;
import id.agristreet.agristreetapp.data.model.Pebisnis;
import id.agristreet.agristreetapp.data.model.User;
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
    private final Context context;
    private final OkHttpClient httpClient;
    private final Api api;

    private RestApi(Context context) {
        this.context = context;

        httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        api = new Retrofit.Builder()
                .baseUrl("http://alamatServer")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    public static RestApi getInstance(Context context) {
        if (instance == null) {
            instance = new RestApi(context);
        }
        return instance;
    }

    public Observable<String> verifyPhonePebisnis(String noTelp) {
        return api.verifyPhonePebisnis(noTelp)
                .map(json -> json.optString("result"))
                .doOnNext(reqId -> {
                    PengelolaDataLokal.getInstance(context).simpanNoTelp(noTelp);
                    PengelolaDataLokal.getInstance(context).simpanRequestId(reqId);
                });
    }

    public Observable<Akun> authPebisnis(String code) {
        return api.authPebisnis(PengelolaDataLokal.getInstance(context).getNoTelp(),
                PengelolaDataLokal.getInstance(context).getReqId(),
                code)
                .map(json -> {
                    Akun akun = new Akun();
                    User user = new User();

                    try {
                        JSONObject result = json.getJSONObject("result");

                        user.setId(result.getString("id_pebisnis"));
                        user.setNama(result.getString("nama_pebisnis"));
                        user.setNoTelp(result.getString("no_telp"));
                        user.setFoto(result.getString("foto"));

                        akun.setToken(result.getString("token"));
                        akun.setUser(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return akun;
                })
                .doOnNext(akun -> {
                    PengelolaDataLokal.getInstance(context).simpanAkun(akun);
                });
    }

    private interface Api {

        @FormUrlEncoded
        @POST("/pebisnis/verifyPhone")
        Observable<JSONObject> verifyPhonePebisnis(@Field("no_telp") String noTelp);

        @FormUrlEncoded
        @POST("/pebisnis/auth")
        Observable<JSONObject> authPebisnis(@Field("no_telp") String noTelp,
                                            @Field("request_id") String reqId,
                                            @Field("code") String code);

    }
}

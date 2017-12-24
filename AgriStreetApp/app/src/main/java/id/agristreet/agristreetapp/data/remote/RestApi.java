package id.agristreet.agristreetapp.data.remote;

/**
 * Created by RyMey on 12/10/17.
 */


import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Akun;
import id.agristreet.agristreetapp.data.model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class RestApi {
    private static RestApi instance;
    private final Context context;
    private final OkHttpClient httpClient;
    private final Api api;

    private RestApi(Context context) {
        this.context = context;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();

        api = new Retrofit.Builder()
                .baseUrl("http://128.199.215.222")
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

    public Observable<String> verifyPhonePetani(String noTelp) {
        return api.verifyPhonePetani(noTelp)
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
                .doOnNext(akun -> PengelolaDataLokal.getInstance(context).simpanAkun(akun));
    }

    private interface Api {

        @FormUrlEncoded
        @POST("/pebisnis/verify-phone")
        Observable<JSONObject> verifyPhonePebisnis(@Field("no_telp") String noTelp);

        @FormUrlEncoded
        @POST("/petani/verify-phone")
        Observable<JSONObject> verifyPhonePetani(@Field("no_telp") String noTelp);

        @FormUrlEncoded
        @POST("/pebisnis/auth")
        Observable<JSONObject> authPebisnis(@Field("no_telp") String noTelp,
                                            @Field("request_id") String reqId,
                                            @Field("code") String code);

    }
}

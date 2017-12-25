package id.agristreet.agristreetapp.data.remote;

/**
 * Created by RyMey on 12/10/17.
 */


import android.content.Context;

import com.google.gson.JsonObject;

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
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
                .map(json -> json.get("result").getAsString())
                .doOnNext(reqId -> {
                    PengelolaDataLokal.getInstance(context).simpanNoTelp(noTelp);
                    PengelolaDataLokal.getInstance(context).simpanRequestId(reqId);
                });
    }

    public Observable<String> verifyPhonePetani(String noTelp) {
        return api.verifyPhonePetani(noTelp)
                .map(json -> json.get("result").getAsString())
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
                        JsonObject result = json.get("result").getAsJsonObject();

                        user.setId(result.get("id_pebisnis").getAsString());
                        user.setNama(result.get("nama_pebisnis").getAsString());
                        user.setNoTelp(result.get("no_telp").getAsString());
                        user.setFoto(result.get("foto").getAsString());

                        akun.setToken(result.get("token").getAsString());
                        akun.setUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return akun;
                })
                .doOnNext(akun -> PengelolaDataLokal.getInstance(context).simpanAkun(akun));
    }

    public Observable<Akun> authPetani(String code) {
        return api.authPetani(PengelolaDataLokal.getInstance(context).getNoTelp(),
                PengelolaDataLokal.getInstance(context).getReqId(),
                code)
                .map(json -> {
                    Akun akun = new Akun();
                    User user = new User();

                    try {
                        JsonObject result = json.get("result").getAsJsonObject();

                        user.setId(result.get("id_petani").getAsString());
                        user.setNama(result.get("nama_petani").getAsString());
                        user.setNoTelp(result.get("no_telp").getAsString());
                        user.setFoto(result.get("foto").getAsString());

                        akun.setToken(result.get("token").getAsString());
                        akun.setUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return akun;
                })
                .doOnNext(akun -> PengelolaDataLokal.getInstance(context).simpanAkun(akun));
    }

    public Observable<Akun> updateProfilePebisnis(String nama, String foto) {
        return api.updateProfilePebisnis(PengelolaDataLokal.getInstance(context).getAkun().getToken(),
                nama, foto)
                .map(json -> {
                    Akun akun = new Akun();
                    User user = new User();

                    try {
                        JsonObject result = json.get("result").getAsJsonObject();

                        user.setId(result.get("id_pebisnis").getAsString());
                        user.setNama(result.get("nama_pebisnis").getAsString());
                        user.setNoTelp(result.get("no_telp").getAsString());
                        user.setFoto(result.get("foto").getAsString());

                        akun.setToken(result.get("token").getAsString());
                        akun.setUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return akun;
                })
                .doOnNext(akun -> PengelolaDataLokal.getInstance(context).simpanAkun(akun));
    }

    public Observable<Akun> updateProfilePetani(String nama, String foto) {
        return api.updateProfilePetani(PengelolaDataLokal.getInstance(context).getAkun().getToken(),
                nama, foto)
                .map(json -> {
                    Akun akun = new Akun();
                    User user = new User();

                    try {
                        JsonObject result = json.get("result").getAsJsonObject();

                        user.setId(result.get("id_petani").getAsString());
                        user.setNama(result.get("nama_petani").getAsString());
                        user.setNoTelp(result.get("no_telp").getAsString());
                        user.setFoto(result.get("foto").getAsString());

                        akun.setToken(result.get("token").getAsString());
                        akun.setUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return akun;
                })
                .doOnNext(akun -> PengelolaDataLokal.getInstance(context).simpanAkun(akun));
    }

    private interface Api {

        @FormUrlEncoded
        @POST("/pebisnis/verify-phone")
        Observable<JsonObject> verifyPhonePebisnis(@Field("no_telp") String noTelp);

        @FormUrlEncoded
        @POST("/petani/verify-phone")
        Observable<JsonObject> verifyPhonePetani(@Field("no_telp") String noTelp);

        @FormUrlEncoded
        @POST("/pebisnis/auth")
        Observable<JsonObject> authPebisnis(@Field("no_telp") String noTelp,
                                            @Field("request_id") String reqId,
                                            @Field("code") String code);

        @FormUrlEncoded
        @POST("/petani/auth")
        Observable<JsonObject> authPetani(@Field("no_telp") String noTelp,
                                          @Field("request_id") String reqId,
                                          @Field("code") String code);

        @FormUrlEncoded
        @PUT("/pebisnis/update-profile")
        Observable<JsonObject> updateProfilePebisnis(@Header("token") String token,
                                                     @Field("nama_pebisnis") String nama,
                                                     @Field("foto") String foto);

        @FormUrlEncoded
        @PUT("/petani/update-profile")
        Observable<JsonObject> updateProfilePetani(@Header("token") String token,
                                                   @Field("nama_petani") String nama,
                                                   @Field("foto") String foto);
    }
}

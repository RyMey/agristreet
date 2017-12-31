package id.agristreet.agristreetapp.data.remote;

/**
 * Created by RyMey on 12/10/17.
 */


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Akun;
import id.agristreet.agristreetapp.data.model.Kategori;
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.data.model.Lowongan;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public class RestApi {
    private static RestApi instance;
    private final Context context;
    private final OkHttpClient httpClient;
    private final Api api;
    private Gson gson;

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
                    try {
                        JsonObject result = json.get("result").getAsJsonObject();
                        akun.setToken(result.get("token").getAsString());
                        akun.setUser(ModelParser.parsePebisnis(result));
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
                    try {
                        JsonObject result = json.get("result").getAsJsonObject();
                        akun.setToken(result.get("token").getAsString());
                        akun.setUser(ModelParser.parsePetani(result));
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
                    Akun akun = PengelolaDataLokal.getInstance(context).getAkun();
                    try {
                        JsonObject result = json.get("result").getAsJsonObject();
                        akun.setUser(ModelParser.parsePebisnis(result));
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
                    Akun akun = PengelolaDataLokal.getInstance(context).getAkun();
                    try {
                        JsonObject result = json.get("result").getAsJsonObject();
                        akun.setUser(ModelParser.parsePetani(result));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return akun;
                })
                .doOnNext(akun -> PengelolaDataLokal.getInstance(context).simpanAkun(akun));
    }

    public Observable<List<Lowongan>> getLowongan() {
        return api.getLowongan(PengelolaDataLokal.getInstance(context).getAkun().getToken())
                .map(json -> {
                    JsonArray jsonArray = json.get("result").getAsJsonArray();
                    List<Lowongan> daftarLowongan = new ArrayList<>();
                    for (JsonElement jsonElement : jsonArray) {
                        daftarLowongan.add(ModelParser.parseLowongan(jsonElement.getAsJsonObject()));
                    }
                    return daftarLowongan;
                });
    }

    public Observable<List<Kerjasama>> getKerjasama() {
        return api.getKerjasama(PengelolaDataLokal.getInstance(context).getAkun().getToken())
                .map(json -> {
                    JsonArray jsonArray = json.get("result").getAsJsonArray();
                    List<Kerjasama> daftarKerjasama = new ArrayList<>();
                    for (JsonElement jsonElement : jsonArray) {
                        daftarKerjasama.add(ModelParser.parseKerjasama(jsonElement.getAsJsonObject()));
                    }
                    return daftarKerjasama;
                });
    }

    public Observable<Void> makeBid(int idLowongan, String keterangan, long harga) {
        return api.makeBid(PengelolaDataLokal.getInstance(context).getAkun().getToken(),
                idLowongan, keterangan, harga)
                .map(jsonObject -> null);
    }

    public Observable<List<Kategori>> getKategori() {
        return api.getKategori(PengelolaDataLokal.getInstance(context).getAkun().getToken())
                .map(json -> {
                    JsonArray jsonArray = json.get("result").getAsJsonArray();
                    List<Kategori> daftarKategori = new ArrayList<>();
                    for (JsonElement jsonElement : jsonArray) {
                        daftarKategori.add(ModelParser.parseKategori(jsonElement.getAsJsonObject()));
                    }
                    return daftarKategori;
                });
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

        @GET("/lowongan")
        Observable<JsonObject> getLowongan(@Header("token") String token);

        @GET("/kerjasama")
        Observable<JsonObject> getKerjasama(@Header("token") String token);

        @FormUrlEncoded
        @POST("/lamaran/make-lamaran-petani")
        Observable<JsonObject> makeBid(@Header("token") String token,
                                       @Field("id_lowongan") int idLowongan,
                                       @Field("deskripsi_lamaran") String keterangan,
                                       @Field("harga_tawar") long harga);

        @GET("/kategori")
        Observable<JsonObject> getKategori(@Header("token") String token);
    }
}

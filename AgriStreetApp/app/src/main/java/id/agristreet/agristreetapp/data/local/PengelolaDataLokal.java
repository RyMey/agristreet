package id.agristreet.agristreetapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import id.agristreet.agristreetapp.data.model.Akun;

/**
 * Created by RyMey on 12/17/17.
 */

public class PengelolaDataLokal {
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    private static PengelolaDataLokal instance;

    private PengelolaDataLokal(Context context) {
        sharedPreferences = context.getSharedPreferences("agristreet", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static PengelolaDataLokal getInstance(Context context) {
        if (instance == null) {
            instance = new PengelolaDataLokal(context);
        }
        return instance;
    }

    public void simpanNoTelp(String noTelp) {
        sharedPreferences.edit().putString("noTelp", noTelp).apply();
    }

    public String getNoTelp() {
        return sharedPreferences.getString("noTelp", null);
    }

    public void simpanAkun(Akun akun) {
        sharedPreferences.edit().putString("akun", gson.toJson(akun)).apply();
    }

    public Akun getAkun() {
        return gson.fromJson(sharedPreferences.getString("akun", null), Akun.class);
    }

    public void simpanRequestId(String reqId) {
        sharedPreferences.edit().putString("reqId", gson.toJson(reqId)).apply();
    }

    public String getReqId() {
        return sharedPreferences.getString("reqId", null);
    }


    public void cacheLastImagePath(String path) {
        sharedPreferences.edit().putString("last_image_path", path).apply();
    }

    public String getLastImagePath() {
        return sharedPreferences.getString("last_image_path", "");
    }

    public void clearData() {
        sharedPreferences.edit().clear().apply();
    }

}

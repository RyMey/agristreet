package id.agristreet.agristreetapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import id.agristreet.agristreetapp.data.model.Akun;
import id.agristreet.agristreetapp.data.model.KeywordSuggestion;

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
        Log.d("ZETRA", "Simpan req id: "+reqId);
        sharedPreferences.edit().putString("reqId", reqId).apply();
    }

    public String getReqId() {
        return sharedPreferences.getString("reqId", null);
    }

    public void simpanUserType(UserType userType) {
        sharedPreferences.edit().putString("user_type", userType.name()).apply();
    }

    public UserType getUserType() {
        return UserType.valueOf(sharedPreferences.getString("user_type", "PEBISNIS"));
    }

    public void cacheLastImagePath(String path) {
        sharedPreferences.edit().putString("last_image_path", path).apply();
    }

    public String getLastImagePath() {
        return sharedPreferences.getString("last_image_path", "");
    }

    public void addKeywordHistory(String keyword) {
        if (keyword == null || keyword.trim().equals("")) {
            return;
        }
        List<KeywordSuggestion> keywordSuggestions = getLastKeyword();
        KeywordSuggestion keywordSuggestion = new KeywordSuggestion(keyword);
        keywordSuggestions.remove(keywordSuggestion);
        keywordSuggestions.add(0, keywordSuggestion);
        if (keywordSuggestions.size() > 5) {
            keywordSuggestions = keywordSuggestions.subList(0, 5);
        }
        sharedPreferences.edit().putString("last_keywords", gson.toJson(keywordSuggestions)).apply();
    }

    public List<KeywordSuggestion> getLastKeyword() {
        List<KeywordSuggestion> keywordSuggestions = gson.fromJson(sharedPreferences.getString("last_keywords", ""),
                new TypeToken<List<KeywordSuggestion>>() {}.getType());
        if (keywordSuggestions == null) {
            keywordSuggestions = new ArrayList<>();
        }
        return keywordSuggestions;
    }

    public void clearData() {
        sharedPreferences.edit().clear().apply();
    }

    public enum UserType {
        PEBISNIS,
        PETANI
    }
}

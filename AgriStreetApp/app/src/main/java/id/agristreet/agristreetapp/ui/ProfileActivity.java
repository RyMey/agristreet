package id.agristreet.agristreetapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d("ZETRA", "Akun: " + PengelolaDataLokal.getInstance(this).getAkun());
    }
}

package id.agristreet.agristreetapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;

public class ChooseUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        ButterKnife.bind(this);
        if (PengelolaDataLokal.getInstance(this).getAkun() != null) {
            startActivity(new Intent(this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            overridePendingTransition(0, 0);
        }
    }

    @OnClick(R.id.pebisnis)
    public void openPebisnisLoginPage() {
        PengelolaDataLokal.getInstance(this).simpanUserType(PengelolaDataLokal.UserType.PEBISNIS);
        startActivity();
    }

    @OnClick(R.id.petani)
    public void openPetaniLoginPage() {
        PengelolaDataLokal.getInstance(this).simpanUserType(PengelolaDataLokal.UserType.PETANI);
        startActivity();
    }

    private void startActivity() {
        startActivity(new Intent(this, VerifyPhoneActivity.class));
    }
}

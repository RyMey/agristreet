package id.agristreet.agristreetapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;

public class ChooseUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.pebisnis)
    public void openPebisnisLoginPage() {
        startActivity(VerifyPhoneActivity.generateIntent(this, VerifyPhoneActivity.USER_TYPE_PEBISNIS));
    }

    @OnClick(R.id.petani)
    public void openPetaniLoginPage() {
        startActivity(VerifyPhoneActivity.generateIntent(this, VerifyPhoneActivity.USER_TYPE_PETANI));
    }
}

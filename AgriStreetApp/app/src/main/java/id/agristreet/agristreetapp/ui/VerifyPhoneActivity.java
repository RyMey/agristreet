package id.agristreet.agristreetapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.presenter.VerifyPhonePresenter;

public class VerifyPhoneActivity extends AppCompatActivity implements VerifyPhonePresenter.View {
    public static final int USER_TYPE_PEBISNIS = 1;
    public static final int USER_TYPE_PETANI = 2;
    private static final String USER_TYPE = "user_type";

    @BindView(R.id.et_nomor_telepon)
    EditText etNomorTelepon;
    @BindView(R.id.bt_masuk)
    Button btMasuk;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.img_pengusaha)
    ImageView ivUserType;
    @BindView(R.id.pebisnis)
    TextView tvUserType;

    private VerifyPhonePresenter presenter;
    private ProgressDialog progressDialog;

    private int userType;

    public static Intent generateIntent(Context context, int userType) {
        Intent intent = new Intent(context, VerifyPhoneActivity.class);
        intent.putExtra(USER_TYPE, userType);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        ButterKnife.bind(this);

        presenter = new VerifyPhonePresenter(this, this);
        progressDialog = new ProgressDialog(this);

        userType = getIntent().getIntExtra(USER_TYPE, 1);
        setupUserTypeView();
    }

    private void setupUserTypeView() {
        if (userType == USER_TYPE_PEBISNIS) {
            ivUserType.setImageResource(R.drawable.ic_pengusaha);
            tvUserType.setText(R.string.label_pebisnis);
        } else {
            ivUserType.setImageResource(R.drawable.ic_petani);
            tvUserType.setText(R.string.label_petani);
        }
    }

    @OnClick(R.id.ubah)
    public void changeUserType() {
        startActivity(new Intent(this, ChooseUserActivity.class));
    }

    @OnClick(R.id.bt_masuk)
    public void masuk() {
        if (etNomorTelepon.getText().length() >= 10) {
            presenter.verifyPhonePebisnis(etNomorTelepon.getText().toString());
        }
    }

    @OnClick(R.id.iv_cancel)
    public void cancelPhoneNumber() {
        etNomorTelepon.setText("");
    }

    @OnTextChanged(R.id.et_nomor_telepon)
    public void setNomorTelepon(CharSequence str) {
        if (str.length() >= 10) {
            btMasuk.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryColor));
            btMasuk.setTextColor(ContextCompat.getColor(this, R.color.primaryTextColor));
            ivCancel.setVisibility(View.VISIBLE);
            btMasuk.setEnabled(true);
        } else {
            btMasuk.setBackgroundColor(ContextCompat.getColor(this, R.color.divider));
            btMasuk.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
            ivCancel.setVisibility(View.INVISIBLE);
            btMasuk.setEnabled(false);
        }
    }

    @Override
    public void onVerifyPhoneSuccess() {
        Toast.makeText(this, "Sukses, yeeee!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.show();
    }

    @Override
    public void dismissLoading() {
        progressDialog.dismiss();
    }
}

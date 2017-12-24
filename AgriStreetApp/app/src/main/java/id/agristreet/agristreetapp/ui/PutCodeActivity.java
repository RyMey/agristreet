package id.agristreet.agristreetapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.presenter.PutCodePresenter;
import id.agristreet.agristreetapp.util.Util;

public class PutCodeActivity extends AppCompatActivity implements PutCodePresenter.View {

    @BindView(R.id.tv_desc_nomor)
    TextView tvDescNomor;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.et_kode_verifikasi)
    EditText etKodeVerifikasi;
    @BindView(R.id.bt_kirim_ulang)
    Button btKirimUlang;

    private String phoneNumber;
    private ProgressDialog progressDialog;

    private PutCodePresenter putCodePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_code);
        ButterKnife.bind(this);
        Util.hideKeyboard(this);
        phoneNumber = PengelolaDataLokal.getInstance(this).getNoTelp();
        tvDescNomor.setText(getString(R.string.desc_nomor_telepon) + " +62" + phoneNumber);

        putCodePresenter = new PutCodePresenter(this, this);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.iv_cancel)
    public void cancelPhoneNumber() {
        etKodeVerifikasi.setText("");

        btKirimUlang.setBackgroundColor(ContextCompat.getColor(this, R.color.divider));
        btKirimUlang.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
        btKirimUlang.setText("Kirim Ulang Kode");
        ivCancel.setVisibility(View.INVISIBLE);
        btKirimUlang.setEnabled(false);

        ivCancel.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.tv_ubah_nomor)
    public void ubahNomor() {
        Intent intent = new Intent(this, VerifyPhoneActivity.class);
        intent.putExtra("nomorTelepon", phoneNumber);
        startActivity(intent);
    }

    @OnTextChanged(R.id.et_kode_verifikasi)
    public void setNomorTelepon(CharSequence str) {
        if (str.length() <= 0) {
            btKirimUlang.setBackgroundColor(ContextCompat.getColor(this, R.color.primaryColor));
            btKirimUlang.setTextColor(ContextCompat.getColor(this, R.color.white));
            btKirimUlang.setText("Kirim Ulang Kode");
            btKirimUlang.setEnabled(false);
        } else {
            btKirimUlang.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryColor));
            btKirimUlang.setTextColor(ContextCompat.getColor(this, R.color.primaryTextColor));
            btKirimUlang.setText("Kirim");
            btKirimUlang.setEnabled(true);
        }

        if (etKodeVerifikasi.getText().toString().equals("")) {
            ivCancel.setVisibility(View.INVISIBLE);
        } else {
            ivCancel.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.bt_kirim_ulang)
    public void verifyCode() {
        if (btKirimUlang.getText().equals("Kirim")) {
            Util.hideKeyboard(this);
            putCodePresenter.verifyCode(etKodeVerifikasi.getText().toString());
        } else {
            btKirimUlang.setBackgroundColor(ContextCompat.getColor(this, R.color.divider));
            btKirimUlang.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
            btKirimUlang.setEnabled(false);
        }
    }

    @Override
    public void onVerifyCodeSuccess() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("isUbahProfile", false);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(btKirimUlang.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
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

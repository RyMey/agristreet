package id.agristreet.agristreetapp.ui;

import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.presenter.VerifyPhonePresenter;

public class VerifyPhoneActivity extends AppCompatActivity implements VerifyPhonePresenter.View {
    @BindView(R.id.et_nomor_telepon) EditText etNomorTelepon;
    @BindView(R.id.bt_masuk) Button btMasuk;
    @BindView(R.id.iv_cancel) ImageView ivCancel;

    private VerifyPhonePresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        ButterKnife.bind(this);

        presenter = new VerifyPhonePresenter(this, this);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.bt_masuk)
    public void masuk() {
        if (etNomorTelepon.getText().length() >= 10) {
            presenter.verifyPhonePebisnis(etNomorTelepon.getText().toString());
        }
    }

    @OnClick(R.id.iv_cancel)
    public void cancelPhoneNumber(){
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

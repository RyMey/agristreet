package id.agristreet.agristreetapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.presenter.AjukanBidPresenter;

public class AjukanBidActivity extends AppCompatActivity implements AjukanBidPresenter.View {

    @BindView(R.id.keterangaan)
    EditText etKeterangan;
    @BindView(R.id.harga_tawar)
    EditText etHarga;
    @BindView(R.id.bt_simpan)
    Button btSimpan;

    private int idLowongan;
    private AjukanBidPresenter ajukanBidPresenter;
    private ProgressDialog progressDialog;

    public static Intent generateIntent(Context context, int idLowongan) {
        Intent intent = new Intent(context, AjukanBidActivity.class);
        intent.putExtra("id_lowongan", idLowongan);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajukan_bid);
        idLowongan = getIntent().getIntExtra("id_lowongan", -1);
        if (idLowongan == -1) {
            return;
        }

        ButterKnife.bind(this);
        ajukanBidPresenter = new AjukanBidPresenter(this, this);
        progressDialog = new ProgressDialog(this);
    }

    @OnTextChanged(R.id.keterangaan)
    public void changeKeterangan() {
        nyalakanButtonSimpan();
    }

    @OnTextChanged(R.id.harga_tawar)
    public void changeHarga() {
        nyalakanButtonSimpan();
    }

    private void nyalakanButtonSimpan() {
        if (!etKeterangan.getText().toString().trim().isEmpty()
                && !etHarga.getText().toString().trim().isEmpty()) {
            btSimpan.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryColor));
            btSimpan.setTextColor(ContextCompat.getColor(this, R.color.primaryTextColor));
            btSimpan.setEnabled(true);
        } else {
            btSimpan.setBackgroundColor(ContextCompat.getColor(this, R.color.divider));
            btSimpan.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
            btSimpan.setEnabled(false);
        }
    }

    @OnClick(R.id.bt_simpan)
    public void simpan() {
        ajukanBidPresenter.bid(idLowongan, etKeterangan.getText().toString(), Long.parseLong(etHarga.getText().toString()));
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(etHarga.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
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

    @Override
    public void onBidSuccess() {
        new AlertDialog.Builder(this)
                .setMessage("Bid berhasil dibuat!")
                .setPositiveButton("OK", (dialog, which) -> {
                    setResult(Activity.RESULT_OK);
                    finish();
                })
                .show();
    }
}

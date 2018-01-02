package id.agristreet.agristreetapp.ui;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.data.model.Lamaran;
import id.agristreet.agristreetapp.presenter.DetailLamaranPresenter;
import id.agristreet.agristreetapp.util.CurrencyFormatter;

public class DetailLamaranActivity extends AppCompatActivity implements DetailLamaranPresenter.View {
    private static final String LAMARAN_KEY = "lamaran";
    private static final String ID_LOWOWNGAN_KEY = "id_lowongan";

    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.nama)
    TextView etNama;
    @BindView(R.id.sms)
    ImageView ivSms;
    @BindView(R.id.keterangan)
    TextView etKeterangan;
    @BindView(R.id.harga_tawar)
    TextView etHarga;
    @BindView(R.id.bt_pilih)
    Button btPilih;

    private int idLowongan;
    private Lamaran lamaran;

    private DetailLamaranPresenter detailLamaranPresenter;
    private ProgressDialog progressDialog;

    public static Intent generateIntent(Context context, int idLowongan, Lamaran lamaran) {
        Intent intent = new Intent(context, DetailLamaranActivity.class);
        intent.putExtra(ID_LOWOWNGAN_KEY, idLowongan);
        intent.putExtra(LAMARAN_KEY, lamaran);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lamaran);
        ButterKnife.bind(this);
        idLowongan = getIntent().getIntExtra(ID_LOWOWNGAN_KEY, -1);
        lamaran = getIntent().getParcelableExtra(LAMARAN_KEY);
        if (idLowongan == -1 || lamaran == null) {
            finish();
            return;
        }

        showLamaran();

        detailLamaranPresenter = new DetailLamaranPresenter(this, this);
        progressDialog = new ProgressDialog(this);
    }

    private void showLamaran() {
        Glide.with(this)
                .load(lamaran.getCreator().getFoto())
                .into(ivPhoto);
        etNama.setText(lamaran.getCreator().getNama());
        etKeterangan.setText(lamaran.getDescription());
        etHarga.setText(CurrencyFormatter.format(lamaran.getPrice()));
    }

    @OnClick(R.id.sms)
    public void sms() {
        Uri uri = Uri.parse("smsto:" + lamaran.getCreator().getNoTelp());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Tidak ada aplikasi SMS!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.bt_pilih)
    public void pilihPetani() {
        detailLamaranPresenter.makeKerjasama(idLowongan, lamaran.getId());
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(btPilih.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
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
    public void onKerjasamaCreated(Kerjasama kerjasama) {
        startActivity(DetailKerjasamaActivity.generateIntent(this, kerjasama, true));
    }
}

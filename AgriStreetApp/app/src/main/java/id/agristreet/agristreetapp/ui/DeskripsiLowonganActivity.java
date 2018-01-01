package id.agristreet.agristreetapp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Akun;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.util.CurrencyFormatter;
import id.agristreet.agristreetapp.util.DateUtil;
import id.agristreet.agristreetapp.util.Util;

public class DeskripsiLowonganActivity extends AppCompatActivity {
    private static final String LOWONGAN_KEY = "lowongan";
    private static final int RC_BID = 24;

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.judul)
    TextView judul;
    @BindView(R.id.kategori)
    TextView kategori;
    @BindView(R.id.tgl_tutup)
    TextView tglTutup;
    @BindView(R.id.deskripsi)
    TextView deskripsi;
    @BindView(R.id.jumlah_komoditas)
    TextView jumlahKomoditas;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.jumlah_pelamar)
    TextView jumlahPelamar;
    @BindView(R.id.pelamar)
    ImageView iconPelamar;
    @BindView(R.id.harga_awal)
    TextView harga;
    @BindView(R.id.bid)
    Button btBid;

    private Lowongan lowongan;
    private Akun akun;

    public static Intent generateIntent(Context context, Lowongan lowongan) {
        Intent intent = new Intent(context, DeskripsiLowonganActivity.class);
        intent.putExtra(LOWONGAN_KEY, lowongan);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deskripsi_lowongan);
        ButterKnife.bind(this);
        lowongan = getIntent().getParcelableExtra(LOWONGAN_KEY);
        if (lowongan == null) {
            finish();
            return;
        }

        akun = PengelolaDataLokal.getInstance(this).getAkun();
        if (PengelolaDataLokal.getInstance(this).getUserType() == PengelolaDataLokal.UserType.PEBISNIS) {
            if (lowongan.getCreator().getId().equals(akun.getUser().getId())) {
                iconPelamar.setVisibility(View.VISIBLE);
            }
            btBid.setVisibility(View.GONE);
        }

        showLowongan();
    }

    private void showLowongan() {
        imageView.setBackgroundColor(Util.randomColor());
        Glide.with(this)
                .load(lowongan.getImageUrl())
                .into(imageView);
        judul.setText(lowongan.getTitle());
        kategori.setText(lowongan.getKategori().getName());
        tglTutup.setText(DateUtil.format(lowongan.getExpiredAt()));
        deskripsi.setText(lowongan.getDescription());
        jumlahKomoditas.setText(String.format("%d", lowongan.getJumlahKomoditas()));
        alamat.setText(lowongan.getAlamat().getDeskripsi());
        jumlahPelamar.setText(String.format("%d", lowongan.getJumlahPelamar()));
        harga.setText(CurrencyFormatter.format(lowongan.getHargaAwal()));
        if (lowongan.isBid()) {
            btBid.setBackgroundColor(ContextCompat.getColor(this, R.color.divider));
            btBid.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
            btBid.setEnabled(false);
        }
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.bid)
    public void bid() {
        startActivityForResult(AjukanBidActivity.generateIntent(this, lowongan.getId()), RC_BID);
    }

    @OnClick(R.id.bt_pelamar)
    public void pilihPetani() {
        if (lowongan.getCreator().getId().equals(akun.getUser().getId())) {
            startActivity(PilihPetaniActivity.generateIntent(this, lowongan.getId()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_BID && resultCode == Activity.RESULT_OK) {
            lowongan.setBid(true);
            showLowongan();
        }
    }
}

package id.agristreet.agristreetapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.util.DateUtil;
import id.agristreet.agristreetapp.util.Util;

public class DeskripsiLowonganActivity extends AppCompatActivity {
    private static final String LOWONGAN_KEY = "lowongan";

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

    private Lowongan lowongan;

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
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}

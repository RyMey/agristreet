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
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.util.CurrencyFormatter;
import id.agristreet.agristreetapp.util.DateUtil;
import id.agristreet.agristreetapp.util.Util;

public class DetailKerjasamaActivity extends AppCompatActivity {
    private static final String KERJASAMA_KEY = "kerjasama";

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.tgl_kerja_sama)
    TextView tglKerjaSama;
    @BindView(R.id.pebisnis)
    TextView namaPebisnis;
    @BindView(R.id.petani)
    TextView namaPetani;
    @BindView(R.id.judul)
    TextView judul;
    @BindView(R.id.kategori)
    TextView kategori;
    @BindView(R.id.deskripsi)
    TextView deskripsi;
    @BindView(R.id.jumlah_komoditas)
    TextView jumlahKomoditas;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.harga_sepakat)
    TextView hargaSepakat;

    private Kerjasama kerjasama;

    public static Intent generateIntent(Context context, Kerjasama kerjasama) {
        Intent intent = new Intent(context, DetailKerjasamaActivity.class);
        intent.putExtra(KERJASAMA_KEY, kerjasama);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kerjasama);
        ButterKnife.bind(this);
        kerjasama = getIntent().getParcelableExtra(KERJASAMA_KEY);
        if (kerjasama == null) {
            finish();
            return;
        }

        showKerjasama();
    }

    private void showKerjasama() {
        imageView.setBackgroundColor(Util.randomColor());
        Glide.with(this)
                .load(kerjasama.getLowongan().getImageUrl())
                .into(imageView);
        status.setText(kerjasama.getStatus());
        judul.setText(kerjasama.getLowongan().getTitle());
        kategori.setText(kerjasama.getLowongan().getKategori().getName());
        tglKerjaSama.setText(DateUtil.format(kerjasama.getCreatedAt()));
        deskripsi.setText(kerjasama.getLowongan().getDescription());
        jumlahKomoditas.setText(String.format("%d", kerjasama.getLowongan().getJumlahKomoditas()));
        alamat.setText(kerjasama.getLowongan().getAlamat().getDeskripsi());
        namaPebisnis.setText(kerjasama.getLowongan().getCreator().getNama());
        namaPetani.setText(kerjasama.getPetani().getNama());
        hargaSepakat.setText(CurrencyFormatter.format(kerjasama.getPrice()));
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}

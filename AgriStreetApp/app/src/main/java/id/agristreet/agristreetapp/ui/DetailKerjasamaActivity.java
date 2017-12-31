package id.agristreet.agristreetapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import id.agristreet.agristreetapp.R;

public class DetailKerjasamaActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kerjasama);
    }
}

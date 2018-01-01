package id.agristreet.agristreetapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import id.agristreet.agristreetapp.R;

public class DetailLamaranActivity extends AppCompatActivity {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.nama)
    TextView etNama;
    @BindView(R.id.sms)
    ImageView ivSms;
    @BindView(R.id.keterangaan)
    TextView etKeterangan;
    @BindView(R.id.harga_tawar)
    TextView etHarga;
    @BindView(R.id.bt_pilih)
    Button btPilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lamaran);
    }
}

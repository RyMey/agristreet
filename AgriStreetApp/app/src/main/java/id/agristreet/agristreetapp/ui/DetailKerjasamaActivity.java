package id.agristreet.agristreetapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.presenter.DetailKerjasamaPresenter;
import id.agristreet.agristreetapp.util.CurrencyFormatter;
import id.agristreet.agristreetapp.util.DateUtil;
import id.agristreet.agristreetapp.util.Util;

public class DetailKerjasamaActivity extends AppCompatActivity implements DetailKerjasamaPresenter.View {
    private static final String KERJASAMA_KEY = "kerjasama";
    private static final String CLEAR_TASK_KEY = "clear_task";
    private static final int RC_FEEDBACK = 25;

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
    @BindView(R.id.finish)
    Button btFinish;

    private Kerjasama kerjasama;
    private boolean clearTask;
    private PengelolaDataLokal.UserType userType;

    private DetailKerjasamaPresenter detailKerjasamaPresenter;
    private ProgressDialog progressDialog;

    public static Intent generateIntent(Context context, Kerjasama kerjasama) {
        return generateIntent(context, kerjasama, false);
    }

    public static Intent generateIntent(Context context, Kerjasama kerjasama, boolean clearTask) {
        Intent intent = new Intent(context, DetailKerjasamaActivity.class);
        intent.putExtra(KERJASAMA_KEY, kerjasama);
        intent.putExtra(CLEAR_TASK_KEY, clearTask);
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

        clearTask = getIntent().getBooleanExtra(CLEAR_TASK_KEY, false);

        userType = PengelolaDataLokal.getInstance(this).getUserType();

        showKerjasama();

        detailKerjasamaPresenter = new DetailKerjasamaPresenter(this, this);
        progressDialog = new ProgressDialog(this);
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

        if (userType == PengelolaDataLokal.UserType.PETANI) {
            btFinish.setVisibility(View.GONE);
        }

        if (kerjasama.getStatus().equals("selesai")) {
            btFinish.setText(R.string.beri_feedback);
            btFinish.setVisibility(View.VISIBLE);
        }

        if (!kerjasama.isNeedFeedback()) {
            btFinish.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (clearTask) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.finish)
    public void finishKerjasama() {
        if (kerjasama.getStatus().equals("selesai")) {
            beriFeedBack();
        } else {
            detailKerjasamaPresenter.finisKerjasam(kerjasama.getId());
        }
    }

    private void beriFeedBack() {
        String userId;
        if (userType == PengelolaDataLokal.UserType.PETANI) {
            userId = kerjasama.getLowongan().getCreator().getId();
        } else {
            userId = kerjasama.getPetani().getId();
        }

        startActivityForResult(BeriFeedbackActivity.generateIntent(this, userId, kerjasama.getId()), RC_FEEDBACK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_FEEDBACK && resultCode == Activity.RESULT_OK) {
            kerjasama.setNeedFeedback(false);
            showKerjasama();
        }
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(btFinish.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
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
    public void onFinishKerjasamaSuccess() {
        new AlertDialog.Builder(this)
                .setMessage("Kerjasama diakhiri!")
                .setPositiveButton("OK", (dialog, which) -> {
                    kerjasama.setStatus("selesai");
                    showKerjasama();
                })
                .show();
    }
}

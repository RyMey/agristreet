package id.agristreet.agristreetapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lamaran;
import id.agristreet.agristreetapp.presenter.PilihPetaniPresenter;
import id.agristreet.agristreetapp.ui.adapter.LamaranAdapter;

public class PilihPetaniActivity extends AppCompatActivity implements PilihPetaniPresenter.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private int idLowongan;
    private PilihPetaniPresenter pilihPetaniPresenter;
    private ProgressDialog progressDialog;
    private LamaranAdapter lamaranAdapter;

    public static Intent generateIntent(Context context, int idLowongan) {
        Intent intent = new Intent(context, PilihPetaniActivity.class);
        intent.putExtra("id_lowongan", idLowongan);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_petani);
        idLowongan = getIntent().getIntExtra("id_lowongan", -1);
        if (idLowongan == -1) {
            return;
        }

        ButterKnife.bind(this);
        lamaranAdapter = new LamaranAdapter(this);
        lamaranAdapter.setOnItemClickListener((view, position) -> pilihLamaran(lamaranAdapter.getData().get(position)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(lamaranAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        pilihPetaniPresenter = new PilihPetaniPresenter(this, this);
        pilihPetaniPresenter.loadPelamar(idLowongan);
    }

    private void pilihLamaran(Lamaran lamaran) {
        //TODO
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(recyclerView.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void dismissLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showLamaran(List<Lamaran> daftarLamaran) {
        lamaranAdapter.addOrUpdate(daftarLamaran);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}

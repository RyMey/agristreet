package id.agristreet.agristreetapp.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Kategori;
import id.agristreet.agristreetapp.presenter.AddLowonganPresenter;
import id.agristreet.agristreetapp.ui.adapter.KategoriSpinnerAdapter;

public class AddLowonganActivity extends AppCompatActivity implements AddLowonganPresenter.View {

    @BindView(R.id.kategori)
    Spinner spinnerKategori;

    private AddLowonganPresenter addLowonganPresenter;
    private ProgressDialog progressDialog;

    private KategoriSpinnerAdapter kategoriSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lowongan);
        ButterKnife.bind(this);

        kategoriSpinnerAdapter = new KategoriSpinnerAdapter(this);
        kategoriSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(kategoriSpinnerAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        addLowonganPresenter = new AddLowonganPresenter(this, this);
        addLowonganPresenter.loadKategori();
    }

    @Override
    public void showError(String errorMessage) {

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
    public void showKategori(List<Kategori> daftarKatgori) {
        kategoriSpinnerAdapter.addAll(daftarKatgori);
    }
}

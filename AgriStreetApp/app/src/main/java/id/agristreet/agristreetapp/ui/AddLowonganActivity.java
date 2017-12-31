package id.agristreet.agristreetapp.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Alamat;
import id.agristreet.agristreetapp.data.model.Kategori;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.presenter.AddLowonganPresenter;
import id.agristreet.agristreetapp.ui.adapter.AlamatSpinnerAdapter;
import id.agristreet.agristreetapp.ui.adapter.KategoriSpinnerAdapter;
import id.agristreet.agristreetapp.util.FileUtil;
import id.agristreet.agristreetapp.util.Util;

public class AddLowonganActivity extends AppCompatActivity implements AddLowonganPresenter.View, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.kategori)
    Spinner spinnerKategori;
    @BindView(R.id.alamat)
    Spinner spinnerAlamat;
    @BindView(R.id.date_text)
    TextView tglTutup;
    @BindView(R.id.judul)
    EditText judul;
    @BindView(R.id.iv_photo)
    ImageView foto;
    @BindView(R.id.deskripsi)
    EditText deskripsi;
    @BindView(R.id.jumlah_komoditas)
    EditText jumlahKomoditas;
    @BindView(R.id.harga_awal)
    EditText hargaAwal;
    @BindView(R.id.simpan)
    Button btSimpan;

    private AddLowonganPresenter addLowonganPresenter;
    private ProgressDialog progressDialog;

    private KategoriSpinnerAdapter kategoriSpinnerAdapter;
    private AlamatSpinnerAdapter alamatSpinnerAdapter;

    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lowongan);
        ButterKnife.bind(this);

        kategoriSpinnerAdapter = new KategoriSpinnerAdapter(this);
        kategoriSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(kategoriSpinnerAdapter);

        alamatSpinnerAdapter = new AlamatSpinnerAdapter(this);
        alamatSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlamat.setAdapter(alamatSpinnerAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon Tunggu...");
        addLowonganPresenter = new AddLowonganPresenter(this, this);
        addLowonganPresenter.loadKategori();
        addLowonganPresenter.loadAlamat();
    }

    @OnClick(R.id.iv_input_photo)
    public void addFoto() {
        String[] inputPhotoOptions = {"From Camera", "From Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(inputPhotoOptions, (dialog, which) -> {
            if (which == 0) Util.openCamera(AddLowonganActivity.this);
            else if (which == 1) Util.openGallery(AddLowonganActivity.this);
            dialog.dismiss();
        }).show();
    }

    @OnClick(R.id.date)
    public void pickDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        c.add(Calendar.DAY_OF_MONTH, 1);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @OnTextChanged({R.id.date_text, R.id.judul, R.id.deskripsi, R.id.jumlah_komoditas, R.id.harga_awal})
    public void nyalakanButtonSimpan() {
        if (!tglTutup.getText().toString().equals(getString(R.string.tanggal_tutup))
                && !judul.getText().toString().trim().isEmpty()
                && !deskripsi.getText().toString().trim().isEmpty()
                && !jumlahKomoditas.getText().toString().trim().isEmpty()
                && !hargaAwal.getText().toString().trim().isEmpty()
                && imageFile != null) {
            btSimpan.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryColor));
            btSimpan.setTextColor(ContextCompat.getColor(this, R.color.primaryTextColor));
            btSimpan.setEnabled(true);
        } else {
            btSimpan.setBackgroundColor(ContextCompat.getColor(this, R.color.divider));
            btSimpan.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
            btSimpan.setEnabled(false);
        }
    }

    @OnClick(R.id.simpan)
    public void simpan() {
        addLowonganPresenter.createLowongan(
                kategoriSpinnerAdapter.getItem(spinnerKategori.getSelectedItemPosition()).getId(),
                alamatSpinnerAdapter.getItem(spinnerAlamat.getSelectedItemPosition()).getId(),
                judul.getText().toString(),
                imageFile,
                deskripsi.getText().toString(),
                tglTutup.getText().toString(),
                Integer.parseInt(jumlahKomoditas.getText().toString()),
                Long.parseLong(hargaAwal.getText().toString())
        );
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(spinnerAlamat.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
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

    @Override
    public void showAlamat(List<Alamat> daftarAlamat) {
        alamatSpinnerAdapter.addAll(daftarAlamat);
    }

    @Override
    public void onLowonganCreated(Lowongan lowongan) {

    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Util.CAMERA_REQUEST) {
            try {
                imageFile = FileUtil.from(this, Uri.parse(PengelolaDataLokal.getInstance(this)
                        .getLastImagePath()));
                Glide.with(this).load(imageFile).into(foto);
                nyalakanButtonSimpan();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == Util.GALLERY_REQUEST) {
            try {
                imageFile = FileUtil.from(this, data.getData());
                Glide.with(this).load(imageFile).into(foto);
                nyalakanButtonSimpan();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tglTutup.setText(String.format("%d-%02d-%02d", year, month + 1, dayOfMonth));
    }
}

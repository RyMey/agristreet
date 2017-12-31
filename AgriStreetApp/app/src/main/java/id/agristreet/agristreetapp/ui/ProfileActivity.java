package id.agristreet.agristreetapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Akun;
import id.agristreet.agristreetapp.presenter.ProfilePresenter;
import id.agristreet.agristreetapp.util.FileUtil;
import id.agristreet.agristreetapp.util.Util;

public class ProfileActivity extends AppCompatActivity implements ProfilePresenter.View {
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.bt_simpan)
    Button btSimpan;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_description)
    TextView tvDesc;
    @BindView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    private ProfilePresenter profilePresenter;
    private ProgressDialog progressDialog;
    private Akun akun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Util.hideKeyboard(this);

        tvPhoneNumber.setText(String.format("%s%s", getString(R.string.desc_nomor_telepon_profile),
                PengelolaDataLokal.getInstance(this).getNoTelp()));

        boolean isUbahProfile = getIntent().getBooleanExtra("isUbahProfile", false);
        if (isUbahProfile) {
            ivBack.setVisibility(View.VISIBLE);
            tvDesc.setVisibility(View.GONE);
        } else {
            ivBack.setVisibility(View.GONE);
            tvDesc.setVisibility(View.VISIBLE);
        }

        profilePresenter = new ProfilePresenter(this, this);
        progressDialog = new ProgressDialog(this);
        akun = PengelolaDataLokal.getInstance(this).getAkun();

        bindUserData();
    }

    private void bindUserData() {
        if (akun != null) {
            etNama.setText(akun.getUser().getNama());
            if (akun.getUser().getFoto() != null) {
                Glide.with(this)
                        .load(akun.getUser().getFoto())
                        .error(R.drawable.ic_person)
                        .placeholder(R.drawable.ic_person)
                        .dontAnimate()
                        .centerCrop().into(ivPhoto);
            } else {
                ivPhoto.setImageResource(R.drawable.ic_person);
            }
        }
    }

    @OnClick(R.id.iv_input_photo)
    public void inputPhoto() {
        String[] inputPhotoOptions = {"From Camera", "From Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(inputPhotoOptions, (dialog, which) -> {
            if (which == 0) Util.openCamera(ProfileActivity.this);
            else if (which == 1) Util.openGallery(ProfileActivity.this);
            dialog.dismiss();
        }).show();
    }

    @OnTextChanged(R.id.et_nama)
    public void changeNama() {
        nyalakanButtonSimpan();
    }

    private void nyalakanButtonSimpan() {
        if (!etNama.getText().toString().trim().isEmpty()) {
            btSimpan.setBackgroundColor(ContextCompat.getColor(this, R.color.secondaryColor));
            btSimpan.setTextColor(ContextCompat.getColor(this, R.color.primaryTextColor));
            btSimpan.setEnabled(true);
        } else {
            btSimpan.setBackgroundColor(ContextCompat.getColor(this, R.color.divider));
            btSimpan.setTextColor(ContextCompat.getColor(this, R.color.secondaryTextColor));
            btSimpan.setEnabled(false);
        }
    }

    @OnClick(R.id.bt_simpan)
    public void simpan() {
        profilePresenter.saveProfile(etNama.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Util.CAMERA_REQUEST) {
            try {
                File imageFile = FileUtil.from(this, Uri.parse(PengelolaDataLokal.getInstance(this)
                        .getLastImagePath()));
                profilePresenter.uploadAvatar(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == Util.GALLERY_REQUEST) {
            try {
                File imageFile = FileUtil.from(this, data.getData());
                profilePresenter.uploadAvatar(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(etNama.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
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
    public void onSavedProfile() {
        Snackbar.make(etNama.getRootView(), "Profil berhasil di ubah", Snackbar.LENGTH_LONG).show();

        if (ivBack.getVisibility() != View.VISIBLE) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onAvatarUploaded() {
        Snackbar.make(etNama.getRootView(), "Photo berhasil diubah", Snackbar.LENGTH_LONG).show();
        akun = PengelolaDataLokal.getInstance(this).getAkun();
        bindUserData();
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}

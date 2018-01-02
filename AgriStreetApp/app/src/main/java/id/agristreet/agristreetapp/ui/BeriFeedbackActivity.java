package id.agristreet.agristreetapp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.presenter.BeriFeedbackPresenter;

public class BeriFeedbackActivity extends AppCompatActivity implements BeriFeedbackPresenter.View {

    @BindView(R.id.happy)
    ImageView happy;
    @BindView(R.id.neutral)
    ImageView neutral;
    @BindView(R.id.sad)
    ImageView sad;
    @BindView(R.id.kesan)
    EditText kesan;
    @BindView(R.id.simpan)
    Button btSimpan;

    private int activeColor;
    private int deactiveColor;

    private String userId;
    private int tipeIkon;
    private BeriFeedbackPresenter beriFeedbackPresenter;
    private ProgressDialog progressDialog;

    public static Intent generateIntent(Context context, String userId) {
        Intent intent = new Intent(context, BeriFeedbackActivity.class);
        intent.putExtra("user_id", userId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beri_feedback);
        ButterKnife.bind(this);

        userId = getIntent().getStringExtra("user_id");
        if (userId == null) {
            return;
        }

        activeColor = ContextCompat.getColor(this, R.color.primaryColor);
        deactiveColor = ContextCompat.getColor(this, R.color.divider);
        happy();

        beriFeedbackPresenter = new BeriFeedbackPresenter(this, this);
        progressDialog = new ProgressDialog(this);
    }

    @OnClick(R.id.happy)
    public void happy() {
        tipeIkon = 0;
        happy.setColorFilter(activeColor, PorterDuff.Mode.SRC_ATOP);
        neutral.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
        sad.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
    }

    @OnClick(R.id.neutral)
    public void neutral() {
        tipeIkon = 1;
        happy.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
        neutral.setColorFilter(activeColor, PorterDuff.Mode.SRC_ATOP);
        sad.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
    }

    @OnClick(R.id.sad)
    public void sad() {
        tipeIkon = 2;
        happy.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
        neutral.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
        sad.setColorFilter(activeColor, PorterDuff.Mode.SRC_ATOP);
    }

    @OnTextChanged(R.id.kesan)
    public void nyalakanButtonSimpan() {
        if (!kesan.getText().toString().trim().isEmpty()) {
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
        beriFeedbackPresenter.sendFeedback(userId, kesan.getText().toString(), tipeIkon);
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(btSimpan.getRootView(), errorMessage, Snackbar.LENGTH_LONG).show();
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
    public void onFeedbackSent() {
        new AlertDialog.Builder(this)
                .setMessage("Feedback berhasil disimpan!")
                .setPositiveButton("OK", (dialog, which) -> {
                    setResult(Activity.RESULT_OK);
                    finish();
                })
                .show();
    }
}

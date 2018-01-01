package id.agristreet.agristreetapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
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

public class BeriFeedbackActivity extends AppCompatActivity {

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

        activeColor = ContextCompat.getColor(this, R.color.primaryColor);
        deactiveColor = ContextCompat.getColor(this, R.color.divider);
        happy();
    }

    @OnClick(R.id.happy)
    public void happy() {
        happy.setColorFilter(activeColor, PorterDuff.Mode.SRC_ATOP);
        neutral.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
        sad.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
    }

    @OnClick(R.id.neutral)
    public void neutral() {
        happy.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
        neutral.setColorFilter(activeColor, PorterDuff.Mode.SRC_ATOP);
        sad.setColorFilter(deactiveColor, PorterDuff.Mode.SRC_ATOP);
    }

    @OnClick(R.id.sad)
    public void sad() {
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
        //TODO
    }

    @OnClick(R.id.iv_back)
    public void back() {
        onBackPressed();
    }
}

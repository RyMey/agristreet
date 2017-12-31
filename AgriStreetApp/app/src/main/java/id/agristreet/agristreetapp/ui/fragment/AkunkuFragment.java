package id.agristreet.agristreetapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Akun;
import id.agristreet.agristreetapp.ui.ChooseUserActivity;
import id.agristreet.agristreetapp.ui.ProfileActivity;

public class AkunkuFragment extends Fragment {

    @BindView(R.id.nama)
    TextView tvName;
    @BindView(R.id.noTelp)
    TextView tvNoTelp;
    @BindView(R.id.foto)
    ImageView ivPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_akunku, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Akun akun = PengelolaDataLokal.getInstance(getActivity()).getAkun();
        tvName.setText(akun.getUser().getNama());
        tvNoTelp.setText(akun.getUser().getNoTelp());
        Glide.with(this)
                .load(akun.getUser().getFoto())
                .error(R.drawable.ic_person)
                .placeholder(R.drawable.ic_person)
                .dontAnimate()
                .into(ivPhoto);
    }

    @OnClick(R.id.iv_edit)
    public void editProfile() {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        intent.putExtra("isUbahProfile", true);
        startActivity(intent);
    }

    @OnClick(R.id.logout)
    public void logout() {
        PengelolaDataLokal.getInstance(getActivity()).clearData();
        Intent intent = new Intent(getActivity(), ChooseUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

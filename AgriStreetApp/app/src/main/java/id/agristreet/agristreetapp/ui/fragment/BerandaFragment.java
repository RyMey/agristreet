package id.agristreet.agristreetapp.ui.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.presenter.BerandaPresenter;

public class BerandaFragment extends Fragment implements BerandaPresenter.View {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private BerandaPresenter berandaPresenter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        ButterKnife.bind(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mohon Tunggu...");

        berandaPresenter = new BerandaPresenter(getActivity(), this);
        berandaPresenter.loadLowongan();
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
    public void showLowongan(List<Lowongan> daftarLowongan) {
        for (Lowongan lowongan : daftarLowongan) {
            Log.d("ZETRA", "Show: " + lowongan);
        }
    }
}

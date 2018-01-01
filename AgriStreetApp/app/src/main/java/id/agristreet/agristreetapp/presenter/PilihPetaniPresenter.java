package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import java.util.List;

import id.agristreet.agristreetapp.data.model.Lamaran;
import id.agristreet.agristreetapp.data.model.Petani;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class PilihPetaniPresenter extends BasePresenter<PilihPetaniPresenter.View> {

    private final Context context;

    public PilihPetaniPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void loadPelamar(int idLowongan) {
        view.showLoading();
        RestApi.getInstance(context)
                .getLamaran(idLowongan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(daftarLamaran -> {
                    view.showLamaran(daftarLamaran);
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void showLamaran(List<Lamaran> daftarLamaran);
    }
}

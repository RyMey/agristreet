package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import java.util.List;

import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class KerjasamaPresenter extends BasePresenter<KerjasamaPresenter.View> {

    private final Context context;

    public KerjasamaPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void loadKerjasama() {
        view.showLoading();
        RestApi.getInstance(context)
                .getKerjasama()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(daftarKerjasama -> {
                    view.showKerjasama(daftarKerjasama);
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void showKerjasama(List<Kerjasama> daftarKerjasama);
    }
}

package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class DetailLamaranPresenter extends BasePresenter<DetailLamaranPresenter.View> {

    private final Context context;

    public DetailLamaranPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void makeKerjasama(int idLowongan, int idLamaran) {
        view.showLoading();
        RestApi.getInstance(context)
                .makeKerjasama(idLowongan, idLamaran)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(kerjasama -> {
                    view.onKerjasamaCreated(kerjasama);
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void onKerjasamaCreated(Kerjasama kerjasama);
    }
}

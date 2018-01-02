package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class DetailKerjasamaPresenter extends BasePresenter<DetailKerjasamaPresenter.View> {

    private final Context context;

    public DetailKerjasamaPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void finisKerjasam(int idKerjasama) {
        view.showLoading();
        RestApi.getInstance(context)
                .finishKerjasama(idKerjasama)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    view.onFinishKerjasamaSuccess();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void onFinishKerjasamaSuccess();
    }
}

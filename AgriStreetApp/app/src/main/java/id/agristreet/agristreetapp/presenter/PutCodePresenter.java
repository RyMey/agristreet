package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class PutCodePresenter extends BasePresenter<PutCodePresenter.View> {

    private final Context context;

    public PutCodePresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void verifyCode(String code) {
        if (PengelolaDataLokal.getInstance(context).getUserType() == PengelolaDataLokal.UserType.PEBISNIS) {
            authPebisnis(code);
        }else {
            authPetani(code);
        }
    }

    private void authPebisnis(String code) {
        view.showLoading();
        RestApi.getInstance(context)
                .authPebisnis(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(akun -> {
                    view.onVerifyCodeSuccess();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    private void authPetani(String code) {
        view.showLoading();
        RestApi.getInstance(context)
                .authPetani(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(akun -> {
                    view.onVerifyCodeSuccess();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void onVerifyCodeSuccess();
    }
}

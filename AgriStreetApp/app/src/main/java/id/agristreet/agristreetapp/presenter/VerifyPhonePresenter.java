package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class VerifyPhonePresenter extends BasePresenter<VerifyPhonePresenter.View> {

    private final Context context;

    public VerifyPhonePresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void verifyPhone(String noTelp) {
        if (PengelolaDataLokal.getInstance(context).getUserType() == PengelolaDataLokal.UserType.PEBISNIS) {
            verifyPhonePebisnis(noTelp);
        } else {
            verifyPhonePetani(noTelp);
        }
    }

    private void verifyPhonePebisnis(String noTelp) {
        view.showLoading();
        RestApi.getInstance(context)
                .verifyPhonePebisnis(noTelp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(reqId -> {
                    view.onVerifyPhoneSuccess();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    private void verifyPhonePetani(String noTelp) {
        view.showLoading();
        RestApi.getInstance(context)
                .verifyPhonePetani(noTelp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(reqId -> {
                    view.onVerifyPhoneSuccess();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void onVerifyPhoneSuccess();
    }
}

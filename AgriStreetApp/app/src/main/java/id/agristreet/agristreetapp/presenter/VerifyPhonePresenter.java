package id.agristreet.agristreetapp.presenter;

import android.content.Context;

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

    public void verifyPhonePebisnis(String noTelp) {
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

    public interface View extends BasePresenter.View {
        void onVerifyPhoneSuccess();
    }
}

package id.agristreet.agristreetapp.presenter;

import android.content.Context;

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
        view.showLoading();
        RestApi.getInstance(context)
                .verifyPhonePebisnis(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(reqId -> {
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

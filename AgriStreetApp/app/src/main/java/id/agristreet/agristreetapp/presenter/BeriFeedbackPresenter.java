package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */
public class BeriFeedbackPresenter extends BasePresenter<BeriFeedbackPresenter.View> {

    private final Context context;

    public BeriFeedbackPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void sendFeedback(String userId, String saran, int tipeIkon) {
        if (PengelolaDataLokal.getInstance(context).getUserType() == PengelolaDataLokal.UserType.PEBISNIS) {
            sendFeedbackToPetani(userId, saran, tipeIkon);
        } else {
            sendFeedbackToPebisnis(userId, saran, tipeIkon);
        }
    }

    private void sendFeedbackToPetani(String userId, String saran, int tipeIkon) {
        view.showLoading();
        RestApi.getInstance(context)
                .sendFeedbackToPetani(userId, saran, tipeIkon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    view.onFeedbackSent();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    private void sendFeedbackToPebisnis(String userId, String saran, int tipeIkon) {
        view.showLoading();
        RestApi.getInstance(context)
                .sendFeedbackToPebisnis(userId, saran, tipeIkon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    view.onFeedbackSent();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void onFeedbackSent();
    }
}

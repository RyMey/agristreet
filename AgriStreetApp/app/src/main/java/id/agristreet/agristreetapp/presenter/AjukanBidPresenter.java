package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class AjukanBidPresenter extends BasePresenter<AjukanBidPresenter.View> {

    private final Context context;

    public AjukanBidPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void bid(int idLowongan, String keterangan, long harga) {
        view.showLoading();
        RestApi.getInstance(context)
                .makeBid(idLowongan, keterangan, harga)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(aVoid -> {
                    view.onBidSuccess();
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void onBidSuccess();
    }
}

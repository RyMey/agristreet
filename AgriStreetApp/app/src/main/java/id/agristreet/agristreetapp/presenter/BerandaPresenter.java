package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import java.util.List;

import id.agristreet.agristreetapp.data.local.PengelolaDataLokal;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */

public class BerandaPresenter extends BasePresenter<BerandaPresenter.View> {

    private final Context context;

    public BerandaPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void loadLowongan() {
        view.showLoading();
        RestApi.getInstance(context)
                .getLowongan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(daftarLowongan -> {
                    view.showLowongan(daftarLowongan);
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void showLowongan(List<Lowongan> daftarLowongan);
    }
}

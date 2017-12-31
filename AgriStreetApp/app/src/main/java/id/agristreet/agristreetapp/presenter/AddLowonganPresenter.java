package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import java.util.List;

import id.agristreet.agristreetapp.data.model.Kategori;
import id.agristreet.agristreetapp.data.remote.RestApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RyMey on 12/13/17.
 */
public class AddLowonganPresenter extends BasePresenter<AddLowonganPresenter.View> {

    private final Context context;

    public AddLowonganPresenter(Context context, View view) {
        super(view);
        this.context = context;
    }

    public void loadKategori() {
        view.showLoading();
        RestApi.getInstance(context)
                .getKategori()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(daftarKategori -> {
                    view.showKategori(daftarKategori);
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void showKategori(List<Kategori> daftarKatgori);
    }
}

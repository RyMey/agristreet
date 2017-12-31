package id.agristreet.agristreetapp.presenter;

import android.content.Context;

import java.io.File;
import java.util.List;

import id.agristreet.agristreetapp.data.model.Alamat;
import id.agristreet.agristreetapp.data.model.Kategori;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.data.remote.ImageUploader;
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

    public void loadAlamat() {
        view.showLoading();
        RestApi.getInstance(context)
                .getAlamat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(daftarAlamat -> {
                    view.showAlamat(daftarAlamat);
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public void createLowongan(int idKategori, int idAlamat, String title, File imageFile,
                               String description, String tglTutup, int jumlahKomoditas, long price) {
        view.showLoading();
        ImageUploader.getInstance(context)
                .upload(imageFile)
                .flatMap(imgUrl -> RestApi.getInstance(context)
                        .createLowongan(idKategori, idAlamat, title, imgUrl, description, tglTutup, jumlahKomoditas, price))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(lowongan -> {
                    view.onLowonganCreated(lowongan);
                    view.dismissLoading();
                }, throwable -> {
                    view.showError(throwable.getMessage());
                    view.dismissLoading();
                });
    }

    public interface View extends BasePresenter.View {
        void showKategori(List<Kategori> daftarKatgori);

        void showAlamat(List<Alamat> daftarAlamat);

        void onLowonganCreated(Lowongan lowongan);
    }
}

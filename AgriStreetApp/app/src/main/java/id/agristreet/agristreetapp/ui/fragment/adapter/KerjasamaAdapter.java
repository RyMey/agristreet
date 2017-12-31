package id.agristreet.agristreetapp.ui.fragment.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.ui.fragment.adapter.viewholder.KerjasamaViewHolder;
import rx.Observable;

public class KerjasamaAdapter extends BaseRecyclerAdapter<Kerjasama, KerjasamaViewHolder> {

    private List<Kerjasama> realData;

    public KerjasamaAdapter(Context context) {
        super(context);
        realData = new ArrayList<>();
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.item_kerjasama;
    }

    @Override
    public KerjasamaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KerjasamaViewHolder(getView(parent, viewType), itemClickListener, itemLongClickListener);
    }

    @Override
    public void addOrUpdate(List<Kerjasama> items) {
        super.addOrUpdate(items);
        updateRealData();
    }

    private void updateRealData() {
        realData.clear();
        realData.addAll(data);
    }

    public void filter(String keyword) {
        data = Observable.from(realData)
                .filter(kerjasama -> kerjasama.getLowongan().getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || kerjasama.getLowongan().getAlamat().getDeskripsi().toLowerCase().contains(keyword.toLowerCase())
                        || kerjasama.getLowongan().getCreator().getNama().contains(keyword.toLowerCase())
                        || kerjasama.getLowongan().getDescription().toLowerCase().contains(keyword.toLowerCase())
                        || kerjasama.getLowongan().getKategori().getName().contains(keyword.toLowerCase())
                        || kerjasama.getLowongan().getKategori().getDescription().contains(keyword.toLowerCase())

                )
                .toList()
                .toBlocking()
                .first();
        notifyDataSetChanged();
    }
}

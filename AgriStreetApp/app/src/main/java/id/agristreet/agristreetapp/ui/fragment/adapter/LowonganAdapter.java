package id.agristreet.agristreetapp.ui.fragment.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.ui.fragment.adapter.viewholder.LowonganViewHolder;
import rx.Observable;

public class LowonganAdapter extends BaseRecyclerAdapter<Lowongan, LowonganViewHolder> {

    private List<Lowongan> realData;

    public LowonganAdapter(Context context) {
        super(context);
        realData = new ArrayList<>();
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.item_lowongan;
    }

    @Override
    public LowonganViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LowonganViewHolder(getView(parent, viewType), itemClickListener, itemLongClickListener);
    }

    @Override
    public void addOrUpdate(List<Lowongan> items) {
        super.addOrUpdate(items);
        updateRealData();
    }

    private void updateRealData() {
        realData.clear();
        realData.addAll(data);
    }

    public void filter(String keyword) {
        data = Observable.from(realData)
                .filter(lowongan -> lowongan.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || lowongan.getAlamat().getDeskripsi().toLowerCase().contains(keyword.toLowerCase())
                        || lowongan.getCreator().getNama().contains(keyword.toLowerCase())
                        || lowongan.getDescription().toLowerCase().contains(keyword.toLowerCase())
                        || lowongan.getKategori().getName().contains(keyword.toLowerCase())
                        || lowongan.getKategori().getDescription().contains(keyword.toLowerCase())

                )
                .toList()
                .toBlocking()
                .first();
        notifyDataSetChanged();
    }
}

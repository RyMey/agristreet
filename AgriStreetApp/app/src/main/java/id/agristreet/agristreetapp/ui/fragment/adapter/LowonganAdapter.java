package id.agristreet.agristreetapp.ui.fragment.adapter;

import android.content.Context;
import android.view.ViewGroup;

import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.ui.fragment.adapter.viewholder.LowonganViewHolder;

public class LowonganAdapter extends BaseRecyclerAdapter<Lowongan, LowonganViewHolder> {

    public LowonganAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.item_lowongan;
    }

    @Override
    public LowonganViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LowonganViewHolder(getView(parent, viewType), itemClickListener, itemLongClickListener);
    }
}

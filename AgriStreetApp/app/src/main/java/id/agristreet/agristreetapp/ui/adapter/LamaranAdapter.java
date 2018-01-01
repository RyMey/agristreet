package id.agristreet.agristreetapp.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lamaran;
import id.agristreet.agristreetapp.ui.adapter.viewholder.LamaranViewHolder;

public class LamaranAdapter extends BaseRecyclerAdapter<Lamaran, LamaranViewHolder> {

    public LamaranAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemResourceLayout(int viewType) {
        return R.layout.item_lamaran;
    }

    @Override
    public LamaranViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LamaranViewHolder(getView(parent, viewType), itemClickListener, itemLongClickListener);
    }
}

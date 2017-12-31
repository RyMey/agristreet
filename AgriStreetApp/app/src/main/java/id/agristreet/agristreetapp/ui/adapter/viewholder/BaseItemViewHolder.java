package id.agristreet.agristreetapp.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;
import id.agristreet.agristreetapp.ui.adapter.ItemClickListener;
import id.agristreet.agristreetapp.ui.adapter.ItemLongClickListener;

public abstract class BaseItemViewHolder<Data> extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnLongClickListener {
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;

    public BaseItemViewHolder(View itemView, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public abstract void bind(Data data);

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (itemLongClickListener != null) {
            itemLongClickListener.onLongClick(v, getAdapterPosition());
            return true;
        }
        return false;
    }
}
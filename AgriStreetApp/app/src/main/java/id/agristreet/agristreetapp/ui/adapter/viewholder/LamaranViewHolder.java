package id.agristreet.agristreetapp.ui.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lamaran;
import id.agristreet.agristreetapp.ui.adapter.ItemClickListener;
import id.agristreet.agristreetapp.ui.adapter.ItemLongClickListener;
import id.agristreet.agristreetapp.util.CurrencyFormatter;

public class LamaranViewHolder extends BaseItemViewHolder<Lamaran> {

    @BindView(R.id.iv_photo)
    ImageView imageView;
    @BindView(R.id.tv_nama)
    TextView name;
    @BindView(R.id.tv_description)
    TextView description;
    @BindView(R.id.tv_harga_tawar)
    TextView price;

    public LamaranViewHolder(View itemView, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
        super(itemView, itemClickListener, itemLongClickListener);
    }

    @Override
    public void bind(Lamaran lamaran) {
        Glide.with(itemView.getContext())
                .load(lamaran.getCreator().getFoto())
                .into(imageView);
        name.setText(lamaran.getCreator().getNama());
        description.setText(lamaran.getDescription());
        price.setText(CurrencyFormatter.format(lamaran.getPrice()));
    }
}

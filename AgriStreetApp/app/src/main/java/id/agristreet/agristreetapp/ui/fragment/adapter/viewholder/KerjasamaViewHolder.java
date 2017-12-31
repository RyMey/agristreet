package id.agristreet.agristreetapp.ui.fragment.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.ui.fragment.adapter.ItemClickListener;
import id.agristreet.agristreetapp.ui.fragment.adapter.ItemLongClickListener;
import id.agristreet.agristreetapp.util.DateUtil;

public class KerjasamaViewHolder extends BaseItemViewHolder<Kerjasama> {

    @BindView(R.id.iv_photo)
    ImageView imageView;
    @BindView(R.id.tv_judul)
    TextView title;
    @BindView(R.id.tv_description)
    TextView description;
    @BindView(R.id.tv_status)
    TextView status;

    public KerjasamaViewHolder(View itemView, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
        super(itemView, itemClickListener, itemLongClickListener);
    }

    @Override
    public void bind(Kerjasama kerjasama) {
        Glide.with(itemView.getContext())
                .load(kerjasama.getLowongan().getImageUrl())
                .into(imageView);
        title.setText(kerjasama.getLowongan().getTitle());
        description.setText(DateUtil.format(kerjasama.getCreatedAt()));
        status.setText(kerjasama.getStatus());
    }
}

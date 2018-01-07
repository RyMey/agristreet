package id.agristreet.agristreetapp.ui.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import id.agristreet.agristreetapp.R;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.ui.adapter.ItemClickListener;
import id.agristreet.agristreetapp.ui.adapter.ItemLongClickListener;
import id.agristreet.agristreetapp.util.CurrencyFormatter;
import id.agristreet.agristreetapp.util.DateUtil;
import id.agristreet.agristreetapp.util.Util;

public class LowonganViewHolder extends BaseItemViewHolder<Lowongan> {

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.expired_date)
    TextView expiredDate;
    @BindView(R.id.kilos)
    TextView kilos;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.price)
    TextView price;

    public LowonganViewHolder(View itemView, ItemClickListener itemClickListener, ItemLongClickListener itemLongClickListener) {
        super(itemView, itemClickListener, itemLongClickListener);
    }

    @Override
    public void bind(Lowongan lowongan) {
        imageView.setBackgroundColor(Util.randomColor());
        Glide.with(itemView.getContext())
                .load(lowongan.getImageUrl())
                .into(imageView);
        expiredDate.setText("Tutup: " + DateUtil.format(lowongan.getExpiredAt()));
        kilos.setText("Butuh: "+lowongan.getJumlahKomoditas()+"kg");
        title.setText(lowongan.getTitle());
        long p = lowongan.getHargaAwal()/lowongan.getJumlahKomoditas();
        price.setText(CurrencyFormatter.format(p)+"/kg");
    }
}

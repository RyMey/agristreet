package id.agristreet.agristreetapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import id.agristreet.agristreetapp.data.model.Kategori;

public class KategoriSpinnerAdapter extends ArrayAdapter<Kategori> {
    public KategoriSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_spinner_item);
    }
}

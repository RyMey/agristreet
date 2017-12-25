package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;

import java.util.List;

/**
 * Created by RyMey on 12/10/17.
 */

public class Pebisnis extends User {
    private List<Alamat> daftarAlamat;

    public Pebisnis() {

    }

    protected Pebisnis(Parcel in) {
        super(in);
    }

    public static final Creator<Pebisnis> CREATOR = new Creator<Pebisnis>() {
        @Override
        public Pebisnis createFromParcel(Parcel in) {
            return new Pebisnis(in);
        }

        @Override
        public Pebisnis[] newArray(int size) {
            return new Pebisnis[size];
        }
    };

    public List<Alamat> getDaftarAlamat() {
        return daftarAlamat;
    }

    public void setDaftarAlamat(List<Alamat> daftarAlamat) {
        this.daftarAlamat = daftarAlamat;
    }

    @Override
    public String toString() {
        return "Pebisnis{" +
                "id='" + getId() + '\'' +
                ", nama='" + getNama() + '\'' +
                ", noTelp='" + getNoTelp() + '\'' +
                ", foto='" + getFoto() + '\'' +
                ", daftarAlamat=" + daftarAlamat +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}

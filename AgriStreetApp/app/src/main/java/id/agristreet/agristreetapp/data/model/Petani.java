package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;

/**
 * Created by RyMey on 12/17/17.
 */

public class Petani extends User {
    private Alamat alamat;

    public Petani() {

    }

    protected Petani(Parcel in) {
        super(in);
    }

    public static final Creator<Petani> CREATOR = new Creator<Petani>() {
        @Override
        public Petani createFromParcel(Parcel in) {
            return new Petani(in);
        }

        @Override
        public Petani[] newArray(int size) {
            return new Petani[size];
        }
    };

    public Alamat getAlamat() {
        return alamat;
    }

    public void setAlamat(Alamat alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return "Petani{" +
                "id='" + getId() + '\'' +
                ", nama='" + getNama() + '\'' +
                ", noTelp='" + getNoTelp() + '\'' +
                ", foto='" + getFoto() + '\'' +
                ", alamat=" + alamat +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}

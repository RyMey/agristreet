package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RyMey on 12/17/17.
 */

public class Alamat implements Parcelable {
    private int id;
    private String deskripsi;
    private String longitude;
    private String latitude;

    public Alamat() {

    }

    protected Alamat(Parcel in) {
        id = in.readInt();
        deskripsi = in.readString();
        longitude = in.readString();
        latitude = in.readString();
    }

    public static final Creator<Alamat> CREATOR = new Creator<Alamat>() {
        @Override
        public Alamat createFromParcel(Parcel in) {
            return new Alamat(in);
        }

        @Override
        public Alamat[] newArray(int size) {
            return new Alamat[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return deskripsi;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(deskripsi);
        dest.writeString(longitude);
        dest.writeString(latitude);
    }
}

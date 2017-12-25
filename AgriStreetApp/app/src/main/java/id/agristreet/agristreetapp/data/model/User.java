package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RyMey on 12/17/17.
 */

public class User implements Parcelable {
    private String id;
    private String nama;
    private String noTelp;
    private String foto;

    public User() {

    }

    protected User(Parcel in) {
        id = in.readString();
        nama = in.readString();
        noTelp = in.readString();
        foto = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nama='" + nama + '\'' +
                ", noTelp='" + noTelp + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nama);
        dest.writeString(noTelp);
        dest.writeString(foto);
    }
}

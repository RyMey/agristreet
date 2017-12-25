package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by RyMey on 12/17/17.
 */

public class Akun implements Parcelable {
    private User user;
    private String token;

    public Akun() {

    }

    protected Akun(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        token = in.readString();
    }

    public static final Creator<Akun> CREATOR = new Creator<Akun>() {
        @Override
        public Akun createFromParcel(Parcel in) {
            return new Akun(in);
        }

        @Override
        public Akun[] newArray(int size) {
            return new Akun[size];
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Akun{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeString(token);
    }
}

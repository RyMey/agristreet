package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Lamaran implements Parcelable {
    private int id;
    private Petani creator;
    private Date createdAt;
    private long price;
    private String description;

    public Lamaran() {

    }

    protected Lamaran(Parcel in) {
        id = in.readInt();
        creator = in.readParcelable(Petani.class.getClassLoader());
        createdAt = new Date(in.readLong());
        price = in.readLong();
        description = in.readString();
    }

    public static final Creator<Lamaran> CREATOR = new Creator<Lamaran>() {
        @Override
        public Lamaran createFromParcel(Parcel in) {
            return new Lamaran(in);
        }

        @Override
        public Lamaran[] newArray(int size) {
            return new Lamaran[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Petani getCreator() {
        return creator;
    }

    public void setCreator(Petani creator) {
        this.creator = creator;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lamaran)) return false;

        Lamaran lamaran = (Lamaran) o;

        return id == lamaran.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Lamaran{" +
                "id=" + id +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(creator, flags);
        dest.writeLong(createdAt.getTime());
        dest.writeLong(price);
        dest.writeString(description);
    }
}

package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Kerjasama implements Parcelable {
    private int id;
    private Date createdAt;
    private long price;
    private String status;
    private Lowongan lowongan;
    private Petani petani;
    private boolean needFeedback;

    public Kerjasama() {

    }

    protected Kerjasama(Parcel in) {
        id = in.readInt();
        createdAt = new Date(in.readLong());
        price = in.readLong();
        status = in.readString();
        lowongan = in.readParcelable(Lowongan.class.getClassLoader());
        petani = in.readParcelable(Petani.class.getClassLoader());
        needFeedback = in.readByte() == 1;
    }

    public static final Creator<Kerjasama> CREATOR = new Creator<Kerjasama>() {
        @Override
        public Kerjasama createFromParcel(Parcel in) {
            return new Kerjasama(in);
        }

        @Override
        public Kerjasama[] newArray(int size) {
            return new Kerjasama[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Lowongan getLowongan() {
        return lowongan;
    }

    public void setLowongan(Lowongan lowongan) {
        this.lowongan = lowongan;
    }

    public Petani getPetani() {
        return petani;
    }

    public void setPetani(Petani petani) {
        this.petani = petani;
    }

    public boolean isNeedFeedback() {
        return needFeedback;
    }

    public void setNeedFeedback(boolean needFeedback) {
        this.needFeedback = needFeedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kerjasama)) return false;

        Kerjasama kerjasama = (Kerjasama) o;

        return id == kerjasama.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Kerjasama{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", lowongan=" + lowongan +
                ", petani=" + petani +
                ", needFeedback=" + needFeedback +
                '}';
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeLong(createdAt.getTime());
        dest.writeLong(price);
        dest.writeString(status);
        dest.writeParcelable(lowongan, flags);
        dest.writeParcelable(petani, flags);
        dest.writeByte((byte) (needFeedback ? 0 : 1));
    }
}

package id.agristreet.agristreetapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Lowongan implements Parcelable {
    private int id;
    private Pebisnis creator;
    private Kategori kategori;
    private Date createdAt;
    private Date expiredAt;
    private String title;
    private String description;
    private String imageUrl;
    private long hargaAwal;
    private int jumlahKomoditas;
    private int jumlahPelamar;
    private Alamat alamat;
    private String status;

    public Lowongan() {

    }

    protected Lowongan(Parcel in) {
        id = in.readInt();
        creator = in.readParcelable(Pebisnis.class.getClassLoader());
        kategori = in.readParcelable(Kategori.class.getClassLoader());
        createdAt = new Date(in.readLong());
        expiredAt = new Date(in.readLong());
        title = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        hargaAwal = in.readLong();
        jumlahKomoditas = in.readInt();
        jumlahPelamar = in.readInt();
        alamat = in.readParcelable(Alamat.class.getClassLoader());
        status = in.readString();
    }

    public static final Creator<Lowongan> CREATOR = new Creator<Lowongan>() {
        @Override
        public Lowongan createFromParcel(Parcel in) {
            return new Lowongan(in);
        }

        @Override
        public Lowongan[] newArray(int size) {
            return new Lowongan[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pebisnis getCreator() {
        return creator;
    }

    public void setCreator(Pebisnis creator) {
        this.creator = creator;
    }

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getHargaAwal() {
        return hargaAwal;
    }

    public void setHargaAwal(long hargaAwal) {
        this.hargaAwal = hargaAwal;
    }

    public int getJumlahKomoditas() {
        return jumlahKomoditas;
    }

    public void setJumlahKomoditas(int jumlahKomoditas) {
        this.jumlahKomoditas = jumlahKomoditas;
    }

    public int getJumlahPelamar() {
        return jumlahPelamar;
    }

    public void setJumlahPelamar(int jumlahPelamar) {
        this.jumlahPelamar = jumlahPelamar;
    }

    public Alamat getAlamat() {
        return alamat;
    }

    public void setAlamat(Alamat alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lowongan)) return false;

        Lowongan lowongan = (Lowongan) o;

        return id == lowongan.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Lowongan{" +
                "id=" + id +
                ", creator=" + creator +
                ", kategori=" + kategori +
                ", createdAt=" + createdAt +
                ", expiredAt=" + expiredAt +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", hargaAwal=" + hargaAwal +
                ", jumlahKomoditas=" + jumlahKomoditas +
                ", jumlahPelamar=" + jumlahPelamar +
                ", alamat=" + alamat +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(creator, flags);
        dest.writeParcelable(kategori, flags);
        dest.writeLong(createdAt.getTime());
        dest.writeLong(expiredAt.getTime());
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeLong(hargaAwal);
        dest.writeInt(jumlahKomoditas);
        dest.writeInt(jumlahPelamar);
        dest.writeParcelable(alamat, flags);
        dest.writeString(status);
    }
}

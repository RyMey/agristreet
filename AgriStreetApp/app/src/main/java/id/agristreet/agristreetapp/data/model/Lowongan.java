package id.agristreet.agristreetapp.data.model;

import java.util.Date;

public class Lowongan {
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
}

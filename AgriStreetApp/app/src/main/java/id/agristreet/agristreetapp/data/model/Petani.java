package id.agristreet.agristreetapp.data.model;

/**
 * Created by RyMey on 12/17/17.
 */

public class Petani extends User {
    private Alamat alamat;

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
}

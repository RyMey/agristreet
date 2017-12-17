package id.agristreet.agristreetapp.data.model;

import java.util.List;

/**
 * Created by RyMey on 12/10/17.
 */

public class Pebisnis extends User{
    private List<Alamat> daftarAlamat;

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
}

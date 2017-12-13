package id.agristreet.agristreetapp.data.model;

/**
 * Created by RyMey on 12/10/17.
 */

public class Pebisnis {
    private String id;
    private String nama;
    private String token;
    private String noTelp;
    private String foto;

    public Pebisnis(String id, String nama, String token, String noTelp, String foto) {
        this.id = id;
        this.nama = nama;
        this.token = token;
        this.noTelp = noTelp;
        this.foto = foto;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}

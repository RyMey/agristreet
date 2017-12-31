package id.agristreet.agristreetapp.data.remote;

import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import id.agristreet.agristreetapp.data.model.Alamat;
import id.agristreet.agristreetapp.data.model.Kategori;
import id.agristreet.agristreetapp.data.model.Kerjasama;
import id.agristreet.agristreetapp.data.model.Lowongan;
import id.agristreet.agristreetapp.data.model.Pebisnis;
import id.agristreet.agristreetapp.data.model.Petani;

final class ModelParser {
    private static DateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    static Pebisnis parsePebisnis(JsonObject json) {
        Pebisnis pebisnis = new Pebisnis();
        pebisnis.setId(json.get("id_pebisnis").getAsString());
        pebisnis.setNama(json.get("nama_pebisnis").getAsString());
        pebisnis.setNoTelp(json.get("no_telp").getAsString());
        pebisnis.setFoto(json.get("foto").getAsString());
        return pebisnis;
    }

    static Petani parsePetani(JsonObject json) {
        Petani petani = new Petani();
        petani.setId(json.get("id_petani").getAsString());
        petani.setNama(json.get("nama_petani").getAsString());
        petani.setNoTelp(json.get("no_telp").getAsString());
        petani.setFoto(json.get("foto").getAsString());
        return petani;
    }

    static Alamat parseAlamat(JsonObject json) {
        Alamat alamat = new Alamat();
        alamat.setId(json.get("id_alamat").getAsInt());
        alamat.setDeskripsi(json.get("deskripsi").getAsString());
        alamat.setLatitude(json.get("latitude").getAsString());
        alamat.setLongitude(json.get("longitude").getAsString());
        return alamat;
    }

    static Kategori parseKategori(JsonObject json) {
        Kategori kategori = new Kategori();
        kategori.setId(json.get("id_kategori").getAsInt());
        kategori.setName(json.get("nama_kategori").getAsString());
        kategori.setDescription(json.get("deskripsi_kategori").getAsString());
        kategori.setImgUrl(json.get("foto").getAsString());
        return kategori;
    }

    static Lowongan parseLowongan(JsonObject json) {
        Lowongan lowongan = new Lowongan();
        lowongan.setId(json.get("id_lowongan").getAsInt());
        lowongan.setCreator(parsePebisnis(json.get("pebisnis").getAsJsonObject()));
        lowongan.setKategori(parseKategori(json.get("kategori").getAsJsonObject()));
        lowongan.setTitle(json.get("judul_lowongan").getAsString());
        lowongan.setDescription(json.get("deskripsi_lowongan").getAsString());
        lowongan.setImageUrl(json.get("foto").getAsString());
        lowongan.setHargaAwal(json.get("harga_awal").getAsInt());
        lowongan.setJumlahKomoditas(json.get("jumlah_komoditas").getAsInt());
        lowongan.setJumlahPelamar(json.get("pelamar").getAsInt());
        lowongan.setStatus(json.get("status_lowongan").getAsString());
        lowongan.setAlamat(parseAlamat(json.get("alamat").getAsJsonObject()));
        lowongan.setBid(json.get("isBid").getAsBoolean());
        try {
            lowongan.setCreatedAt(dateFormat.parse(json.get("tgl_buka").getAsString()));
            lowongan.setExpiredAt(dateFormat.parse(json.get("tgl_tutup").getAsString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lowongan;
    }

    static Kerjasama parseKerjasama(JsonObject json) {
        Kerjasama kerjasama = new Kerjasama();
        kerjasama.setId(json.get("id_kerjasama").getAsInt());
        kerjasama.setPrice(json.get("harga_sepakat").getAsLong());
        kerjasama.setStatus(json.get("status_lamaran").getAsString());
        kerjasama.setLowongan(parseLowongan(json.get("lowongan").getAsJsonObject()));
        try {
            kerjasama.setCreatedAt(dateFormat.parse(json.get("tgl_kerjasama").getAsString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return kerjasama;
    }
}

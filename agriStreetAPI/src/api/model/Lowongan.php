<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 11/2/17
 * Time: 10:01 PM
 */
namespace AgriStreet\Api\Model;

use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;

class Lowongan extends Model{
    const TABLE_NAME = "lowongan";
    const PRIMARY_KEY = "id_lowongan";
    public $timestamps = false;

    public $table = Lowongan::TABLE_NAME;
    public $primaryKey = Lowongan::PRIMARY_KEY;

    public static function makeLowongan($token,$id_kategori,$id_alamat_pengiriman,$judul_lowongan,$foto,$deskripsi_lowongan, $jumlah_komoditas, $tgl_tutup, $harga_awal){
        $pebisnis = Pebisnis::getPebisnisByToken($token);
        $kategori = KategoriKomoditas::query()
            ->where('id_kategori', '=', $id_kategori)
            ->first();
        $alamat = Alamat::getAlamatById($id_alamat_pengiriman);

        $lowongan = new Lowongan();

        if ($pebisnis == null){
            throw new \Exception("Pebisnis was not exist");
        } else if ($kategori == null){
            throw new \Exception("Kategori was not exist");
        } else if ( $alamat == null) {
            throw new \Exception("Alamat was not exist");
        } else {
            $lowongan->id_pebisnis = $pebisnis->id_pebisnis;
            $lowongan->id_kategori = $id_kategori;
            $lowongan->id_alamat_pengiriman = $id_alamat_pengiriman;
            $lowongan->judul_lowongan = $judul_lowongan;
            $lowongan->foto = $foto;
            $lowongan->deskripsi_lowongan = $deskripsi_lowongan;
            $lowongan->jumlah_komoditas = $jumlah_komoditas;
            $lowongan->tgl_buka = date("Y-m-d");
            $lowongan->tgl_tutup = $tgl_tutup;
            $lowongan->harga_awal = $harga_awal;
            $lowongan->status_lowongan = "buka";

            $lowongan->save();

            $lowongan->kategori = KategoriKomoditas::getKategoriKomoditas($lowongan->id_kategori);
            $lowongan->alamat = Alamat::getAlamatById($lowongan->id_alamat_pengiriman);
            $lowongan->pebisnis = Pebisnis::getPebisnis($lowongan->id_pebisnis);
            $lowongan->pelamar = count(LamaranPetani::getLamaranByLowongan($lowongan->id_lowongan));

            $petani = Petani::getPetaniByToken($token);
            $exist = LamaranPetani::getLamaran($lowongan->id_lowongan,$petani->id_petani);
            if($exist==null or $petani==null)
                $lowongan->isBid = false;
            else
                $lowongan->isBid = true;

            return $lowongan;
        }
    }

    public static function getLowongan($id_lowongan, $token){
        $lowongan = Manager::table(Lowongan::TABLE_NAME)->where(Lowongan::PRIMARY_KEY, '=', $id_lowongan)
            ->first();

        $lowongan->kategori = KategoriKomoditas::getKategoriKomoditas($lowongan->id_kategori);
        $lowongan->alamat = Alamat::getAlamatById($lowongan->id_alamat_pengiriman);
        $lowongan->pebisnis = Pebisnis::getPebisnis($lowongan->id_pebisnis);
        $lowongan->pelamar = count(LamaranPetani::getLamaranByLowongan($lowongan->id_lowongan));

        $petani = Petani::getPetaniByToken($token);
        $exist = LamaranPetani::getLamaran($lowongan->id_lowongan,$petani->id_petani);
        if($exist==null or $petani==null)
            $lowongan->isBid = false;
        else
            $lowongan->isBid = true;

        return $lowongan;
    }

    public static function getAllLowongan($token){

        $lowongans = Manager::table(Lowongan::TABLE_NAME)
                    ->where('tgl_tutup','>=',date("Y-m-d"))
                    ->get();

        foreach ($lowongans as $lowongan) {
            $lowongan->kategori = KategoriKomoditas::getKategoriKomoditas($lowongan->id_kategori);
            $lowongan->alamat = Alamat::getAlamatById($lowongan->id_alamat_pengiriman);
            $lowongan->pebisnis = Pebisnis::getPebisnis($lowongan->id_pebisnis);
            $lowongan->pelamar = count(LamaranPetani::getLamaranByLowongan($lowongan->id_lowongan));

            $petani = Petani::getPetaniByToken($token);
            $exist = LamaranPetani::getLamaran($lowongan->id_lowongan,$petani->id_petani);
            if($exist==null or $petani==null)
                $lowongan->isBid = false;
            else
                $lowongan->isBid = true;

        }

        return $lowongans;

    }

    public static function updateLowongan($token, $id_lowongan, $id_alamat_pengiriman,$judul_lowongan,$deskripsi_lowongan, $jumlah_komoditas, $tgl_buka, $tgl_tutup, $harga_awal, $status){
        $pebisnis = Pebisnis::getPebisnisByToken($token);
        $lowongan = Lowongan::query()
            ->where(Lowongan::PRIMARY_KEY, '=', $id_lowongan)
            ->first();

        if($lowongan == null){
            throw new \Exception("Lowongan was not exist");
        }else if($pebisnis == null){
            throw new \Exception("Session expired");
        }else{
            if($id_alamat_pengiriman != null){
                $alamat = Alamat::getAlamatById($id_alamat_pengiriman);
                if($alamat!=null){
                    $lowongan->id_alamat_pengiriman = $id_alamat_pengiriman;
                }
            }
            if($judul_lowongan!=null){
                $lowongan->judul_lowongan = $judul_lowongan;
            }
            if($deskripsi_lowongan!=null){
                $lowongan->deskripsi_lowongan = $deskripsi_lowongan;
            }
            if($jumlah_komoditas!=null){
                $lowongan->jumlah_komoditas = $jumlah_komoditas;
            }
            if($tgl_buka!=null){
                $lowongan->tgl_buka = $tgl_buka;
            }
            if($tgl_tutup!=null){
                $lowongan->tgl_tutup = $tgl_tutup;
            }
            if($harga_awal!=null){
                $lowongan->harga_awal = $harga_awal;
            }
            if($status!=null){
                $lowongan->status = $status;
            }
            $lowongan->save();
        }

        return self::getLowongan($id_lowongan, $token);
    }

    public static function searchLowongan($keywoard){
        $lowongans = Manager::table(Lowongan::TABLE_NAME)
            ->where(Lowongan::PRIMARY_KEY,'LIKE', "%{$keywoard}%")
            ->orWhere('id_pebisnis', 'LIKE', "%{$keywoard}%")
            ->orWhere('id_kategori', 'LIKE', "%{$keywoard}%")
            ->orWhere('id_alamat_pengiriman', 'LIKE', "%{$keywoard}%")
            ->orWhere('judul_lowongan', 'LIKE', "%{$keywoard}%")
            ->orWhere('deskripsi_lowongan', 'LIKE', "%{$keywoard}%")
            ->orWhere('jumlah_komoditas', 'LIKE', "%{$keywoard}%")
            ->orWhere('tgl_buka','LIKE', "%{$keywoard}%")
            ->orWhere('tgl_tutup', 'LIKE', "%{$keywoard}%")
            ->orWhere('harga_awal', 'LIKE', "%{$keywoard}%")
            ->orWhere('status_lowongan', 'LIKE', "%{$keywoard}%")
            ->get();

        return $lowongans;
    }

    public static function getLowonganByPebisnis($token){
        $pebisnis = Pebisnis::getPebisnisByToken($token);

        if($pebisnis==null)
            throw new \Exception("Pebisnis was not exist");

        $lowongan = Manager::table(Lowongan::TABLE_NAME)->where('id_pebisnis', '=', $pebisnis->id_pebisnis)
            ->get();

        $lowongan->kategori = KategoriKomoditas::getKategoriKomoditas($lowongan->id_kategori);
        $lowongan->alamat = Alamat::getAlamatById($lowongan->id_alamat_pengiriman);
        $lowongan->pebisnis = Pebisnis::getPebisnis($lowongan->id_pebisnis);
        $lowongan->pelamar = count(LamaranPetani::getLamaranByLowongan($lowongan->id_lowongan));

        $petani = Petani::getPetaniByToken($token);
        $exist = LamaranPetani::getLamaran($lowongan->id_lowongan,$petani->id_petani);
        if($exist==null or $petani==null)
            $lowongan->isBid = false;
        else
            $lowongan->isBid = true;

        return $lowongan;
    }
}
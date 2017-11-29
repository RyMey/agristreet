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

    public static function makeLowongan($token,$id_kategori,$id_alamat_pengiriman,$judul_lowongan,$deskripsi_lowongan, $jumlah_komoditas, $tgl_buka, $tgl_tutup, $harga_awal, $status){
        $pebisnis = Pebisnis::getPebisnisByToken($token);
        $kategori = KategoriKomoditas::query()
            ->where('id_kategori', '=', $id_kategori)
            ->first();
        $alamat = Alamat::getAlamatById($id_alamat_pengiriman);

        $lowongan = new Lowongan();

        if ($pebisnis == null){
            throw new \Exception("Kategori was not exist");
        } else if ($kategori == null){
            throw new \Exception("Kategori was not exist");
        } else if ( $alamat == null) {
            throw new \Exception("Alamat was not exist");
        } else {
            $lowongan->id_pebisnis = $pebisnis->id_pebisnis;
            $lowongan->id_kategori = $id_kategori;
            $lowongan->id_alamat_pengiriman = $id_alamat_pengiriman;
            $lowongan->judul_lowongan = $judul_lowongan;
            $lowongan->deskripsi_lowongan = $deskripsi_lowongan;
            $lowongan->jumlah_komoditas = $jumlah_komoditas;
            $lowongan->tgl_buka = $tgl_buka;
            $lowongan->tgl_tutup = $tgl_tutup;
            $lowongan->harga_awal = $harga_awal;
            $lowongan->status_lowongan = $status;

            $lowongan->save();
            $result = Manager::table(Lowongan::TABLE_NAME)
                ->first([Lowongan::PRIMARY_KEY, "deskripsi_lowongan", "jumlah_komoditas", "tgl_buka", "tgl_tutup"]);
            return $result;
        }
    }

    public static function getLowongan($id_lowongan){
        $lowongan = Manager::table(Lowongan::TABLE_NAME)->where(Lowongan::PRIMARY_KEY, '=', $id_lowongan)
            ->first([Lowongan::TABLE_NAME . '.' . Lowongan::PRIMARY_KEY,
                Lowongan::TABLE_NAME . '.deskripsi_lowongan',
                Lowongan::TABLE_NAME . '.jumlah_komoditas',
                Lowongan::TABLE_NAME . '.tgl_buka',
                Lowongan::TABLE_NAME . '.tgl_tutup']);

        return $lowongan;
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

        return self::getLowongan($id_lowongan);
    }
}
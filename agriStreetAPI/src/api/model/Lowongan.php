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

    public $table = Lowongan::TABLE_NAME;
    public $primaryKey = Lowongan::PRIMARY_KEY;

    public static function makeAlamat($id_pebisnis,$id_kategori,$id_alamat_pengiriman,$judul_lowongan,$deskripsi_lowongan, $jumlah_komoditas, $tgl_buka, $tgl_tutup, $harga_awal){
        $lowongan = new Lowongan();
        $lowongan->id_pebisnis= $id_pebisnis;
        $lowongan->id_kategori = $id_kategori;
        $lowongan->id_alamat_pengiriman = $id_alamat_pengiriman;
        $lowongan->judul_lowongan = $judul_lowongan;
        $lowongan->deskripsi_lowongan = $deskripsi_lowongan;
        $lowongan->jumlah_komoditas = $jumlah_komoditas;
        $lowongan->tgl_buka = $tgl_buka;
        $lowongan->tgl_tutup = $tgl_tutup;

        $lowongan->save();
        $result = Manager::table(Lowongan::TABLE_NAME)
            ->first([Lowongan::PRIMARY_KEY, "deskripsi_lowongan", "jumlah_komoditas", "tgl_buka", "tgl_tutup"]);
        return $result;
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
}
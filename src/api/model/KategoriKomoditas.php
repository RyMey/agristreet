<?php

namespace AgriStreet\Api\Model;

use Illuminate\Contracts\Debug\ExceptionHandler;
use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;


class KategoriKomoditas extends Model{
	const TABLE_NAME = "kategori_komoditas";
	const PRIMARY_KEY = "id_kategori";

	public $table = KategoriKomoditas::TABLE_NAME;
	public $primaryKey = KategoriKomoditas::PRIMARY_KEY;

	public static function getKategoriKomoditas($id_kategori){


		$kategori = Manager::table(KategoriKomoditas::TABLE_NAME)->where(KategoriKomoditas::PRIMARY_KEY, '=', $id_kategori)
            ->first([KategoriKomoditas::TABLE_NAME . '.' . KategoriKomoditas::PRIMARY_KEY,
                KategoriKomoditas::TABLE_NAME . '.nama_kategori',
                KategoriKomoditas::TABLE_NAME . '.deskripsi_komoditas',
                KategoriKomoditas::TABLE_NAME . '.foto_kategori',
            ]);
        return $kategori;
	}
}

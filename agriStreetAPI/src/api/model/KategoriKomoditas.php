<?php

namespace AgriStreet\Api\Model;

use Illuminate\Contracts\Debug\ExceptionHandler;
use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;

class KategoriKomoditas extends Model{
	const TABLE_NAME = "kategori_komoditas";
	const PRIMARY_KEY = "id_kategori";
    public $timestamps = false;

	public $table = KategoriKomoditas::TABLE_NAME;
	public $primaryKey = KategoriKomoditas::PRIMARY_KEY;

	public static function getKategoriKomoditas($id_kategori){
		$kategori = Manager::table(KategoriKomoditas::TABLE_NAME)
            ->where(KategoriKomoditas::PRIMARY_KEY, '=', $id_kategori)
            ->first();
        return $kategori;
	}

    public static function getKategori(){
        $kategoris = Manager::table(KategoriKomoditas::TABLE_NAME)
            ->get();
        return $kategoris;
    }
}

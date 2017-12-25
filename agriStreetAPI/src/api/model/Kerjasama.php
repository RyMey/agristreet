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

class Kerjasama extends Model{

    const TABLE_NAME = "kerjasama";
    const PRIMARY_KEY = "id_kerjasama";
    public $timestamps = true;

    public $table = Kerjasama::TABLE_NAME;
    public $primaryKey = Kerjasama::PRIMARY_KEY;

    public static function makeKerjasama($token,$id_petani,$id_lowongan,$status_lamaran,$tgl_kerjasama,$harga_sepakat){
        $pebisnis = Pebisnis::getPebisnisByToken($token);
       
        $kerjasama = new Kerjasama();

        if ($pebisnis == null){
            throw new \Exception("Kategori was not exist");
        } else {
            $kerjasama->id_pebisnis = $pebisnis->id_pebisnis;
            $kerjasama->id_petani = $id_petani;
            $kerjasama->id_lowongan = $id_lowongan;
            $kerjasama->id_lowongan = $status_lamaran;
            $kerjasama->id_lowongan = $tgl_kerjasama;
            $kerjasama->id_lowongan = $harga_sepakat;
           
            $kerjasama->save();

            $result = Manager::table(Kerjasama::TABLE_NAME)
                ->first([Kerjasama::PRIMARY_KEY, "id_petani", "status_lamaran", "tgl_kerjasama", "harga_sepakat"]);
            return $result;
        }
    }

    public static function getKerjasama($id_kerjasama){
        $kerjasama = Manager::table(Kerjasama::TABLE_NAME)->where(Kerjasama::PRIMARY_KEY, '=', $id_kerjasama)
            ->first();

        return $kerjasama;
    }

    public static function updateStatusLamaran($token,$id_kerjasama, $status_lamaran){
         $pebisnis = Pebisnis::getPebisnisByToken($token);
        $kerjasama = Kerjasama::query()
            ->where(Kerjasama::PRIMARY_KEY, '=', $id_kerjasama)
            ->first();

        if($kerjasama == null){
            throw new \Exception("Kerjsama was not exist");
        }else if($pebisnis == null){
            throw new \Exception("Session expired");
        }else{
           
            if($status_lamaran!=null){
                $kerjasama->status_lamaran = $status_lamaran;
            }
            $kerjasama->save();
        }

        return self::getKerjasama($id_kerjasama);
    }
}
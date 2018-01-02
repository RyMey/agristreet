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
    public $timestamps = false;

    public $table = Kerjasama::TABLE_NAME;
    public $primaryKey = Kerjasama::PRIMARY_KEY;

    public static function getAllKerjasama($token){

        $user = Petani::getPetaniByToken($token);
        $kerjasamas = null;

        if($user == null) {
            $user = Pebisnis::getPebisnisByToken($token);
            $kerjasamas = Manager::table(Kerjasama::TABLE_NAME)
                ->where('id_pebisnis','=',$user->id_pebisnis)
                ->get();
        }else{
            $kerjasamas = Manager::table(Kerjasama::TABLE_NAME)
                ->where('id_petani','=',$user->id_petani)
                ->get();
        }

        if($user == null) {
            throw new \Exception("User not exist");
        }

        foreach ($kerjasamas as $kerjasama) {
            $kerjasama->lowongan = Lowongan::getLowongan($kerjasama->id_lowongan, $token);
            $petani = Petani::getPetani($kerjasama->id_petani);
            $kerjasama->petani = $petani;
            $kerjasama->butuh_feedback = Feedback::getFeedbackByPengirim($token, $kerjasama->id_kerjasama) == null;
        }

        return $kerjasamas;

    }

    public static function makeKerjasama($token,$id_lowongan,$id_lamaran){
        $pebisnis = Pebisnis::getPebisnisByToken($token);
        $lamaran = LamaranPetani::getLamaranById($id_lamaran);
       
        $kerjasama = new Kerjasama();

        if ($pebisnis == null){
            throw new \Exception("Pebisnis was not exist");
        } else {
            $kerjasama->id_pebisnis = $pebisnis->id_pebisnis;
            $kerjasama->id_petani = $lamaran->id_petani;
            $kerjasama->id_lowongan = $id_lowongan;
            $kerjasama->tgl_kerjasama = date("Y-m-d");
            $kerjasama->harga_sepakat = $lamaran->harga_tawar;
            $kerjasama->status_lamaran = "proses";
           
            $kerjasama->save();

            Lowongan::finishLowongan($token, $id_lowongan);

            return Kerjasama::getKerjasama($token, $kerjasama->id_kerjasama);
        }
    }

    public static function getKerjasama($token, $id_kerjasama){
        $kerjasama = Manager::table(Kerjasama::TABLE_NAME)->where(Kerjasama::PRIMARY_KEY, '=', $id_kerjasama)
            ->first();

        $kerjasama->lowongan = Lowongan::getLowongan($kerjasama->id_lowongan, $token);
        $petani = Petani::getPetani($kerjasama->id_petani);
        $kerjasama->petani = $petani;
        $kerjasama->butuh_feedback = true;

        return $kerjasama;
    }

    public static function finishKerjasama($token,$id_kerjasama){
        $pebisnis = Pebisnis::getPebisnisByToken($token);
        $kerjasama = Kerjasama::query()
            ->where(Kerjasama::PRIMARY_KEY, '=', $id_kerjasama)
            ->first();

        if($kerjasama == null){
            throw new \Exception("Kerjsama was not exist");
        }else if($pebisnis == null){
            throw new \Exception("Session expired");
        }else{
            $kerjasama->status_lamaran = "selesai";
            $kerjasama->save();
        }

        return self::getKerjasama($id_kerjasama);
    }
}
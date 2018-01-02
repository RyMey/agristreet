<?php

namespace AgriStreet\Api\Model;

use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;

class Feedback extends Model{
    const TABLE_NAME = "feedback";
    const PRIMARY_KEY = "id_feedback";
    public $timestamps = false;

    public $table = Feedback::TABLE_NAME;
    public $primaryKey = Feedback::PRIMARY_KEY;


    public static function makeFeedback($token,$id_penerima,$id_kerjasama,$saran,$tipe_ikon){
        $penerimaIsPetani = false;
        $petani = Petani::getPetaniByToken($token);
        $pebisnis = Pebisnis::getPebisnis($id_penerima);
        $kerjasama = Kerjasama::getKerjasama($token,$id_kerjasama);

        if($petani == null or $pebisnis == null){
            $penerimaIsPetani = true;
            $petani = Petani::getPetani($id_penerima);
            $pebisnis = Pebisnis::getPebisnisByToken($token);
        }

        $feedback = new Feedback();

        if($petani == null or $pebisnis == null){
            throw new \Exception("User not exist");
        } else {
            if($penerimaIsPetani){
                $feedback->id_pengirim = $pebisnis->id_pebisnis;
                $feedback->id_penerima = $petani->id_petani;
            }else{
                $feedback->id_pengirim = $petani->id_pebisnis;
                $feedback->id_penerima = $pebisnis->id_petani;
            }
            $feedback->id_kerjasama = $kerjasama->id_kerjasama;
            $feedback->saran = $saran;
            $feedback->tipe_ikon = $tipe_ikon;

            $feedback->save();

            return $feedback;
        }
    }

    public static function getFeedbackById($id_feedback){
        $feedback = Manager::table(Feedback::TABLE_NAME)->where('id_feedback', '=', $id_feedback)
            ->first();
        return $feedback;
    }

    public static function getFeedbackByPenerima($token){
        $penerimaIsPetani = false;
        $user = Pebisnis::getPebisnisByToken($token);

        if($user == null) {
            $user = Petani::getPetaniByToken($token);
            $penerimaIsPetani = true;
        }

        if($user == null)
            throw new \Exception("Session was expired");

        if($penerimaIsPetani) {
            $feedback = Manager::table(Feedback::TABLE_NAME)->where('id_penerima', '=', $user->id_petani)
                ->get();
        }else{
            $feedback = Manager::table(Feedback::TABLE_NAME)->where('id_penerima', '=', $user->id_pebisnis)
                ->get();
        }
        return $feedback;
    }
}

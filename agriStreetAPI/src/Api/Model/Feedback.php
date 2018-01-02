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
        $pengirim = Petani::getPetaniByToken($token);
        $penerima = Pebisnis::getPebisnis($id_penerima);
        $kerjasama = Kerjasama::getKerjasama($token,$id_kerjasama);

        if ($pengirim == null) {
            $pengirim = Pebisnis::getPebisnisByToken($token);
            $penerima = Petani::getPetani($id_penerima);
        }

        $feedback = new Feedback();

        if($pengirim == null or $penerima == null) {
            throw new \Exception("User not exist");
        } else {
            if(isset($pengirim->id_pebisnis)) {
                $feedback->id_pengirim = $pengirim->id_pebisnis;
                $feedback->id_penerima = $penerima->id_petani;
            } else {
                $feedback->id_pengirim = $pengirim->id_petani;
                $feedback->id_penerima = $penerima->id_pebisnis;
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
        $user = Pebisnis::getPebisnisByToken($token);

        if($user == null) {
            $user = Petani::getPetaniByToken($token);
        }

        if($user == null)
            throw new \Exception("Session was expired");

        if(isset($pengirim->id_petani)) {
            $feedback = Manager::table(Feedback::TABLE_NAME)->where('id_penerima', '=', $user->id_petani)
                ->get();
        }else{
            $feedback = Manager::table(Feedback::TABLE_NAME)->where('id_penerima', '=', $user->id_pebisnis)
                ->get();
        }
        return $feedback;
    }
}
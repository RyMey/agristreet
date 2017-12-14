<?php

namespace AgriStreet\Api\Model;

use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;

class FeedbackPebisnis extends Model{
    const TABLE_NAME = "feedback_pebisnis";
    const PRIMARY_KEY = "id_feedback";
    //public $timestamps = false;

    public $table = FeedbackPebisnis::TABLE_NAME;
    public $primaryKey = FeedbackPebisnis::PRIMARY_KEY;


    public static function makeFeedbackPetani($id_feedback,$id_pebisnis,$saran,$tipe_ikon){
        $petani = Pebisnis::getPebisnisById($id_pebisnis);
        $feedback = new FeedbackPebisnis();

        if ($pebisnis == null){
            throw new \Exception("Pebisnis was not exist");
        } else {
            $feedback->id_pebisnis = $pebisnis->id_pebisnis;
            $feedback->saran = $saran;
            $feedback->tipe_ikon = $tipe_ikon;

            $feedback->save();
            $result = Manager::table(FeedbackPebisnis::TABLE_NAME)
                ->first([FeedbackPebisnis::PRIMARY_KEY, "id_pebisnis","saran", "tipe_ikon"]);
            return $result;
        }
    }

    public static function getFeedPebisnis($id_feedback){
        $feedback = Manager::table(FeedbackPebisnis::TABLE_NAME)->where(FeedbackPebisnis::PRIMARY_KEY, '=', $id_feedback)
            ->first([FeedbackPebisnis::TABLE_NAME . '.' . FeedbackPebisnis::PRIMARY_KEY,
                FeedbackPebisnis::TABLE_NAME . '.id_pebisnis',
                FeedbackPebisnis::TABLE_NAME . '.saran',
                FeedbackPebisnis::TABLE_NAME . '.tipe_ikon']);
        return $feedback;
    }
}

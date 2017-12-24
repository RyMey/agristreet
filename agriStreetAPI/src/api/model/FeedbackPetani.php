<?php

namespace AgriStreet\Api\Model;

use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;

class FeedbackPetani extends Model{
    const TABLE_NAME = "feedback_petani";
    const PRIMARY_KEY = "id_feedback";
    //public $timestamps = false;

    public $table = FeedbackPetani::TABLE_NAME;
    public $primaryKey = FeedbackPetani::PRIMARY_KEY;


    public static function makeFeedbackPetani($id_feedback,$id_petani,$saran,$tipe_ikon){
        $petani = Petani::getPetaniById($id_petani);
        $feedback = new FeedbackPetani();

        if ($petani == null){
            throw new \Exception("Petani was not exist");
        } else {
            $feedback->id_petani = $petani->id_petani;
            $feedback->saran = $saran;
            $feedback->tipe_ikon = $tipe_ikon;

            $feedback->save();
            $result = Manager::table(FeedbackPetani::TABLE_NAME)
                ->first([FeedbackPetani::PRIMARY_KEY, "id_petani","saran", "tipe_ikon"]);
            return $result;
        }
    }

    public static function getFeedPetani($id_feedback){
        $feedback = Manager::table(FeedbackPetani::TABLE_NAME)->where(FeedbackPetani::PRIMARY_KEY, '=', $id_feedback)
            ->first([FeedbackPetani::TABLE_NAME . '.' . FeedbackPetani::PRIMARY_KEY,
                FeedbackPetani::TABLE_NAME . '.id_petani',
                FeedbackPetani::TABLE_NAME . '.saran',
                FeedbackPetani::TABLE_NAME . '.tipe_ikon']);
        return $feedback;
    }
}
<?php

namespace AgriStreet\Api\Model;

use Illuminate\Contracts\Debug\ExceptionHandler;
use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;
use \AgriStreet\Api\Model\Petani;
use \AgriStreet\Api\Model\Lowongan;


class LamaranPetani extends Model{
    const TABLE_NAME = "lamaran_petani";
    const PRIMARY_KEY = "id_lamar";
    public $timestamps = false;

    public $table = LamaranPetani::TABLE_NAME;
    public $primaryKey = LamaranPetani::PRIMARY_KEY;

    public static function makeLamaranPetani($token,$id_lowongan,$harga_tawar,$deskripsi_lamaran){
        $petani = Petani::getPetaniByToken($token);

        $lowongan = Lowongan::getLowongan($id_lowongan, $token);

        $lamaran = new LamaranPetani();

        if ($petani == null){
            throw new \Exception("Petani was not exist");
        } else if ($lowongan == null){
            throw new \Exception("Lowongan was not exist");
        } else {
            $lamaran->id_petani = $petani->id_petani;
            $lamaran->id_lowongan = $lowongan->id_lowongan;
            $lamaran->tgl_lamar = date("Y-m-d");
            $lamaran->harga_tawar = $harga_tawar;
            $lamaran->deskripsi_lamaran = $deskripsi_lamaran;

            $lamaran->save();
            return $lamaran;
        }
    }

    public static function getLamaranByLowongan($id_lowongan){
        $lamarans =  Manager::table(LamaranPetani::TABLE_NAME)->where('id_lowongan', '=', $id_lowongan)
                    ->get();

        return $lamarans;
    }

    public static function getLamaran($id_lowongan,$id_petani){
        $lamaran =  Manager::table(LamaranPetani::TABLE_NAME)
                    ->where('id_lowongan', '=', $id_lowongan)
                    ->where('id_petani','=',$id_petani)
                    ->first();

        return $lamaran;
    }

    public static function getLamaranById($id_lamar){
        $lamaran = Manager::table(LamaranPetani::TABLE_NAME)->where(LamaranPetani::PRIMARY_KEY, '=', $id_lamar)
            ->first([LamaranPetani::TABLE_NAME . '.' . LamaranPetani::PRIMARY_KEY,
                LamaranPetani::TABLE_NAME . '.id_petani',
                LamaranPetani::TABLE_NAME . '.id_lowongan',
				LamaranPetani::TABLE_NAME . '.tgl_lamar',
                LamaranPetani::TABLE_NAME . '.harga_tawar',
                LamaranPetani::TABLE_NAME . '.deskripsi_lamaran']);
        return $lamaran;
    }
	
	public static function getLamaranByPetani($token) {
        $petani = Manager::table(Petani::TABLE_NAME)
            ->where('token', '=', $token)
            ->first([Petani::PRIMARY_KEY]);
    
    
        if($petani != null or $token != null or $token !=''){
             $result2 = Manager::table(LamaranPetani::TABLE_NAME)->where(LamaranPetani::TABLE_NAME.'.id_petani', '=', $petani)
                ->first([LamaranPetani::TABLE_NAME . '.' . LamaranPetani::PRIMARY_KEY,
                    LamaranPetani::TABLE_NAME . '.id_petani',
                    LamaranPetani::TABLE_NAME . '.tgl_lamar',
                    LamaranPetani::TABLE_NAME . '.harga_tawar',
                    LamaranPetani::TABLE_NAME . '.deskripsi_lamaran']);
            return $result2;
        }else{
            throw new \Exception("Petani is not exist");
        }
    }	
	 
}
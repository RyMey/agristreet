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

    public $table = Alamat::TABLE_NAME;
    public $primaryKey = Alamat::PRIMARY_KEY;

    public static function makeLamaran($id_lamar,$id_petani,$id_lowongan,$tgl_lamar,$harga_tawar,$deskripsi_lamaran){
        $lamaran = new LamaranPetani();
        $lamaran->id_petani= $id_petani;
        $lamaran->id_lowongan = $id_lowongan;
        $lamaran->tgl_lamar = $tgl_lamar;
        $lamaran->harga_tawar = $harga_tawar;
        $lamaran->deskripsi_lamaran = $deskripsi_lamaran;
        
      
        $lamaran->save();
        $result = Manager::table(LamaranPetani::TABLE_NAME)
            ->first([Alamat::PRIMARY_KEY, "tgl_lamar", "harga_tawar", "deskripsi_lamaran"]);
        return $result;
    }

    public static function getLamaranById($id_lamaran){
        $lamaran = Manager::table(LamaranPetani::TABLE_NAME)->where(LamaranPetani::PRIMARY_KEY, '=', $id_lamaran)
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
            ->first([Petani::PRIMARY_KEY, "id_petani"]);
	
	
		if($petani != null){
		 $result2 = Manager::table(LamaranPetani::TABLE_NAME)->where(LamaranPetani::id_petani, '=', $id_petani)
            ->first([LamaranPetani::TABLE_NAME . '.' . LamaranPetani::PRIMARY_KEY,
                LamaranPetani::TABLE_NAME . '.id_petani',
                LamaranPetani::TABLE_NAME . '.id_lowongan',
                LamaranPetani::TABLE_NAME . '.tgl_lamar',
                LamaranPetani::TABLE_NAME . '.harga_tawar',
                LamaranPetani::TABLE_NAME . '.deskripsi_lamaran']);
        return $result2;
		}
    }	
	 
}
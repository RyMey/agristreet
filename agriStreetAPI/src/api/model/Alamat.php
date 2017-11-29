<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 11/2/17
 * Time: 10:01 PM
 */

namespace AgriStreet\Api\Model;

use Illuminate\Contracts\Debug\ExceptionHandler;
use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;
use \AgriStreet\Api\Model\Pebisnis;


class Alamat extends Model{
    const TABLE_NAME = "alamat";
    const PRIMARY_KEY = "id_alamat";

    public $table = Alamat::TABLE_NAME;
    public $primaryKey = Alamat::PRIMARY_KEY;

    public static function makeAlamat($id_pebisnis,$deskripsi,$latitude,$longitude){
        $pebisnis = Pebisnis::query()
            ->where('id_pebisnis', '=', $id_pebisnis)
            ->first();

        if($id_pebisnis == null or $id_pebisnis == '' or $pebisnis == null) {
            throw new \Exception("Pebisnis is not exist");
        }else{
            $alamat = new Alamat();
            $alamat->id_pebisnis = $id_pebisnis;
            $alamat->deskripsi = $deskripsi;
            $alamat->latitude = $latitude;
            $alamat->longitude = $longitude;

            $alamat->save();
            $result = Manager::table(Alamat::TABLE_NAME)
                ->first([Alamat::PRIMARY_KEY, "deskripsi", "latitude", "longitude"]);
            return $result;
        }
    }

    public static function getAlamatById($id_alamat){
        $alamat = Manager::table(Alamat::TABLE_NAME)->where(Alamat::PRIMARY_KEY, '=', $id_alamat)
            ->first([Alamat::TABLE_NAME . '.' . Alamat::PRIMARY_KEY,
                Alamat::TABLE_NAME . '.id_pebisnis',
                Alamat::TABLE_NAME . '.deskripsi',
				Alamat::TABLE_NAME . '.latitude',
                Alamat::TABLE_NAME . '.longitude']);

        return $alamat;
    }
	
	public static function getAlamatByPebisnis($token) {
        $pebisnis = Manager::table(Pebisnis::TABLE_NAME)
            ->where('token', '=', $token)
            ->first([Pebisnis::PRIMARY_KEY]);
	
	
		if($pebisnis != null or $token != null or $token !=''){
             $result2 = Manager::table(Alamat::TABLE_NAME)->where(Alamat::TABLE_NAME.'.id_pebisnis', '=', $pebisnis)
                ->first([Alamat::TABLE_NAME . '.' . Alamat::PRIMARY_KEY,
                    Alamat::TABLE_NAME . '.id_pebisnis',
                    Alamat::TABLE_NAME . '.alamat',
                    Alamat::TABLE_NAME . '.latitude',
                    Alamat::TABLE_NAME . '.longitude']);
            return $result2;
		}else{
            throw new \Exception("Pebisnis is not exist");
        }
    }
	
	public static function updateAlamat($token, $id_alamat, $alamat, $latitude, $longitude) {
        $pebisnis = Pebisnis::query()
            ->where('token', '=', $token)
            ->first();
			
			
		$alamat2 = Alamat::query()->where('id_alamat', '=', $id_alamat)->first();
      
			
        if ($token == null or $token == "" or $pebisnis== null) {
            throw new \Exception("Session expired, please re-login");
        } else if($alamat2 == null){
            throw new \Exception("Alamat is not exist");
        } else {
			$alamat2->alamat = $alamat;
			$alamat2->latitude = $latitude;
			$alamat2->longitude = $longitude;
            $alamat2->save();
            
        }
    }
	 
}
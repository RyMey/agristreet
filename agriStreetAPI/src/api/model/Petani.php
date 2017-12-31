<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 11/2/17
 * Time: 12:26 PM
 */
namespace AgriStreet\Api\Model;

use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;

class Petani extends Model{
    const TABLE_NAME = "petani";
    const PRIMARY_KEY = "id_petani";
    public $timestamps = false;

    public $table = Petani::TABLE_NAME;
    public $primaryKey = Petani::PRIMARY_KEY;

    public static function generateId(){
        $lastId = Manager::table(Petani::TABLE_NAME)
            ->orderBy(Petani::PRIMARY_KEY,'DESC')
            ->limit(1)
            ->first([Petani::PRIMARY_KEY]);

        if($lastId != null){
            $id = (int) substr($lastId,2);
            $id = $id + 1;
            $new_id = 'pt'.$id;
        }else{
            $new_id = 'pt1';
        }
        return $new_id;
    }

    public static function getPetani($id_petani){
        $petani = Manager::table(Petani::TABLE_NAME)->where(Petani::PRIMARY_KEY, '=', $id_petani)
            ->first([Petani::TABLE_NAME . '.' . Petani::PRIMARY_KEY,
                Petani::TABLE_NAME . '.nama_petani',
                Petani::TABLE_NAME . '.no_telp',
                Petani::TABLE_NAME . '.foto']);
        return $petani;
    }

    public static function getSelfPetani($id_petani){
        $petani = Manager::table(Petani::TABLE_NAME)->where(Petani::PRIMARY_KEY, '=', $id_petani)
            ->first([Petani::TABLE_NAME . '.' . Petani::PRIMARY_KEY,
                Petani::TABLE_NAME . '.nama_petani',
                Petani::TABLE_NAME . '.no_telp',
                Petani::TABLE_NAME . '.foto',
                Petani::TABLE_NAME . '.token']);
        return $petani;
    }

    public static function getPetaniByToken($token) {
        $petani = Manager::table(Petani::TABLE_NAME)->where('token', '=', $token)
            ->first([Petani::TABLE_NAME . '.' . Petani::PRIMARY_KEY,
                Petani::TABLE_NAME . '.nama_petani',
                Petani::TABLE_NAME . '.no_telp',
                Petani::TABLE_NAME . '.foto',
                Petani::TABLE_NAME . '.token']);
        return $petani;
    }

    public static function getIdByPhone($no_telp){
        $id = Manager::table(Petani::TABLE_NAME)->where('no_telp', '=', $no_telp)
            ->first([Petani::TABLE_NAME . '.' . Petani::PRIMARY_KEY]);
        return $id;
    }

    public static function verifyPhone($no_telp){
        $ch = curl_init();
        curl_setopt($ch,CURLOPT_URL,"https://Api.nexmo.com/verify/json");
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS,"api_key=467d8cb9&api_secret=496197a14e8c1c48&number=$no_telp&brand=AgriStreet");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);

        $raw_data = curl_exec($ch);
        curl_close($ch);

        $data = json_decode($raw_data);
        return $data->request_id;
    }

    public static function auth($no_telp, $request_id, $code){
        $ch = curl_init();
        curl_setopt($ch,CURLOPT_URL,"https://Api.nexmo.com/verify/check/json");
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS,"api_key=b6b121fd&api_secret=e26e5acecfe7189f&request_id=$request_id&code=$code");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER,1);

        $raw_data = curl_exec($ch);
        curl_close($ch);

        $data = json_decode($raw_data);
        $status = $data->status;

        if($status == 0){
            $id = self::getIdByPhone($no_telp);
            if($id != null or $id != ""){
                return self::getSelfPetani($id->id_petani);
            }else{
                return self::register("",$no_telp,"","","","");
            }
        }

        throw new \Exception($data->error_text);
    }

    public static function register($nama_petani, $no_telp, $foto, $alamat, $latitude,$longitude){
        $petani = new Petani();
        $petani->id_petani= self::generateId();
        $petani->nama_petani = $nama_petani;
        $petani->no_telp = $no_telp;
        $petani->foto = $foto;
        $petani->alamat = $alamat;
        $petani->latitude = $latitude;
        $petani->longitude = $longitude;
        $petani->token = md5(uniqid($no_telp, true));

        if (Manager::table(Petani::TABLE_NAME)->where('no_telp', '=', $no_telp)->first() == null) {
            $petani->save();
            $result = Manager::table(Petani::TABLE_NAME)
                ->where('no_telp', '=', $no_telp)
                ->first([Petani::PRIMARY_KEY, "nama_petani", "no_telp", "foto", "token"]);
            return $result;

        } else {
            throw new \Exception($no_telp . " has been taken.");
        }

    }

    public static function updateProfile($token, $no_telp, $nama_petani, $foto) {
        $petani = Petani::query()
            ->where('token', '=', $token)
            ->first();
        if ($token == null or $token == "" or $petani == null) {
            throw new \Exception("Session expired, please re-login");
        } else {
            if ($nama_petani != null && $nama_petani != "") {
                $petani->nama_petani = $nama_petani;
            }
            if ($foto != null && $foto != "") {
                $petani->foto = $foto;
            }
            if ($no_telp != null && $no_telp != "") {
                $telp = Petani::query()
                    ->where('no_telp', '=', $no_telp)
                    ->first();

                if($telp==null) {
                    $petani->no_telp = $no_telp;
                }else{
                    throw new \Exception("Number has been exist");
                }
            }
            $petani->save();
        }

        return Petani::getPetaniByToken($token);
    }

}
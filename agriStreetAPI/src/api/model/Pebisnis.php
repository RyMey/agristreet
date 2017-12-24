<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 11/2/17
 * Time: 12:26 PM
 */
namespace AgriStreet\Api\Model;

use Illuminate\Contracts\Debug\ExceptionHandler;
use Illuminate\Database\Capsule\Manager;
use Illuminate\Database\Eloquent\Model;

class Pebisnis extends Model{
    const TABLE_NAME = "pebisnis";
    const PRIMARY_KEY = "id_pebisnis";
    public $timestamps = false;

    public $table = Pebisnis::TABLE_NAME;
    public $primaryKey = Pebisnis::PRIMARY_KEY;

    public static function generateId(){
        $output = Manager::table(Pebisnis::TABLE_NAME)
            ->orderBy(Pebisnis::PRIMARY_KEY,'DESC')
            ->limit(1)
            ->first([Pebisnis::PRIMARY_KEY]);

        $lastId = $output->id_pebisnis;

        if($lastId != null){
            $id = (int) substr($lastId,2,strlen($lastId)-2);
            $id = $id + 1;
            $newId = 'pb'.$id;
        }else{
            $newId = 'pb1';
        }

        return $newId;
    }

    public static function getPebisnis($id_pebisnis){
        $pebisnis = Manager::table(Pebisnis::TABLE_NAME)->where(Pebisnis::PRIMARY_KEY, '=', $id_pebisnis)
            ->first([Pebisnis::TABLE_NAME . '.' . Pebisnis::PRIMARY_KEY,
                Pebisnis::TABLE_NAME . '.nama_pebisnis',
                Pebisnis::TABLE_NAME . '.no_telp',
                Pebisnis::TABLE_NAME . '.foto']);
        return $pebisnis;
    }

    public static function getSelfPebisnis($id_pebisnis){
        $pebisnis = Manager::table(Pebisnis::TABLE_NAME)->where(Pebisnis::PRIMARY_KEY, '=', $id_pebisnis)
            ->first([Pebisnis::TABLE_NAME . '.' . Pebisnis::PRIMARY_KEY,
                Pebisnis::TABLE_NAME . '.nama_pebisnis',
                Pebisnis::TABLE_NAME . '.no_telp',
                Pebisnis::TABLE_NAME . '.foto',
                Pebisnis::TABLE_NAME . '.token']);
        return $pebisnis;
    }

    public static function getPebisnisByToken($token) {
        $pebisnis = Manager::table(Pebisnis::TABLE_NAME)->where('token', '=', $token)
            ->first([Pebisnis::TABLE_NAME . '.' . Pebisnis::PRIMARY_KEY,
                Pebisnis::TABLE_NAME . '.nama_pebisnis',
                Pebisnis::TABLE_NAME . '.no_telp',
                Pebisnis::TABLE_NAME . '.foto',
                Pebisnis::TABLE_NAME . '.token']);
        return $pebisnis;
    }

    public static function getIdByPhone($no_telp){
        $id = Manager::table(Pebisnis::TABLE_NAME)->where('no_telp', '=', $no_telp)
            ->first([Pebisnis::TABLE_NAME . '.' . Pebisnis::PRIMARY_KEY]);
        return $id;
    }

    public static function verifyPhone($no_telp){
        $ch = curl_init();
        curl_setopt($ch,CURLOPT_URL,"https://Api.nexmo.com/verify/json");
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS,"api_key=b6b121fd&api_secret=e26e5acecfe7189f&number=$no_telp&brand=AgriStreet");
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
                return self::getSelfPebisnis($id->id_pebisnis);
            }else{
                return self::register("",$no_telp,"");
            }
        }

        throw new \Exception($data->error_text);
    }

    public static function register($nama_pebisnis, $no_telp, $foto){
        $pebisnis = new Pebisnis();
        $pebisnis->id_pebisnis= self::generateId();
        $pebisnis->nama_pebisnis = $nama_pebisnis;
        $pebisnis->no_telp = $no_telp;
        $pebisnis->foto = $foto;
        $pebisnis->token = md5(uniqid($no_telp, true));

        if (Manager::table(Pebisnis::TABLE_NAME)->where('no_telp', '=', $no_telp)->first() == null) {
            $pebisnis->save();
            $result = Manager::table(Pebisnis::TABLE_NAME)
                ->where('no_telp', '=', $no_telp)
                ->first([Pebisnis::PRIMARY_KEY, "nama_pebisnis", "no_telp", "foto", "token"]);
            return $result;

        } else {
            throw new \Exception($no_telp . " has been taken.");
        }

    }

    public static function updateProfile($token, $no_telp, $nama_pebisnis, $foto) {
        $pebisnis = Pebisnis::query()
            ->where('token', '=', $token)
            ->first();
        if ($token == null or $token == "" or $pebisnis == null) {
            throw new \Exception("Session expired, please re-login");
        } else {
            if ($nama_pebisnis != null && $nama_pebisnis != "") {
                $pebisnis->nama_pebisnis = $nama_pebisnis;
            }
            if ($foto != null && $foto != "") {
                $pebisnis->foto = $foto;
            }
            if ($no_telp != null && $no_telp != "") {
                $telp = Pebisnis::query()
                    ->where('no_telp', '=', $no_telp)
                    ->first();

                if($telp==null) {
                    $pebisnis->no_telp = $no_telp;
                }else{
                    throw new \Exception("Number has been exist");
                }
            }
            $pebisnis->save();
        }

        return Pebisnis::getPebisnisByToken($token);
    }

}
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

    public $table = Petani::TABLE_NAME;
    public $primaryKey = Petani::PRIMARY_KEY;


    public static function generateId(){
        $lastId = Manager::table(Petani::TABLE_NAME)
            ->first(Petani::PRIMARY_KEY)
            ->orderBy(Petani::PRIMARY_KEY,'DESC')
            ->limit(1);

        if($lastId != null){
            $id = (int) substr($lastId,2);
            $id = $id + 1;
            $new_id = $id.'pb';
        }else{
            $new_id = 'pb1';
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

    public static function register($nama_petani, $no_telp, $foto){
        $petani = new Petani();
        $petani->id_petani= self::generateId();
        $petani->nama_petani = $nama_petani;
        $petani->no_telp = $no_telp;
        $petani->foto = $foto;
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

    public static function login($no_telp){
        if ($no_telp == null or $no_telp == "") {
            throw new \Exception("Invalid no_telp!");
        }

        $result = Manager::table(User::TABLE_NAME)
            ->where('no_telp', '=', $no_telp)
            ->first([Petani::PRIMARY_KEY, "nama_petani", "no_telp", "foto", "token"]);

        return $result;
    }

    public static function getUserByToken($token) {
        $result = Manager::table(Petani::TABLE_NAME)
            ->where('token', '=', $token)
            ->first([Petani::PRIMARY_KEY, "nama_petani", "no_telp", "foto", "token"]);

        return $result;
    }

    public static function updateProfile($token, $nama_petani, $foto) {
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

            $petani->save();
        }

        return Petani::getUserByToken($token);
    }
}
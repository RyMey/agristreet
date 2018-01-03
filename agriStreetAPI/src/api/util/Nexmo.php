<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 2/27/16
 * Time: 11:58 AM
 */

namespace AgriStreet\Api\Util;

class Nexmo {

    const API_KEY = "f8ae05ee";
    const API_SECRET = "68ea79c43a4a414b";

    public static function sendCode($no_telp){
        $ch = curl_init();
        curl_setopt($ch,CURLOPT_URL,"https://api.nexmo.com/verify/json");
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS,"api_key=".Nexmo::API_KEY."&api_secret=".Nexmo::API_SECRET."&number=$no_telp&brand=AgriStreet");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

        $raw_data = curl_exec($ch);
        curl_close($ch);

        $data = json_decode($raw_data);
        return $data->request_id;
    }

    public static function verify($request_id, $code) {
        $ch = curl_init();
        curl_setopt($ch,CURLOPT_URL,"https://api.nexmo.com/verify/check/json");
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS,"api_key=".Nexmo::API_KEY."&api_secret=".Nexmo::API_SECRET."&request_id=$request_id&code=$code");
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

        $raw_data = curl_exec($ch);
        curl_close($ch);

        $data = json_decode($raw_data);
        return $data;
    }
}

?>
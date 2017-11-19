<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 2/27/16
 * Time: 12:00 PM
 */

require "vendor/autoload.php";
require "src/api/util/Database.php";

use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Slim\Container;
use Slim\Handlers\Strategies\RequestResponseArgs;
use AgriStreet\Api\Model\Pebisnis;

use AgriStreet\Api\Model\Alamat;

use AgriStreet\Api\Model\KategoriKomoditas;
use AgriStreet\Api\Model\LamaranPetani;

use AgriStreet\Api\Model\User;
use AgriStreet\Api\Model\UserShared;
use AgriStreet\Api\Util\ResultWrapper;

$container = new Container();
$container['foundHandler'] = function () {
    return new RequestResponseArgs();
};

$slim = new Slim\App($container);

$slim->get("/", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        return ResultWrapper::getResult("AgriStreet App", $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/pebisnis/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(Pebisnis::getPebisnis($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/alamat/getAlamatById/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(Alamat::getAlamatById($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/alamat/getAlamatByPebisnis/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(Alamat::getALamatByPebisnis($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/komoditas/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(KategoriKomoditas::getKategoriKomoditas($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/lamaran/getLamaranById/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(LamaranPetani::getLamaranById($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});


$slim->get("/lamaran/getLamaranByPetani/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(LamaranPetani::getLamaranByPetani($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/lowongan/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(Lowongan::get($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->post("/pebisnis/verify-phone", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $data = Pebisnis::verifyPhone($params['no_telp']);

        if ($data == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($data, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->post("/pebisnis/auth", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $pebisnis = Pebisnis::auth($params['no_telp'],$params['request_id'],$params['code']);

        if ($pebisnis == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($pebisnis, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->put("/pebisnis/update-profile", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $pebisnis = Pebisnis::updateProfile($req->getHeader('token'),$params['no_telp'],$params['nama_pebisnis'],$params['foto']);

        if ($pebisnis == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($pebisnis, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});




$slim->run();
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
use AgriStreet\Api\Model\Alamat;
use AgriStreet\Api\Model\KategoriKomoditas;
use AgriStreet\Api\Model\LamaranPetani;
use AgriStreet\Api\Model\Lowongan;
use AgriStreet\Api\Model\Pebisnis;
use AgriStreet\Api\Model\Petani;
use AgriStreet\Api\Model\Kerjasamas;
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

$slim->get("/petani/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(Petani::getPetani($id), $res);
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

$slim->get("/getLamaranById/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(LamaranPetani::getLamaranById($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/getLamaranByPetani/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(LamaranPetani::getLamaranByPetani($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/lowongan/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(Lowongan::getLowongan($id), $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->get("/kerjasama/{id}",function (ServerRequestInterface $req, ResponseInterface $res, $id){
    try {
        return ResultWrapper::getResult(Kerjasama::getKerjasama($id), $res);
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

$slim->post("/petani/verify-phone", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $data = Petani::verifyPhone($params['no_telp']);

        if ($data == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($data, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->post("/petani/auth", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $petani = Petani::auth($params['no_telp'],$params['request_id'],$params['code']);

        if ($petani == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($petani, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->post("/lowongan/make-lowongan", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $lowongan = Lowongan::makeLowongan($req->getHeader('token'),$params['id_kategori'],$params['id_alamat_pengiriman'],
                    $params['judul_lowongan'], $params['deskripsi_lowongan'], $params['jumlah_komoditas'],
                    $params['tgl_buka'], $params['tgl_tutup'], $params['harga_awal'], $params['status']);

        if ($lowongan == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($lowongan, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->post("/lamaranPetani/make-LamaranPetani", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $lamaranPetani = LamaranPetani::makeLamaranPetani($req->getHeader('token'),$params['id_lowongan'],$params['tgl_lamar'],
                    $params['harga_tawar'], $params['deskripsi_lamaran']);

        if ($lamaranPetani == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($lamaranPetani, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->post("/kerjasama/make-kerjasama", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $kerjasama = Kerjasama::makeKerjasama($req->getHeader('token'),$params['id_petani'],$params['id_lowongan'],
                    $params['status_lamaran'], $params['tgl_kerjasama'], $params['harga_sepakat']);

        if ($kerjasama == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($kerjasama, $res);
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

$slim->put("/petani/update-profile", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $petani = Petani::updateProfile($req->getHeader('token'),$params['no_telp'],$params['nama_petani'],$params['foto']);

        if ($petani == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($petani, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->put("/lowongan/update-lowongan", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $lowongan = Lowongan::updateLowongan($req->getHeader('token'),$params['id_lowongan'],$params['id_alamat_pengiriman'],
                    $params['judul_lowongan'],$params['deskripsi_lowongan'], $params['jumlah_komoditas'],
                    $params['tgl_buka'], $params['tgl_tutup'], $params['harga_awal'], $params['status']);

        if ($lowongan == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($lowongan, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});
  
$slim->put("/kerjasama/update-StatusLamaran", function (ServerRequestInterface $req, ResponseInterface $res) {
    try {
        $params = $req->getParsedBody();
        $kerjasama = Kerjasama::updateStatusLamaran($req->getHeader('token'),$params['kerjasama'],$params['status_lamaran']);

        if ($kerjasama == null) {
            throw new Exception ("Ups, something wrong!");
        }
        return ResultWrapper::getResult($kerjasama, $res);
    } catch (Exception $e) {
        return ResultWrapper::getError($e->getMessage(), $res);
    }
});

$slim->run();
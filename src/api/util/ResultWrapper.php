<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 2/27/16
 * Time: 11:58 AM
 */

namespace AgriStreet\Api\Util;

use Psr\Http\Message\ResponseInterface;

class ResultWrapper {
    public static function getResult($item, ResponseInterface $response) {
        $result = json_encode(array('status' => 'success', 'result' => $item),
            JSON_HEX_TAG | JSON_HEX_APOS | JSON_HEX_QUOT | JSON_HEX_AMP | JSON_UNESCAPED_UNICODE);
        $response->getBody()->write($result);
        return $response->withStatus(200)->withHeader('Content-Type', 'application/json');
    }

    public static function getError($item, ResponseInterface $response) {
        $result = json_encode(array('status' => 'error', 'message' => $item),
            JSON_HEX_TAG | JSON_HEX_APOS | JSON_HEX_QUOT | JSON_HEX_AMP | JSON_UNESCAPED_UNICODE);
        $response->getBody()->write($result);
        return $response->withStatus(404)->withHeader('Content-Type', 'application/json');
    }
}

?>
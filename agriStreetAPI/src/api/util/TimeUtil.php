<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 2/27/16
 * Time: 1:39 PM
 */

namespace AgriStreet\Api\Util;

class TimeUtil {
    public static function getDateTimeNow() {
        $timeZone = new \DateTimeZone('Asia/Jakarta');
        $dateTime = new \DateTime();
        $dateTime->setTimezone($timeZone);
        return $dateTime->format('Y-m-d H:i:s');
    }
}
<?php
/**
 * Created by PhpStorm.
 * User: RyMey
 * Date: 2/27/16
 * Time: 12:12 PM
 */

use Illuminate\Database\Capsule\Manager;

$manager = new Manager();
$manager->addConnection(array(
    'driver' => 'mysql',
    'host' => 'localhost',
    'database' => 'agristreet',
    'username' => 'agristreet',
    'password' => 'agristreet',
    'prefix' => '',
    'charset' => 'utf8',
    'collation' => 'utf8_general_ci'
));
$manager->bootEloquent();
$manager->setAsGlobal();
$conn = $manager->connection();
?>

<?php

libxml_disable_entity_loader(false);

$wsdl = "http://localhost:8080/TTTWebApplication/TTTWebService?WSDL";
$trace = true;
$exceptions = true;

try {
    $proxy = new SoapClient($wsdl, ['trace' => $trace, 'exceptions' => $exceptions]);
} catch (Exception $e) {
    echo $e->getMessage();
}
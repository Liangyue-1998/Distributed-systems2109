<?php
require_once '../../soap_connect.php';
require_once '../../common.php';

try {
    $xml_array['name'] = $_POST['name'];
    $xml_array['surname'] = $_POST['surname'];
    $xml_array['username'] = $_POST['username'];
    $xml_array['password'] = $_POST['password'];
    
    $response = $proxy->register($xml_array);
    $result = $response->return;
    if (is_numeric($result)) {
        echo json_encode(['status' => 1, 'message' => 'Register Success']);
    } else {
        echo json_encode(['status' => 0, 'message' => $result]);
    }
} catch (Exception $e) {
    echo $e->getMessage();
}
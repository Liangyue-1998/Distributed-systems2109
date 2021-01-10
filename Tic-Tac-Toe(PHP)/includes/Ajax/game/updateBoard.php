<?php
require_once '../../soap_connect.php';
require_once '../../common.php';

if (!isset($_SESSION)) {
    session_start();
}

$gid = $_POST['gid'];

try {
    $response = $proxy->getBoard(['gid' => $_POST['gid']]);
    $result = $response->return;
    $positions = format_soap_string_2_array($result);
	
    if ($positions !== false) {
        echo json_encode(['status' => 1, 'message' => '', 'data' => $positions, 'result' => $result]);
        return;
    } else {
		
		echo json_encode(['status' => 1, 'message' => $result, 'data' => []]);
       
        return;
    }
} catch (Exception $exception) {
    // logger($exception);
    echo json_encode(['status' => 3, 'message' => $exception->getMessage()]);
    return;
}

echo json_encode(['status' => 3, 'message' => $result]);
return;

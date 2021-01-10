<?php
require_once '../../soap_connect.php';
require_once '../../common.php';

if (!isset($_SESSION)) {
    session_start();
}

$gid = $_POST['gid'];
$pid = $_POST['pid'];
$x = $_POST['x'];
$y = $_POST['y'];

try {
    $response = $proxy->takeSquare(['x' => $_POST['x'], 'y' => $_POST['y'], 'gid' => $_POST['gid'], 'pid' => $_POST['pid']]);
    $result = $response->return;
    if (is_numeric($result) && $result == '1') {
        echo json_encode(['status' => 1, 'message' => '']);
        return;
    } else {
        echo json_encode(['status' => 2, 'message' => 'Operation is invalid!']);
        return;
    }
} catch (Exception $exception) {
    // logger($exception);
    echo json_encode(['status' => 3, 'message' => $exception->getMessage()]);
    return;
}

echo json_encode(['status' => 3, 'message' => $result]);
return;

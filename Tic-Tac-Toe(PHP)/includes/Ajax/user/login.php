<?php
require_once '../../soap_connect.php';
require_once '../../common.php';

if (!$_POST['username'] || !$_POST['password']) {
    echo json_encode(['status' => 2, 'message' => 'Please fill required information']);
    return;
}

if (!isset($_SESSION)) {
    session_start();
}

try {
    $response = $proxy->login(['username' => $_POST['username'], 'password' => $_POST['password']]);
    $result = $response->return;

    if (is_numeric($result) && (int) $result > 0) {
        $_SESSION['uid'] = (int) $result;
        $_SESSION['username'] = $_POST['username'];
        echo json_encode(['status' => 1, 'message' => 'Login success']);
        return;
    } else {
        echo json_encode(['status' => 2, 'message' => 'User not exist or password not right!']);
        return;
    }
} catch (Exception $exception) {
    // logger($exception);
    echo json_encode(['status' => 3, 'message' => $exception->getMessage()]);
    return;
}

echo json_encode(['status' => 3, 'message' => $result]);
return;

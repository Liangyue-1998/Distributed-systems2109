<?php
require_once '../../soap_connect.php';
require_once '../../common.php';

if (!isset($_SESSION)) {
    session_start();
}

$gid = $_POST['gid'];

try {
    // 根据棋盘检测游戏状态
    $response = $proxy->checkWin(['gid' => $_POST['gid']]);
    $result = $response->return;

    if (is_numeric($result)) {
        //游戏进行中
        if ($result == '0') {
            echo json_encode(['status' => 0, 'message' => '']);return;
        }
        if ($result == '1') {
            //更新P1胜利
            $proxy->setGameState(['gid' => $_POST['gid'], 'gstate' => 1]);
            echo json_encode(['status' => 1, 'message' => '']);return;
        }
        if ($result == '2') {
            //更新P2胜利
            $proxy->setGameState(['gid' => $_POST['gid'], 'gstate' => 2]);
            echo json_encode(['status' => 2, 'message' => '']);return;
        }
        if ($result == '3') {
            //更新平局
            $proxy->setGameState(['gid' => $_POST['gid'], 'gstate' => 3]);
            echo json_encode(['status' => 3, 'message' => '']);return;
        }
    } else {
        echo json_encode(['status' => -1, 'message' => $result]);
        return;
    }
} catch (Exception $exception) {
    // logger($exception);
    echo json_encode(['status' => -1, 'message' => $exception->getMessage()]);
    return;
}

echo json_encode(['status' => -1, 'message' => $result]);
return;

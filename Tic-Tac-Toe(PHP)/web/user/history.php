<?php
require_once '../../includes/common.php';
require_once '../../includes/soap_connect.php';

if (!$_SESSION['uid']) {
    guest_redirect();
} else {
    $response = $proxy->leagueTable();
    $result = $response->return;
    $history = format_soap_string_2_array($result);

    //如果返回数据不是数组
    if ($history === false) {
        echo $history;
        return;
    }

    /**
     * 统计用户输赢次数
     * 
     * $history  = [
     *      '用户id' => [赢，输，平, 总数],
     *      '用户id' => [赢，输，平, 总数],
     * ];
     */
    $logs = [];
    foreach ($history as $game) {
        //以 p1 角度统计
        if (!isset($logs[$game[1]])) {
            $logs[$game[1]] = [0, 0, 0, 0];
        }
        switch ($game[3]) {
            case 1:
                $logs[$game[1]][0] += 1;
                $logs[$game[1]][3] += 1;
                break;
            case 2:
                $logs[$game[1]][1] += 1;
                $logs[$game[1]][3] += 1;
                break;
            case 3:
                $logs[$game[1]][2] += 1;
                $logs[$game[1]][3] += 1;
                break;
            default:
                break;
        }

        //以 p2 角度统计
        if (!isset($logs[$game[2]])) {
            $logs[$game[2]] = [0, 0, 0];
        }
        switch ($game[3]) {
            case 1:
                $logs[$game[2]][1] += 1;
                $logs[$game[2]][3] += 1;
                break;
            case 2:
                $logs[$game[2]][2] += 1;
                $logs[$game[2]][3] += 1;
                break;
            case 3:
                $logs[$game[2]][2] += 1;
                $logs[$game[2]][3] += 1;
                break;
            default:
                break;
        }
    }
    //以 赢的次数降序排列
    uasort($logs, function($a, $b) {
        return $a < $b;
    });
}
?>
        <?php require '../header.php'; ?>
        <div class="container">
            <h2>Game List</h2>
            <table class="table table-bordered">

            <thead>
                    <tr>
                        <th>Username</th>
                        <th>Win</th>
                        <th>Lose</th>
                        <th>Draw</th>
                        <th>Count</th>
                    </tr>
                </thead>
                <tbody>

            <?php
                            $html = '';
                            foreach ($logs as $uid => $status) {
                                $win = $status[0];
                                $lose = $status[1];
                                $draw = $status[2];
                                $count = $status[3];

                                $add_html     = <<<EOT
                        <tr>
                        <td>{$uid}</td>
                        <td>{$win}</td>
                        <td>{$lose}</td>
                        <td>{$draw}</td>
                        <td>{$count}</td>
                        </tr>

EOT;
                                $html .= $add_html;
                            }

                            echo $html;
                            ?>

                </tbody>
            </table>
        </div>

        </body>
        <?php require '../footer.php'; ?>

        <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
        <script src="/resource/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
        <script src="../../resource/layer/layer.js"></script>

        </html>
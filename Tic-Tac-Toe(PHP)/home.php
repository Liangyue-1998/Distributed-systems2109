<?php
require_once 'includes/common.php';
require_once 'includes/soap_connect.php';

if (!$_SESSION['uid']) {
    guest_redirect();
} else {
    $response = $proxy->leagueTable();
    $result = $response->return;
   
    $history = format_soap_string_2_array($result);
	
    //如果返回数据不是数组
    if ($history === false) {
        echo $result;
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
            $logs[$game[2]] = [0, 0, 0, 0];
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
	
    $response = $proxy->showOpenGames();
    $result = $response->return;
	
    $games = format_soap_string_2_array($result);

    //如果返回数据不是数组
    if ($games === false && $result != 'ERROR-NOGAMES') {
        echo $result;
        return;
    }
	if ($result == 'ERROR-NOGAMES') {
		$games = [];
	}
}
?>
        <?php require 'web/header.php'; ?>

        <!-- 用户个人战绩 -->
        <div class="container">
            <h2 class='text-center'>Welcome!Gamer: <?php echo $_SESSION['username']; ?></h2>

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
                        //如果历史战绩的所有者不是当前登陆用户则不显示
                        if ($uid != $_SESSION['uid']) {
                            continue;
                        }
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

        <div class="container">
            <h2 class='text-center'>Game List</h2>

            <table class="table table-bordered">

                <thead>
                    <tr>
                        <th>Game ID</th>
                        <th>Username</th>
                        <th>Game Date</th>
                        <th>Operation</th>
                    </tr>
                </thead>
                <tbody>

                    <?php
                    $html = '';
                    foreach ($games as $game) {

                        $id = $game[0];
                        $username = $game[1];
                        $date = $game[2];
                        //如果房间是我自己创建的房间,但是还没人加入，就在我访问首页的时候删除掉这个房间
                        if ($_SESSION['username'] == $username) {
                            $proxy->deleteGame(['gid' => $id, 'uid' => $_SESSION['uid']]);
                        }

                        $add_html     = <<<EOT
                        <tr>
                        <td>{$id}</td>
                        <td>{$username}</td>
                        <td>{$date}</td>
                        <td>
                            <a href="/web/games/p2_room.php?gid={$id}" class="btn btn-info" role="button">Join</a>
                        </td>
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
        <?php require 'web/footer.php'; ?>

        <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
        <script src="/resource/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
        <script src="/resource/layer/layer.js"></script>
        <script src="/resource/layer/layer.js"></script>

        </html>
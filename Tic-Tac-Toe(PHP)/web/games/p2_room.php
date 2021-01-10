<?php
require_once '../../includes/common.php';
require_once '../../includes/soap_connect.php';

if (!$_SESSION['uid']) {
    guest_redirect();
}
/**
 * 如果没有传入gid参数，则返回到大厅
 */
if (!isset($_GET['gid'])) {
    header('location: /home.php');
}

//判断游戏状态
$response = $proxy->getGameState(['gid' => $_GET['gid']]);
$game_state = $response->return;


if (!is_numeric($game_state)) {
    //如果获取失败返回大厅
    header('location: /home.php');
} 

if ($game_state == '-1') {
    //如果游戏状态是等待他人加入
    $response = $proxy->joinGame(['uid' => $_SESSION['uid'], 'gid' => $_GET['gid']]);
    //如果加入失败
    if ($response->return != '1') {
        header('location: /home.php');
    }
} else {
    //如果游戏状态不是等待和进行中则返回到大厅
    header('location: /home.php');
}
?>
<?php require '../header.php'; ?>


<style>
    #bg {
        width: 300px;
        margin: auto;
        margin-top: 15%;
    }

    #bg div {
        width: 94px;
        height: 94px;
        border: solid 1px #33bcfa;
        background: #2db648;
        float: left;
        font-size: 94px;
        text-align: center;
        line-height: 90px;
        cursor: pointer;
    }
</style>

<div class="container">
    <div class="row">

        <h2>Welcome!Gamer: <?php echo $_SESSION['username']; ?></h2>

        <div id='bg'>
            <div onclick="move(this)" id="00"></div>
            <div onclick="move(this)" id="10"></div>
            <div onclick="move(this)" id="20"></div>
            <div onclick="move(this)" id="01"></div>
            <div onclick="move(this)" id="11"></div>
            <div onclick="move(this)" id="21"></div>
            <div onclick="move(this)" id="02"></div>
            <div onclick="move(this)" id="12"></div>
            <div onclick="move(this)" id="22"></div>
        </div>
    </div>

    <hr style="margin: 50px;">

    <div class="row">
        <h2 id='game_status'>Game is Processing</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Username</th>
                    <th>X</th>
                    <th>Y</th>
                </tr>
            </thead>
            <tbody id='move_hisotry'>
            </tbody>
        </table>
    </div>
</div>

</body>
<?php require '../footer.php'; ?>

<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
<script src="/resource/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="/resource/layer/layer.js"></script>
<script src="/resource/layer/layer.js"></script>
<script>
    //标记目前是第几步棋
    var keys = 0;
    //根据数据画出棋盘
    function drawBoardByArr(actions) {
        //清空棋盘
        $('#bg').children('div').each(function(index, element) {
            $(this).html('');
        });

        var uid = '<?php echo $_SESSION['uid']; ?>';
        $.each(actions, function(index, item) {
            if (item[0] == uid) {
                //如果是自己则为白子
                inner_html = '<span style="color:#fff">●</span>';
            } else {
                inner_html = '<span style="color:#000">●</span>';
            }
            $('#' + String(item[1]) + String(item[2])).html(inner_html);
        });
    }
    //更新落子的历史
    function drawMoveHistory(actions) {
        var html = '';
        $.each(actions, function(index, item) {
            html += "<tr><td>" + String(item[0]) + "</td><td>" + String(item[1]) + "</td><td>" + String(item[2]) + "</td></tr>";
        });
        $('#move_hisotry').html(html);
    }


    function move(obj) {
        //P1先行，keys为奇数则P2可行
        if (keys % 2 === 0) {
            return;
        }

        var x = obj.id[0];
        var y = obj.id[1];
        var pid = '<?php echo $_SESSION['uid']; ?>';
        var gid = '<?php echo $_GET['gid']; ?>';
        //移动棋子
        $.ajax({
            url: '/includes/Ajax/game/move.php',
            type: 'POST',
            async: false, //这个操作为同步执行
            data: {
                x: x,
                y: y,
                gid: gid,
                pid: pid
            },
            dataType: 'JSON',
            success: function(json) {
                console.log(json);
                if (json.status !== 1) {
                    layer.msg(json.message, {
                        icon: 5
                    });
                } else {
                    //加入别人房间一定为P2即白子
                    $('#' + x + y).html('<span style="color:#fff">●</span>');
                }
            },
            error: function(json) {
                layer.msg('Server Error', {
                    icon: 5
                });
            }
        });

    }

    function checkWin()
    {
        var gid = '<?php echo $_GET['gid']; ?>';
		var result = false;
        //移动完检测游戏状态
        $.ajax({
            url: '/includes/Ajax/game/checkWin.php',
            type: 'POST',
            data: {
                gid: gid,
            },
			async: false, //这个操作为同步执行
            dataType: 'JSON',
            success: function(json) {
                console.log(json);
                if (json.status > 0) {
                    switch (json.status) {
                        case 1:
                            var message = 'You Lose';break;
                        case 2:
                            var message = 'You Win';break;
                        default:
                            var message = 'You Got a Draw';break;
                    }
                       
                    layer.msg(message, {
                        btn: ['submit'],
						time: 0,//不自动关闭
                        yes: function(index) {
                            //确认后跳转到大厅
                            layer.close(index);
                            window.location.href = '/home.php'
                        }
                    });
                	result = true;
                } else {
					result = false;
				}
            },
            error: function(json) {
                console.log(json);
            }
        });
		return result;
    }


    //ajax 异步请求
    //如果修改为同步请求页面会卡顿无法擦操作
    function getData() {
        return new Promise((resolve, reject) => {
            var gid = '<?php echo $_GET['gid']; ?>';
            $.ajax({
                url: '/includes/Ajax/game/updateBoard.php',
                type: 'POST',
                data: {
                    gid: gid
                },
                dataType: 'JSON',
                success: function(json) {
                    if (json.status !== 1) {
                        layer.msg(json.message, {
                            icon: 5
                        });
                        resolve({ data: []});
                    } else {
                        resolve({ data: json.data
                        }); //异步请求必须使用Promise才能获取结果
                    }
                },
                error: function(json) {
                    layer.msg('Server Error', {
                        icon: 5
                    });
                }
            });
        });
    }

    // 轮询
    async function updateBoard() {
        const {
            data
        } = await getData(); // 模拟请求
        drawBoardByArr(data);
        drawMoveHistory(data);
		//如果游戏有了结果，就不再轮询了
        if (checkWin() == true) {
			return;
		}
        keys = data.length;
        setTimeout(updateBoard, 1000); //1秒查询一次
    }

    $(function() {
        //启动更新棋盘的轮询
        updateBoard();
    });
</script>
tic_tac.zip
</html>
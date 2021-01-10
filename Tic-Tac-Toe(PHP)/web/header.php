<?php
if (!isset($_SESSION)) {
    session_start();
}

if ($_SESSION['uid']) {
    $html = <<<EOT
                                <!DOCTYPE html>
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Tic-tac-toe</title>
        <link rel="stylesheet" href="/resource/bootstrap-4.3.1-dist/css/bootstrap.min.css">
        <link rel="stylesheet" href="/resource/layer/theme/default/layer.css?v=3.1.1" id="layuicss-layer">
        <link rel="stylesheet" href="/resource/css/app.css">
    </head>
    
    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container ">
            <a class="navbar-brand" href="/home.php">Games Lobby</a>
            <ul class="navbar-nav justify-content-end">
                <li class="nav-item"><a class="nav-link" href="/web/games/p1_room.php">New Game</a></li>
                <li class="nav-item"><a class="nav-link" href="/web/user/history.php">Leaderboard</a></li>
                <li class="nav-item"><a class="nav-link" href="/logout.php">Logout</a></li>
            </ul>
        </div>
    </nav>

EOT;

} else {
    $html = <<<EOT
                                <!DOCTYPE html>
    <html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Tic-tac-toe</title>
    
        <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
        <link href="https://unpkg.com/gijgo@1.9.13/css/gijgo.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="/resource/layer/theme/default/layer.css?v=3.1.1" id="layuicss-layer">
        <link rel="stylesheet" href="/resource/css/app.css">
    </head>
    
    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container ">
            <a class="navbar-brand" href="/home.php">Games Lobby</a>
            <ul class="navbar-nav justify-content-end">
                <li class="nav-item"><a class="nav-link" href="/register.php">Register</a></li>
                <li class="nav-item"><a class="nav-link" href="/login.php">Login</a></li>
            </ul>
        </div>
    </nav>

EOT;

}
echo $html;
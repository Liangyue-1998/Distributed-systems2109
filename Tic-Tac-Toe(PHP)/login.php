<?php
require_once 'includes/common.php';

if ($_SESSION['uid']) {
    user_redirect();
}
?>

<?php require 'web/header.php'; ?>

<div class="container">
    <div class="offset-md-1 col-md-10">
        <div class="offset-md-2 col-md-8">
            <div class="card ">
                <div class="card-header">
                    <h5>login</h5>
                </div>
                <div class="card-body">


                    <form>
                        <div class="form-group">
                            <label for="username">username:</label>
                            <input type="text" name="username" class="form-control" value="">
                        </div>


                        <div class="form-group">
                            <label>Password:</label>
                            <input type="password" id='password' name="password" class="form-control" value="">
                        </div>


                        <button type="button" id="submit" class="btn btn-primary">login</button>
                    </form>
                </div>
            </div>
        </div>
        <?php require 'web/footer.php'; ?>
    </div>
</div>
<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
<script src="/resource/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="resource/layer/layer.js"></script>
<script>

    $(function () {
        //回车触发登陆按钮
        $("input").keydown(function (e) {
            if (e.which == 13) {
                $(this).parents('form').children().filter('button').click();
            }
        });


        $('#submit').click(
            function () {
                var username = $("[name=username]").val();
                var password = $("[name=password]").val();

                $.ajax({
                        url: 'includes/Ajax/user/login.php',
                        type: 'POST',
                        data: {
                            username: username,
                            password: password
                        },
                        dataType: 'JSON',
                        success: function (json) {
                            if (json.status !== 1) {

                                layer.msg(json.message, {icon: 5});
                                console.log(json.message);
                            } else {
                                layer.msg(json.message, {icon: 1, time: 1000}, function () {
                                    window.location.href = '/home.php';
                                });
                            }
                        },
                        error: function (json) {
                            layer.msg('Server Error', {icon: 5});
                        }
                    }
                );
            });
    });
</script>
</body>
</html>

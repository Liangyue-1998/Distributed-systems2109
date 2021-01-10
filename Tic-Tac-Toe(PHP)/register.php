<?php
require_once 'includes/common.php';

if (!isset($_SESSION)) {
    session_start();
}

if ($_SESSION['email']) {
    user_redirect();
}
?>

<?php require 'web/header.php'; ?>

<div class="container">
    <div class="offset-md-1 col-md-10">
        <div class="offset-md-2 col-md-8">
            <div class="card ">
                <div class="card-header">
                    <h5>Register</h5>
                </div>
                <div class="card-body">


                    <form>
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" name="username" class="form-control" value="">
                        </div>

                        <div class="form-group">
                            <label>Password:</label>
                            <input type="password" name="password" class="form-control" value="">
                        </div>

                        <div class="form-group">
                            <label>Confirm Your Password:</label>
                            <input type="password" name="confirm_password" class="form-control" value="">
                        </div>

                        <div class="form-group">
                            <label for="name">Your Name:</label>
                            <input type="text" name="name" class="form-control" value="">
                        </div>

                        <div class="form-group">
                            <label for="surname">Your Surname:</label>
                            <input type="text" name="surname" class="form-control" value="">
                        </div>

                        <button type="button" id="submit" class="btn btn-primary">Register</button>
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
        $('#submit').click(
            function () {
                var username = $("[name=username]").val();
                var password = $("[name=password]").val();
                var name = $("[name=name]").val();
                var surname = $("[name=surname]").val();
                var confirm_password = $("[name=confirm_password]").val();
                
                if (password !==confirm_password ) {
                    layer.msg('The confirm passwod is not equal with password', {icon: 5});
                    return;
                }

                $.ajax({
                        url: 'includes/Ajax/user/register.php',
                        type: 'POST',
                        data: {
                            username: username,
                            password: password,
                            name: name,
                            surname: surname
                        },
                        dataType: 'JSON',
                        success: function (json) {
                            if (json.status !== 1) {
                                layer.msg(json.message, {icon: 5});
                                console.log(json.message);
                            } else {
                                layer.msg(json.message, {icon: 1, time: 1000}, function () {
                                    window.location.href = 'login.php';
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

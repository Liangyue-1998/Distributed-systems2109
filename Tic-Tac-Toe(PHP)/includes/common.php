<?php

# 修改报错级别
error_reporting(E_ALL^E_NOTICE);

# 开启session
if (!isset($_SESSION)) {
    session_start();
}

/**
 * 游客权限校验
 */
function guest_redirect()
{
    header('location: /login.php');
}

/**
 * 已登陆权限校验
 */
function user_redirect()
{
    header('location: /home.php');
}
/**
 * 检测webservice的返回结构，如果符合数组形式则
 * 将 hans,xixi\n1,0,2\n 形式的数据格式化为PHP的数据 [["hans", "xixi"],[1,0,2]]
 * 否则返回false
 * 
 * @param string $string
 * 
 * @return boolean|array 
 */
function format_soap_string_2_array($string) {
    //如果字符包含 ERROR
    if (strpos($string, 'ERROR') !== false) {
        return false;
    }

    //清空两侧的空格
    $string = trim($string);
 
     //如果字符为空，则返回 []
     if ($string === '') {
        return [];
    }
    $result = [];

    $items = explode("\n", $string);
    foreach($items as $item) {
        $result[] = explode(',', $item);
    }
	if ($items[count($items) - 1] == []) {
		unset($items[count($items) - 1]);
	}
	
    return $result;
}
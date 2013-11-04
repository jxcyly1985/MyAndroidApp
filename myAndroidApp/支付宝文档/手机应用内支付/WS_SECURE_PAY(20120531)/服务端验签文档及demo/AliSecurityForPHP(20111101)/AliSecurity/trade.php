<?php

/**
 *类名：trade.php
 *功能  服务器端创建交易Demo
 *版本：1.0
 *日期：2011-09-15
 '说明：
 '以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 '该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
*/

require_once ("alipay_config.php");
require_once ("alipay_function.php");

    //获取待签名字符串
    $content = $this->getData();
    
    //生成签名
    $mySign = sign($this->content);
    
    //返回参数格式
    $return = "<result><is_success>T</is_success><content>" . $this->content . "</content><sign>" . mySign . "</sign></result>";
    
    //返回数据到手机客户端
    echo urlencode($this->return);
    

    //获取客户端创建交易请求的参数
    function getData(){
        //获取商品名称
        $subject = $_POST["subject"];
        //获取商品描述
        $body = $_POST["body"];
        //获取商品金额
        $totalFee = $_POST["total_fee"];
        
        //组装待签名数据
        $signData = "partner=" . "\"" . $partner ."\"";
        $signData .= "&";
        $signData .= "seller=" . "\"" .$seller . "\"";
        $signData .= "&";
        $signData .= "out_trade_no=" . "\"" . $out_trade_no ."\"";
        $signData .= "&";
        $signData .= "subject=" . "\"" . $subject ."\"";
        $signData .= "&";
        $signData .= "body=" . "\"" . $body ."\"";
        $signData .= "&";
        $signData .= "total_fee=" . "\"" . $totalFee ."\"";
        $signData .= "&";
        $signData .= "notify_url=" . "\"" . $notify_url ."\"";
        
        return $signData;
        
    }

?>
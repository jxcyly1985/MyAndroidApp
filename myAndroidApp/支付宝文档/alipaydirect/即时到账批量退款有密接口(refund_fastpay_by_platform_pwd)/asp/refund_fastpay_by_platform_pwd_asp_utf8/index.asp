<%@LANGUAGE="VBSCRIPT" CODEPAGE="65001"%>
<%
' 功能：支付宝即时到账批量退款有密接口调试入口页面
' 版本：3.2
' 日期：2011-03-31
' 说明：
' 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<head>
	<META http-equiv=Content-Type content="text/html; charset=utf-8">
	<title>支付宝即时到账批量退款有密接口</title>
</head>
	<body>
        <FORM name=alisubmit action=refund_fastpay_by_platform_pwd.asp method=post target="_blank">
            <div style="text-align: center; font-size: 9pt; font-family: 宋体">
                退款笔数：<INPUT type="text" size="30" name="batch_num" value=""><br />
                退款详细：<INPUT type="text" size="30" name="detail_data" value=""><br />
                <INPUT type="submit" value="确认"  name="btnAlipay">
            </div>
        </FORM>
	</body>
</html>
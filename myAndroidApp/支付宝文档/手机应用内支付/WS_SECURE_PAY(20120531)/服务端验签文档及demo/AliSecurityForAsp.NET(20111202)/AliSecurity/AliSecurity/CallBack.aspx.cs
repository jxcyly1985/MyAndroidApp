using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using Alipay.Class;

namespace AliSecurityNotify
{
    /// <summary>
    /// 功能：安全支付服务器同步通知页面
    /// 版本：1.0
    /// 日期：2011-09-15
    /// 说明：
    /// 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
    /// 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
    /// 
    /// ///////////////////页面功能说明///////////////////
    /// 客户端callback回调之后请求服务器callback_url
    /// 验签通过就给客户端返回2，不通过就返回1
    /// </summary>
    public partial class CallBack : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                //得到签名
                string sign = HttpUtility.HtmlDecode(Request.Form["sign"].ToString());

                //得到待签名字符串
                string content = HttpUtility.HtmlDecode(Request.Form["content"].ToString());

                //验签数据
                bool isVerify = Function.Verify(content, sign, Config.Alipaypublick, Config.Input_charset_UTF8);

                //请结合客户端，本示例返回1表示验签不通过，返回2表示验签通过
                if (!isVerify)
                {
                    Response.Write("1");
                }
                else
                {
                    Response.Write("2");
                }
            }
        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using Alipay.Class;

/// <summary>
/// 功能：安全支付服务器异步通知页面
/// 版本：1.0
/// 日期：2011-09-15
/// 说明：
/// 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
/// 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
/// 
/// ///////////////////页面功能说明///////////////////
/// 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
/// 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
/// TRADE_FINISHED(表示交易已经成功结束);
/// </summary>

namespace AliSecurityNotify
{
    public partial class Notify : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                //获取notify_data数据，需要添加notify_data=
                //不需要解密，直接是明文xml格式
                string notify_data = "notify_data=" + Request.Form["notify_data"].ToString();

                //获取签名
                string sign = Request.Form["sign"].ToString();

                //验证签名
                bool vailSign = Function.Verify(notify_data, sign, Config.Alipaypublick, Config.Input_charset_UTF8);
                if (!vailSign)
                {
                    Response.Write("fail");
                    return;
                }

                //获取交易状态
                string trade_status = Function.GetStrForXmlDoc(Request.Form["notify_data"].ToString(), "notify/trade_status");

                if (!trade_status.Equals("TRADE_FINISHED"))
                {
                    Response.Write("fail");
                }
                else
                {
                    //成功必须在页面上输出success，支付宝才不会再发送通知
                    Response.Write("success");

                    ///////////////////////////////处理数据/////////////////////////////////
                    // 用户这里可以写自己的商业逻辑
                    // 例如：修改数据库订单状态
                    // 以下数据仅仅进行演示如何调取
                    // 参数对照请详细查阅开发文档
                    // 里面有详细说明
                    string partner = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/partner");
                    string discount = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/discount");
                    string payment_type = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/payment_type");
                    string subject = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/subject");
                    string trade_no = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/trade_no");
                    string buyer_email = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/buyer_email");
                    string gmt_create = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/gmt_create");
                    string quantity = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/quantity");
                    string out_trade_no = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/out_trade_no");
                    string seller_id = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/seller_id");
                    string is_total_fee_adjust = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/trade_status");
                    string total_fee = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/total_fee");
                    string gmt_payment = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/gmt_payment");
                    string seller_email = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/seller_email");
                    string gmt_close = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/gmt_close");
                    string price = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/price");
                    string buyer_id = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/buyer_id");
                    string use_coupon = Alipay.Class.Function.GetStrForXmlDoc(notify_data, "notify/use_coupon");
                    ////////////////////////////////////////////////////////////////////////////

                }
            }
        }
    }
}
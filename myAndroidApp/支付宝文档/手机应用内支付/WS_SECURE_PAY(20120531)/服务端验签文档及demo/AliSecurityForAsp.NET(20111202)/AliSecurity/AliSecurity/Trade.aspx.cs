using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Text;

using Alipay.Class;


namespace AliSecurityNotify
{
    /// <summary>
    /// 类名：Trade
    /// 日期：2011-09-15
    /// 功能：服务器端创建交易Demo
    /// 详细：用于服务器端接收到客户端的请求，并返回签名和待签名参数
    /// 说明：
    /// 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
    /// 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
    /// </summary>
    public partial class Trade : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack) 
            {
                //获取待签名字符串
                string content = getData();
                //生成签名
                string mysign = RSAFromPkcs8.sign(content, Config.privateKey, Config.Input_charset_UTF8);
                //返回参数格式
                string strReturn = "<result><is_success>T</is_success><content>" + content + "</content><sign>" + mysign + "</sign></result>";
                //对参数进行urlencode 防止中文导致乱码
                strReturn = HttpUtility.UrlEncode(strReturn, Encoding.GetEncoding("utf-8"));
                //返回客户端
                Response.Write(strReturn);
            }
        }

        /// <summary>
        /// 获取客户端创建交易传递的参数
        /// </summary>
        /// <returns>待签名字符串</returns>
        private string getData() {

            //合作商户ID
            string partner = Config.Partner;
            //卖家帐号
            string seller = Config.Seller;
            // 外部交易号 这里取当前时间，商户可根据自己的情况修改此参数，但保证唯一性
            string outTradeNo = System.DateTime.Now.ToString();

            //获取商品名称
            string subject = Request.Form["subject"].ToString();
            //获取商品描述
            string body = Request.Form["body"].ToString();
            //获取商品金额
            string totalFee = Request.Form["total_fee"].ToString();
            //notify_url通知地址
            string notify_url = Config.notify_url;

            //组装待签名数据
            string signData = "partner=" + "\"" + partner + "\"";
            signData += "&";
            signData += "seller=" + "\"" + seller + "\"";
            signData += "&";
            signData += "out_trade_no=" + "\"" + outTradeNo + "\"";
            signData += "&";
            signData += "subject=" + "\"" + subject + "\"";
            signData += "&";
            signData += "body=" + "\"" + body + "\"";
            signData += "&";
            signData += "total_fee=" + "\"" + totalFee + "\"";
            signData += "&";
            signData += "notify_url=" + "\"" + notify_url + "\"";
            return signData;
        }
    }
}
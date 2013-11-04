using System.Web;
using System.Text;
using System.IO;
using System.Net;
using System;
using System.Collections.Generic;

namespace Alipay.Class
{
    /// <summary>
    /// 类名：Config
    /// 功能：支付宝配置公共类
    /// 详细：该类是配置所有请求参数，支付宝网关、接口，商户的基本参数等
    /// 版本：1.0
    /// 日期：2011-09-15
    /// 说明：
    /// 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
    /// 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
    /// </summary>
    public class Config
    {
        static Config()
        {
            Partner = "";
            Seller = "";
            Subject = "";
            body = "";
            privateKey = "";
            notify_url = "";
            Input_charset_UTF8 = "utf-8";//必须为utf-8格式
            Alipaypublick = "";
        }

        #region 属性

        /// <summary>
        /// 合作商户ID
        /// </summary>
        public static string Partner
        { get; set; }
        /// <summary>
        /// 卖家帐号
        /// </summary>
        public static string Seller
        { get; set; }
        /// <summary>
        /// 商品名称
        /// </summary>
        public static string Subject
        { get; set; }
        /// <summary>
        /// 商品描述
        /// </summary>
        public static string body
        { get; set; }
        /// <summary>
        /// 商户私钥
        /// </summary>
        public static string privateKey
        { get; set; }
        /// <summary>
        /// 支付宝公钥
        /// </summary>
        public static string Alipaypublick
        { get; set; }
        /// <summary>
        /// 编码格式UTF-8
        /// </summary>
        public static string Input_charset_UTF8
        { get; set; }
        /// <summary>
        /// Notify异步通知URL
        /// </summary>
        public static string notify_url
        { get; set; }

        #endregion
    }
}
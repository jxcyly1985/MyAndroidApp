/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2008 All Rights Reserved.
 */
package com.alipay.client.trade;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Security;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.alipay.client.base.PartnerConfig;
import com.alipay.client.security.RSASignature;
import com.alipay.client.util.StringUtil;

/**
 * 
 * 安全支付服务器端处理程序
 * 
 * 1.将业务参数：合作商户ID、 外部交易号、商品名称、商品的具体描述、商品总价、卖家帐户、notify_url这些参数按照固定顺序签名
 * 2.将签名结果返回客户端
 * @author 3Y
 */
public class RSATrade extends HttpServlet {

	private static final long serialVersionUID = -3035307235076650766L;
	static {
	      Security.addProvider(new BouncyCastleProvider());
	   }
	String basePath="";
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
	    System.out.println("request in");
	    response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
	    //得到应用服务器地址
	    String path = request.getContextPath();
	    basePath = request.getScheme() + "://" + request.getLocalAddr() + ":"
	                               + request.getServerPort() + path + "/";
	    
		String strReString="";
		//检查商户PartnerConfig.java文件的配置参数
		if(!checkInfo()){
		    strReString="<result><is_success>F</is_success><error>缺少partner或者seller," +
		    		"请在com/alipay/client/base/PartnerConfig.java中增加</error></result>";
		    out.print(strReString);
		    return;
		}
		
		String signData=getSignDate(request);
		String sign = sign(signData,PartnerConfig.RSA_PRIVATE);
		//返回待签名数据和签名数据
		strReString="<result><is_success>T</is_success><content>"+signData+"</content><sign>"+sign+"</sign></result>";
		//对返回客户端的数据encode
		out.print(URLEncoder.encode(strReString,"utf-8")); 

        return;
	}
	
	

	//检查商户PartnerConfig.java文件的配置参数
	private boolean checkInfo() {
        String partner = PartnerConfig.PARTNER;
        String seller = PartnerConfig.SELLER;
        //如果合作商户ID为空或者账号ID为空返回false
        if (StringUtil.isBlank(partner) || StringUtil.isBlank(seller))
            return false;
        
        return true;
    }
	
	
	/**
	 * 准备待签名的数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getSignDate(
			HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		
        //合作商户ID
        String partner = PartnerConfig.PARTNER;
        //卖家帐号
        String seller = PartnerConfig.SELLER;
		// 外部交易号 这里取当前时间，商户可根据自己的情况修改此参数，但保证唯一性
		String outTradeNo = System.currentTimeMillis() + "";
		// 商品名称
        String subject = request.getParameter("subject").trim();
        // 商品具体描述
        String body = request.getParameter("body").trim();
        // 商品总价
        String totalFee = request.getParameter("total_fee").trim();
		// 接收支付宝发送的通知的url 商户可根据自己的情况修改此参数
		String notify_url = basePath+"servlet/RSANotifyReceiver";
		
		//组装待签名数据
		String signData = "partner=" + "\"" + partner + "\"";
		signData += "&";
		signData += "seller=" + "\"" + seller + "\"";
		signData += "&";
		signData += "out_trade_no=" + "\"" + outTradeNo + "\"";
		signData += "&";
		signData += "subject=" + "\"" + subject+ "\"";
		signData += "&";
		signData += "body=" + "\"" + body + "\"";
		signData += "&";
		signData += "total_fee=" + "\""+ totalFee + "\"";
		signData += "&";
		signData += "notify_url=" + "\""+notify_url+ "\"";
		return signData;
	}

	
	
	/**
	 * 对参数进行签名
	 * 
	 * @param signData 待签名数据，key rsa商户私钥
	 * @return
	 */
	private String sign(String signData,String key) {
		System.out.println("signData:"+signData);
		String sign = "";
		try {
			sign = RSASignature.sign(signData, key);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sign;
	}

	
}

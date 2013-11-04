/*
 * PartnerConfig.h
 *
 *  Created on: 2010-10-14
 *      Author: vip
 */

#ifndef PARTNERCONFIG_H_
#define PARTNERCONFIG_H_


//合作商户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
_LIT8(PartnerID,"");

//账户ID。用签约支付宝账号登录ms.alipay.com后，在账户信息页面获取。
_LIT8(SellerID,"");

//安全校验码（RSA）支付宝公钥  用签约支付宝账号登录ms.alipay.com后，在密钥管理页面获取。
_LIT8(RSER_PUB_KEY,"");
  
//商户私钥，用于加密数据，商户生成。
_LIT8(RUSER_PRI_KEY,"");

//商户公钥，服务端验签使用， 商户生成。
_LIT8(RUSER_PUB_KEY,"");


#endif /* CONFIGURATION_H_ */

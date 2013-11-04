/************************************************************************/
//crypt.h
//author：landong.zhao
/************************************************************************/


#ifndef CRYPH_H
#define CRYPH_H
#define __NO_MEMORY__ 0x9999

#define __S60_CRPTPT__




#ifdef __S60_CRPTPT__

#include <e32base.h>
//#include "rsa.h"

class Crptpt : public CBase
{
  protected:
      /* 功能： 用公钥加密明文
      * 参数：  aszPlaintText：加密明文,  aszPK: 加密公钥, aszEncode: 秘文
      * 返回值：  错误值
      * 注解： aszEncode需要调用者释放，传入为空
      */
      static  TInt Encrptpt_117L ( const TDesC8& aszPlaintText, const TDesC8& aszPK, HBufC8** aszEncode );

      /* 功能： 用私钥解密秘文
      * 参数：  aszEncode：加密秘文,  aszPK: 解密私钥, aszPlaintText: 明文
      * 返回值：  错误值
      * 注解： aszPlaintText需要调用者释放，传入为空
      */
      static  TInt Decryptor_128L ( const TDesC8& aszEncode, const TDesC8& aszPK, HBufC8** aszPlaintText );
      
  public:
      /* 功能： 用公钥加密明文(无字符长度限制，通过截取字符串多次加密拼接而成)
      * 参数：  aszPlaintText：加密明文,  aszPK: 加密公钥, aszEncode: 秘文
      * 返回值：  错误值
      * 注解： aszEncode需要调用者释放，传入为空
      */
      static  TInt EncrptptL ( const TDesC8& aszPlaintText, const TDesC8& aszPK, HBufC8** aszEncode );
      
      /* 功能： 用私钥解密秘文
      * 参数：  aszEncode：加密秘文,  aszPK: 解密私钥, aszPlaintText: 明文
      * 返回值：  错误值
      * 注解： aszPlaintText需要调用者释放，传入为空
      */
      static  TInt DecryptorL ( const TDesC8& aszEncode, const TDesC8& aszPK, HBufC8** aszPlaintText );
      
      /* 功能： 用公钥验证签名
      * 参数：  aSignPubKey：验证签名的公钥,  aVerifyText: 签名, aPlaintText: 明文
      * 返回值：  错误值
      * 注解： aszPlaintText需要调用者释放，传入为空
      */
      static  TInt VerifyL ( const TDesC8& aSignPubKey,const TDesC8& aVerifyText,const TDesC8& aPlaintText );      
 
      /* 功能： 用私钥签名
      * 参数：  aszText：等待签名的字串,  aszPrivKey: 签名私钥, aszSignText:签名后的字串
      * 返回值：  错误值
      * 注解： aszSignText需要调用者释放，传入为空
      */
      static  TInt RsaSha1SignL ( const TDesC8& aszText, const TDesC8& aszPrivKey, HBufC8** aszSignText );      
  private:
      Crptpt ();
};



#else 

#ifdef __cplusplus
extern "C"
{
#endif

/* 功能： 用公钥加密明文
* 参数：  dst：密文, dlen：密文长度，  pbkey: 加密公钥, pblen:公钥长度, src: 明文,slen:明文长度
* 返回值：  错误值
* 注解： dst需要调用者释放，传入为空
*/
int Encrptpt(unsigned char** dst, int* dlen, 
		  unsigned char* src, int slen, unsigned char* pbkey, int pblen);

/* 功能： 公钥签名验证
* 参数：  dst：密文, dlen：密文长度，  pbkey: 加密公钥, pblen:公钥长度, src: 明文,slen:明文长度
* 返回值：  错误值
* 注解： dst需要调用者释放，传入为空
*/

int Verify( unsigned char* pbKey, int pbKeyLen, 
	  		unsigned char* verifyText, int vLen, unsigned char* plaintText, int ptLen );  

/* 功能： 用私钥解密密文
* 参数：  plaintText: 明文,ptLen:明文长度， pbCryText：密文, ctLen：密文长度，  privKeyLen: 加密私钥, privKeyLen:私钥长度
* 返回值：  错误值
* 注解： plaintText需要调用者释放，传入为空
*/
int Decrypt( unsigned char** plaintText, int* ptLen,
		unsigned char* pbCryText, int ctLen, unsigned char* privKey, int privKeyLen  );  

/* 功能： 私钥签名
* 参数：  sigText：签名, sigLen：签名长度, plaintText: 明文,ptLen:明文长度，  privKey: 签名私钥, privKeyLen:私钥长度
* 返回值：  错误值
* 注解： sigText需要调用者释放，传入为空
*/
int RsaSha1Sign(unsigned char** sigText, int* sigLen, 
		unsigned char* plaintText, int ptLen, unsigned char* privKey, int privKeyLen);  

#ifdef __cplusplus
}
#endif

#endif //__S60_CRPTPT__

#endif //CRYPH_H

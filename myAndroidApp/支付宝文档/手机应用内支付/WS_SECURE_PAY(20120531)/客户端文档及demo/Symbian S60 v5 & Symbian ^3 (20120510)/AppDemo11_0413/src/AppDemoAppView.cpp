/*
 ============================================================================
 Name		: AppDemoAppView.cpp
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : Application view implementation
 ============================================================================
 */

// INCLUDE FILES
#include <coemain.h>
#include "AppDemoAppView.h"

#include <AppDemo_0xED46D97D.rsg>
#include <BARSREAD.H>
#include <EscapeUtils.h>
#include <e32math.h>
#include <hash.h>
#include "PartnerConfig.h"
#include <AknNoteWrappers.h>
#include <APGCLI.H>
#include "crypt.h"
_LIT(KInstallPlugin,"c:\\data\\alipay.sis");
_LIT8(KAliXPayOperationName,"alixpay");

// ============================ MEMBER FUNCTIONS ===============================

// -----------------------------------------------------------------------------
// CAppDemoAppView::NewL()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
CAppDemoAppView* CAppDemoAppView::NewL(const TRect& aRect)
	{
	CAppDemoAppView* self = CAppDemoAppView::NewLC(aRect);
	CleanupStack::Pop(self);
	return self;
	}

// -----------------------------------------------------------------------------
// CAppDemoAppView::NewLC()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
CAppDemoAppView* CAppDemoAppView::NewLC(const TRect& aRect)
	{
	CAppDemoAppView* self = new (ELeave) CAppDemoAppView;
	CleanupStack::PushL(self);
	self->ConstructL(aRect);
	return self;
	}

// -----------------------------------------------------------------------------
// CAppDemoAppView::ConstructL()
// Symbian 2nd phase constructor can leave.
// -----------------------------------------------------------------------------
//
void CAppDemoAppView::ConstructL(const TRect& aRect)
	{
	// Create a window for this application view
	CreateWindowL();
	
	iSecurePayHelper = CSecurePayHelper::NewL(*this);
	TBool ret = iSecurePayHelper->CheckPlugin();
	
	if(ret)
		{
		//StartWaitDialog(ETrue,_L("\x4E0B\x8F7D\x4E2D\x8BF7\x7A0D\x5019\x2E\x2E\x2E"));//下载中请稍候...
		}
	else
		{
		TRAPD(err,iInterface = CInterfaceDefinition::NewL(KAliXPayOperationName));
		}
	
	CreateListBox();
	// Set the windows size
	SetRect(aRect);
	// Activate the window, which makes it ready to be drawn
	ActivateL();
	}

// -----------------------------------------------------------------------------
// CAppDemoAppView::CAppDemoAppView()
// C++ default constructor can NOT contain any code, that might leave.
// -----------------------------------------------------------------------------
//
CAppDemoAppView::CAppDemoAppView()
	{
	// No implementation required
	}

// -----------------------------------------------------------------------------
// CAppDemoAppView::~CAppDemoAppView()
// Destructor.
// -----------------------------------------------------------------------------
//
CAppDemoAppView::~CAppDemoAppView()
	{
	// No implementation required
	if(iListBox)
		{
		delete iListBox;
		iListBox = NULL;
		}
	
	if(iSecurePayHelper)
		{
		delete iSecurePayHelper;
		iSecurePayHelper = NULL;
		}
	
	if(iInterface)
		{
		delete iInterface;
		iInterface = NULL;
		REComSession::FinalClose();
		}	
	}

// -----------------------------------------------------------------------------
// CAppDemoAppView::Draw()
// Draws the display.
// -----------------------------------------------------------------------------
//
void CAppDemoAppView::Draw(const TRect& /*aRect*/) const
	{
	// Get the standard graphics context
	CWindowGc& gc = SystemGc();

	// Gets the control's extent
	TRect drawRect(Rect());

	// Clears the screen
	gc.Clear(drawRect);

	}


// -----------------------------------------------------------------------------
// CAppDemoAppView::HandlePointerEventL()
// Called by framework to handle pointer touch events.
// Note: although this method is compatible with earlier SDKs, 
// it will not be called in SDKs without Touch support.
// -----------------------------------------------------------------------------
//
void CAppDemoAppView::HandlePointerEventL(const TPointerEvent& aPointerEvent)
	{

	// Call base class HandlePointerEventL()
	CCoeControl::HandlePointerEventL(aPointerEvent);
	}

// End of File
void CAppDemoAppView::SizeChanged()
    {
    // TODO: Add here control resize code etc.
	if (iListBox)
		iListBox->SetExtent(TPoint(0, 0), iListBox->MinimumSize());
    }

// ---------------------------------------------------------
// COtherContainer::CountComponentControls() const
// ---------------------------------------------------------
//
TInt CAppDemoAppView::CountComponentControls() const
    {
    return 1; // return nbr of controls inside this container
    }

// ---------------------------------------------------------
// COtherContainer::ComponentControl(TInt aIndex) const
// ---------------------------------------------------------
//
CCoeControl* CAppDemoAppView::ComponentControl(TInt aIndex) const
    {
    switch ( aIndex )
        {
        case 0:
        	return iListBox;
        default:
            return NULL;
        }
    }

void CAppDemoAppView::HandleListBoxEventL(CEikListBox* aListBox, TListBoxEvent aEventType)
	{
	if (aEventType == MEikListBoxObserver::EEventEnterKeyPressed)
		{
		if(aListBox)
			{
			TInt index = aListBox->CurrentItemIndex();
			OnItemSelect(index);
			}
		}
	}

TKeyResponse CAppDemoAppView::OfferKeyEventL(const TKeyEvent& aKeyEvent,TEventCode aType)
	{
	if ( aKeyEvent.iCode == EKeyLeftArrow || aKeyEvent.iCode == EKeyRightArrow )
		{
		return EKeyWasNotConsumed;
		}
	
	if(aType == EEventKeyDown)
		{
		if(aKeyEvent.iScanCode == 1)//C键
			{
			return EKeyWasConsumed;
			}	
		}	
	
	if (iListBox)
		return iListBox->OfferKeyEventL (aKeyEvent, aType);
	else
		return EKeyWasNotConsumed;
	}

void CAppDemoAppView::CreateListBox()
	{
		iListBox = new (ELeave) CAknDoubleLargeStyleListBox();//CAknDoubleStyleListBox;
		iListBox->SetContainerWindowL(*this);
		
		// Second Phase Construction
		TResourceReader reader;
		CEikonEnv::Static()->CreateResourceReaderLC(reader, R_LISTBOX_DOUBLE);
		iListBox->ConstructFromResourceL(reader);
		CleanupStack::PopAndDestroy(); // reader
			
		CArrayPtr<CGulIcon>* icons = new (ELeave) CArrayPtrFlat<CGulIcon>(1);
		CleanupStack::PushL(icons);
		MAknsSkinInstance* skin = AknsUtils::SkinInstance();
		///Add icon
		
		CFbsBitmap* AppIcon(NULL);
		CFbsBitmap* AppIconMsk(NULL);
		CGulIcon* appIcon = NULL;
		appIcon = AknsUtils::CreateGulIconL(skin, KAknsIIDQgnMenuUnknownLst, ETrue);
		icons->AppendL(appIcon);

		CleanupStack::Pop();
		iListBox->ItemDrawer()->ColumnData()->SetIconArray(icons);
		
		CDesCArrayFlat* array = new (ELeave) CDesCArrayFlat(3);
		CleanupStack::PushL(array);
		
		_LIT(KFormat,"\t%S");
		_LIT(KFormatPrice,"\t\x5355\x4ef7\xff1a %S\x5143"); //价格   元
		_LIT(KFormatInt,"%d");
		
		TBuf<256> buf;
		TInt index = 0;
		
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x82f9\x679c"));//苹果
		buf.AppendFormat(KFormatPrice,&_L("0.01"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x8461\x8404"));//葡萄
		buf.AppendFormat(KFormatPrice,&_L("50"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x9999\x8549"));//香蕉
		buf.AppendFormat(KFormatPrice,&_L("51"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x6a31\x6843"));//樱桃
		buf.AppendFormat(KFormatPrice,&_L("100"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x8292\x679c"));//芒果
		buf.AppendFormat(KFormatPrice,&_L("101"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x897f\x74dc"));//西瓜
		buf.AppendFormat(KFormatPrice,&_L("200"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x83e0\x841d"));//菠萝
		buf.AppendFormat(KFormatPrice,&_L("201"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x756a\x8304"));//番茄
		buf.AppendFormat(KFormatPrice,&_L("500"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x9ec4\x6843"));//黄桃
		buf.AppendFormat(KFormatPrice,&_L("501"));
		array->AppendL(buf);
		//
		buf.Zero();
		buf.AppendFormat(KFormatInt,index);
		buf.AppendFormat(KFormat,&_L("\x8354\x679d"));//荔枝
		buf.AppendFormat(KFormatPrice,&_L("2000"));
		array->AppendL(buf);
		//
				
		CleanupStack::Pop();
		iListBox->Model()->SetItemTextArray(array);	
		
		iListBox->SetListBoxObserver(this);
		iListBox->CreateScrollBarFrameL(ETrue);
		iListBox->ScrollBarFrame()->SetScrollBarVisibilityL(
			CEikScrollBarFrame::EOff, CEikScrollBarFrame::EAuto);
		
		iListBox->ItemDrawer()->ColumnData()->EnableMarqueeL(ETrue);
	}

void CAppDemoAppView::AlipayPluginEvent(const TDesC8& resultStatus)
   {
   HBufC8* decodeResult = EscapeUtils::EscapeDecodeL(resultStatus);
   if(decodeResult)
      {
      HandleResult(*decodeResult);   // only handle succ for test   
      delete decodeResult;
      }
   }


void CAppDemoAppView::OnItemSelect(TInt aPosition)
	{
	TBuf8<10> subject;
	TBuf8<10> fee;

	switch (aPosition)
		{
		case 0:
			subject.Copy(_L8("苹果"));//苹果\x82f9\x679c
			fee.Copy(_L8("0.01"));
			break;
		case 1:
			subject.Copy(_L8("葡萄"));//\x8461\x8404
			fee.Copy(_L8("50"));
			break;
		case 2:
			subject.Copy(_L8("香蕉"));//\x9999\x8549
			fee.Copy(_L8("51"));
			break;
		case 3:
			subject.Copy(_L8("樱桃"));//\x6a31\x6843
			fee.Copy(_L8("100"));
			break;
		case 4:
			subject.Copy(_L8("芒果"));//\x8292\x679c
			fee.Copy(_L8("101"));
			break;
		case 5:
			subject.Copy(_L8("西瓜"));//\x897f\x74dc
			fee.Copy(_L8("200"));
			break;
		case 6:
			subject.Copy(_L8("菠萝"));//\x83e0\x841d
			fee.Copy(_L8("201"));
			break;
		case 7:
			subject.Copy(_L8("番茄"));//\x756a\x8304
			fee.Copy(_L8("500"));
			break;
		case 8:
			subject.Copy(_L8("黃桃"));//\x9ec4\x6843
			fee.Copy(_L8("501"));
			break;
		case 9:
			subject.Copy(_L8("荔枝"));//\x8354\x679d
			fee.Copy(_L8("2000"));
			break;
		case 10:
			subject.Copy(_L8("枇杷"));//\x6787\x6777
			fee.Copy(_L8("2001"));
			break;
		case 11:
			subject.Copy(_L8("甘蔗"));//\x7518\x8517
			fee.Copy(_L8("5000"));
			break;
		case 12:
			subject.Copy(_L8("龙眼"));//\x9f99\x773c
			fee.Copy(_L8("5001"));
			break;
		default:
			return;
		}
	
		TBuf8<32> aPartnerID;
		TBuf8<32> aSellerID;
		TBuf8<64> aMD5_KEY;
		
		
		aPartnerID.Copy(PartnerID);
		aSellerID.Copy(SellerID);
		aMD5_KEY.Copy(MD5_KEY);
		
		if(aPartnerID.Length() == 0 || aSellerID.Length() == 0 || aMD5_KEY.Length() == 0)
			{
			CAknInformationNote* informationNote;
			informationNote = new (ELeave) CAknInformationNote;
			// Show the information Note
			informationNote->ExecuteLD(_L("Parameter is null"));
			return;
			}

		OrderPay(subject, fee);
	}


HBufC8* CAppDemoAppView::DoMD5(const TDesC8& aString)
	{
	TBuf8<1024> string;
	string.Append(aString);
	string.Append(MD5_KEY);

	CMD5* md5 = CMD5::NewL();
	CleanupStack::PushL(md5);
	TPtrC8 hashedSig = md5->Hash(string);
	HBufC8* buf = HBufC8::NewL(hashedSig.Length() * 2);
	TPtr8 bufPtr = buf->Des();

	for (TInt i = 0; i < hashedSig.Length(); i++)
		bufPtr.AppendFormat(_L8("%+02x"), hashedSig[i]);

	CleanupStack::PopAndDestroy(md5);
	return buf;
	}

HBufC8* CAppDemoAppView::DoRSA(const TDesC8& aString)
{
	HBufC8* signText = NULL;
	Crptpt::RsaSha1SignL(aString,RUSER_PRI_KEY,&signText);
	return signText;
}

void CAppDemoAppView::GenerateKey(TDes8& aKey, TInt aLen)
	{
	aKey.Zero();

	TTime currentTime;
	currentTime.HomeTime();

	// �?000年开始计�?
	TInt startYear = 2000;

	// 当前年份
	TInt currentYear = currentTime.DateTime().Year();
	TTime time(TDateTime(currentYear, EJanuary, 0, 0, 0, 0, 0));

	TTimeIntervalSeconds s;
	currentTime.SecondsFrom(time, s);

	// 得到秒数
	TInt i = s.Int();

	aKey.AppendFormat(_L8("%X"), i);
	aKey.AppendFormat(_L8("%X"), currentYear - startYear);

	TInt len = aKey.Length();
	if (len > aLen)
		{
		aKey.Mid(0, aLen);
		}
	else
		{
		for (TInt i = 0; i < aLen - len; i++)
			{
			TTime theTime;
			theTime.UniversalTime();
			TInt64 randSeed(theTime.Int64());
			TInt number(Math::Rand(randSeed) + i);

			number = number % 10 + 48;
			aKey.Append(number);
			}
		}
	}

void CAppDemoAppView::OrderPay(const TDesC8& aSubject,
		const TDesC8& aTotalFee)
	{	
	HBufC8* order = GetOrderInfo(aSubject, aTotalFee);
	if (order == NULL)
		return;

	TBuf8<2048> info;

	HBufC8* sign = NULL;
	sign = DoRSA(order->Des());

	HBufC8* signEncoded = EscapeUtils::EscapeEncodeL(sign->Des(),
			EscapeUtils::EEscapeUrlEncoded);

	info.Append(order->Des());
	info.Append(_L8("&sign=\""));
	info.Append(signEncoded->Des());
	info.Append(_L8("\""));
	info.Append(_L8("&sign_type=\"RSA\""));
//	info.Append(_L8("&pay_channel_id=\""));
//	info.AppendNum(KPayChannelId);
//	info.Append(_L8("\""));

	delete sign;
	delete signEncoded;
	delete order;

	TUint32 id = 0;
	
	if(iInterface)
		{
		TRAPD(error, iInterface->AliXPay(REINTERPRET_CAST(CCoeAppUi*, iCoeEnv->AppUi()), this, info, id));
		}
	else
		{
		NotifyUser(_L("\x6682\x4E0D\x80FD\x652F\x4ED8\xFF0C\x8BF7\x5B89\x88C5\x652F\x4ED8\x5B9D\x5B89\x5168\x652F\x4ED8\x670D\x52A1\x63D2\x4EF6"));//暂不能支付，请安装支付宝安全支付服务插件
		}
	}

HBufC8* CAppDemoAppView::GetOrderInfo(const TDesC8& aSubject,
		const TDesC8& aTotalFee)
	{
	TBuf8<1024> info;
	info.Append(_L8("partner=\""));
	info.Append(PartnerID);
	info.Append(_L8("\"&seller=\""));
	info.Append(SellerID);
	info.Append(_L8("\"&out_trade_no=\""));

	TBuf8<15> out_trade_no;
	GenerateKey(out_trade_no, 15);
	info.Append(out_trade_no);

	info.Append(_L8("\"&subject=\""));
	info.Append(aSubject);
	info.Append(_L8("\"&body=\"kkk\"&total_fee=\""));
	info.Append(aTotalFee);
	info.Append(_L8("\"&notify_url=\""));

	TBuf8<256> url;
	url.Copy(_L8("http://notify.java.jpxx.org/index.jsp"));
	HBufC8* urlEncoded = EscapeUtils::EscapeEncodeL(url,
			EscapeUtils::EEscapeUrlEncoded);

	info.Append(urlEncoded->Des());
	info.Append(_L8("\"&call_back_url=\""));
	info.Append(urlEncoded->Des());
	delete urlEncoded;
	info.Append(_L8("\""));

	HBufC8* buf = HBufC8::NewL(info.Length());
	buf->Des().Copy(info);

	return buf;
	}

void CAppDemoAppView::NotifyUser(const TDesC& aData)
	{
	CAknInformationNote* informationNote;
	informationNote = new (ELeave) CAknInformationNote;
	// Show the information Note
	informationNote->ExecuteLD(aData);
	}

 void CAppDemoAppView::AliPayObserver(TInt aStatus)
	 {
	 if(aStatus == 0)
		 {
		 //安装成功，请重启应用后再尝试支付
		 NotifyUser(_L("\x5B89\x88C5\x6210\x529F\xFF0C\x8BF7\x91CD\x542F\x5E94\x7528\x540E\x518D\x5C1D\x8BD5\x652F\x4ED8"));
		 TRAPD(err,iInterface = CInterfaceDefinition::NewL(KAliXPayOperationName));
		 }
	 else
		 {
		 //安装失败，请手动安装后再进行支付
		 NotifyUser(_L("\x5B89\x88C5\x5931\x8D25\xFF0C\x8BF7\x624B\x52A8\x5B89\x88C5\x540E\x518D\x8FDB\x884C\x652F\x4ED8"));
		 }
	 }
 
 void CAppDemoAppView::HandleResult(const TDesC8& aResult)
 	{ 		
 		TInt status = GetPayStatus(aResult);
 		
 		if(status != 9000)//failed
 			return; 		
 		
 		HBufC8* afBuf = aResult.AllocL();
 		TPtr8 ptr8 = afBuf->Des();
 		EscapeChar(ptr8);
 		HBufC8* verify = NULL;
 		HBufC8* plaint = NULL; 		
 		HBufC* retult = HBufC::NewL(64);
 		GetVerifyText(*afBuf,&verify,&plaint); 		
 		delete afBuf;
 		
 		if(verify&&plaint)
 		{
			TInt ret = Crptpt::VerifyL(RSER_PUB_KEY,*verify,*plaint);

		    TBool isSucc = IsSuccess(aResult);
		   
 			if(ret && isSucc)//verify and find succ
 				retult->Des().Append(_L("  verify succ  "));
 			else
 				retult->Des().Append(_L("  verify fail  "));
 		}
 		
 		NotifyUser(*retult);
 		delete retult;
 		
 		if(verify)
 			delete verify;
 		if(plaint)
 			delete plaint;	 
 		
 	}

 TInt CAppDemoAppView::GetPayStatus(const TDesC8& aResult)
 	{
 		TInt value = 4006;
 		TInt oriPos = 0;
 		TInt endPos = 0;
 		oriPos = aResult.Find(_L8("resultStatus={"));
 		if(  oriPos != KErrNotFound )
 		{
 			oriPos+=14;
			HBufC8* temp =  aResult.Mid(oriPos).Left(4).AllocL();
			if(temp)
			{
				TLex8 lex(*temp);				
				if(lex.Val(value) == KErrNone) 
				{
					delete temp;
					return value;
				}
				delete temp;
			}
 		}
 		return 4006;
 	}

 TBool CAppDemoAppView::IsSuccess(const TDesC8& aResult)
 	{
 	TInt oriPos = 0;
 	TInt endPos = 0;
 	oriPos = aResult.Find(_L8("success=\\\""));
 	if( oriPos != KErrNotFound )
 	{
 		oriPos+=10;
 		TInt posSucc = aResult.Find(_L8("\\\"&sign_type"));
 		if(posSucc != KErrNotFound && (posSucc-oriPos>0))
 		{
			HBufC8* ptrSucc = aResult.Mid(oriPos,posSucc-oriPos).AllocL();
			if(ptrSucc->CompareF(_L8("true"))==0) 
			{
			delete ptrSucc;
			return ETrue;
			}
			delete ptrSucc;
 		}
 	}	
 	return EFalse;
 	}

 _LIT8(KText1,"&sign=\"");
 _LIT8(KText2,"\"");

 _LIT8(KText3,"result=\{");
 _LIT8(KText4,"&sign_type=");

 void CAppDemoAppView::GetVerifyText(const TDesC8& aResult, HBufC8** aSign,HBufC8** aPlaintText)
 	{
 	TInt oriPos = 0;
 	oriPos = aResult.Length();
 	oriPos = 0;
 	TInt endPos = 0;
 	oriPos = aResult.Find(KText1);
 	if(  oriPos != KErrNotFound )
 	{
 		oriPos+=7;
 		TInt posSucc = aResult.Mid(oriPos).Find(KText2);
 		if(posSucc != KErrNotFound)
 		{
			*aSign = aResult.Mid(oriPos,posSucc).AllocL();
 		}
 	}
 	oriPos = 0;
 	endPos = 0;	
 	if( (oriPos = aResult.Find(KText3)) != KErrNotFound )
 	{
 		oriPos+=8;
 		TInt posSucc = aResult.Mid(oriPos).Find(KText4);
 		if(posSucc != KErrNotFound)
 		{
			*aPlaintText = aResult.Mid(oriPos,posSucc).AllocL();
 		} 		
 	}
 }

 void CAppDemoAppView::EscapeChar(TDes8& aData)
 {
	 TInt pos = 0;
	 while(1)
	 {
		 pos = aData.Find(_L8("\\\""));
		 if(pos == KErrNotFound)
			 break;
		 else
		 {
		 aData.Replace(pos,2,_L8("\""));
		 }
	 }
 }

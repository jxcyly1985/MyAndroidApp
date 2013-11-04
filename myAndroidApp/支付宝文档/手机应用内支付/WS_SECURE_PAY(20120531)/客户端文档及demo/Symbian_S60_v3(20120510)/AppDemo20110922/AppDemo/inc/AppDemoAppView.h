/*
 ============================================================================
 Name		: AppDemoAppView.h
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : Declares view class for application.
 ============================================================================
 */

#ifndef __APPDEMOAPPVIEW_h__
#define __APPDEMOAPPVIEW_h__

// INCLUDES
#include <coecntrl.h>
#include <aknlists.h>

#include "Alipay.h"
#include "AlipayPluginObserver.h"

// CLASS DECLARATION
class CAppDemoAppView : public CCoeControl,
						public MEikListBoxObserver,
						public MAlipayPluginObserver
	{
public:
	// New methods

	/**
	 * NewL.
	 * Two-phased constructor.
	 * Create a CAppDemoAppView object, which will draw itself to aRect.
	 * @param aRect The rectangle this view will be drawn to.
	 * @return a pointer to the created instance of CAppDemoAppView.
	 */
	static CAppDemoAppView* NewL(const TRect& aRect);

	/**
	 * NewLC.
	 * Two-phased constructor.
	 * Create a CAppDemoAppView object, which will draw itself
	 * to aRect.
	 * @param aRect Rectangle this view will be drawn to.
	 * @return A pointer to the created instance of CAppDemoAppView.
	 */
	static CAppDemoAppView* NewLC(const TRect& aRect);

	/**
	 * ~CAppDemoAppView
	 * Virtual Destructor.
	 */
	virtual ~CAppDemoAppView();

public:
	// Functions from base classes

	/**
	 * From CCoeControl, Draw
	 * Draw this CAppDemoAppView to the screen.
	 * @param aRect the rectangle of this view that needs updating
	 */
	void Draw(const TRect& aRect) const;

	/**
	 * From CoeControl, SizeChanged.
	 * Called by framework when the view size is changed.
	 */
	virtual void SizeChanged();

	/**
	 * From CoeControl, HandlePointerEventL.
	 * Called by framework when a pointer touch event occurs.
	 * Note: although this method is compatible with earlier SDKs, 
	 * it will not be called in SDKs without Touch support.
	 * @param aPointerEvent the information about this event
	 */
	virtual void HandlePointerEventL(const TPointerEvent& aPointerEvent);

private:
	TInt CountComponentControls() const;
	CCoeControl* ComponentControl(TInt aIndex) const;
	void HandleListBoxEventL(CEikListBox* aListBox, TListBoxEvent aEventType);
	TKeyResponse OfferKeyEventL(const TKeyEvent& aKeyEvent,TEventCode aType);	

private:
	// Constructors

	/**
	 * ConstructL
	 * 2nd phase constructor.
	 * Perform the second phase construction of a
	 * CAppDemoAppView object.
	 * @param aRect The rectangle this view will be drawn to.
	 */
	void ConstructL(const TRect& aRect);

	/**
	 * CAppDemoAppView.
	 * C++ default constructor.
	 */
	CAppDemoAppView();
	
private:
	void CreateListBox();
	void AlipayPluginEvent(const TDesC8& resultStatus);
	void OnItemSelect(TInt aPosition);
	HBufC8* DoRSA(const TDesC8& aString);
	void GenerateKey(TDes8& aKey, TInt aLen);
	void OrderPay(const TDesC8& aSubject,const TDesC8& aTotalFee);
	HBufC8* GetOrderInfo(const TDesC8& aSubject,const TDesC8& aTotalFee);
	TBool CheckPlugin();
	void HandleResult(const TDesC8& aResult);	
	TInt GetPayStatus(const TDesC8& aResult);
	TBool IsSuccess(const TDesC8& aResult);
	void GetVerifyText(const TDesC8& aResult, HBufC8** aSign,HBufC8** aPlaintText);
	void EscapeChar(TDes8& aData);
private:
	void NotifyUser(const TDesC& aData);
	
private:
	CAknDoubleLargeStyleListBox*   iListBox;
	CInterfaceDefinition* iInterface;
	CAknWaitDialog* iWaitDialog;
	};

#endif // __APPDEMOAPPVIEW_h__
// End of File

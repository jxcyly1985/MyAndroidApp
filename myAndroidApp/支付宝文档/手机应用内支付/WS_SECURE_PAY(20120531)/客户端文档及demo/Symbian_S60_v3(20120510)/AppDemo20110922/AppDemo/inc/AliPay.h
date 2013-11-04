/*
 ============================================================================
 Name		 	: AliPay.h
 Author	  	 	: kuohai
 Copyright   	: (C) Copyright 2009-2010, Alipay all right reserved.
 Description 	: AliPayInterfaceDefinition.h - CAliPayInterfaceDefinition class header
 ============================================================================
 */

#ifndef _C_ALIPAY_INTERFACEDEFINITION_
#define _C_ALIPAY_INTERFACEDEFINITION_

#include <e32base.h>
#include <ECom.h>
#include <badesca.h>
#include <coeaui.h>

#include "AlipayPluginObserver.h"
/**
 * Constant:    KAliPayUid
 *
 * Description: UID of this ECOM interface. It should be unique in the system.
 *              It is used to identify this specific custom interface.
 *              Implementations of this interface will use this ID, when they
 *              publish the implementation. Clients use this UID to search for
 *              implementations for this interface (the
 *              AliPay.inl does this).
 */
#ifdef EKA2
    const TUid KAliPayUid = {0xE0009999};
#else
    const TUid KAliPayUid = {0x10009999};
#endif

/**
 * Class:       CAliPayInterfaceDefinition
 *
 * Description: Custom ECOM interface definition. This interface is used by
 *              clients to find specific instance and do corresponding
 *              operation for given too numbers. Plugin
 *              implementations implement the  function.
 */
class CInterfaceDefinition : public CBase
    {
public: // Wrapper functions to handle ECOM "connectivity".
	
    static CInterfaceDefinition* NewL(const TDesC8& aOperationName);

    /**
     * Function:   ~CAlixPay
     *
     * Description: Wraps ECom object destruction. Notifies the ECOM
     *              framework that specific instance is being destroyed.
     *              See EcomInterfaceDefinition.inl for details.
     */
    virtual ~CInterfaceDefinition();

public: // Public pure virtual functions, which are implemented by
	virtual void AliXPay(CCoeAppUi * aAppUi,MAlipayPluginObserver * aObserver,const TDesC8& aOrderInfo, TUint32 aIapId) = 0;
	

private:

    /** iDtor_ID_Key Instance identifier key. When instance of an
     *               implementation is created by ECOM framework, the
     *               framework will assign UID for it. The UID is used in
     *               destructor to notify framework that this instance is
     *               being destroyed and resources can be released.
     */
    TUid iDtor_ID_Key;
    };

#include "AliPay.inl" // Our own base implementations for ECOM

#endif // __C_INTERFACEDEFINITION__

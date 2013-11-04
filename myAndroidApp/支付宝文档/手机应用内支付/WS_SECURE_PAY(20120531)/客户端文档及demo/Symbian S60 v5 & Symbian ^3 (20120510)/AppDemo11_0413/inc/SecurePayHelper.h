/*
 ============================================================================
 Name		: SecurePayHelper.h
 Author	  : Mark15021
 Version	 : 1.0
 Copyright   : Your copyright notice
 Description : CSecurePayHelper declaration
 ============================================================================
 */

#ifndef SECUREPAYHELPER_H
#define SECUREPAYHELPER_H

// INCLUDES
#include <e32std.h>
#include <e32base.h>
#include "AlipayHelpObserver.h"

// CLASS DECLARATION

/**
 *  CSecurePayHelper
 * 
 */
class CSecurePayHelper : public CBase
	{
public:
	// Constructors and destructor

	/**
	 * Destructor.
	 */
	~CSecurePayHelper();

	/**
	 * Two-phased constructor.
	 */
	static CSecurePayHelper* NewL(MAliPayHelpObserver& aMAliPayHelpObserver);

	/**
	 * Two-phased constructor.
	 */
	static CSecurePayHelper* NewLC(MAliPayHelpObserver& aMAliPayHelpObserver);

private:

	/**
	 * Constructor for performing 1st stage construction
	 */
	CSecurePayHelper(MAliPayHelpObserver& aMAliPayHelpObserver);

	/**
	 * EPOC default constructor for performing 2nd stage construction
	 */
	void ConstructL();
	
public:
	TBool CheckPlugin(TInt aIap = 0);
	};

#endif // SECUREPAYHELPER_H

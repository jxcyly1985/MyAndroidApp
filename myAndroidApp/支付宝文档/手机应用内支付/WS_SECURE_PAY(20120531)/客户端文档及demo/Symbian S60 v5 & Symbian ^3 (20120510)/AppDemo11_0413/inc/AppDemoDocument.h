/*
 ============================================================================
 Name		: AppDemoDocument.h
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : Declares document class for application.
 ============================================================================
 */

#ifndef __APPDEMODOCUMENT_h__
#define __APPDEMODOCUMENT_h__

// INCLUDES
#include <akndoc.h>

// FORWARD DECLARATIONS
class CAppDemoAppUi;
class CEikApplication;

// CLASS DECLARATION

/**
 * CAppDemoDocument application class.
 * An instance of class CAppDemoDocument is the Document part of the
 * AVKON application framework for the AppDemo example application.
 */
class CAppDemoDocument : public CAknDocument
	{
public:
	// Constructors and destructor

	/**
	 * NewL.
	 * Two-phased constructor.
	 * Construct a CAppDemoDocument for the AVKON application aApp
	 * using two phase construction, and return a pointer
	 * to the created object.
	 * @param aApp Application creating this document.
	 * @return A pointer to the created instance of CAppDemoDocument.
	 */
	static CAppDemoDocument* NewL(CEikApplication& aApp);

	/**
	 * NewLC.
	 * Two-phased constructor.
	 * Construct a CAppDemoDocument for the AVKON application aApp
	 * using two phase construction, and return a pointer
	 * to the created object.
	 * @param aApp Application creating this document.
	 * @return A pointer to the created instance of CAppDemoDocument.
	 */
	static CAppDemoDocument* NewLC(CEikApplication& aApp);

	/**
	 * ~CAppDemoDocument
	 * Virtual Destructor.
	 */
	virtual ~CAppDemoDocument();

public:
	// Functions from base classes

	/**
	 * CreateAppUiL
	 * From CEikDocument, CreateAppUiL.
	 * Create a CAppDemoAppUi object and return a pointer to it.
	 * The object returned is owned by the Uikon framework.
	 * @return Pointer to created instance of AppUi.
	 */
	CEikAppUi* CreateAppUiL();

private:
	// Constructors

	/**
	 * ConstructL
	 * 2nd phase constructor.
	 */
	void ConstructL();

	/**
	 * CAppDemoDocument.
	 * C++ default constructor.
	 * @param aApp Application creating this document.
	 */
	CAppDemoDocument(CEikApplication& aApp);

	};

#endif // __APPDEMODOCUMENT_h__
// End of File

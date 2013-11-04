/*
 ============================================================================
 Name		: AppDemoAppUi.h
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : Declares UI class for application.
 ============================================================================
 */

#ifndef __APPDEMOAPPUI_h__
#define __APPDEMOAPPUI_h__

// INCLUDES
#include <aknappui.h>

// FORWARD DECLARATIONS
class CAppDemoAppView;

// CLASS DECLARATION
/**
 * CAppDemoAppUi application UI class.
 * Interacts with the user through the UI and request message processing
 * from the handler class
 */
class CAppDemoAppUi : public CAknAppUi
	{
public:
	// Constructors and destructor

	/**
	 * ConstructL.
	 * 2nd phase constructor.
	 */
	void ConstructL();

	/**
	 * CAppDemoAppUi.
	 * C++ default constructor. This needs to be public due to
	 * the way the framework constructs the AppUi
	 */
	CAppDemoAppUi();

	/**
	 * ~CAppDemoAppUi.
	 * Virtual Destructor.
	 */
	virtual ~CAppDemoAppUi();

private:
	// Functions from base classes

	/**
	 * From CEikAppUi, HandleCommandL.
	 * Takes care of command handling.
	 * @param aCommand Command to be handled.
	 */
	void HandleCommandL(TInt aCommand);

	/**
	 *  HandleStatusPaneSizeChange.
	 *  Called by the framework when the application status pane
	 *  size is changed.
	 */
	void HandleStatusPaneSizeChange();

	/**
	 *  From CCoeAppUi, HelpContextL.
	 *  Provides help context for the application.
	 *  size is changed.
	 */
	CArrayFix<TCoeHelpContext>* HelpContextL() const;

private:
	// Data

	/**
	 * The application view
	 * Owned by CAppDemoAppUi
	 */
	CAppDemoAppView* iAppView;

	};

#endif // __APPDEMOAPPUI_h__
// End of File

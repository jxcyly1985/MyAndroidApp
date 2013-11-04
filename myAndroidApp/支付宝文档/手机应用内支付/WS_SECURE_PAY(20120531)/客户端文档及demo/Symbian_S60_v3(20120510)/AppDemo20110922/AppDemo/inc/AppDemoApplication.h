/*
 ============================================================================
 Name		: AppDemoApplication.h
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : Declares main application class.
 ============================================================================
 */

#ifndef __APPDEMOAPPLICATION_H__
#define __APPDEMOAPPLICATION_H__

// INCLUDES
#include <aknapp.h>
#include "AppDemo.hrh"

// UID for the application;
// this should correspond to the uid defined in the mmp file
const TUid KUidAppDemoApp =
	{
	_UID3
	};

// CLASS DECLARATION

/**
 * CAppDemoApplication application class.
 * Provides factory to create concrete document object.
 * An instance of CAppDemoApplication is the application part of the
 * AVKON application framework for the AppDemo example application.
 */
class CAppDemoApplication : public CAknApplication
	{
public:
	// Functions from base classes

	/**
	 * From CApaApplication, AppDllUid.
	 * @return Application's UID (KUidAppDemoApp).
	 */
	TUid AppDllUid() const;

protected:
	// Functions from base classes

	/**
	 * From CApaApplication, CreateDocumentL.
	 * Creates CAppDemoDocument document object. The returned
	 * pointer in not owned by the CAppDemoApplication object.
	 * @return A pointer to the created document object.
	 */
	CApaDocument* CreateDocumentL();
	};

#endif // __APPDEMOAPPLICATION_H__
// End of File

/*
 ============================================================================
 Name		: AppDemoApplication.cpp
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : Main application class
 ============================================================================
 */

// INCLUDE FILES
#include "AppDemo.hrh"
#include "AppDemoDocument.h"
#include "AppDemoApplication.h"

// ============================ MEMBER FUNCTIONS ===============================

// -----------------------------------------------------------------------------
// CAppDemoApplication::CreateDocumentL()
// Creates CApaDocument object
// -----------------------------------------------------------------------------
//
CApaDocument* CAppDemoApplication::CreateDocumentL()
	{
	// Create an AppDemo document, and return a pointer to it
	return CAppDemoDocument::NewL(*this);
	}

// -----------------------------------------------------------------------------
// CAppDemoApplication::AppDllUid()
// Returns application UID
// -----------------------------------------------------------------------------
//
TUid CAppDemoApplication::AppDllUid() const
	{
	// Return the UID for the AppDemo application
	return KUidAppDemoApp;
	}

// End of File

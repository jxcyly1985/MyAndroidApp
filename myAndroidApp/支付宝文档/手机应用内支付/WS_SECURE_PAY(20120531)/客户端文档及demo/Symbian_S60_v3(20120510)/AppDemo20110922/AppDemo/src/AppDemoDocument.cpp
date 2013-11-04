/*
 ============================================================================
 Name		: AppDemoDocument.cpp
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : CAppDemoDocument implementation
 ============================================================================
 */

// INCLUDE FILES
#include "AppDemoAppUi.h"
#include "AppDemoDocument.h"

// ============================ MEMBER FUNCTIONS ===============================

// -----------------------------------------------------------------------------
// CAppDemoDocument::NewL()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
CAppDemoDocument* CAppDemoDocument::NewL(CEikApplication& aApp)
	{
	CAppDemoDocument* self = NewLC(aApp);
	CleanupStack::Pop(self);
	return self;
	}

// -----------------------------------------------------------------------------
// CAppDemoDocument::NewLC()
// Two-phased constructor.
// -----------------------------------------------------------------------------
//
CAppDemoDocument* CAppDemoDocument::NewLC(CEikApplication& aApp)
	{
	CAppDemoDocument* self = new (ELeave) CAppDemoDocument(aApp);

	CleanupStack::PushL(self);
	self->ConstructL();
	return self;
	}

// -----------------------------------------------------------------------------
// CAppDemoDocument::ConstructL()
// Symbian 2nd phase constructor can leave.
// -----------------------------------------------------------------------------
//
void CAppDemoDocument::ConstructL()
	{
	// No implementation required
	}

// -----------------------------------------------------------------------------
// CAppDemoDocument::CAppDemoDocument()
// C++ default constructor can NOT contain any code, that might leave.
// -----------------------------------------------------------------------------
//
CAppDemoDocument::CAppDemoDocument(CEikApplication& aApp) :
	CAknDocument(aApp)
	{
	// No implementation required
	}

// ---------------------------------------------------------------------------
// CAppDemoDocument::~CAppDemoDocument()
// Destructor.
// ---------------------------------------------------------------------------
//
CAppDemoDocument::~CAppDemoDocument()
	{
	// No implementation required
	}

// ---------------------------------------------------------------------------
// CAppDemoDocument::CreateAppUiL()
// Constructs CreateAppUi.
// ---------------------------------------------------------------------------
//
CEikAppUi* CAppDemoDocument::CreateAppUiL()
	{
	// Create the application user interface, and return a pointer to it;
	// the framework takes ownership of this object
	return new (ELeave) CAppDemoAppUi;
	}

// End of File

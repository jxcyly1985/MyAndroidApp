/*
 ============================================================================
 Name		: AppDemo.cpp
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : Main application class
 ============================================================================
 */

// INCLUDE FILES
#include <eikstart.h>
#include "AppDemoApplication.h"

LOCAL_C CApaApplication* NewApplication()
	{
	return new CAppDemoApplication;
	}

GLDEF_C TInt E32Main()
	{
	return EikStart::RunApplication(NewApplication);
	}


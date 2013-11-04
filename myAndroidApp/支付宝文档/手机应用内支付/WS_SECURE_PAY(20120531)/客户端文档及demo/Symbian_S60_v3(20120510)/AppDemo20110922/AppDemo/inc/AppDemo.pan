/*
 ============================================================================
 Name		: AppDemo.pan
 Author	  : Mark15021
 Copyright   : Your copyright notice
 Description : This file contains panic codes.
 ============================================================================
 */

#ifndef __APPDEMO_PAN__
#define __APPDEMO_PAN__

/** AppDemo application panic codes */
enum TAppDemoPanics
	{
	EAppDemoUi = 1
	// add further panics here
	};

inline void Panic(TAppDemoPanics aReason)
	{
	_LIT(applicationName, "AppDemo");
	User::Panic(applicationName, aReason);
	}

#endif // __APPDEMO_PAN__

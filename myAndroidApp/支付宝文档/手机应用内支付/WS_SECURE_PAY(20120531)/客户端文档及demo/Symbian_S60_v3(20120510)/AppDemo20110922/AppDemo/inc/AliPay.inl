/*
 ============================================================================
 Name		 	: AliPay.inl
 Author	  	 	: kuohai
 Copyright   	: (C) Copyright 2009-2010, Alipay all right reserved.
 Description 	: AliPay.inl - CAlixPay
 ============================================================================
 */

// Interface's (abstract base class's) destructor
inline CInterfaceDefinition::~CInterfaceDefinition()
	{
	// If in the NewL some memory is reserved for member data, it must be
	// released here. This interface does not have any instance variables so
	// no need to delete anything.

	// Inform the ECOM framework that this specific instance of the
	// interface has been destroyed.
	REComSession::DestroyedImplementation(iDtor_ID_Key);
	}

// Interface's (abstract base class's) static factory method implementation.
// Asks ECOM plugin framework to instantiateappropriate concret plugin
// implementation.
inline CInterfaceDefinition* CInterfaceDefinition::NewL(
		const TDesC8& aOperationName)
	{
	// In this example the aOperationName name can be either "sum" or
	// "multiply". Implementations define recognition data in their
	// "default_data" section of the resource, so that resolver can decide,
	// which implementation to choose. For an example resource definition, see
	//     ..\plugin\101F5465.rss
	// This NewL uses default resolver, which just compares given
	// aOperationName and default_data string in the implementation
	// resource file.

	// Define options, how the default resolver will find appropriate
	// implementation.
	TEComResolverParams resolverParams;
	resolverParams.SetDataType(aOperationName);
	resolverParams.SetWildcardMatch(ETrue); // Allow wildcard matching

	// Find implementation for our interface.
	// - KInterfaceDefinitionUid is the UID of our custom ECOM
	//   interface. It is defined in EComInterfaceDefinition.h
	// - This call will leave, if the plugin architecture cannot find
	//   implementation.
	// - The returned pointer points to one of our interface implementation
	//   instances.
	TAny* ptr = REComSession::CreateImplementationL(KAliPayUid, _FOFF(
			CInterfaceDefinition, iDtor_ID_Key),
	// _FOFF specifies offset of iDtor_ID_Key so that
			// ECOM framework can update its value.
			resolverParams);

	// The type of TAny* ptr should be CCalculationInterfaceDefinition.
	return	REINTERPRET_CAST(CInterfaceDefinition*, ptr);
	}

/*
 ============================================================================
 Name		 	: AlipayPluginObserver.h
 Author	  	 	: kuohai
 Copyright   	: (C) Copyright 2009-2010, Alipay all right reserved.
 Description 	: AlipayPluginObserver.h - MAlipayPluginObserver class header
 ============================================================================
 */
#ifndef __ALIPAYPLUGINOBSERVER_H__
#define __ALIPAYPLUGINOBSERVER_H__

class MAlipayPluginObserver
	{
public:
	virtual void AlipayPluginEvent(const TDesC8& resultStatus) = 0;
	};

#endif /* __ALIPAYPLUGINOBSERVER_H__ */

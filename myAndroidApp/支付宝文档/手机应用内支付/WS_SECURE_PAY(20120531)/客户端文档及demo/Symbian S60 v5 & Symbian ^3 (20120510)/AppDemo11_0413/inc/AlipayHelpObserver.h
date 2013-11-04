/*
 * AlipayHelpObserver.h
 *
 *  Created on: 2011-2-17
 *      Author: Mark15021
 */

#ifndef ALIPAYHELPOBSERVER_H_
#define ALIPAYHELPOBSERVER_H_

class MAliPayHelpObserver
	{
public:
	virtual void AliPayObserver(TInt aStatus) = 0;
	};


#endif /* ALIPAYHELPOBSERVER_H_ */

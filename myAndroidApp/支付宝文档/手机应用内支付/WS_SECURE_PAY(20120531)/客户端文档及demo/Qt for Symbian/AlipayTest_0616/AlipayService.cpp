#include <eikenv.h>
#include <coemain.h>
#include <aknappui.h>
#include "AlipayService.h"
#include "AlipayWrapperPrivate.h"

QAlipayService::QAlipayService()
{
    iWrapperPrivate = NULL;
}

void QAlipayService::AliXPay(const QByteArray& aOrderInfo, TUint32 aIapId)
{
    if(iWrapperPrivate)
    {
        delete iWrapperPrivate;
        iWrapperPrivate = NULL;
    }
    iWrapperPrivate = new QAlipayWrapperPrivate(this);
    if(iWrapperPrivate)
    {
        TRAPD(error, iWrapperPrivate->AliXPay(aOrderInfo, aIapId));
    }
}

QAlipayService::~QAlipayService()
{
    if(iWrapperPrivate)
    {
        delete iWrapperPrivate;
        iWrapperPrivate = NULL;
    }
    REComSession::FinalClose();
}

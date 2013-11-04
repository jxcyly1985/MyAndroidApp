#include <QApplication>
# include <QtGui/qdesktopwidget.h>
#include <eikenv.h>
#include <coemain.h>
#include <aknappui.h>
#include "AlipayWrapperPrivate.h"
#include "alipay.h"
#include "AlipayService.h"
#include "alipaypluginobserver.h"
#include <QFile>

_LIT8(KAliXPayOperationName,"alixpay");
_LIT8(KNotSurpot,"resultStatus={6003};memo={¿ò¼Ü²»Ö§³Ö};result={}");

QAlipayWrapperPrivate::QAlipayWrapperPrivate(QAlipayService *wrapper): q_ptr(wrapper)
{
    iInterface = NULL;
    iActiveWindow = NULL;
    isSoftkeysVisibleHint = true;
    iInterface = CInterfaceDefinition::NewL(KAliXPayOperationName);
    connect(this,SIGNAL(PayFinished(const QByteArray&)),q_ptr,SIGNAL(PayFinished( const QByteArray&)));
}

void QAlipayWrapperPrivate::AliXPay(const QByteArray& aOrderInfo, TUint32 aIapId)
{
    CAknAppUi *aknAppUi = dynamic_cast<CAknAppUi *>(CEikonEnv::Static()->AppUi());
    if(!aknAppUi)
    {
        QByteArray array(reinterpret_cast<const char*>(KNotSurpot().Ptr()),KNotSurpot().Length());
        emit PayFinished(array);
        return;
    }

    TPtrC8 data( reinterpret_cast<const TText8*> (aOrderInfo.constData()),aOrderInfo.size());

    //Take a copy of the data
    HBufC8* buffer = HBufC8::NewL(data.Length());
    buffer->Des().Copy(data);
    if(iInterface)
    {
        TRAPD(error, iInterface->AliXPay(aknAppUi, this, *buffer, aIapId));
    }
    delete buffer;
    iActiveWindow = QApplication::activeWindow();
    if(iActiveWindow)
    {
        flgs = iActiveWindow->windowFlags();
        isSoftkeysVisibleHint = flgs.testFlag(Qt::WindowSoftkeysVisibleHint);
        if(!isSoftkeysVisibleHint)
           iActiveWindow->setWindowFlags(flgs | Qt::WindowSoftkeysVisibleHint);
    }
}

QAlipayWrapperPrivate::~QAlipayWrapperPrivate()
{
    if(iInterface)
    {
        delete iInterface;
        iInterface = NULL;
    }

}

void QAlipayWrapperPrivate::AlipayPluginEvent(const TDesC8& resultStatus)
{
    QByteArray array;
    if(resultStatus.Length()>0)
        array.append(reinterpret_cast<const char*>(resultStatus.Ptr()),resultStatus.Length());
    emit PayFinished(array);
    if(iActiveWindow)
    {
        if(!isSoftkeysVisibleHint)
        {
            iActiveWindow->setWindowFlags(flgs);
        }
        iActiveWindow->showMaximized();
        iActiveWindow = NULL;
    }
}

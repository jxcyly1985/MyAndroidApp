#ifndef ALIPAYWRAPPERPRIVATE_H
#define ALIPAYWRAPPERPRIVATE_H

#include <QObject>
#include "alipaypluginobserver.h"
class CInterfaceDefinition;
class MAlipayPluginObserver;
class QAlipayService;


class QAlipayWrapperPrivate : public QObject,public MAlipayPluginObserver
{
    Q_OBJECT
public:
    QAlipayWrapperPrivate(QAlipayService *wrapper);
    ~QAlipayWrapperPrivate();
    void AliXPay(const QByteArray& aOrderInfo, TUint32 aIapId);
    void AlipayPluginEvent(const TDesC8& resultStatus);
signals:
    void PayFinished( const QByteArray& resultInfo );

private:
    CInterfaceDefinition* iInterface;
    QAlipayService *q_ptr;
    Qt::WindowFlags flgs;
    bool isSoftkeysVisibleHint;
    QWidget* iActiveWindow;
    //Q_DECLARE_PRIVATE()
};

#endif // ALIPAYWRAPPERPRIVATE_H

#ifndef ALIPAYSERVICE_H
#define ALIPAYSERVICE_H
#include <QObject>

class QAlipayWrapperPrivate;

class QAlipayService : public QObject
{
    Q_OBJECT
public:
    QAlipayService();
    ~QAlipayService();
    void AliXPay(const QByteArray& aOrderInfo, TUint32 aIapId);
signals:
    void PayFinished( const QByteArray& resultInfo );

private:
    QAlipayWrapperPrivate* iWrapperPrivate;
};

#endif // ALIPAYSERVICE_H

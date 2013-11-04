#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

#include "AlipayService.h"
#ifdef Q_OS_SYMBIAN
#include <eikenv.h>
#include <coemain.h>
#include <aknappui.h>
#include <EscapeUtils.h>
#include <e32math.h>
#include <hash.h>
#endif
#include <QModelIndex>

namespace Ui {
    class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
private:
    void Order();
    void OrderPay(const TDesC8& aSubject, const TDesC8& aTotalFee);
    void Order(QModelIndex index);
    HBufC8* DoMD5(const TDesC8& aString);
    void GenerateKey(TDes8& aKey, TInt aLen);
    HBufC8* GetOrderInfo(const TDesC8& aSubject,const TDesC8& aTotalFee);

private:
    Ui::MainWindow *ui;
    QAlipayService* iAlipayService;

private slots:
    void on_listWidget_clicked(QModelIndex index);
    void payResultNotice( const QByteArray& resultInfo );
};

#endif // MAINWINDOW_H

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QFile>
# include <QtGui/qdesktopwidget.h>
#include <QMenubar>
#include "PartnerConfig.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    iAlipayService = new QAlipayService();
    connect(iAlipayService,SIGNAL(PayFinished(const QByteArray&)),this,SLOT(payResultNotice( const QByteArray&)));
}

MainWindow::~MainWindow()
{
    if(iAlipayService)
    {
        delete iAlipayService;
        iAlipayService = NULL;
    }
    delete ui;

}

void MainWindow::Order()
        {
        HBufC8* order = GetOrderInfo(_L8("uninstall king"), _L8("1.00"));

        if (order == NULL)
                return;

        TBuf8<2048> info;

        HBufC8* sign = NULL;
        sign = DoMD5(order->Des());

        HBufC8* signEncoded = EscapeUtils::EscapeEncodeL(sign->Des(),
                        EscapeUtils::EEscapeUrlEncoded);

        info.Append(order->Des());
        info.Append(_L8("&sign=\""));
        info.Append(signEncoded->Des());
        info.Append(_L8("\""));
        info.Append(_L8("&sign_type=\"MD5\""));
        delete sign;
        delete signEncoded;
        delete order;

        QByteArray array(reinterpret_cast<const char*>(info.Ptr()),info.Length());
        iAlipayService->AliXPay(array,0);
        }


HBufC8* MainWindow::GetOrderInfo(const TDesC8& aSubject,const TDesC8& aTotalFee)
        {
        TBuf8<1024> info;
        info.Append(_L8("partner=\""));
        info.Append(PartnerID);
        info.Append(_L8("\"&seller=\""));
        info.Append(SellerID);
        info.Append(_L8("\"&out_trade_no=\""));

        TBuf8<15> out_trade_no;
        GenerateKey(out_trade_no, 15);
        info.Append(out_trade_no);

        info.Append(_L8("\"&subject=\""));
        info.Append(aSubject);
        info.Append(_L8("\"&body=\"kkk\"&total_fee=\""));
        info.Append(aTotalFee);
        info.Append(_L8("\"&notify_url=\""));

        TBuf8<256> url;
        url.Copy(_L8("http://notify.java.jpxx.org/index.jsp"));
        HBufC8* urlEncoded = EscapeUtils::EscapeEncodeL(url,
                        EscapeUtils::EEscapeUrlEncoded);

        info.Append(urlEncoded->Des());
        info.Append(_L8("\"&call_back_url=\""));
        info.Append(urlEncoded->Des());
        delete urlEncoded;
        info.Append(_L8("\""));

        HBufC8* buf = HBufC8::NewL(info.Length());
        buf->Des().Copy(info);

        return buf;
        }


HBufC8* MainWindow::DoMD5(const TDesC8& aString)
        {
        TBuf8<1024> string;
        string.Append(aString);
        string.Append(MD5_KEY);

        CMD5* md5 = CMD5::NewL();
        CleanupStack::PushL(md5);
        TPtrC8 hashedSig = md5->Hash(string);
        HBufC8* buf = HBufC8::NewL(hashedSig.Length() * 2);
        TPtr8 bufPtr = buf->Des();

        for (TInt i = 0; i < hashedSig.Length(); i++)
                bufPtr.AppendFormat(_L8("%+02x"), hashedSig[i]);

        CleanupStack::PopAndDestroy(md5);
        return buf;
        }

void MainWindow::GenerateKey(TDes8& aKey, TInt aLen)
        {
        aKey.Zero();

        TTime currentTime;
        currentTime.HomeTime();

        // ??000ƒÍø™ ºº∆??
        TInt startYear = 2000;

        // µ±«∞ƒÍ∑›
        TInt currentYear = currentTime.DateTime().Year();
        TTime time(TDateTime(currentYear, EJanuary, 0, 0, 0, 0, 0));

        TTimeIntervalSeconds s;
        currentTime.SecondsFrom(time, s);

        // µ√µΩ√Î ˝
        TInt i = s.Int();

        aKey.AppendFormat(_L8("%X"), i);
        aKey.AppendFormat(_L8("%X"), currentYear - startYear);

        TInt len = aKey.Length();
        if (len > aLen)
                {
                aKey.Mid(0, aLen);
                }
        else
                {
                for (TInt i = 0; i < aLen - len; i++)
                        {
                        TTime theTime;
                        theTime.UniversalTime();
                        TInt64 randSeed(theTime.Int64());
                        TInt number(Math::Rand(randSeed) + i);

                        number = number % 10 + 48;
                        aKey.Append(number);
                        }
                }
        }

void MainWindow::Order(QModelIndex index)
{
    TBuf<10> subject;
    TBuf8<10> fee;

    switch (index.row())
        {
        case 0:
                subject.Copy(_L("\x82f9\x679c"));//∆ªπ˚
                fee.Copy(_L("0.01"));
                break;
        case 1:
                subject.Copy(_L("\x8461\x8404"));//∆œÃ—
                fee.Copy(_L("1"));
                break;
        case 2:
                subject.Copy(_L("\x9999\x8549"));//œ„Ω∂
                fee.Copy(_L("5.23"));
                break;
        case 3:
                subject.Copy(_L("\x6a31\x6843"));//”£Ã“
                fee.Copy(_L("10"));
                break;
        case 4:
                subject.Copy(_L("\x8292\x679c"));//√¢π˚
                fee.Copy(_L("50"));
                break;
        case 5:
                subject.Copy(_L("\x897f\x74dc"));//Œ˜πœ
                fee.Copy(_L("59"));
                break;
        case 6:
                subject.Copy(_L("\x83e0\x841d"));//≤§¬‹
                fee.Copy(_L("100"));
                break;
        case 7:
                subject.Copy(_L("\x756a\x8304"));//∑¨«—
                fee.Copy(_L("256.1"));
                break;
        case 8:
                subject.Copy(_L("\x9ec4\x6843"));//¸SÃ“
                fee.Copy(_L("1000"));
                break;
        case 9:
                subject.Copy(_L("\x8354\x679d"));//¿Û÷¶
                fee.Copy(_L("1234.12"));
                break;
        case 10:
                subject.Copy(_L("\x6787\x6777"));//Ë¡ËÀ
                fee.Copy(_L("2000"));
                break;
        case 11:
                subject.Copy(_L("\x7518\x8517"));//∏ ’·
                fee.Copy(_L("5000"));
                break;
        case 12:
                subject.Copy(_L("\x9f99\x773c"));//¡˙—€
                fee.Copy(_L("9999"));
                break;
        default:
                return;
        }
    HBufC8* subData = EscapeUtils::ConvertFromUnicodeToUtf8L(subject);
    OrderPay(*subData, fee);
    delete subData;
}

void MainWindow::OrderPay(const TDesC8& aSubject, const TDesC8& aTotalFee)
{
    HBufC8* order = GetOrderInfo(aSubject, aTotalFee);

    if (order == NULL)
            return;

    TBuf8<2048> info;

    HBufC8* sign = NULL;
    sign = DoMD5(order->Des());

    HBufC8* signEncoded = EscapeUtils::EscapeEncodeL(sign->Des(),
                    EscapeUtils::EEscapeUrlEncoded);

    info.Append(order->Des());
    info.Append(_L8("&sign=\""));
    info.Append(signEncoded->Des());
    info.Append(_L8("\""));
    info.Append(_L8("&sign_type=\"MD5\""));
    delete sign;
    delete signEncoded;
    delete order;

    QByteArray array(reinterpret_cast<const char*>(info.Ptr()),info.Length());
    iAlipayService->AliXPay(array,0);
}
void MainWindow::payResultNotice( const QByteArray& resultInfo )
{

}

void MainWindow::on_listWidget_clicked(QModelIndex index)
{
    Order(index);
}

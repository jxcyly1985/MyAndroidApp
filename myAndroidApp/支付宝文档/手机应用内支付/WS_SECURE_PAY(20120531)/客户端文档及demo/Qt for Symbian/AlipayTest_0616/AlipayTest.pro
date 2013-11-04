#-------------------------------------------------
#
# Project created by QtCreator 2011-02-11T19:02:38
#
#-------------------------------------------------

QT       += core gui

TARGET = AlipayTest
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    AliPay.inl \
    AlipayService.cpp \
    AlipayWrapperPrivate.cpp

HEADERS  += mainwindow.h \
    AliPay.h \
    AlipayPluginObserver.h \
    AlipayService.h \
    AlipayWrapperPrivate.h \
    PartnerConfig.h

INCLUDEPATH += \epoc32\include\ecom

FORMS    += mainwindow.ui

CONFIG += mobility
MOBILITY = 

symbian {
    TARGET.UID3 = 0xefbec8a5
    LIBS += -lconnmon -lcone -lavkon -lecom -linetprotutil -lhash -lhtmlcontrol -ltouchfeedback -lws32 -lbafl -lfbscli -lws32 -lefsrv
    LIBS += -lgdi -laknskins -laknskinsrv -laknswallpaperutils -laknicon -leikcoctl -leikctl -leikdlg
    LIBS += -lform -letext -luiklaf -leikcore -lbitgdi -lcharconv -linetprotutil -limageconversion -legul -lflogger
    TARGET.CAPABILITY += LocalServices Location NetworkServices PowerMgmt ProtServ ReadDeviceData ReadUserData SurroundingsDD SwEvent TrustedUI UserEnvironment WriteDeviceData WriteUserData
    TARGET.EPOCSTACKSIZE = 0x14000
    TARGET.EPOCHEAPSIZE = 0x020000 0x800000
}

目录结构如下：
-安全支付服务iOS平台开发指南.pdf	安全支付接口、demo配置说明文档，请先阅读。

-AlixPayDemo				供商户客户端参照的iPhone示例工程。注：可以直接体验一分钱                                          订单流程，订单支付金额无法退还。

-libs					安全支付接口静态库，其中libalixpay.a同时适用于真机和模拟                                          器。

-include				安全支付接口静态库头文件。

-JSON                                   JSON操作相关源码

-src                                    安全支付接口静态库对应源码


注意：
现已支持iPad安全支付接入.
如果使用了libalixpay.a，则JSON和src中的源码都不要加入到工程中。如果遇到libalixpay.a和工程现有lib冲突的情况，可不使用libalixpay.a而使用JSON和src中的源码。



支付宝（中国）网络技术有限公司
2011-11-22

//
//  AlixPayDemoViewController.h
//  AlixPayDemo
//
//  Created by Jing Wen on 7/27/11.
//  Copyright 2011 alipay.com. All rights reserved.
//

#import <UIKit/UIKit.h>

//
//测试商品信息封装在Product中,外部商户可以根据自己商品实际情况定义
//
@interface Product : NSObject{
@private
	float     _price;
	NSString *_subject;
	NSString *_body;
	NSString *_orderId;
}

@property (nonatomic, assign) float price;
@property (nonatomic, retain) NSString *subject;
@property (nonatomic, retain) NSString *body;
@property (nonatomic, retain) NSString *orderId;

@end

//
//demo测试皮,采取TableView布局,方便测试,外部商户根据自己软件实际情况布局
//
@interface AlixPayDemoViewController : UIViewController {
	IBOutlet UINavigationBar *navBar;
	IBOutlet UITableView *productTv;
	NSMutableArray *_products; //<Product>
}

@end


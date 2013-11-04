//
//  AlixPayDemoAppDelegate.h
//  AlixPayDemo
//
//  Created by Jing Wen on 7/27/11.
//  Copyright 2011 alipay.com. All rights reserved.
//

#import <UIKit/UIKit.h>

@class AlixPayDemoViewController;

@interface AlixPayDemoAppDelegate : NSObject <UIApplicationDelegate> {
    UIWindow *window;
    AlixPayDemoViewController *viewController;
}

@property (nonatomic, retain)  UIWindow *window;
@property (nonatomic, retain)  AlixPayDemoViewController *viewController;

@end


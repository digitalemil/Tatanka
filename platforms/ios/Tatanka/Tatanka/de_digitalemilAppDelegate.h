//
//  de_digitalemilAppDelegate.h
//  Tatanka
//
//  Created by Emil Siemes on 12/17/12.
//  Copyright (c) 2012 digitalemil. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <GameKit/GameKit.h>

// Preferred method for testing for Game Center
BOOL isGameCenterAvailable();



@class de_digitalemilViewController;

@interface de_digitalemilAppDelegate : UIResponder <UIApplicationDelegate, UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) de_digitalemilViewController *viewController;

@end

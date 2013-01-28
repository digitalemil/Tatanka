//
//  TatankaView.h
//  Tatanka
//
//  Created by Emil Siemes on 12/17/12.
//  Copyright (c) 2012 digitalemil. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <GameKit/GameKit.h>
#import "de_digitalemilViewController.h"
#import "all.h"

@interface TatankaView : UIView {
    TatankaModell *modell;
    
	NSTimer *animationTimer;
	NSTimeInterval animationInterval;
	NSObject *lock;

    CALayer *drawLayer;
    CALayer **imgs;
    int nimgs, *imgw, *imgh;
    NSString **imgnames;
    de_digitalemilViewController *controller;
    unsigned int t1, t2;
    
}
- (UIImage *)resizeImage;
- (float)min4;
- (float)max4;

@property (nonatomic, assign) NSTimer *animationTimer;
@property NSTimeInterval animationInterval;
@end

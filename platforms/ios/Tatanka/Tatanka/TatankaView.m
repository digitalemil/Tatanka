//
//  TatankaView.m
//  Tatanka
//
//  Created by Emil Siemes on 12/17/12.
//  Copyright (c) 2012 digitalemil. All rights reserved.
//

#import "TatankaView.h"
#import "de/digitalemil/eagle/Globals.h"

@implementation TatankaView

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
  
        // Initialization code
        [DeDigitalemilEagleGlobals setDefaultsWithInt:768 withInt:1024];
        [DeDigitalemilEagleGlobals setWithInt:960 withInt:640];
    
    }
    return self;
}


// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
    // Drawing code
    CGContextRef context = UIGraphicsGetCurrentContext();
    CGContextSetLineWidth(context, 5.0);
    CGColorSpaceRef colorspace = CGColorSpaceCreateDeviceRGB();
    CGFloat components[] = {0.0, 0.0, 1.0, 1.0};
    CGColorRef color = CGColorCreate(colorspace, components);
    CGContextSetStrokeColorWithColor(context, color);
    CGContextMoveToPoint(context, 0, 0);
    CGContextAddLineToPoint(context, 300, 400);
    CGContextStrokePath(context);
    CGColorSpaceRelease(colorspace);
    CGColorRelease(color);
  
}
@end

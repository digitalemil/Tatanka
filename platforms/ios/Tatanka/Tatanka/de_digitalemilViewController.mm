//
//  de_digitalemilViewController.m
//  Tatanka
//
//  Created by Emil Siemes on 12/17/12.
//  Copyright (c) 2012 digitalemil. All rights reserved.
//

#import "de_digitalemilViewController.h"
#import <QuartzCore/CoreAnimation.h>
#import "all.h"


#define min(X,Y) ((X) < (Y) ? (X) : (Y))

#define max(X,Y) ((X) > (Y) ? (X) : (Y))

#define RECTCOORDS  6

@interface de_digitalemilViewController ()

@end

@implementation de_digitalemilViewController

- (void)drawLayer:(CALayer *)layer inContext:(CGContextRef)context {
    [self.view drawTatanka:context];
   /* CGContextSetFillColorWithColor(context, [[UIColor darkTextColor] CGColor]);
    
    UIGraphicsPushContext(context);
    
    [@"Hello World!" drawAtPoint:CGPointMake(30.0f, 30.0f)
                        forWidth:200.0f
                        withFont:[UIFont boldSystemFontOfSize:32]
                   lineBreakMode:UILineBreakModeClip];
    
    UIGraphicsPopContext();
    */
}


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
   // self.view.layer.backgroundColor = [UIColor orangeColor].CGColor;
   // self.view.layer.cornerRadius = 20.0;
   // self.view.layer.frame = CGRectInset(self.view.layer.frame, 0, 0);
    [self.view setup:self];
  //  self.view.layer.delegate= self.view;
  //  [self.view.layer setNeedsDisplay];
   /*
    
    CALayer *sublayer = [CALayer layer];

    sublayer.backgroundColor = [UIColor blueColor].CGColor;
    sublayer.shadowOffset = CGSizeMake(0, 3);
    sublayer.shadowRadius = 5.0;
    sublayer.shadowColor = [UIColor blackColor].CGColor;
    sublayer.shadowOpacity = 0.8;
    sublayer.frame = CGRectMake(30, 30, 128, 192);
  */
     /*
    [self.view.layer addSublayer:sublayer];
    sublayer.delegate= self;
    [sublayer setNeedsDisplay];
   
    CALayer *imageLayer = [CALayer layer];
    imageLayer.frame = sublayer.bounds;
    imageLayer.cornerRadius = 10.0;
    imageLayer.contents = (id) [UIImage imageNamed:@"valleygras512.jpg"].CGImage;
    imageLayer.masksToBounds = YES;
   // [self.view.layer addSublayer:imageLayer];
    */
   
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
/*
*/
@end

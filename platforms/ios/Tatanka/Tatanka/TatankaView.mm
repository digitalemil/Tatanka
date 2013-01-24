//
//  TatankaView.m
//  Tatanka
//
//  Created by Emil Siemes on 12/17/12.
//  Copyright (c) 2012 digitalemil. All rights reserved.
//

#import "TatankaView.h"
#import <QuartzCore/CoreAnimation.h>
#import "de_digitalemilViewController.h"

#define min(X,Y) ((X) < (Y) ? (X) : (Y))

#define max(X,Y) ((X) > (Y) ? (X) : (Y))

#define RECTCOORDS  6
@implementation TatankaView
@synthesize animationTimer;
@synthesize animationInterval;


- (void) drawLayer:(CALayer*) layer inContext:(CGContextRef) ctx
{
	//NSLog(@"draw");
    [drawLayer setNeedsDisplay];
}

- (id)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
  
        // Initialization code
       }
    return self;
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
	UITouch *touch = [touches anyObject];
    CGPoint currentTouchPosition = [touch locationInView:self];
 //   NSLog(@"touch began %i %i %i\n", [touch hash], t1, t2);
	
    if(t1== 0) {
        t1= [touch hash];
    }
    else
        t2= [touch hash];
    
 	@synchronized(lock) {
		modell->touchStart(currentTouchPosition.x, currentTouchPosition.y);
	}
}

- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event {
	UITouch *touch = [touches anyObject];
    unsigned int t= [touch hash];
  	
    if(t== t2) {
        return;
    }
    
    CGPoint currentTouchPosition = [touch locationInView:self];
    
	@synchronized(lock) {
		modell->touch(currentTouchPosition.x, currentTouchPosition.y);
	}
}

- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
	UITouch *touch = [touches anyObject];
    unsigned int t= [touch hash];
  	
    if(t== t2) {
        t2= 0;
        return;
    }
    t1= 0;
    t2= 0;
    
    CGPoint currentTouchPosition = [touch locationInView:self];
 	@synchronized(lock) {
		modell->touchStop(currentTouchPosition.x, currentTouchPosition.y);
	}
}

- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
}


- (void)setup:(de_digitalemilViewController *)c {
    lock= [NSObject alloc];
    
    controller= c;
    drawLayer= 0;
    t1= t2= 0;
    int w, h;
    
    Globals::setDefaults(768, 1024);
    w= [[UIScreen mainScreen] bounds].size.width;
    h= [[UIScreen mainScreen] bounds].size.height;
    Globals::set(w, h);

    if(drawLayer== 0) {
        drawLayer = [CALayer layer];
        drawLayer.frame = CGRectMake(0, 0, w, h);
        drawLayer.delegate= controller;
        [self.layer addSublayer:drawLayer];
    }

    
    srand((int)(OS::currentTimeMillies()*1000));
    nimgs= 1;
    imgusers= new int[256];
    for(int i= 0; i< 256; i++) {
        imgusers[i]= 0;
    }
    imgs= new CALayer*[256];
    uiimgs= new UIImage*[256];
    
    imgnames= new NSString*[256];
    imgh= new int[256];
    imgw= new int[256];
    
    tmptextbuffer= (unsigned char *)malloc(256);
    modell = new TatankaModell();
    modell->setup();
    modell->start();
    animationInterval = 1.0 / 60.0;
    
    self.multipleTouchEnabled = YES;
        
    [self startAnimation];
}



- (float)min4:(float)f1 fi2:(float)f2 fi3:(float)f3 fi4:(float)f4 {
    float ret = f1;
    ret = min(ret, f2);
    ret = min(ret, f3);
    ret = min(ret, f4);
    return ret;
}

- (float)max4:(float)f1 fi2:(float)f2 fi3:(float)f3 fi4:(float)f4 {
    float ret = f1;
    ret = max(ret, f2);
    ret = max(ret, f3);
    ret = max(ret, f4);
    return ret;
}


// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.

- (void)drawRect:(CGRect)rect {
    CGContextRef context = UIGraphicsGetCurrentContext();
    [self drawTatanka:context];
}

- (void)drawTatanka:(CGContextRef)context
{
   CGContextSetFillColorWithColor(context, [[UIColor darkTextColor] CGColor]);
    
   
    CGContextSaveGState(context);
   @synchronized(lock) {
    modell->update(OS::currentTimeMillies());
       CGContextSetAllowsAntialiasing(context, true);
   // CGContextSetLineWidth(context, 5.0);
       CGColorSpaceRef colorspace = CGColorSpaceCreateDeviceRGB();
       CGContextSetFillColorSpace(context, colorspace);
        CGContextSetInterpolationQuality(context, kCGInterpolationNone);
        // Drawing code
    for (int i = 0; i < modell->getNumberOfThings(); i++) {
        
        if (modell->getThings()[i] == null)
            continue;
        if ((modell->getType(i) == Types::IMAGE)
            && (modell->getImageName(i) != 0 || modell->getTexID(i) != 0)) {
            int t = modell->getTexID(i);
            if (modell->imageNameChanged(i)) {
                if (modell->isTexIDSet(i)) {
                    imgusers[t]--;
                    if(imgusers[t]== 0) {
                       // textures.remove(t);
                       // texNames.remove(t);
                       // texUsage.remove(t);
                    }
                }
                if (modell->getImageName(i) == null) {
                    modell->setTexIDForQuad(i, 0);
                    continue;
                }
                NSString *name= [NSString stringWithUTF8String:(const char *)modell->getImageName(i)];
                NSString *subname= [name substringWithRange:NSMakeRange(0, [name length]-4)];
                unichar c= [name characterAtIndex:[name length]-3];
                if(c== 'j')
                    t = [self loadJPG:subname width:modell->getImageWidth(i) height:modell->getImageHeight(i)];
                else
                    t = [self loadPNG:subname width:modell->getImageWidth(i) height:modell->getImageHeight(i)];
                
                modell->setTexIDForQuad(i, t);
            }
        }
    }
    for (int t = 0; t < modell->getNumberOfThings(); t++) {
        
        if (!modell->isVisible(t))
            continue;
 
        if ((modell->getType(t) == Types::IMAGE)
            && (modell->getImageName(t) != null || modell->getTexID(t) != 0)) {
            int id= modell->getTexID(t);
            CALayer *timg = imgs[id];
            if (timg == 0)
                continue;
            float *d = modell->getData(t);
            
            float xmin = [self min4:d[RECTCOORDS] fi2:d[RECTCOORDS + 2]
                                   fi3:d[RECTCOORDS + 4] fi4:d[RECTCOORDS + 6]];
            float ymin = [self min4:d[RECTCOORDS + 1] fi2:d[RECTCOORDS + 3]
                                   fi3:d[RECTCOORDS + 5] fi4:d[RECTCOORDS + 7]];
            
            CGPoint pt= CGPointMake((xmin + Globals::getW2()+ imgw[id]/2), (Globals::getH2() + ymin+ imgh[id]/2));
            [CATransaction setDisableActions:YES];
            timg.position = pt;
            [CATransaction setDisableActions:NO];
          //  [uiimgs[id] drawAtPoint:pt];
            
        } else {
            const char** textandfont = (const char**)modell->getTextAndFont(t);
            float *d = modell->getData(t);
            int texts = 0;
            
            int di = 0;
            int len = modell->getNumberOfData(t);
            while (di < len) {
                int type = (int) d[di];
                if (type == Types::BOUNDINGCIRCLE
                    && !BoundingCircle::visibleboundingcircle) {
                    di += 7;
                    continue;
                }
                int c= (int)d[di + 2];
                float a= ((unsigned int) (c >> 24))/255.0f;
                float r=((c & 0x00FF0000) >> 16)/255.0f;
                float g=((c & 0x0000FF00) >> 8)/255.0f;
                float b=((c & 0x000000FF))/255.0f;
                CGFloat components[] = {r, g, b, a};
                CGContextSetFillColor(context, components);
            
                CGContextBeginPath(context);

                int ni = (int) d[di + 1];
                int cor = (int) d[di + 3];
                if (type == Types::BOUNDINGCIRCLE) {
                    di++;
                }
                if (type == Types::TEXT) {
                    
                    UIGraphicsPushContext(context);
                    NSString *text=[NSString stringWithCString:textandfont[texts ] encoding:NSASCIIStringEncoding];
                    NSString *fontName= [NSString stringWithCString:(textandfont[texts+1][0]== 0)?"MarkerFelt-Thin":textandfont[texts+1] encoding:NSASCIIStringEncoding];
                    int size= (int)(d[di + 6]);
                    
                    UIFont * font   = [UIFont fontWithName:fontName size:size];
                    
                    CGSize theSize = [text sizeWithFont:font];
                    int mw = theSize.width;
                    int mh = theSize.height;
                    
                    int x = (int) (d[di + 4] + Globals::getW2());
                    switch ((int) d[di + 7]) {
                        case Text::TEXT_RIGHT:
                            x -= mw;
                            break;
                        case Text::TEXT_CENTER:
                            x -= mw / 2;
                            break;
                    }
                    CGPoint pt= CGPointMake(x, +d[di + 5]
                                            + (int) Globals::getH2() + mh / 2);

                    texts += 2;

                    [text drawAtPoint:pt
                                          forWidth:theSize.width
                                          withFont:font
                                     lineBreakMode:UILineBreakModeClip];
                    
                    UIGraphicsPopContext();
    
                    di += 8;
                } else {
                    CGContextMoveToPoint(context, d[di + 4 + 2 * cor] +Globals::getW2(), d[di + 4 + 2
                                                            * cor + 1]+Globals::getH2()
                                         );
                    
                    for (int i = 2; i < 2 * ni; i += 2) {
                        CGContextAddLineToPoint(context, d[di + 4 + i + 2 * cor]+Globals::getW2() , d[di + 4
                                                                    + i + 2 * cor + 1]+Globals::getH2()
                                                );
                    }
                    CGContextClosePath(context);
                    CGContextFillPath(context);
                    di += 4 + 2 * (ni + 1);
                }
            }
            
        }
    }
   CGColorSpaceRelease(colorspace);
    }
    CGContextRestoreGState(context);
 }



/*  Typeface tf= paint.getTypeface();
 paint.setTypeface(Typeface.create(textandfont[texts + 1], Typeface.BOLD));
 float ts= paint.getTextSize();
 paint.setTextSize(Math.round(d[di + 6]));
 Rect fm= new Rect();
 paint.getTextBounds(textandfont[texts], 0, textandfont[texts + 1].length(), fm);
 int mw = fm.width();
 int mh = fm.height();
 int x = (int) (d[di + 4] + Globals.getW2());
 switch ((int) d[di + 7]) {
 case Text.TEXT_RIGHT:
 x -= mw;
 break;
 case Text.TEXT_CENTER:
 x -= mw / 2;
 break;
 }
 
 canvas.drawText(textandfont[texts], x, d[di + 5]
 + (int) Globals.getH2() + mh / 2, paint);
 paint.setTextSize(ts);
 paint.setTypeface(tf);
 texts += 2;
 */

- (int)loadPNG:(NSString *)name  width:(int)w height:(int)h {
	if([name length]== 0)
		return 0;
    /*
    for(int i= 1; i<nimgs; i++) {
        if([name isEqual:imgnames[i]] && w== imgw[i] && h== imgh[i]) {
            [imgs[i] retain];
            return i;
        }
    }
     */
    NSString *imagePath = [[NSBundle mainBundle] pathForResource:name ofType:@"png"];
    UIImage *myImageObj = [[UIImage alloc] initWithContentsOfFile:imagePath];
    CALayer *img= [self resizeImage:myImageObj toWidth:w height:h];
    [img retain];
    
    imgs[nimgs]= img;
    imgw[nimgs]= w;
    imgh[nimgs]= h;
    [name retain];
    imgnames[nimgs]= name;
    return nimgs++;
}


- (int)loadJPG:(NSString *)name  width:(int)w height:(int)h {
	if([name length]== 0)
		return 0;
   /*
    for(int i= 1; i< nimgs; i++) {
        NSString *n= imgnames[i];
        if([name isEqual:imgnames[i]] && w== imgw[i] && h== imgh[i]) {
            [imgs[i] retain];
            return i;
        }
    }
    */
    NSString *imagePath = [[NSBundle mainBundle] pathForResource:name ofType:@"jpg"];
    UIImage *myImageObj = [[UIImage alloc] initWithContentsOfFile:imagePath];
    CALayer *img= [self resizeImage:myImageObj toWidth:w height:h];
    [img retain];
    
    imgs[nimgs]= img;
    imgw[nimgs]= w;
    imgh[nimgs]= h;
    [name retain];
    imgnames[nimgs]= name;
    return nimgs++;
}

- (CALayer*)resizeImage:(UIImage*)image toWidth:(NSInteger)width height:(NSInteger)height
{
    // Create a graphics context with the target size
    // On iOS 4 and later, use UIGraphicsBeginImageContextWithOptions to take the scale into consideration
    // On iOS prior to 4, fall back to use UIGraphicsBeginImageContext
    CGSize size = CGSizeMake(width, height);
    if (NULL != UIGraphicsBeginImageContextWithOptions)
        UIGraphicsBeginImageContextWithOptions(size, NO, 0);
    else
        UIGraphicsBeginImageContext(size);
    
    CGContextRef context = UIGraphicsGetCurrentContext();
    
    
    
    // Flip the context because UIKit coordinate system is upside down to Quartz coordinate system
    CGContextTranslateCTM(context, 0.0, height);
    CGContextScaleCTM(context, 1.0, -1.0);
    
    // Draw the original image to the context
    CGContextSetBlendMode(context, kCGBlendModeCopy);
    CGContextDrawImage(context, CGRectMake(0.0, 0.0, width, height), image.CGImage);
    
    // Retrieve the UIImage from the current context
    UIImage *imageOut = UIGraphicsGetImageFromCurrentImageContext();
    
    CALayer *sublayer = [CALayer layer];
    CGRect rect    = CGRectMake(.0, .0, width, height);
    sublayer.frame= rect;
    sublayer.contents = (id)imageOut.CGImage;
    uiimgs[nimgs]= imageOut;
    [imageOut retain];
    [[self layer] insertSublayer:sublayer below:drawLayer];
    
    UIGraphicsEndImageContext();
    return sublayer;
}

- (void)startAnimation {
	self.animationTimer = [NSTimer scheduledTimerWithTimeInterval:animationInterval target:self.layer selector:@selector(setNeedsDisplay) userInfo:nil repeats:YES];
}


- (void)stopAnimation {
	self.animationTimer = nil;
}


- (void)setAnimationTimer:(NSTimer *)newTimer {
	[animationTimer invalidate];
	animationTimer = newTimer;
}


- (void)setAnimationInterval:(NSTimeInterval)interval {
	
	animationInterval = interval;
	if (animationTimer) {
		[self stopAnimation];
		[self startAnimation];
	}
}

@end

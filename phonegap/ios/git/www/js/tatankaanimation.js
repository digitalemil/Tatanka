function TatankaAnimation(thing, steplength, duration) {
    this.STOP = 0;
    this.STARTWALK = 1;
    this.WALK = 2;
    this.STOPWALK = 3;
    this.STARTRUNNING = 4;
    this.RUNNING = 5;
    this.STOPRUNNING = 6;
    this.RUNWALK = 7;

    
    this.state = 0;
    this.swimming = false;
    this.sl = steplength;
    this.dur = duration;
    this.duration = duration;
    this.tatanka = thing;
    this.lslx = 0.0;
    this.lsly = 0.0;
    this.killed = false;
    this.maxstep = 0;
   
    this.init("TatankaAnimation", 4, 1, true);
}

TatankaAnimation.prototype= new CompositeAnimation();

TatankaAnimation.prototype.undoTranslation= function() {
    this.tatanka.translate(-this.lslx, -this.lsly, 0.0);
    this.lslx = 0.0;
    this.lsly = 0.0;
};

TatankaAnimation.prototype.kill= function() {
	this.stopWalk();
	if(this.dir!= undefined )
		this.dir.running= false;
	this.killed= true;
};

TatankaAnimation.prototype.createKillAnimation= function() {
    if(this.ka == undefined) {
    	   
        this.ka = new CompositeAnimation();
        this.ka.init("KillAnimation", 3, 1, false);
        var duration= this.duration;
        var sl= this.sl;
    	
        sl*= 1.5;
        duration/= 8;
       
        var ka1 = new CompositeAnimation();
        ka1.init("ka", 1, 9, false);
        var pa= new PartAnimation();
        pa.init(this.tatanka.getByName("LeftForelegPart1"), 0, -sl, 0, 1.0, 1.0, duration);
        ka1.addAnimation(pa, 0);    
        
        pa= new PartAnimation();
        pa.init(this.tatanka.getByName("LeftForelegPart2"), 0, -sl / 2, 0, 1.0, 2.0, duration);   
        ka1.addAnimation(pa, 0);
       
        pa= new PartAnimation();
        pa.init(this.tatanka.getByName("RightHindlegPart1"), 0, sl, 0, 1.0, 1.0, duration);
        ka1.addAnimation(pa, 0);
       
        pa= new PartAnimation();
        pa.init(this.tatanka.getByName("RightHindlegPart2"), 0, sl / 2, 0, 1.0, 2.0, duration);
        ka1.addAnimation(pa, 0);
        
        pa= new PartAnimation();
        pa.init(this.tatanka.getByName("RightForelegPart1"), 0, -sl, 0, 1.0, 1.0, duration);
        ka1.addAnimation(pa, 0);
        
        pa= new PartAnimation();
        pa.init(this.tatanka.getByName("RightForelegPart2"), 0, -sl / 2, 0, 1.0, 2.0, duration);
        ka1.addAnimation(pa, 0);
        
        pa= new PartAnimation();
        pa.init(this.tatanka.getByName("LeftHindlegPart1"), 0, sl, 0, 1.0, 1.0, duration);
        ka1.addAnimation(pa, 0);
        
        pa= new PartAnimation();
        pa.init(this.tatanka.getByName("LeftHindlegPart2"), 0, sl / 2, 0, 1.0, 2.0, duration);
        ka1.addAnimation(pa, 0);
      
        pa = new PartAnimation();
        pa.init(this.tatanka, 0, 0, 0, 0.95, 0.95, duration);
        ka1.addAnimation(pa, 0);      
        this.ka.addAnimation(ka1, 0);
         
        pa = new PartAnimation();
        pa.initColorAnimation(this.tatanka, 128, 1000);
      //  this.ka.addAnimation(pa, 1);
        
        var pa1= new PartAnimation();
        pa1.init(this.tatanka.getByName("Tongue"), 0, -20, 0, 10.0, 16.0, 1000);
        pa1.name="Tongue";
        this.ka.addAnimation(pa1, 2);
      
        this.ka.start();
    }
};

TatankaAnimation.prototype.unkill= function() {
  //  this.tatanka.setAlpha(1.0);
    this.killed = false;
    this.tatanka.rotate(-this.tatanka.rot);
    if (this.ka != undefined) {
        this.ka.stop();
        this.ka= undefined;
    }
};

TatankaAnimation.prototype.left= function(degree) {
    this.rotate(degree);
};

TatankaAnimation.prototype.right= function(degree) {
    this.rotate(-degree);
};

TatankaAnimation.prototype.rotate= function(degree) {
    if (!(this.state > this.STOP && this.state <= this.RUNWALK) && this.state!= this.DRINKING)
        return;
    if (this.dir != undefined && this.dir.isRunning())
        return;

    if (this.state == this.WALK)
        degree *= 1.5;

    var d = this.duration;
    if (this.state == this.RUNNING)
        d /= 3;
    this.dir = new PartAnimation();
    this.dir.init(this.tatanka, 0.0, 0.0, degree, 1.0, 1.0, d);
    this.dir.start();
};

TatankaAnimation.prototype.clear= function() {
    for (var i = 0; i < this.maxanimation; i++) {
        for (var j = 0; j < this.maxlevel; j++) {
            if (this.anis[j * this.maxanimation + i] != null) {
                this.anis[j * this.maxanimation + i].stop();
                if (this.anis[j * this.maxanimation + i] != null)
                    this.anis[j * this.maxanimation + i]= null;
                this.anis[j * this.maxanimation + i] = null;
            }
        }
    }
};

TatankaAnimation.prototype.createWalk= function(s, d) {
    this.clear();
    this._loops = true;
    var na = 9;
    var sl= this.sl;
    var wa1 = new CompositeAnimation();
    wa1.init("2", 1, na, false);
    var duration= this.duration;
    
    if(s==2) {
    	sl*= 1.5;
    	duration/= 8;
    }
    var pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftForelegPart1"), 0, -sl, 0, 1.0, 1.0, duration);
    wa1.addAnimation(pa, 0);    
    
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftForelegPart2"), 0, -sl / 2, 0, 1.0, 2.0, duration);   
    wa1.addAnimation(pa, 0);
   
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightHindlegPart1"), 0, -sl, 0, 1.0, 1.0, duration);
    wa1.addAnimation(pa, 0);
   
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightHindlegPart2"), 0, -sl / 2, 0, 1.0, 2.0, duration);
    wa1.addAnimation(pa, 0);
    
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightForelegPart1"), 0, sl, 0, 1.0, 1.0, duration);
    wa1.addAnimation(pa, 0);
    
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightForelegPart2"), 0, sl / 2, 0, 1.0, 2.0, duration);
    wa1.addAnimation(pa, 0);
    
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftHindlegPart1"), 0, sl, 0, 1.0, 1.0, duration);
    wa1.addAnimation(pa, 0);
    
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftHindlegPart2"), 0, sl / 2, 0, 1.0, 2.0, duration);
    wa1.addAnimation(pa, 0);
    
      pa= new PartAnimation();
    pa.init(this.tatanka.getByName("Head"), 0, 0, d, 1.0, 1.0, duration);
    wa1.addAnimation(pa, 0);

  
    var wa2 = new CompositeAnimation();
    wa2.init("2.1", 1, na, false);
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftForelegPart1"), 0, sl, 0, 1.0, 1.0, duration);
    wa2.addAnimation(pa, 0);
   
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftForelegPart2"), 0, sl / 2, 0, 1.0, 2.0, duration);
    wa2.addAnimation(pa, 0);
    
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightHindlegPart1"), 0, sl, 0, 1.0, 1.0, duration);
    wa2.addAnimation(pa, 0);
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightHindlegPart2"), 0, sl / 2, 0, 1.0, 2.0, duration);
    wa2.addAnimation(pa, 0);
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightForelegPart1"), 0, -sl, 0, 1.0, 1.0, duration);
    wa2.addAnimation(pa, 0);
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("RightForelegPart2"), 0, -sl / 2, 0, 1.0, 2.0, duration);
    wa2.addAnimation(pa, 0);
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftHindlegPart1"), 0, -sl, 0, 1.0, 1.0, duration);
    wa2.addAnimation(pa, 0);
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("LeftHindlegPart2"), 0, -sl / 2, 0, 1.0, 2.0, duration);
    wa2.addAnimation(pa, 0);
    pa= new PartAnimation();
    pa.init(this.tatanka.getByName("Head"), 0, 0, -d, 1.0, 1.0, duration);
    wa2.addAnimation(pa, 0);

    this.addAnimation(wa2, 0);
    this.addAnimation(wa2.createReverseAnimation(), 1);
    this.addAnimation(wa1, 2);
    this.addAnimation(wa1.createReverseAnimation(), 3);
    this.tatankaStart();
};

TatankaAnimation.prototype.stopWalk= function() {
    this._loops = false;
    switch (this.state) {
        case this.STARTWALK:
        case this.WALK:
            this.state = this.STOPWALK;
            break;
        case this.STARTRUNNING:
        case this.RUNNING:
            this.state = this.STOPRUNNING;
            break;
    }
};

TatankaAnimation.prototype.startRun= function() {
 //   if (this.state == this.RUNNING)
 //       return;
    this._loops = false;
    if (this.state == this.STOP) {
        this.createWalk(2, 0);
        this.state = this.RUNNING;
    }
    else
        this.state = this.STARTRUNNING;
};

TatankaAnimation.prototype.tatankaIncreaseLevel= function() {
   if(this.killed) {
//	   console.log("state: "+this.state+" l: "+this.level);
   }
    if (this.state == this.STOPRUNNING && this.level == 4) {
        this.state = this.STOP;
        this.running = false;
        console.log("create kill ani");
        if(this.killed)
        	this.createKillAnimation();
    }
    if (this.state == this.STOPWALK && this.level == 4) {
        this.state = this.STOP;
        this.running = false;
        if(this.killed)
        	this.createKillAnimation();
    }
    if (this.state == this.STARTRUNNING && this.level == 4) {
        this.createWalk(2, 0);
        this.state = this.RUNNING;
    }
    if (this.state == this.RUNWALK && this.level == 4) {
    	var deg= 0;
    	if(this.tatanka.getType()== TATANKA)
    		deg= 12;
        this.createWalk(1, deg);
        this.state = this.WALK;
    }
    if (this.state == this.STARTWALK && this.level == 4) {
        this.state = this.WALK;
    }
    if(this.level == 4)
        this.tatanka.reset();
};

TatankaAnimation.prototype.startWalk= function() {
    if (this.state >= this.STARTRUNNING && this.state <= this.RUNWALK) {
        this._loops = false;
        this.state = this.RUNWALK;
        return;
    }
    if (this.state != this.WALK) {
     	var deg= 0;
    	if(this.tatanka.getType()== TATANKA)
    		deg= 12;
        this.createWalk(1, deg);
        this.state = this.STARTWALK;
    }
};

TatankaAnimation.prototype.tatankaStart= function() {
    this.start();
    this.lt = this._start = (new Date()).getTime();
    this.tatanka.reset();
};


TatankaAnimation.prototype.tatankaAnimate= function() {
    var now= (new Date()).getTime();
    if (this.dir != undefined && this.dir.running) {
        this.dir.animate(now);
    }

    if (this.ka != undefined) {
        this.ka.animate(now);
        if (!this.ka.running) {
            this.ka = undefined;
        }
        return 0.0;
    }

//console.log("Animation: "+this.running+" "+this.STOP+" "+this.killed);

    if (!this.running) {
        return 1.0;
    }

    if (this.state == this.STOP )
        return 0.0;
//console.log("Animation");
    var l= this.level;
    var ret = this.animate(now);
    if(l!= this.level)
        this.tatankaIncreaseLevel();
    var rot = Math.PI * 2.0 * this.tatanka.rot / 360.0;
    var delta = now - this.lt;
    var percentage = delta / this.duration;
    if (this.state == this.STARTRUNNING || this.state == this.RUNWALK) {
        percentage *= 2;
    }
    if (this.state == this.RUNNING) {
        percentage *= 3;
    }
    if (percentage > 1.0) {
        percentage = 1.0;
    }
    var esl = this.sl;
    if (this.state == this.RUNNING || this.state == this.STARTRUNNING)
        esl *= 1.5;
    if (this.state != this.STOP) {
        this.lslx = -Math.sin(rot) * esl * this.tatanka.sx * percentage;
        this.lsly = -Math.cos(rot) * esl * this.tatanka.sy * percentage;
      //  this.tatanka.translate(this.lslx, this.lsly, 0.0);
    }
    this.lt = now;
    return ret;
};

TatankaAnimation.prototype.getType= function() {
    return TATANKANIMATION;
};



var ctx;
var alldiv;
var objects;
var retina = 1;
var timer;
var fpstimer;
var fps;
var fpstext = "";
var deg = -10;
var shooting = true, shootAtX, shootAtY, checkHit = false;
var hit = false;
var SPEED = [ 0, 1, 2, 3 ];
var sioux, horse, arrow, tatanka, bg1, bg2, bgd, speed = 2, tatankaani, horseani, tatankaspeed = 2.5, tseff;
var startY = 320;
var na = 0;
var tx, ty, tr;
var hx = 0;
var hy = 0;
var hr;
var arrowsleft = 30;
var siouxs = 3;
var tatankahealth = 100, tatankas = 0;
var myobjects = new Array(23);
var frames = 0;
var aggressive = false;
var goodie;
var startarrowobjs = 7;
var sprint = 0;

function Goodie() {
}

function getRandom(min, max) {
	if (min > max) {
		return -1;
	}

	if (min == max) {
		return min;
	}

	var r;

	do {
		r = Math.random();
	} while (r == 1.0);

	return min + parseInt(r * (max - min + 1));
}

Goodie.prototype = new Quad();

Goodie.prototype.create = function(type) {
	this.quadinit(128 * scale, 128 * scale, 0);
	this.rotate(90);
	this.setTexName((type == 0) ? "arrows.png" : "eaglefeather.png");
	this.bc = new Thing(1);
	var obj = new BoundingCircle();
	obj.initBoundingCircle(10, 0, -2, 0);
	this.bc.add(obj);
	var obj = new BoundingCircle();
	obj.initBoundingCircle(10, -10, 30, 0);
	this.bc.add(obj);

	var obj = new BoundingCircle();
	obj.initBoundingCircle(10, 10, -30, 0);
	this.bc.add(obj);

	this.bc.setupDone();
	this.move(-this.x)
};

Goodie.prototype.move = function(x, y) {
	this.translate(x, y);
	this.bc.translate(x, y);
}

function handleHit() {
	if (hit) {
		var narr = new Arrow();
		narr.scaleRoot(scale, scale);
		narr.translateRoot(tatanka.x + tatanka.rx, tatanka.y + tatanka.ry, 0);
		narr.translate(hx - narr.rx, hy - narr.ry, 0);
		narr.rrot = (sioux.rot + sioux.getByName("Bow").rot
				+ sioux.getByName("Arrow").rot + sioux.getByName("rest").rot + sioux
				.getByName("ArmLeft").rot);
		myobjects[startarrowobjs + na] = narr;
		na++;
		services.getView().pa.stop();
		var arr = sioux.getByName("Arrow");
		arr.setVisibility(false);
		checkHit = false;
		hit = false;
	}
	var td;
	if (checkHit) {
		var sd = sioux.getThingData();
		var ax = sd[bcarrow + 4];
		var ay = sd[bcarrow + 5];
		var ar = sd[bcarrow + 6] * scale;
		td = tatanka.getThingData();
		for ( var i = 0; i < 5; i++) {
			tx = td[4 + i * 7];
			ty = td[5 + i * 7];
			tr = td[6 + i * 7] * scale;

			if ((tx - ax) * (tx - ax) + (ty - ay) * (ty - ay) <= (tr + ar)
					* (tr + ar)) {
				if (services.getView().pa != undefined) {
					hit = true;
					hx = ax;
					hy = ay;
					if (i < 2) {
						tatankahealth -= 80+ getRandom(0, 40);
					} else {
						tatankahealth -= 30+ getRandom(0, 10);
					}
					if (tatankahealth <= 0) {
						tatankaani.kill();
						tatankaani.stopWalk();
						tatankaspeed = 0;
						tatankahealth = 100;
						tatankas++;
						services.getView().tatankas = tatankas;
						tatankaani
					}
					services.getView().tatankahealth = tatankahealth;
					// console.log("ax: "+ax+ " ay: "+ay );
					// console.log("hx: "+hx+ " hy: "+hy );

					return;
				}
			}
		}
	}
}

function translateHorseAndSioux(x, y) {
	horse.translate(x, y);
	sioux.translate(x, y);
}

function tatankaUpdate() {
	if (!services.getView().enabled)
		return;
	frames;
	handleHit();
	var deg = ((tatanka.x + tatanka.rx - sioux.x - sioux.rx) > 0) ? 1 : -1;
	if (!aggressive)
		deg = -deg;
	if (frames % 5 == 0
			&& tatankaspeed > 0
			&& ((tatanka.rot > 340 && tatanka.rot <= 359)
					|| (tatanka.rot < 20 && tatanka.rot >= 0)
					|| (tatanka.rot <= 180 && tatanka.rot >= 20 && deg < 0) || (tatanka.rot <= 340
					&& tatanka.rot > 180 && deg > 0))) {
		tatanka.rotate(deg);
		for ( var j = 0; j < na; j++) {
			myobjects[startarrowobjs + j].rotate(deg);
		}
	}
	var phi = horse.rot * Math.PI * 2.0 / 360;
	var speedy = -Math.cos(phi) * SPEED[speed];
	var speedx = -Math.sin(phi) * SPEED[speed];
	var y = horse.ry + horse.y;
	var x = horse.rx + horse.x;
	var seff = 0;
	var zone = 0;
	var minx = -292 * scale, maxx = 292 * scale;

	if (sprint != 0) {
		sprint++;
		if (sprint == 120) {
			handleKey(40);
		}
	}

	// canvasheight * scale== 1024, split in 4
	if (y > -0 * scale && y <= 0) {
		zone = 1;
	} else {
		if (y > 256 * scale && y < (256 + 128) * scale) {
			zone = 2;
		} else {
			if (y >= (256 + 128) * scale)
				zone = 3;
			if (y > (256 + 192) * scale)
				zone = 4;
		}
	}
	seff = 0;// speedy;
	switch (speed) {
	case 3:
		if (zone > 0) {
			seff -= 1;
		}
		break;
	case 2:
		if (zone <= 1) {
			seff += 1;
		}
		if (zone > 1) {
			seff -= 1;
		}
		break;
	case 1:
		if (zone <= 2) {
			seff += 1;
		}
		if (zone == 4)
			seff -= 1;
		break;
	case 0:
		seff += 1;
		if (zone == 4)
			seff = 0;
	}
	var oty = tatanka.y;
	tseff = -Math.cos(tatanka.rot * Math.PI * 2.0 / 360) * tatankaspeed
			- speedy + seff;

	if (/* (tseff> 0 && tatanka.y> (320*scale) && tatankaani.killed== false) || */(tseff < 0
			&& speed > 0 && tatanka.y < -320 * scale)) {
	//	console.log("tseff: " + tseff + " y: " + tatanka.y);
		tseff = 0;
	}
	// var speedx = -Math.sin(tatanka.rot) * tatankaspeed;

	// tseff= -tatankaspeed - (speedy-seff);
	if (tatanka.y > 712 * scale) {
		for ( var i = startarrowobjs; i < myobjects.length; i++) {
			myobjects[i] = undefined;
		}
		na = 0;
		tatanka.reset();
	
		tatanka.x = -200 * scale + 400 * Math.random(0, 1);
		tatanka.y = -600 * scale;
		tatankaspeed = 2;
		tatankaani.unkill();
		tatankaani.startRun();
		if(getRandom(0, 3)==3) 
		{
			tatanka.rotate(180-5*getRandom(0, 10));
			console.log("Tatanka rotate");
		}

	}
	if ((tatanka.y > -620 * scale && tatanka.y < 720 * scale)
			|| ((tatanka.y > 400 * scale) && tseff < 0)
			|| ((tatanka.y < -300 * scale) && tseff > 0)) {
		var tx = tatanka.rx + tatanka.x;
		var deg = 0;
		if (tx < minx + 32 * scale && tatanka.rot > 0 && tatankaspeed > 0) {
			tatanka.rotate(-3);
			deg = -3;
		}
		if (tx > maxx && tatanka.rot >= 270 && tatankaspeed > 0) {
			tatanka.rotate(3);
			deg = 3;
		}
		tx = -Math.sin(tatanka.rot * Math.PI * 2.0 / 360) * tatankaspeed;
		tatanka.translate(tx, tseff, 0);
		for ( var j = 0; j < na; j++) {
			myobjects[startarrowobjs + j].translate(tx, tseff, 0);
			// myobjects[5 + j].rotate(deg);
		}
	}

	// console.log(y+" "+(seff-256*scale));
	if (y > seff - 256 * scale || ((y <= seff - 256 * scale) && seff > 0)) {
		translateHorseAndSioux(speedx, seff);
	} else {
		seff = 0;
	}
	// console.log("zone: "+zone+" seff: "+seff+" speedy: "+speedy+" speed:
	// "+speed);

	bg1.translate(0, -speedy + seff, 0); // -sy+seff= 3 seff=
	bg2.translate(0, -speedy + seff, 0);
	if (bg1.y > bgd - 1)
		bg1.y -= 2 * (bgd - 1);
	if (bg2.y > bgd)
		bg2.y -= 2 * (bgd - 1);
	if ((x > minx && x < maxx)
			|| ((x < minx && speedx > 0) || (x > maxx && speedx < 0))) {
		horse.translate(speedx, 0, 0);
		sioux.translate(speedx, 0, 0);
	}
	if (x < minx && horse.rot > 0) {
		sioux.rotate(-1);
		horse.rotate(-1);
	}
	if (x > maxx && horse.rot >= 270) {
		sioux.rotate(1);
		horse.rotate(1);
	}
	if (checkCollision(0)) {
		horse.translate(-horse.x + x - horse.rx, -horse.y + y - horse.ry, 0);
		sioux.translate(-sioux.x + x - sioux.rx, -sioux.y + y - sioux.ry, 0);
		tatanka.translate(0, -tatanka.y + oty, 0);
		for ( var j = 0; j < na; j++) {
			myobjects[startarrowobjs + j].translate(0, -(-tatankaspeed + seff),
					0);
		}
		var dx = 100;
		arrowsleft = 0;

		var s = siouxs - 1;
		if (s < 0) {
			deleteMenu();
			createMainMenu();
		}
		if (!shooting) {
			if (sioux.x < tatanka.x && sioux.x > 100 * scale) {
				dx = -100;
			}
			resetTatanka();
			siouxs = s;
			services.getView().siouxs = siouxs;

		}
	}
	// console.log(x+" "+scale);
	// console.log(horse.x+" "+(canvaswidth/2-5*scale)+" "+horse.rot);
	/*
	 * if(horse.x>= canvaswidth/2- 50*scale && (horse.rot<360 && horse.rot>
	 * 180)) { handleKey(37); } if(horse.x<= -canvaswidth/2+ 50*scale &&
	 * (horse.rot<180 && horse.rot> 0)) { handleKey(39); }
	 */
}

function resetTatanka() {
	shooting = true;
	hit = false;
	arrowsleft = 30;
	services.getView().arrows = arrowsleft;
	siouxs = 3;
	services.getView().siouxs = siouxs;
	services.getView().pa = undefined;
	for ( var i = startarrowobjs; i < myobjects.length; i++) {
		myobjects[i] = undefined;
	}
	na = 0;
	tatankaani.stop();
	horseani.stop();
	tatanka.reset();
	horse.reset();
	sioux.reset();
	tatanka.x = -80 * scale;
	tatanka.y = -64 * scale;
	horse.x = 0; // startY * scale;
	sioux.x = 0; // startY* scale;
	horse.y = 0; // startY * scale;
	sioux.y = 0; // startY* scale;
	horse.rot = 0; // startY * scale;
	sioux.rot = 0; // startY* scale;

	tatankaani.startRun();
	horseani.startRun();
	shooting = false;
	/*
	 * tatanka.translate(-80*scale, -64*scale, 0); tatanka.scale(scale, scale);
	 * horse.translate(0, startY*scale, 0); horse.scale(scale, scale);
	 * sioux.translate(0, startY*scale, 0); sioux.scale(scale, scale);
	 */
}

function initTatanka(c) {
	tatanka = new Tatanka();
	horse = new Horse();
	sioux = new Sioux();
	bgd = Math.max(canvaswidth, canvasheight) + 1;
	bg1 = new Quad();
	bg1.quadinit(bgd, bgd, 0);
	bg1.rotate(90);
	bg1.setTexName("valleygras512.jpg");
	myobjects[0] = bg1;
	bg2 = new Quad();
	bg2.quadinit(bgd, bgd, 0);
	bg2.rotate(90);
	bg2.setTexName("valleygras512.jpg");
	myobjects[1] = bg2;
	bg2.translate(0, -bgd + 1, 0);

	myobjects[2] = tatanka;
	tatanka.translateRoot(-80 * scale, -64 * scale, 0);
	tatanka.scaleRoot(scale, scale);
	myobjects[3] = horse;
	horse.translateRoot(0, startY * scale, 0);
	horse.scaleRoot(scale, scale);
	myobjects[4] = sioux;
	sioux.translateRoot(0, startY * scale, 0);
	sioux.scaleRoot(scale, scale);
	tatankaani = new TatankaAnimation(tatanka, 16, 1000);
	tatankaani.startRun();

	horseani = new TatankaAnimation(horse, 16, 1200);
	horseani.startRun();

	goodie = new Goodie();
	goodie.create(1);
	myobjects[6] = goodie;
	myobjects[5] = goodie.bc;

	new View(c, myobjects, tatankaUpdate);
	shooting = false;
}

function shootStep1() { // get fibre
	if (arrowsleft < 0)
		return;
	services.getView().arrows = arrowsleft;
	shooting = true;
	var bow = sioux.getByName("Bow");
	var ra = sioux.getByName("ArmRight");
	var arr = sioux.getByName("Arrow");
	var x = shootAtX - w / 2 - sioux.x - sioux.rx;
	var y = h / 2 - shootAtY + sioux.y + sioux.ry;
	var newdir = Math.atan2(y, x);
	var sr = sioux.rot;
	if (sr > 180)
		sr = -360 + sr;
	if (newdir < 0) {
		newdir += 2 * Math.PI;
	}
	newdir = newdir * 360 / (2 * Math.PI);
	deg = -135 + newdir - sr;
	// console.log("deg: "+deg+" sr: "+sr);
	if (deg > 90) {
		shooting = false;
		return;
	}
	arrowsleft--;

	arr.setVisibility(true);
	bow.rotate(35);
	ra.rotate(40);
	ra.scale(1.0, 1.4);
	setTimeout(shootStep2, 500);
}

function shootStep2() { //
	var rest = sioux.getByName("rest");
	services.getView().pa = new PartAnimation();
	// console.log(sioux.rot + " " + deg);
	services.getView().pa.init(rest, 0, 0, deg, 1.0, 1.0, Math.abs(deg * 3),
			false);
	services.getView().pa.start();
	setTimeout(shootStep3, Math.abs(deg * 3) + 100);
}

function shootStep3() { // span bow
	var la = sioux.getByName("ArmLeft");
	var ra = sioux.getByName("ArmRight");
	var fibre = sioux.getByName("Fibre");
	var arr = sioux.getByName("Arrow");

	la.scale(1.0, 1.5);
	ra.scale(1.0, 1 / 1.4);
	arr.scale(1.0, 1 / 1.5);
	arr.translate(0, 15, 0);
	fibre.scale(1.0, 15.0);
	setTimeout(shootStep4, 500);
}

function shootStep4() {
	var bow = sioux.getByName("Bow");
	var fibre = sioux.getByName("Fibre");
	var arr = sioux.getByName("Arrow");

	fibre.scale(1.0, 1.0 / 15.0);
	bow.scale(1.0, 1.0 / 1.5);
	arr.scale(1.0, 1.5);

	var l = Math.sqrt(canvaswidth * canvaswidth + canvasheight * canvasheight)
			/ scale;

	services.getView().pa = new PartAnimation();
	services.getView().pa.init(arr, 0, -l, 0, 1.0, 1.0, 1500, false);
	services.getView().pa.start();
	checkHit = true;

	setTimeout(shootStep5, 1500);
}

function shootStep5() {
	var la = sioux.getByName("ArmLeft");
	var bow = sioux.getByName("Bow");
	var ra = sioux.getByName("ArmRight");
	checkHit = false;
	bow.scale(1.0, 1.5);
	ra.scale(1.0, 1.0 / 1.2);
	la.scale(1.0, 1.0 / 1.5);
	setTimeout(shootStep6, 500);
}

function shootStep6() {
	if (!shooting)
		return;

	var la = sioux.getByName("ArmLeft");
	var bow = sioux.getByName("Bow");
	var ra = sioux.getByName("ArmRight");
	var rest = sioux.getByName("rest");

	la.rotate(-30);
	ra.rotate(-40);
	bow.rotate(-25);
	services.getView().pa = new PartAnimation();
	services.getView().pa.init(rest, 0, 0, -deg, 1.0, 1.0, Math.abs(deg * 3),
			false);
	services.getView().pa.start();
	setTimeout(shootStep7, Math.abs(deg * 3) + 100);
}

function shootStep7() {
	if (!shooting)
		return;
	var arr = sioux.getByName("Arrow");
	arr.setVisibility(false);
	sioux.reset();
	shooting = false;
}

function tatankaclick(e) {
	e.preventDefault();
	var event;
	if (e.touches != undefined) {
		event = e.touches[0];
	} else {
		event = e;
	}

	if (!phonegap) {
		var x = event.clientX;
		var y = event.clientY;
		// console.log(x+" "+y+" "+(w- 219*scale)/2+" "+(h-144*scale));
		if (x > (w - 219 * scale) / 2
				&& x < (w - 219 * scale) / 2 + (219 * scale)
				&& y > h - 144 * scale) {
			if (x > (w - 219 * scale) / 2 + 1.0 / 3.0 * scale * 219
					&& x < (w - 219 * scale) / 2 + (219 * scale) * 2 / 3
					&& y > h - 144 * scale && y < h - 144 * scale * 2 / 3.0) {
				handleKey(38);
			}
			if (x > (w - 219 * scale) / 2 + 1.0 / 3.0 * scale * 219
					&& x < (w - 219 * scale) / 2 + (219 * scale) * 2 / 3
					&& y > h - 144 * scale * 1 / 3.0) {
				handleKey(40);
			}

			if (x > (w - 219 * scale) / 2
					&& x < (w - 219 * scale) / 2 + (219 * scale) / 3
					&& y < h - 144 * scale / 3 && y > h - 144 * scale * 2 / 3.0)
				handleKey(72);
			if (x > (w - 219 * scale) / 2 + 2.0 / 3.0 * scale * 219
					&& x < (w - 219 * scale) / 2 + (219 * scale)
					&& y < h - 144 * scale / 3 && y > h - 144 * scale * 2 / 3) {
				handleKey(76);
			}
			return;
		}
	}

	if (shooting == true)
		return;
	shooting = true;

	shootAtX = event.clientX - document.getElementById("canvas").offsetLeft;
	shootAtY = event.clientY - document.getElementById("canvas").offsetTop;
	setTimeout(shootStep1, 0);
	return;
}

function checkCollision(d) {
	if (tatankaani.killed)
		return false;
	var hd = horse.getThingData();
	var td = tatanka.getThingData();

	var gd = goodie.bc.getThingData();
	for ( var hb = 0; hb < 5; hb++) {
		var ax = hd[hb * 7 + 4];
		var ay = hd[hb * 7 + 5];
		var ar = hd[hb * 7 + 6] * scale;
		for ( var i = 0; i < 3; i++) {
			var tx = gd[4 + i * 7];
			var ty = gd[5 + i * 7];
			var tr = gd[6 + i * 7] * scale;
			// console.log("tx: "+tx+" ty: "+ty+" tz: "+tr);
			if ((tx - ax) * (tx - ax) + (ty - ay) * (ty - ay) <= (tr + ar)
					* (tr + ar)) {
				goodie.bc.visible = false;
				console.log("Goodie!");

			}
		}
	}
	var collision = false;
	for ( var hb = 0; hb < 5; hb++) {
		var ax = hd[hb * 7 + 4];
		var ay = hd[hb * 7 + 5];
		var ar = hd[hb * 7 + 6] * scale;
		for ( var i = 0; i < 5; i++) {
			var tx = td[4 + i * 7];
			var ty = td[5 + i * 7];
			var tr = td[6 + i * 7] * scale;
			if ((tx - ax) * (tx - ax) + (ty - ay) * (ty - ay) <= (tr + ar)
					* (tr + ar)) {
				collision = true;
				if (!tatankaani.killed)
					horse.setAlpha(100);
			}
		}
	}
	if (collision) {
		horse.rotate(d);
		sioux.rotate(d);
		console.log("Collision: " + services.getView().enabled);
		return true;
	}
	return false;
}

function handleKey(keyCode) {
	if (horse == undefined)
		return;
	var r = horse.rot;
	switch (keyCode) {
	case 77:
		break;
	case 78:
		break;

	case 37:
	case 72:
		if (checkHit)
			return;
		if ((r < 180 && r >= 45) || speed == 0)
			return;
		sioux.rotate(3);
		horse.rotate(3);
		checkCollision(-3);
		break;
	case 39:
	case 76:
		if (checkHit)
			return;
		if ((r > 180 && r <= 315) || speed == 0)
			return;
		sioux.rotate(-3);
		horse.rotate(-3);

		checkCollision(3);
		break;

	case 38:
		if (sprint != 0)
			break;
		if (speed < 3) {
			if (speed == 2) {
				horseani.duration = 800;
				horseani.sl = 20;
				horseani.startRun();
				sprint = 1;
			}
			speed++;
		}
		if (speed == 1)
			horseani.startWalk();
		if (speed > 1)
			horseani.startRun();
		break;

	case 40:
		if (speed == 3) {
			horseani.duration = 1200;
			horseani.sl = 16;
			horseani.startRun();
			sprint = -60;
		}
		if (speed > 0)
			speed--;
		if (speed == 0)
			horseani.stopWalk();
		if (speed == 1)
			horseani.startWalk();
		break;
	case 88:
		tatankaani.kill();
		tatankaani.stopWalk();
		tatankaspeed = 0;
		break;
	case 67:
		tatankaani.startRun();
		break;

	}
}

function keyDown(e) {
	handleKey(e.keyCode);
}

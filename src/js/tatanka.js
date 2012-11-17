var ctx;
var alldiv;
var objects;
var retina = 1;
var timer;
var fpstimer;
var fps;
var fpstext = "";
var deg = -10;
var shooting = true;
var checkHit = false;
var SPEED = [ 0, 1.5, 3, 4 ];
var TATANKASPEED = 3;
var sioux, horse, arrow, tatanka, bg1, bg2, bgd, speed = 2, tatankaspeed = TATANKASPEED, tseff;
var startY = 320;
var na = 0;
var tx, ty, tr;
var hx = 0;
var hy = 0;
var hr;
var arrowsleft = 30;
var siouxs = 3;
var tatankahealth = 100, tatankas = 0;
var allthings = new Array(256);
var frames = 0;
var aggressive = false;
var goodie;
var sprint = 0;
var rope = null;
var minx, maxx;
var lakotas;
var nlakotas= 1;
var herds;
var prairie;
var activelakota= 0;


function initTatanka(c) {
	//scale*= 2;
	prairie= new Prairie(canvaswidth, canvasheight, "valleygras512.jpg");
	
	herds = new Array(1);
	herds[0]= new TatankaHerd(3);
	
	lakotas= new Array(nlakotas);
	for( var i= 0; i< nlakotas; i++) {
		lakotas[i]= new MountedLakota(scale, 80*i, startY);
		lakotas[i].translateRoot(-240*scale*nlakotas/2+i*100*scale, -100*scale+-50*scale+getRandom(0, 100), 0);
	}
	
	var pos= 0;
	for(var layer= 0; layer< 1000; layer++) {
		pos+= prairie.addThings(allthings, pos, layer);
		pos+= herds[0].addThings(allthings, pos, layer);
		for(var j= 0; j< nlakotas; j++) {
			pos+= lakotas[j].addThings(allthings, pos, layer);
		}
	}

	for(var j= 0; j< nlakotas; j++) {
		lakotas[j].arrow.setCollisionHandler(new ArrowCollisionHandler(allthings, 4, 4+6));
	}
	
	minx = -292 * scale;
	maxx = 292 * scale;

	new View(c, allthings, updateAll);
	shooting = false;
}



function tatankaUpdateDirection() {
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
	}
}

function calculateHorsesEffectiveSpeed(x, y) {
	var zone = 0;
	// canvasheight * scale== 1024, split in 4 zones
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
	var seff = 0;
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
	return seff;
}

function updateTatanka(tat, seff, speedy) {
	tatankaUpdateDirection();

	var oty = tat.y;
	var tseff = -Math.cos(tat.rot * Math.PI * 2.0 / 360) * tatankaspeed
			- speedy + seff;

	// Tatanka going north but already too far. Pause tatanka
	if (tseff < 0 && speed > 0 && tat.y < -320 * scale) {
		tseff = 0;
	}

	// Tatanka too far south. Chasing a new one
	if (tat.y > 712 * scale) {
		console.log("Unkill tatanka");
		// Reset Tatanka, unkill and start running
		tat.sx = 1.0;
		tat.sy = 1.0;
		tat.reset();
		tat.resetHealth();

		tatankaspeed = TATANKASPEED;

		//tatankaani.unkill();
		//tatankaani.startRun();

		// Calculate new Tatanka position and rotation
		tat.x = -200 * scale + 400 * Math.random(0, 1);
		tat.y = -600 * scale;
		if (getRandom(0, 3) == 3) {
			tat.rotate(180 - 5 * getRandom(0, 10));
		}

	}

	if ((tat.y > -620 * scale && tat.y < 720 * scale)
			|| ((tat.y > 400 * scale) && tseff < 0)
			|| ((tat.y < -300 * scale) && tseff > 0)) {
		var tx = tat.rx + tat.x;
		var deg = 0;
		if (tx < minx + 32 * scale && tat.rot > 0 && tatankaspeed > 0) {
			tat.rotate(-3);
			deg = -3;
		}
		if (tx > maxx && tat.rot >= 270 && tatankaspeed > 0) {
			tat.rotate(3);
			deg = 3;
		}
		tx = -Math.sin(tat.rot * Math.PI * 2.0 / 360) * tatankaspeed;

		tat.translate(tx, tseff, 0);

	}
}

function updateHorseAndSioux(seff, speedx, speedy, x, y, tat) {
	lakotas[0].update();

	// decrease horse's air if sprinting. Stop sprinting if no more reserves
	if (sprint != 0) {
		sprint++;
		if (sprint == 120) {
			handleKey(40);
		}
	}

	if (y > seff - 256 * scale || ((y <= seff - 256 * scale) && seff > 0)) {
		translateHorseAndSioux(speedx, seff);
	} else {
		seff = 0;
	}
	if ((x > minx && x < maxx)
			|| ((x < minx && speedx > 0) || (x > maxx && speedx < 0))) {
		translateHorseAndSioux(speedx, 0);
	}
	if (x < minx) { // && horse.rot > 0) {
		rotateHorseAndSioux(-3);
	}
	if (x > maxx) { // && horse.rot >= 270) {
		rotateHorseAndSioux(3);
	}
	if (checkCollision(0)) {
		translateHorseAndSioux(-horse.x + x - horse.rx, -horse.y + y - horse.ry);
		var dx = 100;
		arrowsleft = 0;

		var s = siouxs - 1;
		if (s < 0) {
			deleteMenu();
			createMainMenu();
		}
		if (!shooting) {
			if (sioux.x < tat.x && sioux.x > 100 * scale) {
				dx = -100;
			}
			resetTatanka();
			siouxs = s;
			services.getView().siouxs = siouxs;

		}
	}

}


function updateAll() {
	prairie.update(0, 1);
	herds[0].update();
	for(var j= 0; j< nlakotas; j++)
		lakotas[j].update();
	return;
	
	if (!services.getView().enabled)
		return;

	var x = lakotas[activelakota].mustang.rx + lakotas[activelakota].mustang.x;
	var y = lakotas[activelakota].mustang.ry + lakotas[activelakota].mustang.y;
	var phi = lakotas[activelakota].mustang.rot * Math.PI * 2.0 / 360;
	var seff = calculateHorsesEffectiveSpeed(x, y);
	var speedy = -Math.cos(phi) * SPEED[speed];
	var speedx = -Math.sin(phi) * SPEED[speed];

	updateTatanka(tatanka, seff, speedy);

	updateHorseAndSioux(seff, speedx, speedy, x, y, tatanka);

	checkForAndHandleHit();

	updateBackground(seff, speedy);
}

function resetTatanka() {
	return;
	shooting = true;
	hit = false;
	arrowsleft = 30;
	services.getView().arrows = arrowsleft;
	siouxs = 3;
	services.getView().siouxs = siouxs;
	services.getView().pa = undefined;
	//tatankaani.stop();
	//horseani.stop();
	tatanka.reset();
	horse.setAlpha(255);
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

	// tatankaani.startRun();
	//horseani.startRun();
	shooting = false;

	/*
	 * tatanka.translate(-80*scale, -64*scale, 0); tatanka.scale(scale, scale);
	 * horse.translate(0, startY*scale, 0); horse.scale(scale, scale);
	 * sioux.translate(0, startY*scale, 0); sioux.scale(scale, scale);
	 */
}



function tatankaclick(e) {
	e.preventDefault();
	var event;
	if (e.touches != undefined) {
		event = e.touches[0];
	} else {
		event = e;
	}

	shootAtX = event.clientX - document.getElementById("canvas").offsetLeft;
	shootAtY = event.clientY - document.getElementById("canvas").offsetTop;
	lakotas[activelakota].shoot(shootAtX, shootAtY);
	return;
}

function checkCollision(d) {
//	if (tatankaani.killed)
//		return false;
	var hd = horse.getThingData();
	var td = tatanka.getThingData();

	/*
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
	*/
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
			//	collision = true;
		//		if (!tatankaani.killed)
			//		horse.setAlpha(100);
			}
		}
	}
	if (collision) {
		rotateHorseAndSioux(d);
		console.log("Collision: " + services.getView().enabled);
		return true;
	}
	return false;
}

function handleKey(keyCode) {
	if (horse == undefined)
		return;
	var minx = -292 * scale, maxx = 292 * scale;
	var y = horse.ry + horse.y;
	var x = horse.rx + horse.x;

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
		if ((r < 180 && r >= 30) || speed == 0 || x > maxx)
			return;
		rotateHorseAndSioux(1);
		checkCollision(-3);
		break;
	case 39:
	case 76:
		if (checkHit)
			return;
		if ((r > 180 && r <= 330) || speed == 0 || x < minx)
			return;
		rotateHorseAndSioux(-1);
		checkCollision(3);
		break;

	case 38:
		if (sprint != 0)
			break;
		if (speed < 3) {
			if (speed == 2) {
				horseani.duration = 800;
				horseani.sl = 20;
				//horseani.startRun();
				sprint = 1;
			}
			speed++;
		}
		/*
		if (speed == 1)
			//horseani.startWalk();
		if (speed > 1)
			//horseani.startRun();
		*/
		activelakota++;
		break;

	case 40:
		if (speed == 3) {
			horseani.duration = 1200;
			horseani.sl = 16;
			//horseani.startRun();
			sprint = -60;
		}
		if (speed > 0)
			speed--;
/*
		if (speed == 0)
			//horseani.stopWalk();
		if (speed == 1)
			//horseani.startWalk();
	*/
			activelakota++;
console.log("ac: "+activelakota);
			break;
	case 88:
	//	tatankaani.kill();
	//	tatankaani.stopWalk();
		tatankaspeed = 0;
		break;
	case 67:
	//	tatankaani.startRun();
		break;

	}
}

function keyDown(e) {
	handleKey(e.keyCode);
}

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

function Triangle(rx,  ry,  rz,  px1,  py1,  px2,  py2,  px3,  py3,  pr, color)  {
	this.partinit();
	this.setRoot(rx, ry, rz, pr);
	this.setColor(color);
	this.x1 = px1;
	this.y1 = py1;
	this.x2 = px2;
	this.y2 = py2;
	this.x3 = px3;
	this.y3 = py3;   
}		

Triangle.prototype= new Part();
	
Triangle.prototype.reset= function() {
	this.partreset();
};

Triangle.prototype.getNumberOfData= function() {
		return 4+2 * (3+1); // type, n coord, color, data 3*(x & y)
};
	
Triangle.prototype.getData= function(d, startD,  xn,  yn,  zn,  a11,  a21,  a12,  a22) {
		var n= startD;
		d[n++]= this.getType();
		d[n++]= 3;
		d[n++] =  (this.a<<24)+(this.r<<16)+(this.g<<8)+this.b;
		d[n++] = 1;
		n+=2;
		var phi = (this.rot + this.rrot);
		phi= Math.round(phi);
		while (phi < 0) {
			phi += 360;
		}
		phi %= 360;
		var sinbeta = mysin[phi];
		var cosbeta = mycos[phi];
		var dummy;
		var dummy2;
		dummy = this.x1 * cosbeta - this.y1 * sinbeta +this.rx +this.x;
		dummy2 = this.x1 * sinbeta + this.y1 * cosbeta+ this.ry+ this.y;
		d[n] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n+1] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		dummy = this.x2 * cosbeta - this.y2 * sinbeta +this.rx +this.x;
		dummy2 = this.x2 * sinbeta + this.y2 * cosbeta+ this.ry+ this.y;
		d[n+2] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 3] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		dummy = this.x3 * cosbeta - this.y3 * sinbeta +this.rx +this.x;
		dummy2 = this.x3 * sinbeta + this.y3 * cosbeta+ this.ry+ this.y;
		d[n + 4] = Math.round(dummy * a11 + dummy2 * a12 + xn);
		d[n + 5] = Math.round(dummy * a21 + dummy2 * a22 + yn);
		return this.getNumberOfData();
};

Triangle.prototype.getType= function() {
		return TRIANGLE;
};

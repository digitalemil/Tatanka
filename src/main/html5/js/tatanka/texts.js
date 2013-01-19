function Texts() {
	this.thinginit(3);
		var s= scale;
		this.t1 = new Text();
		this.t1.init("0", 0, -30* s,
				0x80000000);
		console.log("t1: "+this.t1);
		this.t1.setSize(36*s);
		this.t1.setAlignment(TEXT_LEFT);
		this.add(this.t1);
		
		this.t2 = new Text();
		this.t2.init("30", 0, -80* s,
				0x80000000);
		this.t2.setSize(36*s);
		this.t2.setAlignment(TEXT_LEFT);
		this.add(this.t2);
		
		this.t3 = new Text();
		this.t3.init("3", 0, -150* s,
				0x80000000);
		this.t3.setSize(36*s);
		this.t3.setAlignment(TEXT_LEFT);
		this.add(this.t3);
		
		this.setupDone();
}

Texts.prototype = new Thing(3);

Texts.prototype.setArrows= function(a) {
		this.t2.setText(a);
};
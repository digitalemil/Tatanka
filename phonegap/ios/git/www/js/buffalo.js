function Buffalo() {
		var darkbrown = 0xff190d01;
		var horn = 		0xff665544;
		var huf = 		0xA0665544;
		var red = 		0xff9f0609;
		var schnauze = 	0xff020314;
    
		var el;
		this.name= "Buffalo";
		var head = new Bone(0, -44, 16, 0, 8);
		head.name="Head";
		
		el = new Ellipse(12, 12, 0, -32, -3, 0, TRIANGLES8, red);
		el.name="Tongue";
		head.add(el);
		el = new Ellipse(16, 12, 0, -30, 0, -2, TRIANGLES8, schnauze);
		el.name="Schnauze";
		head.add(el);
		el = new Ellipse(18, 8, -32, -12, -1, 30, TRIANGLES8, darkbrown);
		el.name="LinkesOhr";
		head.add(el);
    
		el = new Ellipse(18, 8, 32, -12, -1, -30, TRIANGLES8, darkbrown);
		el.name="RechtesOhr";
		head.add(el);
    
		el = new Ellipse(32, 24, 0, -10, -1, 0, TRIANGLES12, darkbrown);
		el.name="Schaedel";
		head.add(el);
		el = new Ellipse(60, 12, 0, -10, 0, 0, TRIANGLES10, horn);
		el.name="Horn";
		head.add(el);
		el = new Ellipse(6, 6, -56, -6, 0, 0, TRIANGLES8, horn);
		el.name="LinkesHorn";
		head.add(el);
    
		el = new Ellipse(6, 6, 56, -6, 0, 0, TRIANGLES8, horn);
		el.name="RechtesHorn";
		head.add(el);
    
		head.setupDone();
		head.rotate(0);
    
		var body = new Bone(0, 0, 0, 0, 7);
		body.name="Body";
    
		var leg = new Bone(0, -40, 0, 0, 2);
		leg.name="LeftForeleg";
		el = new Ellipse(12, 12, -32, -6, -1, 0, TRIANGLES10, huf);
		leg.add(el);
		el.name="LeftForelegPart1";
		el = new Ellipse(12, 12, -30, 0, 0, 0, TRIANGLES10, darkbrown);
		leg.add(el);
		el.name="LeftForelegPart2";
		leg.setupDone();
		body.add(leg);
		leg = new Bone(0, -40, 0, 0, 2);
		leg.name="RightForeleg";
		el = new Ellipse(12, 12, 32, -6, -1, 0, TRIANGLES10, huf);
		leg.add(el);
		el.name="RightForelegPart1";
		el = new Ellipse(12, 12, 30, -0, 0, 0, TRIANGLES10, darkbrown);
		leg.add(el);
		el.name="RightForelegPart2";
		leg.setupDone();
		body.add(leg);
    
		leg = new Bone(0, 30, 0, 0, 2);
		leg.name="RightHindleg";
		el = new Ellipse(12, 12, 32, 0, -1, 0, TRIANGLES10, huf);
		leg.add(el);
		el.name="RightHindlegPart1";
		el = new Ellipse(12, 12, 30, 6, 0, 0, TRIANGLES10, darkbrown);
		leg.add(el);
		el.name="RightHindlegPart2";
		leg.setupDone();
		body.add(leg);
		leg = new Bone(0, 30, 0, 0, 2);
		leg.name="LeftHindleg";
		el = new Ellipse(12, 12, -32, 0, -1, 0, TRIANGLES10, huf);
		leg.add(el);
		el.name="LeftHindlegPart1";
		el = new Ellipse(12, 12, -30, 6, 0, 0, TRIANGLES10, darkbrown);
		leg.add(el);
		el.name="LeftHindlegPart2";
		leg.setupDone();
		body.add(leg);
    
		el = new Ellipse(36, 60, 0, 0, 0, 0, TRIANGLES20, darkbrown);
		el.name="Rumpf";
		body.add(el);
	
		el = new Ellipse(4, 12, 0, 60, 0, 0, TRIANGLES8, darkbrown);
		el.name="Tail";
		body.add(el);  
		body.setupDone();
    
    	var rest = new Bone(0, -30, 0, 0, 2);
		rest.name="Rest";
		el = new Ellipse(18, 24, 0, -24, -2, 0, TRIANGLES12, darkbrown);
		el.name="Throat";
		rest.add(el);
		rest.add(head);
		rest.setupDone();
 
		body.scaleRoot(1.0, 1.2);
		rest.scaleRoot(1.0, 1.2);
    
		this.add(body);
		this.add(rest);
 		this.setupDone();
}

Buffalo.prototype.getType= function() {
	return BUFFALO;
};

Buffalo.prototype= new Thing(2);




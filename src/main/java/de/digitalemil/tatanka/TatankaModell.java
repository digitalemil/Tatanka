package de.digitalemil.tatanka;

import de.digitalemil.eagle.*;
import de.digitalemil.tocplusplus.MethodDefinitionChangerAnnotation;

public class TatankaModell extends Modell {
	
	public TatankaModell() {
		super(256);
	}

	
	public void setup() {
		Screen huntingScreen= new HuntingScreen();
		Screen firstScreen= new FirstScreen();
		Screen moreScreen= new MoreScreen();
		Screen.registerScreen(huntingScreen);
		Screen.registerScreen(firstScreen);
		Screen.registerScreen(moreScreen);
		Screen.registerScreen(new AboutScreen());
		Screen.registerScreen(new GalleryScreen());		
		showScreen(firstScreen.getScreenID());
	//	System.exit(-1);
	}


}
/*
 * for(int i= 0; i< Ellipse.mysin.length; i++) {
 * System.out.print(Math.round(Ellipse.mysin[i]*10000)+", "); }
 * System.out.println(""); System.out.println("");
 * 
 * for(int i= 0; i< Ellipse.mysin.length; i++) {
 * System.out.print(Math.round(Ellipse.mycos[i]*10000)+", "); }
 * System.out.println("");
 * 
 * 
 * for(int i= 0; i< Part.mysin.length; i++) {
 * System.out.print(Math.round(Part.mysin[i]*10000)+", "); }
 * System.out.println(""); System.out.println("");
 * 
 * for(int i= 0; i< Part.mysin.length; i++) {
 * System.out.print(Math.round(Part.mycos[i]*10000)+", "); }
 * System.out.println("");
 */

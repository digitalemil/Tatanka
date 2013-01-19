package de.digitalemil.eagle;

public interface Joystick {
	
		public void up() ;
		
		public boolean down(int x, int y);

		public void move(int tx, int ty); 
		
		public void translate(float x, float y, float z);
}

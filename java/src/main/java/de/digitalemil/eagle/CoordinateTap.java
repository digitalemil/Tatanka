package de.digitalemil.eagle;

public interface CoordinateTap {
	
	public void save(float ix, float iy, float ir, float ia11, float ia21,
			float ia12, float ia22) ;
	
	public void reset();

	public String getName();
	
	public float getX();

	public float getY();
	
	public float getR();

	public float getA11();

	public float getA21();
	
	public float getA12();

	public float getA22();

	public void setName(String name) ;

}
	
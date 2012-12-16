package de.digitalemil.eagle;

public interface CanHaveChilds {

	public int getNumberOfBCs();
	
	public int addBCs(BoundingCircle[] bcs, int start);
}

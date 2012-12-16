package de.digitalemil.eagle;

public interface Animation {

	public void start();
	
	public Animation createReverseAnimation();
	
	public float animate();
	
	public float animate(long now);
	
	public void finish();
	
	public boolean isRunning();
	
	public void stop();
	
	public void pause() ;
	
	public void cont();

	public float animateDelta(long d);
	
	public void faster();
	
	public void slower();
	
	public int getType();
}

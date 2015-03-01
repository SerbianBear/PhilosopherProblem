package mainPackage;

public class Fork {

	private boolean available = true;
	
	public Fork(){
		
	}
	
	
	
	//Given that we can just use synchronized(fork) I don't even know 
	//if we need all of these classes to manage the availability of the fork :S
	
	public boolean isAvailable(){
		return available;
	}
	
	public boolean takeFork(){
		this.available = false;
		return available;
	}
	
	public boolean returnFork(){
		this.available = true;
		return available;
	}
}

package mainPackage;

public class Fork {
	
	private int forkNumber;
	private boolean isAvailable;
	
	public void takeFork(){
		this.isAvailable = false;
	}
	
	public void putForkDown(){
		this.isAvailable = true;
	}
	
	public int getForkNumber(){
		return this.forkNumber;
	}
	
	public Fork(int forkNumber){
		this.forkNumber = forkNumber;
		this.isAvailable = true;
	}
}

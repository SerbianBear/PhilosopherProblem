package mainPackage;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 */
public class Philosopher extends Thread{

	private long consumptionTime; //sleep time in milliseconds
	String name, done, thinking, status;
	String eating;
	Fork rightFork, leftFork;
	long before, after;

	public Philosopher(long consumptionTime){
		this.consumptionTime = consumptionTime;
		name = "Philosopher-" + this.getId();
		done = name + "has finished eating!";
		thinking = name + " is thinking.";
		eating = name + " is eating.";
		status = thinking;
		before = System.currentTimeMillis();
	}

	@Override
	public void run(){
		try {
			consume();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(done);
	}

	private void consume() throws InterruptedException{
		System.out.println(eating);
		Thread.sleep(consumptionTime);
		setStatus(done);
	}
	
	public void holdForks(Fork fork1, Fork fork2){
		rightFork = fork1;
		leftFork = fork2;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

package mainPackage;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 * 
 * The number of philosophers is equal to the number of Forks.
 */
public class Table {

	private final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 5;
	private final long DEFAULT_TIME_OF_PHILOSOPHERS = 5;
	private int numberOfPhilosophers = 0;
	private long timeOfPhilosophers = 0;
	Philosopher[] philosophers;
	public static Fork[] forks;
	
	public Table(){
		this.numberOfPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;
		this.timeOfPhilosophers = DEFAULT_TIME_OF_PHILOSOPHERS;
		initializePhilosopherForkArrays();
	}
	
	public Table(int numberOfPhilosophers, long timeOfPhilosophers){
		this.numberOfPhilosophers = numberOfPhilosophers;
		this.timeOfPhilosophers = timeOfPhilosophers;
		initializePhilosopherForkArrays();
	}
	
	public void begin (){
		for(Philosopher p : philosophers){
			p.start();
		}
		for(Fork f : forks){
			f.notify();
		}
	}
	
	private void initializePhilosopherForkArrays(){
		//TODO maybe clean this up so that there is a central static timeOfPhilosophers in the philosopher class?
		philosophers = new Philosopher[numberOfPhilosophers];
		for(int i = 0; i < numberOfPhilosophers; i++){
			philosophers[i] = new Philosopher(timeOfPhilosophers);
		}
		
		forks = new Fork[numberOfPhilosophers];
		for(int i = 0; i < numberOfPhilosophers; i++){
			forks[i] = new Fork();
		}
	}
}

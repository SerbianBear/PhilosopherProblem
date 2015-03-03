package mainPackage;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 * 
 * The number of philosophers is equal to the number of Forks.
 */
public class Table {

	private int numberOfPhilosophers = 0;
	private List<Philosopher> philosophers;
	private List<Philosopher.Fork> forks;

	public Table(){
		//Default values for #philosophers and time of philosophers.
		this(3, 4800);
	}

	public Table(int numberOfPhilosophers, long timeOfPhilosophers){
		this.numberOfPhilosophers = numberOfPhilosophers;
		//this.timeOfPhilosophers = timeOfPhilosophers;
		Philosopher.startingConsumptionTime = timeOfPhilosophers;
		initializePhilosopherForkArrays();
		Philosopher.maximumConsumptionTimePerTurn = 1500;
		Philosopher.minimumConsumptionTimePerTurn = 100;
	}

	public void begin () throws InterruptedException {
		for(Philosopher p : philosophers){
			p.start();
		}
		for(Philosopher p : philosophers){
			p.join();
		}
	}

	private void initializePhilosopherForkArrays(){
		//TODO maybe clean this up so that there is a central static timeOfPhilosophers in the philosopher class?
		forks = new ArrayList<Philosopher.Fork>();
		for(int i = 0; i < numberOfPhilosophers; i++){
			forks.add(new Philosopher.Fork(i));
		}

		philosophers = new ArrayList<Philosopher>();
		philosophers.add(new Philosopher(0, forks.get(numberOfPhilosophers-1), forks.get(0)));
		for(int i = 1; i < numberOfPhilosophers; i++){
			philosophers.add(new Philosopher(i, forks.get(i-1), forks.get(i)));
		}
	}
}

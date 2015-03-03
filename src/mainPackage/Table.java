package mainPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 * 
 * The number of philosophers is equal to the number of Forks.
 */
public class Table {

	private int numberOfPhilosophers = 0;
	//private long timeOfPhilosophers = 0;
	private List<Philosopher> philosophers;
	private List<Fork> forks;
	public static final Logger logger = Logger.getLogger("PhilosopherLog.txt");  

	public Table(){
		//Default values for #philosophers and time of philosophers.
		this(3, 4800);
	}

	public Table(int numberOfPhilosophers, long timeOfPhilosophers){
		this.numberOfPhilosophers = numberOfPhilosophers;
		//this.timeOfPhilosophers = timeOfPhilosophers;
		Philosopher.startingConsumptionTime = timeOfPhilosophers;
		initializePhilosopherForkArrays();
		initializeLogger();
		Philosopher.maximumConsumptionTimePerTurn = 1500;
		Philosopher.minimumConsumptionTimePerTurn = 100;
		Table.logger.info("The table has been set.");
	}

	public void begin () throws InterruptedException {
		Table.logger.info("Start eating!");
		for(Philosopher p : philosophers){
			p.start();
		}
		while(true){
			Thread.sleep(1000);
		}
	}

	private void initializePhilosopherForkArrays(){
		//TODO maybe clean this up so that there is a central static timeOfPhilosophers in the philosopher class?
		forks = new ArrayList<Fork>();
		for(int i = 0; i < numberOfPhilosophers; i++){
			forks.add(new Fork(i));
		}

		philosophers = new ArrayList<Philosopher>();
		philosophers.add(new Philosopher(0, forks.get(numberOfPhilosophers-1), forks.get(0)));
		for(int i = 1; i < numberOfPhilosophers; i++){
			philosophers.add(new Philosopher(i, forks.get(i-1), forks.get(i)));
		}
	}

	private void initializeLogger(){
		FileHandler fh;  
		try {  
			fh = new FileHandler("C:/Users/Nikola/workspace/PhilosopherProblem/src/mainPackage/PhilosopherLog");  
			Table.logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}

}

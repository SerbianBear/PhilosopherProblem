package mainPackage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
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
	private long timeOfPhilosophers = 0;
	private Queue<Philosopher> philosophers;
	private Queue<Fork> forks;
	public static final Logger logger = Logger.getLogger("PhilosopherLog.txt");  

	public Table(){
		//Default values for #philosophers and time of philosophers.
		this(5, 5000);
	}

	public Table(int numberOfPhilosophers, long timeOfPhilosophers){
		this.numberOfPhilosophers = numberOfPhilosophers;
		this.timeOfPhilosophers = timeOfPhilosophers;
		initializePhilosopherForkArrays();
		initializeLogger();
		
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
		philosophers = new LinkedList<Philosopher>();
		for(int i = 0; i < numberOfPhilosophers; i++){
			philosophers.add(new Philosopher(timeOfPhilosophers));
		}

		forks = new LinkedList<Fork>();
		for(int i = 0; i < numberOfPhilosophers; i++){
			forks.add(new Fork());
		}
	}
	
	private void initializeLogger(){
		FileHandler fh;  

		try {  
			fh = new FileHandler("C:/Users/Nikola/workspace/PhilosopherProblem/src/mainPackage/PhilosopherLog");  
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}
}

package mainPackage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
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
public class Table implements ThreadCompleteListener{

	private int numberOfPhilosophers = 0;
	private long timeOfPhilosophers = 0;
	private Queue<Philosopher> philosophers;
	public static Queue<Fork> forks;
	public static final Logger logger = Logger.getLogger("PhilosopherLog.txt");  

	public Table(){
		//Default values for #philosophers and time of philosophers.
		this(3, 4500);
	}

	public Table(int numberOfPhilosophers, long timeOfPhilosophers){
		this.numberOfPhilosophers = numberOfPhilosophers;
		this.timeOfPhilosophers = timeOfPhilosophers;
		initializePhilosopherForkArrays();
		initializeLogger();
		Philosopher.maximumConsumptionTimePerTurn = 1500;
		Table.logger.info("The table has been set.");
	}

	public void begin () throws InterruptedException {
		Table.logger.info("Start eating!");
		for(Philosopher p : philosophers){
			p.start();
		}
		while(!philosophers.isEmpty()){
			
			System.out.println("THE SIZE OF THE MOTHERFUCKING FORKS ARRAY IS: " + forks.size());
			Philosopher philosopher;
			synchronized(philosophers){
				//philosophers.wait();
				
				try{
					philosopher = philosophers.remove();
					
				}catch (NoSuchElementException e){
					break;
				}
				//philosophers.notify();
			}
			synchronized (Table.forks){
				if(Table.forks.size() >= 2){
					System.out.println("They are picking up forks!");
					philosopher.holdForks(forks.remove(), forks.remove());
				}else{
					System.out.println("I'm waiting!");
					Table.forks.wait();
				}
			}
			//When a philosopher is done eating the thread terminates and this removes it from the list. 
			synchronized(philosophers){
				//philosophers.wait();
				philosophers.add(philosopher);
				philosophers.notify();
			}
			System.out.println("THE SIZE OF THE LIST IS " + philosophers.size());
		}
		System.out.println("All of the bearded guys have eaten!");
	}

	private void initializePhilosopherForkArrays(){
		//TODO maybe clean this up so that there is a central static timeOfPhilosophers in the philosopher class?
		philosophers = new LinkedList<Philosopher>();
		for(int i = 0; i < numberOfPhilosophers; i++){
			philosophers.add(new Philosopher(timeOfPhilosophers));
		}
		for(Philosopher p : philosophers){
			p.addListener(this);
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
			Table.logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}

	@Override
	public void notifyOfThreadComplete(Thread thread) {
		// TODO Auto-generated method stub
		Philosopher philosopher = (Philosopher)thread;
		synchronized(philosophers){
			
			try {
				System.out.println("Philosopher waiting!");
				philosophers.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(Philosopher p : philosophers){
				System.out.println("COMPARISON " + p.equals(philosopher));
				if(p.equals(philosopher)){
					System.out.println("REMOVED ONE");
					philosophers.remove(p);
				}

			}
			philosophers.notify();
		}
	}
}

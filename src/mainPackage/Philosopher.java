package mainPackage;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 */
public class Philosopher extends Thread{

	private String name, done, thinking, eating;
	private Fork rightFork, leftFork;
	private long before, after, thinkingTime, eatingTime, remainingConsumptionTime;
	public static long maximumConsumptionTimePerTurn;
	private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

	public Philosopher(long consumptionTime){
		this.remainingConsumptionTime = consumptionTime;
		name = "Philosopher-" + this.getId();
		done = name + "has finished eating!";
		thinking = name + " is thinking.";
		eating = name + " is eating.";
		System.out.println(name + " has been created!");
		before = System.currentTimeMillis();
	}


	public final void addListener(final ThreadCompleteListener listener) {
		listeners.add(listener);
	}
	public final void removeListener(final ThreadCompleteListener listener) {
		listeners.remove(listener);
	}
	private final void notifyListeners() {
		for (ThreadCompleteListener listener : listeners) {
			listener.notifyOfThreadComplete(this);
		}
	}

	@Override
	public void run(){
		while(remainingConsumptionTime > 0){

			if(leftFork == null && rightFork == null){
				continue;
			} else {
				try {
					System.out.println(eating);
					consume();
					System.out.println(thinking);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}
		System.out.println(done);
		System.out.println(name + "Time spent thinking: " + thinkingTime);
		System.out.println(name + "Time spent eating: " + eatingTime);
		notifyListeners();
	}

	private void consume() throws InterruptedException{

		//adds time spent thinking to the thinkingTime long
		after = System.currentTimeMillis();
		long difference = after - before; 
		thinkingTime += difference;

		//set before to current time to measure time spent thinking
		before = System.currentTimeMillis();
		long sleepTime = 0;
		if(remainingConsumptionTime < maximumConsumptionTimePerTurn){
			sleepTime = remainingConsumptionTime;
		}else {
			sleepTime = maximumConsumptionTimePerTurn;
		}
		Thread.sleep(sleepTime);
		after = System.currentTimeMillis();
		difference = after - before; 
		eatingTime += difference;

		remainingConsumptionTime -= sleepTime;

		putForksDown();
		System.out.println("The sleeping time is: " + sleepTime);
		//reset before to time immediately after stopping eating
		before = System.currentTimeMillis();
	}

	public void holdForks(Fork fork1, Fork fork2){
		rightFork = fork1;
		leftFork = fork2;
	}

	public void putForksDown(){
		synchronized (Table.forks){
			Table.forks.add(rightFork);
			Table.forks.add(leftFork);
			Table.forks.notify();
		}

		rightFork = null;
		leftFork = null;
	}

	public String getPhilosopherName(){
		return name;
	}

	@Override
	public boolean equals(Object obj){
		Philosopher philosopher = null;
		if(obj instanceof Philosopher){
			philosopher = (Philosopher)obj;
		}else{
			return false;
		}
		
		if(philosopher.getPhilosopherName().equals(this.getPhilosopherName())){
			return true;
		}
		
		return false;
	}
}

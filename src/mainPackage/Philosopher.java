package mainPackage;

import java.util.Random;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 */
public class Philosopher extends Thread{

	protected static Random random = new Random();		
	private Status status;
	private int numberOfTimesToEat, philosopherNumber;
	private String name, done, thinking, eating;
	private Fork rightFork, leftFork;
	private long before, after, thinkingTime, eatingTime, remainingConsumptionTime;
	public static long maximumConsumptionTimePerTurn, minimumConsumptionTimePerTurn, startingConsumptionTime;

	public Philosopher(int philosopherNumber, Fork leftFork, Fork rightFork){
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.philosopherNumber = philosopherNumber;
		this.remainingConsumptionTime = startingConsumptionTime;
		name = "Philosopher-" + this.philosopherNumber;
		done = name + "has finished eating!";
		thinking = name + " is thinking.";
		eating = name + " is eating.";
		status = Status.RUNNING;
		numberOfTimesToEat = 0;
		before = System.currentTimeMillis();
	}

	@Override
	public void run(){
		while(remainingConsumptionTime > 0){
			try {
				Thread.sleep((long)(random.nextFloat()*1000));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			synchronized(leftFork){
				System.out.println(name + " has picked up left Fork");
				synchronized(rightFork){
					System.out.println(name + " has picked up right Fork");
					try {
						consume();
						rightFork.notify();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				leftFork.notify();
			}	
		}
		System.out.println(done);
		System.out.println(name + " ate " + numberOfTimesToEat + " times.");
		System.out.println(name + " spent thinking: " + thinkingTime);
		System.out.println(name + " spent eating: " + eatingTime);
		status = Status.DONE;
	}

	private void consume() throws InterruptedException{
		numberOfTimesToEat++;
		//adds time spent thinking to the thinkingTime long
		after = System.currentTimeMillis();
		long difference = after - before; 
		thinkingTime += difference;

		//set before to current time to measure time spent thinking
		before = System.currentTimeMillis();
		long sleepTime = 0;
	    sleepTime = random.nextInt(((int)maximumConsumptionTimePerTurn - (int)minimumConsumptionTimePerTurn) + 1) + (int)minimumConsumptionTimePerTurn;
		if(remainingConsumptionTime < sleepTime){
			sleepTime = remainingConsumptionTime;
		}
		System.out.println("The sleeping time is " + sleepTime);
		Thread.sleep(sleepTime);
		after = System.currentTimeMillis();
		difference = after - before; 
		eatingTime += difference;

		remainingConsumptionTime -= sleepTime;

		putForksDown();
		//reset before to time immediately after stopping eating
		before = System.currentTimeMillis();
	}

	public void holdForks(Fork fork1, Fork fork2){
		rightFork = fork1;
		leftFork = fork2;
	}

	public void putForksDown(){
		System.out.println(name + " has put the forks down.");
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
	
	public Status getStatus() {
		return status;
	}
}

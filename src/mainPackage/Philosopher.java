package mainPackage;

import java.util.ArrayList;
import java.util.Random;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 */
public class Philosopher extends Thread{

	protected static Random random = new Random();		
	private ArrayList<Integer> sleepTimes;
	private int numberOfTimesToEat, philosopherNumber;
	private String name, done;
	private Fork rightFork, leftFork;
	private long before, after, thinkingTime, eatingTime, remainingConsumptionTime;
	public static long maximumConsumptionTimePerTurn, minimumConsumptionTimePerTurn, startingConsumptionTime;

	public Philosopher(int philosopherNumber, Fork leftFork, Fork rightFork){
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.philosopherNumber = philosopherNumber;
		this.remainingConsumptionTime = startingConsumptionTime;
		this.sleepTimes = new ArrayList<Integer>();
		name = "Philosopher-" + this.philosopherNumber;
		done = name + "has finished eating!";
		numberOfTimesToEat = 0;
		before = System.currentTimeMillis();
	}

	@Override
	public void run(){
		while(remainingConsumptionTime > 0){
			//			try {
			//				Thread.sleep((long)(random.nextFloat()*1000));
			//			} catch (InterruptedException e1) {
			//				// TODO Auto-generated catch block
			//				e1.printStackTrace();
			//			}

			if(!leftFork.takeFork(philosopherNumber)){
				synchronized(leftFork){
					try {leftFork.wait();}
					catch (InterruptedException e1) {e1.printStackTrace();}
					System.out.println(name + " has picked up left Fork");
				}
			}
			
			if(rightFork.takeFork(philosopherNumber)){
				System.out.println(name + " has picked up right Fork");
				try {
					consume();
					rightFork.dropFork(philosopherNumber);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			leftFork.dropFork(philosopherNumber);
			Thread.yield();

		}
		System.out.println("START==================================================");
		System.out.println(done);
		System.out.println(name + " ate " + numberOfTimesToEat + " times.");
		System.out.println(name + " spent thinking: " + thinkingTime);
		System.out.println(name + " spent eating: " + eatingTime);
		for(Integer integer : sleepTimes){
			System.out.println(name + " ate " + integer.intValue());
		}
		System.out.println("END====================================================");
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
		sleepTimes.add(new Integer((int)sleepTime));

		//reset before to time immediately after stopping eating
		before = System.currentTimeMillis();
	}

	protected static class Fork {

		private boolean inUse;
		private int forkNumber;

		public Fork(int forkNumber){
			this.forkNumber = forkNumber;
			System.out.println("Fork " + forkNumber + " has been created!");
		}

		public synchronized boolean takeFork (int philosopherNumber){
			return inUse ? false : (inUse = true);
		}

		public synchronized void dropFork (int philosopherNumber){
			inUse = false;
			notify();
		}

		public synchronized void waitFor (int who) {
			while (!takeFork(who))
				try {
					wait();
				} catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}

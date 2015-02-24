package mainPackage;

/**
 * @since 2/17/2015
 * @author Nikola Neskovic and Kevin Rosengren
 */
public class Philosopher extends Thread{

	private long consumptionTime; //sleep time in milliseconds
	String name;
	String doneEating;
	String thinking;
	String eating;

	public Philosopher(long consumptionTime){
		this.consumptionTime = consumptionTime;
		name = "Philosopher-" + this.getId();
		doneEating = name + "has finished eating!";
		thinking = name + " is thinking.";
		eating = name + " is eating.";
	}

	//THE BULK OF THE LOGIC HAS TO BE DONE HERE, I THINK THIS IS ALL THAT'S LEFT
	//@override
	public void run(){
		int leftFork = -1;
		int rightFork = -1;
		int forksHeld = 0;

		for(int i = 0; i < Table.forks.length; i++){
			System.out.println(thinking);
			if(Table.forks[i].isAvailable()){
				synchronized(Table.forks[i]){
					try {
						System.out.println(thinking);
						Table.forks[i].wait();
						System.out.println("COCK");
						
						if(leftFork == -1){leftFork = i;}
						else{rightFork = i;}
						Table.forks[i].takeFork();
						forksHeld ++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if(forksHeld == 2){break;}
		}

		try {
			consume();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Table.forks[leftFork].returnFork();
		Table.forks[rightFork].returnFork();

		Table.forks[leftFork].notify();
		Table.forks[rightFork].notify();

		System.out.println(doneEating);
	}

	private void consume() throws InterruptedException{
		System.out.println(eating);
		Thread.sleep(consumptionTime);
	}
}

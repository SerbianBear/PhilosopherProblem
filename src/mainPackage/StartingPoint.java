package mainPackage;

public class StartingPoint {
	
	/* @param args[0] (int) number of Philosophers between 2 and 1000 threads
	 * @param args[1] (int) consumption time of Philosophers between 1 an 100000 milliseconds
	 */
	public static void main (String[] args){
		
		Table table;		
		
		//If there are no arguments use default constructor, otherwise use the user-given arguments.
		if(args.length == 0){
			table = new Table();
		}else {
			int numberOfPhilosophers = 0;
			long timeOfPhilosophers = 0;
			
			try{
				numberOfPhilosophers = Integer.parseInt(args[0]);
				if(numberOfPhilosophers < 2 || numberOfPhilosophers > 1000){throw new NumberFormatException();}
				timeOfPhilosophers  = Long.parseLong(args[1]);
				if(timeOfPhilosophers < 1 || timeOfPhilosophers > 100000){throw new NumberFormatException();}
			}catch(NumberFormatException e){
				e.printStackTrace();
				System.out.println("Invalid Arguments! Exiting Program!");
				System.exit(0);
			}catch(ArrayIndexOutOfBoundsException e){
				e.printStackTrace();
				System.out.println("Invalid Number of Arguments! Exiting Program!");
				System.exit(0);
			}
			
			table = new Table(numberOfPhilosophers, timeOfPhilosophers);
		}
		try {
			table.begin();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

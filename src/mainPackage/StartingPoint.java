package mainPackage;

public class StartingPoint {

	/*
	 * @param args[0] (int) number of Philosophers
	 * @param args[1] (int) consumption time of Philosophers
	 */
	public static void main (String[] args){
		
		int numberOfPhilosophers = 0;
		long timeOfPhilosophers = 0;
		
		try{
			numberOfPhilosophers = Integer.parseInt(args[0]);
			timeOfPhilosophers  = Long.parseLong(args[1]);
		}catch(NumberFormatException e){
			e.printStackTrace();
			System.out.println("Invalid Arguments! Exiting Program!");
			System.exit(0);
		}
		
		Table table = new Table(numberOfPhilosophers, timeOfPhilosophers);
		table.begin();
	}
}

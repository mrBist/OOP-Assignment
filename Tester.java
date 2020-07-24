import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 

public class Tester {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("The number of players for this game are fixed to 5. Please enter 5 to continue.");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			int num = reader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		int number_of_players = 5;
		
		//create game
		Game game = Game.getInstance(number_of_players);
		
		//instantiate classes
		Moderator mod = new Moderator(game);
		Player player1 = new Player(game,1);
		Player player2 = new Player(game,2);
		Player player3 = new Player(game,3);
		Player player4 = new Player(game,4);
		Player player5 = new Player(game,5);
		//create threads
		Thread moderator = new Thread(mod,"Moderator");
		Thread p1 = new Thread(player1,"Player1");
		Thread p2 = new Thread(player2,"Player2");
		Thread p3 = new Thread(player3,"Player3");
		Thread p4 = new Thread(player4,"Player4");
		Thread p5 = new Thread(player5,"Player5");
		//start the game
		System.out.println("Game Begins");
		
		moderator.start();
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
	}

}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Moderator implements Runnable {
	
	private List<Integer> deck = new ArrayList<Integer>(); //will contain 10 random numbers to be used for the game.
	private Game game;
	boolean winner_found = false;
	private int deck_index = 0;
	
	Moderator(Game game) {
		this.game = game;
		Random random = new Random();
		for(int i=0; i<10; i++) {
			deck.add(random.nextInt(51));
		}
	}
	
	@Override
	public void run() {
		
		//run the thread until either the winner is found or all the tokens are shown.
		while(game.is_game_over == false && deck_index < 10) {
			try {
				synchronized(game.flag1){
					//get a number from the moderator
					game.number_shown = deck.get(deck_index);
					deck_index++;
					System.out.println("Current token: " + game.number_shown);
					game.is_shown = true;
					
					//introduce a lock on shared game so that the token is read by every player
					synchronized(game.flag2) {
						
						//allow all the player threads to read the token released by the moderator
						for(int player_number=1; player_number<=game.num_players; ++player_number) {
							game.player_turn[player_number] = true;
//							System.out.println("here");
							game.flag1.wait();
							game.flag1.notifyAll();
							
						}
						
					}
					
					//check if all the tokens are shown
					if(deck_index == 10)
						game.is_game_over = true;
					
				}
				
				//check if their is a winner
				findWinner();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(int player = 1; player<=game.num_players; player++) {
			System.out.println("Number of tokens matched for Player" + player + " are : " + game.player_matches[player]);
		}
		if(game.is_winner_found == false) {
			System.out.println("No player wins");
		} 
		System.out.println("Game Over");
		
		synchronized(game.flag2){
			Arrays.fill(game.player_turn, true);
			game.number_shown=0;
			game.is_shown=true;
			game.flag2.notifyAll();
		}
		
	}
	
	
	private void findWinner() {
		
		synchronized(game.flag1) {
			for(int i=1; i<=game.num_players; ++i) {
				if(game.winner[i]){
					
					System.out.println("Winner is: Player" + i);
					game.is_game_over = true;
					game.is_winner_found = true;
					
				}
			}
		}
	}

}

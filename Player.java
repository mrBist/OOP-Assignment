import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Runnable {
	
	private Game game;
	private int player_number; //player number or id
	private int tokens_matched; //to keep count of number of matches
	private int [] number_present = new int[51]; //to get O(1) lookup for the announced numbers
	private List<Integer> tokens = new ArrayList<Integer>();
	
	Player(Game current_game, int player_id){
		this.player_number = player_id;
		this.game = current_game;
		tokens_matched = 0;
		Random random = new Random();
		
		for(int i=0; i<10; i++) {
			int value = random.nextInt(51);
			tokens.add(value);
			number_present[value]++;
		}
	}
	
	@Override
	public void run() {
		
		displayTokens(); //show individual player tokens
		
		while(game.is_game_over == false) {
			try {
				synchronized(game.flag1) {
					
					if(!this.game.is_shown || !this.game.player_turn[player_number])
						game.flag1.wait();
					
					if(game.is_game_over)
					{
						return;
					}
					if(number_present[game.number_shown] > 0) {
						tokens_matched++;
						game.player_matches[player_number] = tokens_matched;
						number_present[game.number_shown]--;
					}
					
					if(tokens_matched == 3) {
						game.winner[player_number] = true;
					}
					
					game.player_turn[player_number] = false;
					game.flag1.notifyAll();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void displayTokens() {
		synchronized(game.flag1) {
			System.out.print("Player" + this.player_number +"'s tokens: " );
			
			for(int token : tokens) {
				System.out.print(token + " ");
			}
			System.out.println();
		}
	}
	

}

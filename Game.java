 
public class Game {
	private static Game game;
	public int num_players;
	public boolean [] player_turn; //if true the corresponding player gets its turn
	public boolean [] winner; //true if a particular player wins
	public boolean is_game_over = false; //to represent game state
	public int number_shown = -1;
	public boolean is_shown = false;
	public boolean is_winner_found = false;
	public int [] player_matches; // to store the number of matches each player got
	public Object flag1 = new Object(); //flag1 for concurrency
	public Object flag2 = new Object(); //flag2 for concurrency
	
	//using the singleton pattern
	private Game(int num_players) {
		this.num_players = num_players;
		player_turn = new boolean[num_players+1];
		winner = new boolean[num_players+1];
		player_matches = new int[num_players+1];
	}
	
	public static synchronized Game getInstance(int num_players) {
		if(game == null) {
			game = new Game(num_players);
		}
		return game;
	}	
	
}

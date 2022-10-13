public class Main {
	public static void main(String[] args) {
		
		Connect4 game = new Connect4();
		System.out.println("Example of one game simulation:");
		game.showRandomGame();
		
		System.out.println("Probabilities of Player Winning:");
		game.simulateThousandGames();
	}
}

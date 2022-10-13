import java.util.Random;

public class Connect4 {
	
	int currentPlayer;
	boolean gameOver;
	int[][] board;
	
	
	Connect4(){
		this.currentPlayer = 1;
		this.gameOver = false;
//		Note: Board is a numerical representation of the connect four game board
//		Instead of red and blue pieces we can visualize connect four pieces as X's and O's
//		0 = Empty, 1 = X, -1 = 0
//		initalize 6x7 board with all 0s
		this.board = new int[6][7];
		for(int row=0;row < this.board.length;row++) {
			for(int col=0; col < 7;col++) {
				this.board[row][col] = 0;
			}
	}
}

	void showRandomGame() {
		while(!this.gameOver) {
			char user = '-';
			if(this.currentPlayer == 1) {
				user = 'X';
			}
			else if(this.currentPlayer == -1) {
				user = 'O';
			}
			System.out.println(" 1 2 3 4 5 6 7");
			System.out.println(this.toString()); 
			System.out.println("Player "+user+"'s turn. Enter a column to place a piece in :");
			Random random = new Random();
			int col = random.nextInt(7);
			int row = this.getRowIndice(col);
			if(row < 0) {
				continue;
			}
			this.board[row][col] = this.currentPlayer;
			System.out.println(col+1);
			int state = this.getGameState();
			if(state == 100) {
				System.out.println(this.currentPlayer+" won the game!");
				this.gameOver = true;
			}
			else if(state == -100) {
				System.out.println("The game was a tie!");
				this.gameOver = true;
			}
			if(this.currentPlayer == 1) {
				this.currentPlayer = -1;
			}
			else {
				this.currentPlayer = 1;
			}
		}
	}
	
	int getRowIndice(int column) {
		int row = 0;
		while(row<6) {
			if(this.board[row][column] == 1 || this.board[row][column] == -1) {
				row--;
				return row;
			}
			else if(row == 5 && this.board[row][column] == 0) {
				return row;
			}
			else if(this.board[row][column] == 0) {
				row++;
			}
		}
		return row;
	}
	
	int getGameState() {
		int player = 1;
		boolean tie = false;
		boolean state = false;
		for(int i=0; i<2; i++) {
			for(int row = 0;row < 6;row++) {
				for(int col=0; col < 7; col++) {
					if(this.checkDiagonal(row, col, player) || this.checkVertical(row,col,player) || this.checkDiagonal(row, col, player)) {
						state = true;
					}
				}
			}
			player = -1;
		}
		if(state == false && this.checkTie()) {
			tie = true;
		}
		if(state) {
			return 100;
		}
		else if(tie) {
			return -100;
		}
		else {
			return 0;
		}
	}

	
	boolean checkDiagonal(int row, int column, int player) {
		int ro = row;
		int col = column;
		if(this.board[row][column] == player) {
//			theres two 'matches' variables as a player can win diagonally in two ways
//            one way is a ray traveling from the bottom left direction to the top right direction ('upperRightMathces')
//            the other way is a ray traveling from the top left direction to the bottom right direction ('upperLeftMathces')
            int upperRightMatches = 1;
            int upperLeftMatches = 1;
            
//          these next two while loops are for checking from bottom left to top right
            while(ro > 0 && col < 6) {
            	if(this.board[ro-1][col+1] == player) {
            		upperRightMatches++;
            		ro--;
            		col++;
            	}
            	else {
            		break;
            	}
            }
            ro = row;
            col = column;
            while(ro < 5 && col > 0) {
            	if(this.board[ro+1][col-1] == player) {
            		upperRightMatches++;
            		ro++;
            		col--;
            	}
            	else {
            		break;
            	}
            } 
//          these next two while loops are for checking from bottom right to top left
            ro = row;
            col = column;
            while(ro > 0 && col > 0) {
            	if(this.board[ro-1][col-1] == player) {
            		upperLeftMatches++;
            		ro--;
            		col--;
            	}
            	else {
            		break;
            	}
            }
            ro = row;
            col = column;
            while(ro < 5 && col < 6) {
            	if(this.board[ro+1][col+1] == player) {
            		upperLeftMatches++;
            		ro++;
            		col++;
            	}
            	else {
            		break;
            	}
            }
            
            if(upperRightMatches >= 4 || upperLeftMatches >= 4) {
            	return true;
            }
            else {
            	return false;
            }
		}
		else {
			return false;
		}
	}
	
	boolean checkHorizontal(int row, int column, int player) {
//		'col' is a copy variable that we will manipulate as we need to retain 'column'
		int col = column;
//		this while loop checks matches to the left of the given position
		if(this.board[row][column] == player) {
			int matches = 1;
			while(col>0) {
				if(this.board[row][col-1] == player) {
					matches++;
					col--;
				}
				else {
					break;
				}
			}
			col = column;
//			this while loops checks for matches to the right of the given position
			while(col<6) {
				if(this.board[row][col+1] == player) {
					matches++;
					col++;
				}
			}
			if(matches >= 4) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	boolean checkVertical(int row, int column, int player) {
//		'ro' is a copy variable that we will manipulate as we need to retain 'row'
		int ro = row;
		if(this.board[row][column] == player) {
			int matches = 1;
			while(ro>0) {
				if(this.board[ro-1][column] == player) {
					matches++;
					ro--;
				}
				else {
					break;
				}
			}
			ro = row;
			while(ro<5) {
				if(this.board[ro+1][column] == player) {
					matches++;
					ro++;
				}
			}
			if(matches>=4) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}

	}
	
	boolean checkTie() {
		boolean isTie = true;
		for(int[] row : this.board) {
			for(int column : row) {
				if(column == 0) {
					isTie = false;
				}
			}
		}
		return isTie;
	}
	
	public String toString() {
		String gameRepr = "";
		int lineBreak = 0;
		for(int[] row : this.board) {
			gameRepr += "|";
			for(int col : row) {
				if(col==1) {
					gameRepr += "X" + "|";
				}
				else if(col == -1) {
					gameRepr += "O" + "|";
				}
				else {
					gameRepr += "-"+"|";
				}
			
			}
			if(lineBreak < 5) {
				gameRepr += "\n";

			}
			lineBreak += 1;
	}
		return gameRepr;
}
	
}

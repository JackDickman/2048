package twenty_forty_eight;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class Board {
	private final int ROWS = 4;
	private final int COLUMNS = 4;
	private int[][] board;
	private int score = 0;
	private int s = -1;

	//---------------------------------------------------------------------

	public Board() {
		board = new int[ROWS][COLUMNS];
		initialize();
	}

	//---------------------------------------------------------------------

	public void initialize() {
		Random r = new Random();

		int x = r.nextInt(ROWS * COLUMNS);
		int y = r.nextInt(ROWS * COLUMNS);
		while(y == x) {
			y = r.nextInt(16);
		}

		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				if((i * ROWS) + j == x || (i * ROWS) + j == y) {
					board[i][j] = 2;
				} else {
					board[i][j] = 0;
				}
			}
		}
	}

	//---------------------------------------------------------------------

	public void move() {
		if(s != -1) {
			int[][] temp = new int[ROWS][COLUMNS];

			for(int i = 0; i < ROWS; i++) {
				for(int j = 0; j < COLUMNS; j++) {
					temp[i][j] = board[i][j];
				}
			}

			if(s == KeyEvent.VK_UP) {
				moveUp();
				s = -1;
			} else if(s == KeyEvent.VK_DOWN) {
				moveDown();
				s = -1;
			} else if(s == KeyEvent.VK_LEFT) {
				moveLeft();
				s = -1;
			} else if(s == KeyEvent.VK_RIGHT) {
				moveRight();
				s = -1;
			}

			//if board before move isn't same as board after move, add tile
			if(!Arrays.deepEquals(temp, board)) {
				add();
			}
		}
	}

	//---------------------------------------------------------------------

	public void moveDown() {
		for(int c = 0; c < COLUMNS; c++) {
			//load temp array with non zero elements of column
			int[] a = new int[ROWS];
			int i = 0;
			for(int r = ROWS - 1; r >= 0; r--) {
				if(board[r][c] != 0) {
					a[i] = board[r][c];
					board[r][c] = 0;
					i++;
				}
			}
			//put contents of temp back into board while combining if necessary
			i = 0;
			for(int r = ROWS - 1; i < ROWS && a[i] != 0 && r >= 0; r--) {
				if(r < ROWS - 1 && a[i] == board[r + 1][c]) {
					board[r + 1][c] += a[i];
					score += board[r + 1][c];
					i++;
					if(i < ROWS) {
						board[r][c] = a[i];
						i++;
					}
				} else {
					board[r][c] = a[i];
					i++;
				}
			}
		}
	}

	//---------------------------------------------------------------------

	public void moveRight() {
		for(int r = 0; r < ROWS; r++) {
			//load temp array with non zero elements of row
			int[] a = new int[COLUMNS];
			int i = 0;
			for(int c = COLUMNS - 1; c >= 0; c--) {
				if(board[r][c] != 0) {
					a[i] = board[r][c];
					board[r][c] = 0;
					i++;
				}
			}
			//put contents of temp back into board while combining if necessary
			i = 0;
			for(int c = COLUMNS - 1; i < COLUMNS && a[i] != 0 && c >= 0; c--) {
				if(c < COLUMNS - 1 && a[i] == board[r][c + 1]) {
					board[r][c + 1] += a[i];
					score += board[r][c + 1];
					i++;
					if(i < COLUMNS) {
						board[r][c] = a[i];
						i++;
					}
				} else {
					board[r][c] = a[i];
					i++;
				}
			}
		}
	}

	//---------------------------------------------------------------------

	public void moveUp() {
		for(int c = 0; c < COLUMNS; c++) {
			//load temp array with non zero elements of column
			int[] a = new int[ROWS];
			int i = 0;
			for(int r = 0; r < ROWS; r++) {
				if(board[r][c] != 0) {
					a[i] = board[r][c];
					board[r][c] = 0;
					i++;
				}
			}
			//put contents of temp back into board while combining if necessary
			i = 0;
			for(int r = 0; i < ROWS && a[i] != 0 && r < ROWS; r++) {
				if(r > 0 && a[i] == board[r - 1][c]) {
					board[r - 1][c] += a[i];
					score += board[r - 1][c];
					i++;
					if(i < ROWS) {
						board[r][c] = a[i];
						i++;
					}
				} else {
					board[r][c] = a[i];
					i++;
				}
			}
		}
	}

	//---------------------------------------------------------------------

	public void moveLeft() {
		for(int r = 0; r < ROWS; r++) {
			//load temp array with non zero elements of row
			int[] a = new int[COLUMNS];
			int i = 0;
			for(int c = 0; c < COLUMNS; c++) {
				if(board[r][c] != 0) {
					a[i] = board[r][c];
					board[r][c] = 0;
					i++;
				}
			}
			//put contents of temp back into board while combining if necessary
			i = 0;
			for(int c = 0; i < COLUMNS && a[i] != 0 && c < COLUMNS; c++) {
				if(c > 0 && a[i] == board[r][c - 1]) {
					board[r][c - 1] += a[i];
					score += board[r][c - 1];
					i++;
					if(i < COLUMNS) {
						board[r][c] = a[i];
						i++;
					}
				} else {
					board[r][c] = a[i];
					i++;
				}
			}
		}
	}

	//---------------------------------------------------------------------

	public void add() {
		Random r = new Random();
		int i = r.nextInt(ROWS);
		int j = r.nextInt(COLUMNS);

		while(board[i][j] != 0) {
			i = r.nextInt(ROWS);
			j = r.nextInt(COLUMNS);
		}

		board[i][j] = 2;
	}

	//---------------------------------------------------------------------

	public void print() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				System.out.print(board[i][j] + "  ");
			}
			System.out.println();
		}
	}

	//---------------------------------------------------------------------

	public boolean canPlay() { //true if can make a move
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				if(i >= 0 && i <= 2) {
					if(j >= 0 && j <= 2) {
						//check right and down
						if(board[i][j] == 0 || board[i][j] == board[i][j + 1] || board[i][j] == board[i + 1][j]) {
							return true;
						}
					} else if(j == 3) {
						//check down
						if(board[i][j] == 0 || board[i][j] == board[i + 1][j]) {
							return true;
						}
					}
				} else if(i == 3) {
					if(j >= 0 && j <= 2) {
						//check right
						if(board[i][j] == 0 || board[i][j] == board[i][j + 1]) {
							return true;
						}
					} else if(j == 3) {
						//check self
						if(board[i][j] == 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	//---------------------------------------------------------------------

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			s = e.getKeyCode();
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			s = e.getKeyCode();
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			s = e.getKeyCode();
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			s = e.getKeyCode();
		}
	}

	//---------------------------------------------------------------------

	public int getScore() {
		return score;
	}

	//---------------------------------------------------------------------

	public void set(int x, int r, int c) {
		board[r][c] = x;
	}
	
	//---------------------------------------------------------------------
	
	public void setScore(int x) {
		score = x;
	}

	//---------------------------------------------------------------------

	public int get(int r, int c) {
		return board[r][c];
	}

	//---------------------------------------------------------------------

	public void clear() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				board[i][j] = 0;
			}
		}
	}

	//---------------------------------------------------------------------
}

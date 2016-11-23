package twenty_forty_eight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Play {
	private final static int INITIAL_CAPACITY = 100;
	
	//---------------------------------------------------------------------

	public static void main(String[] args) throws InterruptedException, IOException {
		Board b = new Board();
		Display display = new Display(b);

		JFrame frame = new JFrame("2048");
		frame.setSize(400, 645);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(display);
		frame.setVisible(true);

		while(b.canPlay()) {
			b.move();
			if(b.getScore() > display.getBest()) {
				display.setBest(b.getScore());
			}
			display.repaint();
			Thread.sleep(10);
		}

		leaderboard(b.getScore());

		JOptionPane.showMessageDialog(frame, "Game Over\nScore: " + b.getScore(), "Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(0);
	}

	//---------------------------------------------------------------------
	
	public static void leaderboard(int s) throws IOException {
		Scanner in = new Scanner(new File("Leaderboard_2048.txt"));

		//create array
		int[] leaderboard = new int[INITIAL_CAPACITY];

		//fill array with contents of Leaderboard_2048.txt while sorting
		int size = 0;
		while(in.hasNextInt()) {
			if(size == leaderboard.length - 3) {
				leaderboard = reallocate(leaderboard);
			}
			int next = in.nextInt();
			if(next != 0) {
				int z = find(leaderboard, size, next);
				if(leaderboard[z] != 0) {
					for(int k = size; k >= z; k--) {
						leaderboard[k + 1] = leaderboard[k];
					}
				}
				leaderboard[z] = next;
				size++;
			}
		}

		//find where the score should be placed in the array and add it
		int x = find(leaderboard, size, s);
		for(int j = size; j >= x; j--) {
			leaderboard[j + 1] = leaderboard[j];
		}
		leaderboard[x] = s;

		//put contents of array back into Leaderboard_2048.txt
		File file = new File("Leaderboard_2048.txt");

		try {
			if(file.exists() == false) {
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(file, false));
			for(int n = 0; n <= size; n++) {
				out.append(leaderboard[n] + "\n");
			}
			out.close();
		} catch(IOException e) {}

		in.close();
	}
	
	//---------------------------------------------------------------------

	public static int[] reallocate(int[] l) {
		int[] temp = new int[2 * l.length];
		System.arraycopy(l, 0, temp, 0, l.length);
		return temp;
	}
	
	//---------------------------------------------------------------------

	//return the index for where score should be added to the leaderboard given a sorted (descending order) array
	public static int find(int[] l, int size, int s) {
		int first = 0;
		int last = size;
		
		if(l[first] <= s) {
			return first;
		} else if(l[last] >= s) {
			return last + 1;
		}

		while(first < last) {
			int middle = (last + first) / 2;
			if(l[middle] >= s) {
				first = middle + 1;
			} else if(l[middle] < s) {
				last = middle;
			}
		}

		return first;
	}
	
	//---------------------------------------------------------------------
}

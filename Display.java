package twenty_forty_eight;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Display extends JPanel {
	private Board board;
	private Scanner in;
	private int best;
	
	//---------------------------------------------------------------------
	
	private Color background = new Color(249, 249, 242);
	private Color outline = new Color(170, 160, 155);
	private Color emptysquare = new Color(196, 187, 178);
	private Color two = new Color(239, 239, 232);
	private Color four = new Color(236, 236, 210);
	private Color eight = new Color(255, 178, 102);
	private Color sixteen = new Color(255, 144, 110);
	private Color thirtytwo = new Color(255, 111, 67);
	private Color sixtyfour = new Color(255, 87, 36);
	private Color onetwentyeight = new Color(255, 230, 133);
	private Color twofiftysix = new Color(255, 224, 94);
	private Color fivetwelve = new Color(255, 217, 61);
	private Color tentwentyfour = new Color(255, 207, 13);
	private Color twentyfortyeight = new Color(245, 196, 0);
	private Color fortyninetysix = new Color(255, 119, 119);
	private Color eightyoneninetytwo = new Color(255, 71, 71);

	//---------------------------------------------------------------------

	public Display(Board b, Frame frame) throws FileNotFoundException {
		board = b;
		in = new Scanner(new File("Leaderboard_2048.txt"));
		best = in.nextInt();

		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				board.keyPressed(e);
			}
		});

		addMouseListener(new MouseAdapter() { 
			public void mousePressed(MouseEvent m) { 
				//leaderboard
				if(m.getX() >= 290 && m.getX() <= 380 && m.getY() >= 110 && m.getY() <= 160) {
					Scanner scan;
					try {
						scan = new Scanner(new File("Leaderboard_2048.txt"));
						String s = "";
						int count = 1;
						while(scan.hasNextInt() && count <= 10) {
							s += scan.nextInt() + "\n";
							count++;
						}
						JOptionPane.showMessageDialog(frame, s, "LEADERBOARD", JOptionPane.YES_NO_OPTION);
					} catch (FileNotFoundException e) {}
				}

				//menu
				if(m.getX() >= 180 && m.getX() <= 270 && m.getY() >= 110 && m.getY() <= 160) {
					JOptionPane.showMessageDialog(frame, "    Jack Dickman\nNovember 24, 2016", "MENU", JOptionPane.YES_NO_OPTION);
				}
			}
		}); 

		setFocusable(true);
	}

	//---------------------------------------------------------------------

	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//draw light background
		g.setColor(background);
		g.fillRect(0, 0, 400, 645);

		//draw dark outline part
		g.setColor(outline);
		g.fillRoundRect(20, 240, 360, 360, 10, 10);

		//draw squares
		for(int x = 28; x <= 292; x += 88)  {
			for(int y = 248; y <= 512; y += 88) {
				int z = board.get((y - 200) / 88, x / 88);
				if(z == 0) {
					g.setColor(emptysquare);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  2) {
					g.setColor(two);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  4) {
					g.setColor(four);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  8) {
					g.setColor(eight);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  16) {
					g.setColor(sixteen);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  32) {
					g.setColor(thirtytwo);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  64) {
					g.setColor(sixtyfour);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  128) {
					g.setColor(onetwentyeight);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  256) {
					g.setColor(twofiftysix);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  512) {
					g.setColor(fivetwelve);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  1024) {
					g.setColor(tentwentyfour);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  2048) {
					g.setColor(twentyfortyeight);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  4096) {
					g.setColor(fortyninetysix);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				} else if(z ==  8192) {
					g.setColor(eightyoneninetytwo);
					g.fillRoundRect(x, y, 80, 80, 7, 7);
				}
			}
		}

		//draw yellow box w/ 2048
		g.setColor(new Color(245, 196, 0));
		g.fillRoundRect(20, 20, 140, 140, 10, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.PLAIN, 45));
		g.drawString("2048", 30, 106);

		//draw score box and "SCORE"
		g.setColor(outline);
		g.fillRoundRect(180, 20, 90, 70, 10, 10);
		g.setColor(new Color(230, 224, 220));
		g.setFont(new Font("Arial Black", Font.PLAIN, 17));
		g.drawString("SCORE", 192, 46);

		//draw score
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.PLAIN, 25));
		if(board.getScore() >= 10000) {
			g.setFont(new Font("Arial Black", Font.PLAIN, 21));
			g.drawString(board.getScore() + "", 190, 76);
		} else if(board.getScore() >= 1000) {
			g.drawString(board.getScore() + "", 191, 78);
		} else if(board.getScore() >= 100) {
			g.drawString(board.getScore() + "", 200, 78);
		} else if(board.getScore() >= 10) {
			g.drawString(board.getScore() + "", 207, 78);
		} else {
			g.drawString(board.getScore() + "", 217, 77);
		}

		//draw best box and "BEST"
		g.setColor(outline);
		g.fillRoundRect(290, 20, 90, 70, 10, 10);
		g.setColor(new Color(230, 224, 220));
		g.setFont(new Font("Arial Black", Font.PLAIN, 17));
		g.drawString("BEST", 310, 46);

		//draw best
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.PLAIN, 25));
		if(best >= 10000) {
			g.setFont(new Font("Arial Black", Font.PLAIN, 21));
			g.drawString(best + "", 300, 76);
		} else if(best >= 1000) {
			g.drawString(best + "", 301, 78);
		} else if(best >= 100) {
			g.drawString(best + "", 310, 78);
		} else if(best >= 10) {
			g.drawString(best + "", 317, 78);
		} else {
			g.drawString(best + "", 327, 77);
		}

		//draw menu box and "MENU"
		g.setColor(new Color(255, 163, 101));
		g.fillRoundRect(180, 110, 90, 50, 10, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.PLAIN, 17));
		g.drawString("MENU", 197, 141);

		//draw leaderboard box and "LEADERBOARD"
		g.setColor(new Color(255, 163, 101));
		g.fillRoundRect(290, 110, 90, 50, 10, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.PLAIN, 10));
		g.drawString("LEADERBOARD", 293, 139);

		//draw "Join the numbers and get to the 2048 tile!"
		g.setColor(new Color(111, 102, 93));
		g.setFont(new Font("Arial Black", Font.PLAIN, 15));
		g.drawString("Join the numbers and get to the 2048 tile!", 28, 207);

		//draw numbers
		for(int r = 0; r < 4; r++) {
			for(int c = 0; c < 4; c++) {
				if(board.get(r, c) > 1000) {
					g.setFont(new Font("Arial Black", Font.PLAIN, 25));
					g.setColor(Color.WHITE);
					g.drawString(board.get(r, c) + "", (c * 88) + 33, (r * 88) + 298);
				} else if(board.get(r, c) > 100) {
					g.setFont(new Font("Arial Black", Font.PLAIN, 28));
					g.setColor(Color.WHITE);
					g.drawString(board.get(r, c) + "", (c * 88) + 38, (r * 88) + 300);
				} else if(board.get(r, c) > 10) {
					g.setFont(new Font("Arial Black", Font.PLAIN, 32));
					g.setColor(Color.WHITE);
					g.drawString(board.get(r, c) + "", (c * 88) + 45, (r * 88) + 301);
				} else {
					g.setFont(new Font("Arial Black", Font.PLAIN, 35));
					if(board.get(r, c) == 8) {
						g.setColor(Color.WHITE);
					} else {
						g.setColor(new Color(111, 102, 93));
					}
					if(board.get(r, c) != 0) {
						g.drawString(board.get(r, c) + "", (c * 88) + 55, (r * 88) + 303);
					}
				}
			}
		}
	}

	//---------------------------------------------------------------------

	public int getBest() {
		return best;
	}

	//---------------------------------------------------------------------

	public void setBest(int b) {
		best = b;
	}

	//---------------------------------------------------------------------
}

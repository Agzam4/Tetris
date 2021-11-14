package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Work.File_;

public class Game {

	public int quality = 25; // TODO: quality (25 reccomend)
	
	private int size = 5;
	
	private int fbX = 0;
	private double fbY = 0;
	private int fbWight = 0;
	private int fbXS = 0;
	
	private boolean isGame = true;
	private boolean pause = false;
	private boolean down = false;
	
	private boolean mouseIsPress = false;
	private boolean waitmousedontpress = false;
	private int mouseX = 0;
	private int mouseY = 0;
	
	private int plx = 0;
	private boolean mapFall = false;

	private BufferedImage gameScreen; 
	private boolean[][] map = new boolean[10][20];

	private String[] blocks = new String[7];
	private int fallingBlock = (int) (Math.random()*7);
	private int nextFallingBlock = (int) (Math.random()*7);
	private boolean[][] block = new boolean[4][4];
	private boolean[][] nextBlock = new boolean[4][4];
	private int blw = 4;
	private int blh = 2;
	private int score = 0;
	private boolean turn = false;
	
	private double migalka = 0;
	
	private ArrayList<Integer> goX = new ArrayList<Integer>();
	private ArrayList<Integer> goY = new ArrayList<Integer>();
	private ArrayList<Integer> posX = new ArrayList<Integer>();
	private ArrayList<Integer> posY = new ArrayList<Integer>();
	private ArrayList<Integer> sizeList = new ArrayList<Integer>();
	private ArrayList<Boolean> grav = new ArrayList<Boolean>();
	private ArrayList<Color> colors = new ArrayList<Color>();

	public void Go(JLabel screen/* , JFrame f */) {

		File_ file = new File_();
		for (int i = 0; i < blocks.length; i++) {
//				try {
					try {
					blocks[i] = file.ReadFile("src/Blocks/" + i + ".txt"/*Game.class.getResource("/Blocks/" + i + ".txt").toURI() */);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
//				} catch (URISyntaxException e1) {
//					JOptionPane.showMessageDialog(null, e1.getMessage());
//				}
		}


		Thread t = new Thread() {
			@Override
			public void run() {
				size = 500;
				gameScreen = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
				screen.setIcon(new ImageIcon(gameScreen));
				isGame = true;
//				Dimension dimension = f.getSize();
				while (true) {

					if(isGame) {
						
						goX.clear();
						goY.clear();
						posX.clear();
						posY.clear();
						sizeList.clear();
						colors.clear();
						grav.clear();
						
						score = 0;
						fbY = 0;
						for (int y = 0; y < 20; y++) {
							for (int x = 0; x < 10; x++) {
								map[x][y] = false;//Math.random() > 0.5;//
							}
						}

						fallingBlock = (int) (Math.random()*7);
						nextFallingBlock = (int) (Math.random()*7);
						StringToBlock();
						while (isGame) {

							// SOME LOGIC

							fbY += 0.1;
							
							if(mouseIsPress) {
								if(!(mouseY == -1)) {
//									System.out.println(mouseY);
									if(mouseY < 100) {
										down = true;
										mouseY = -1;
									}
								}
							}
							
							
							if(down) {
								down = false;
								fbY = Math.ceil(fbY);
							}
							
							// DRAW GAME

							BufferedImage game = getGame();
							Graphics graphics = gameScreen.getGraphics();
							Graphics2D g = (Graphics2D) graphics;
							

//							if(!(dimension == f.getSize())) {
//								dimension = f.getSize();
//								int width = (int) dimension.getWidth();
//								int height = (int) dimension.getHeight();
////
////								screen.setSize(2000,2000);//dimension);
//
//								if(height < width) {
//									size = height-4;
//								}else {
//									size = width-4;
//								}
//								
//								g.setColor(Color.BLACK);
//								g.fillRect(0, 0, width, height);
//								
////								gameScreen = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
//								screen.repaint();
//							}
							
							g.drawImage(game, 0, 0, size, size, null);

//							Print(g, screen);
							
							

							// SIZE CHANGE

							
							g.dispose();
							screen.repaint();
							

							migalka += 0.05; // TODO: thread end

							// TIMING
							
							try {
								Thread.sleep(25);
							} catch (InterruptedException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
							if(pause) {
								for (int i = 0; i < 25; i++) {
									Graphics graphics2 = gameScreen.getGraphics();
									Graphics2D g11 = (Graphics2D) graphics2;
									g11.setColor(new Color(255,255,255,i));
									g11.fillRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
									g11.dispose();
									screen.repaint();
									try {
										Thread.sleep(15);
									} catch (InterruptedException e) {
										JOptionPane.showMessageDialog(null, e.getMessage());
									}
								}
								Graphics graphics1 = gameScreen.getGraphics();
								Graphics2D g1 = (Graphics2D) graphics1;
								g1.setColor(Color.BLACK);
								g1.setFont(new Font("Arial", Font.BOLD, 50));
								g1.drawString("Pause", 180, 100);
								g1.setFont(new Font("Arial", Font.PLAIN, 15));
								g1.drawString("Press ESC or click to continue", 150, 130);
								g1.dispose();
								
								screen.repaint();
								while (pause) {
									try {
										Thread.sleep(25);
									} catch (InterruptedException e) {
										JOptionPane.showMessageDialog(null, e.getMessage());
									}
								}
							}
						}
						Graphics graphics = gameScreen.getGraphics();
						Graphics2D g = (Graphics2D) graphics;
//						g.setColor(new Color(255,255,255,75));
						for (int i = 0; i < 25; i++) {
							Graphics graphics2 = gameScreen.getGraphics();
							Graphics2D g2 = (Graphics2D) graphics2;
							g2.setColor(new Color(255,255,255,i));
							g2.fillRect(0, 0, gameScreen.getWidth(), gameScreen.getHeight());
							g2.dispose();
							screen.repaint();
							try {
								Thread.sleep(15);
							} catch (InterruptedException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
						g.setFont(new Font("Arial", Font.PLAIN, 50));
						g.setColor(Color.BLACK);
						g.drawString("Game Over", 120, 100);
						g.setFont(new Font("Arial", Font.PLAIN, 25));
						String str = "Score: " + score;
						g.drawString(str, 250 + str.length()*-5, 140);
						g.setFont(new Font("Arial", Font.PLAIN, 12));
						g.drawString("PRESS SPASE OR CLICK TO RESTART", 140, 200);
						g.dispose();
						screen.repaint();
					}
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}
		};
		t.start();


		// TODO: Listeners
		
		screen.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					plx = 1;
					break;
				case KeyEvent.VK_LEFT:
					plx = -1;
					break;
				case KeyEvent.VK_DOWN:
					down = true;
					break;
				case KeyEvent.VK_SPACE:
					isGame = true;
					break;
				case KeyEvent.VK_ESCAPE:
					pause = !pause;
					break;
				case KeyEvent.VK_DELETE:
					isGame = false;
					break;
				case KeyEvent.VK_R:
					turn = true;
					break;
				default:
					break;
				}
//				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
//				}
//				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
//				}
//				if(e.getKeyCode() == KeyEvent.VK_DOWN) {
//				}
//				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
//				}
//				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
//					pause = !pause;
//				}
//				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
//				}
//				if(e.getKeyCode() == KeyEvent.VK_R) {
//				}
			}
		});
		
		screen.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				mouseIsPress = true;
			}
		});
		screen.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseIsPress = false;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mouseIsPress = true;
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				isGame = true;
				pause = false;
			}
		});
	}


	private BufferedImage getGame() {

		BufferedImage game = new BufferedImage(20*quality, 20*quality, BufferedImage.TYPE_INT_RGB);

		Graphics graphics = game.getGraphics();
		Graphics2D g = (Graphics2D) graphics;

		Print(g, quality);

		g.setColor(Color.LIGHT_GRAY);
		//		g .drawRect(0, 0, 100, 100);
		
		g.setColor(Color.WHITE);
//		System.out.println(g.getFont().getSize());
		g.setFont(new Font(g.getFont().getFamily(), Font.PLAIN, (int) (0.5 * quality)));
		g.drawRect(5*quality, 0, 10*quality, 20*quality-1);

		for (int x = 0; x < 10; x++) {
			
			if(Math.random() > 0.75) {
				int gy = getPlaceToFall(x,0);
				int mx = (5*quality) + (x*quality);
				goX.add(0);
				goY.add(-(int)((Math.random()*(quality/5))));
				posX.add((int) (mx + Math.random()*(quality)));
				posY.add(gy*quality);
				grav.add(false);
				sizeList.add(1);
				colors.add(new Color(255,255,255,50));
			}
			
			for (int y = 0; y < 20; y++) {
				if(map[x][y]) {
					g.setColor(Color.WHITE);
					g.fillRect((5*quality)+(x*quality), y*quality, quality, quality);
				}
			}
		}
		

		boolean block = false;
		
		if(fbX + fbXS < 0) {
			fbX = 0 + fbXS;
		}

		if(fbX + fbWight - fbXS > 9) {
			fbX = 9 - fbWight + fbXS;
		}
		
		
		ArrayList<Integer> blockList = new ArrayList<Integer>();
		blockList.clear();
		
		for (int y = 0; y < blh; y++) {
			for (int x = 0; x < blw; x++) {
//				try {
				
				if(this.block[x][y]) {
					int idY = (int)(fbY);
					g.fillRect((5*quality) + ((fbX+x)*quality),
							(int) (5*quality + ((fbY+y-5)*quality)), quality, quality);
					
					// TODO: greadient

					
					if(-1 == blockList.indexOf(x)) {
						
						int gy = getPlaceToFall(fbX+x, idY+y);
						
						if(Math.random() > 0.75) {
							goX.add(0);
							goY.add((int) (quality/-5)) ;
							posX.add((int) ((5*quality) + ((x+fbX)*quality + Math.random()*quality)));
							posY.add((int) (5*quality + ((fbY+y-4)*quality)));
							grav.add(false);
							sizeList.add(1);
							colors.add(new Color(255,255,255,75));
						}
						
						int mx = (5*quality) + ((fbX+x)*quality);
						
						
						GradientPaint gradient = new GradientPaint(
								mx,
								(int) (gy*quality), // y1
								new Color(255,255,255,150), mx,
								(float) (gy-1)*quality, // y2
								new Color(255,255,255,0));
						
						g.setPaint(gradient);
						g.fillRect(mx, (gy-1)*quality, (int) (quality), quality);
						
						
						g.setColor(Color.WHITE);
						
						
						if(Math.random() > 0.4) {
							goX.add(0);
							goY.add(-(int)((Math.random()*(quality/5))));
							posX.add((int) (mx + Math.random()*(quality)));
							posY.add(gy*quality);
							grav.add(false);
							sizeList.add(1);
							colors.add(new Color(255,255,255,(int) (Math.random()*75 + 25)));
						}
					}

					blockList.add(x);
					
//					g.drawRect(mx, (gy-1)*quality, (int) (quality), quality);

					if(!block) {
						try {
							block = map[fbX+x][idY+1+y];
						} catch (ArrayIndexOutOfBoundsException e) {
							if(fbX+x < 0) {
								fbX = x;
							}else if(fbX+x > 9){
								fbX = 9-x;
							}else {
								block = true;
							}
						}

//						if(block) {
//							for (int i = 0; i < 26; i++) {
//								goX.add((int)((Math.random()*quality)-quality/2));
//								goY.add((int)((Math.random()*(quality/2))-quality));
//								posX.add((5*quality) + ((fbX+x)*quality) +
//										(int)((Math.random()*quality)-quality/2));
//								posY.add((int) (5*quality + ((fbY+y-5)*quality)));
//								sizeList.add(1);
//								colors.add(new Color(255,255,255,200));
//							}
//						}
					}
					
					if(idY+1+y < 20) {
						if(map[fbX+x][idY+1+y]) {
							for (int i = 0; i < 26; i++) {
								goX.add((int)((Math.random()*quality)-quality/2));
								goY.add((int)((Math.random()*(quality/2))-quality));
								posX.add((5*quality) + ((fbX+x)*quality) +
										(int)((Math.random()*quality)-quality/2));
								posY.add((int) (5*quality + ((fbY+y-5)*quality)));
								grav.add(true);
								sizeList.add(1);
								colors.add(new Color(255,255,255,200));
							}
						}
					}else {
						for (int i = 0; i < 26; i++) {
							goX.add((int)((Math.random()*quality)-quality/2));
							goY.add((int)((Math.random()*(quality/2))-quality));
							posX.add((5*quality) + ((fbX+x)*quality) +
									(int)((Math.random()*quality)-quality/2));
							posY.add((int) (5*quality + ((fbY+y-5)*quality)));
							grav.add(true);
							sizeList.add(1);
							colors.add(new Color(255,255,255,200));
						}
					}
// TODO: DEBUG				
					//}else {
//					g.drawRect((5*quality) + ((fbX+x)*quality),
//					(int) (5*quality + ((fbY+y-5)*quality)), quality, quality);
				}
//				} catch (ArrayIndexOutOfBoundsException e) {
//					System.err.println("##################");
//				}
			}
		}

		// TEXT

		g.drawString(" Score:", (int) (15.65*quality), (2*quality));
		g.drawString(" " + score, (int) (15.65*quality), (int)  (2.75*quality));
		
		// NEXT BLOCK
		
		g.drawString(" Next Block:", (int) (15.65*quality), 4*quality);
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(nextBlock[x][y])
					g.fillRect((int) ((16*quality) + 0.5*x*quality), (int) (5*quality + (0.5*y*quality)),
							(int) Math.floor(quality/1.9),
							(int) Math.floor(quality/1.9));
					
			}
		}
		
		// GRADIENT OF BOUNDS
		
		Color colorM = new Color(255,255,255,(int) (Math.cos(migalka)*25 + 50));
		
		GradientPaint gradient = new GradientPaint(
				0, // x1
				0, // y1,
				colorM, // x2
				quality*2,
				0, // y2
				new Color(255,255,255,0));
		
		g.setPaint(gradient);
		g.fillRect(0, 0, (quality)*2, quality*20);
		
		gradient = new GradientPaint(
				(quality)*20, // x1
				0, // y1,
				colorM, 
				quality*18,// x2
				0, // y2
				new Color(255,255,255,0));
		g.setPaint(gradient);
		
		g.fillRect((quality)*18, 0, (quality)*2, quality*20);
		
		
		g.setColor(Color.WHITE);
		
		// BUTTON <<TURN ON RIGHT>>

		g.drawRect((int) (16.1*quality), (int) (17.1*quality), 2*quality, 2*quality);

		
		g.setFont(new Font(g.getFont().getFamily(), Font.PLAIN, (int) (1.2 * quality)));
		g.drawString("\u21BB", (int) (16.5*quality), (int)(18.5*quality));
		
		if(!mouseIsPress) {
			waitmousedontpress = true;
		}
		
		if(mouseX > 16.1*quality && mouseX < 18.1*quality &&
				mouseY > 17.1*quality && mouseY < 19.1*quality) {
			g.setColor(new Color(255,255,255,150));
			g.fillRect((int) (16.1*quality), (int) (17.1*quality), 2*quality, 2*quality);
			if(mouseIsPress && waitmousedontpress) {
				waitmousedontpress = false;
				turn = true;
			}
		}else {
			waitmousedontpress = true;
		}
		
		// LOGIC
		


		if(block) {
			
			for (int y = 0; y < blh; y++) {
				for (int x = 0; x < blw; x++) {
					int idY = (int)(fbY);
					if(this.block[x][y]) {
						try {
							map[fbX+x][idY+y] = true;
						} catch (ArrayIndexOutOfBoundsException e) {

						}
					}
				}
			}

			if(fbY < 1) {
				isGame = false;
			}
			
			fbY = 0;
//			fbX = 1;
			// TODO: boom!
			fallingBlock = nextFallingBlock;
			nextFallingBlock = (int) (Math.random()*7);
			StringToBlock();
			DetectLine();
			
		}else if(!mapFall) {
			
			if(plx == 0 && mouseIsPress) {
				if(mouseX < 15*quality) {
					if(mouseX > 5*quality) {
						if(!(mouseX == -1)) {
							if(mouseX > (fbX+fbWight)*quality + 5*quality) {
								plx = 1;
							}else if(mouseX < (fbX+fbXS)*quality + 5*quality){
								plx = -1;
							}
						}
					}
				}
			}
			
			int plx2 = plx;
			fbX += plx;
			plx = 0;


			block = false;

			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					if(this.block[x][y]) {
						int idY = (int)(fbY);
						if(!block) {
							try {
								block = map[fbX+x][idY+1+y];
							} catch (ArrayIndexOutOfBoundsException e) {
								block = true;
							}
						}
					}
				}
			}
			
			if(block) {
				fbX -= plx2;
			}
			
			if (turn) {
				turnRight();

				block = false;
				
				for (int y = 0; y < 4; y++) {
					for (int x = 0; x < 4; x++) {
						if(this.block[x][y]) {
							int idY = (int)(fbY);
							if(!block) {
								try {
									block = map[fbX+x][idY+1+y];
								} catch (ArrayIndexOutOfBoundsException e) {
									block = true;
								}
							}
						}
					}
				}
				
				if(block) {
					for (int i = 0; i < 3; i++) {
						turnRight();
					}
				}
			}
		}

		turn = false;
		return game;
	}

	private void DetectLine() {
		// DetectLine

		int xScore = 0;
		
		for (int y = 0; y < 20; y++) {
			boolean full = true;
			for (int x = 0; x < 10; x++) {
				if(full) {
					full = map[x][y];
				}
			}
			
			
			if(full) {
				xScore ++;
				
				// Shift

				for (int y1 = y; y1 > 0; y1--) {
					for (int x = 0; x < 10; x++) {		
						map[x][y1] = map[x][y1-1];
					}
				}	
				
				for (int x = 0; x < 20; x++) {
					for (int yy = 0; yy < 6; yy++) {
						goX.add((int) (Math.random()*quality/5)-quality/10);
						goY.add((int) (quality*((Math.random()*2)-1)));
						posX.add((int) ((5*quality) + ((x)*quality*0.5)));
						posY.add((int) (5*quality + ((fbY+y-5+yy)*quality)));
						grav.add(true);
						sizeList.add(1);
						colors.add(new Color(255,255,255,200));
					}
				}
				
//				for (int x = 0; x < 10; x++) {
//						map[x][y] = false;
//				}
//				for (int x = 0; x < 10; x++) {
//					map[x][y] = false;
//					block =  new boolean[10][20-y];
////					System.out.print(y);
//					blw = 5;
//					blh = y-1;
//					for (int y1 = 1; y1 < y; y1++) {
//						for (int x1 = 1; x1 < 9; x1++) {
//							if(map[x1][y1]) {
//								System.out.print("#");
//							}else {
//								System.out.print("_");
//							}
//							try {
//								
//							} catch (Exception e) {
//								block[x1][y1] = map[x1][y1];
//								map[x1][y1] = false;
//							}
//						}
//						System.out.println();
//					}
//					fbY = 0;
//					fbX = 1;
//					fbWight = 9999;
//					mapFall = true;
//				}
			}
		}
		
		int[] pl = new int[] {0,100,300,700,1500};
		if(xScore > 0)
			score += pl[xScore];
	}
	
	private void StringToBlock() {
		block =  new boolean[4][4];
		mapFall = false;
		char[] arr = (blocks[fallingBlock] + "________").toCharArray();
		char[] nextArr = (blocks[nextFallingBlock] + "________").toCharArray();
		//		System.out.println(fallingBlock);
		fbWight = 0;
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 4; x++) {
				block[x][y] = arr[x + y*4] == '#';
				nextBlock[x][y] = nextArr[x + y*4] == '#';
				if(x > fbWight && block[x][y]) {
					fbWight = x;
				}
			}
		}
		
		fbXS = 0;
		blw = 4;
		blh = 4;
	}


	private void turnRight() {

		boolean[][] block2 = new boolean[4][4];
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				block2[x][y] = block[x][y];
			}
		}
		
		fbWight = 0;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				block[3-x][y] = block2[y][x];
				if(x > fbWight && block[x][y]) {
					fbWight = x;
				}
			}
		}


		Cutting();


		/*
		 * #__
		 * ##_
		 * #_#
		 */
	}
	
	private void Cutting() {
		fbWight = 0;
		fbXS = 0;

		for (int x = 0; x < 4; x++) {
			boolean hasblock = false;
			for (int y = 0; y < 4; y++) {
				if(!hasblock) {
					hasblock = block[x][y];
				}
			}
			if(hasblock) {
				fbWight++;
			}else{
				fbXS++;
			}
		}
	}
	
	public void Print(Graphics2D g, int quality) {
		
		boolean gravitation = true;
		boolean bounds = false;
		
        
        try {
     	   for (int i = 0; i < posX.size(); i++) {
     		   
     		   gravitation = grav.get(i);
     		   
     		   g.setColor(colors.get(i));//225 - lights.get(i),225 - lights.get(i),225 - lights.get(i)));
     		   g.fillRect(posX.get(i), posY.get(i), (int) (sizeList.get(i)*0.1*quality), (int) (0.1*quality*sizeList.get(i)));
     		   posX.set(i, posX.get(i) +  (int)(goX.get(i)/2));
     		   posY.set(i, posY.get(i) + (int)(goY.get(i)/2));
     		   colors.set(i, new Color(255,255,255, (int) (colors.get(i).getAlpha()-1)));
     		   
          	  if(colors.get(i).getAlpha() < 25) {
          		  	colors.remove(i);
          		  	sizeList.remove(i);
          		  	posY.remove(i);
          			posX.remove(i);
          			goY.remove(i);
          			goX.remove(i);
					grav.remove(i);
          	  }
          	  if(gravitation){
          		  {goY.set(i, goY.get(i) + (quality/5));}

          		  if(posY.get(i) > 20*quality && bounds) {
          			  goY.set(i,(goY.get(i)/-2));
          		  }
          		  if(posX.get(i) < 5*quality && bounds) {
          			  goX.set(i,goX.get(i)/-2 +quality) ;
          		  }
          		  if(posX.get(i) > (5*quality) + 10*quality && bounds) {
          			  goX.set(i,goX.get(i)/-2  -quality);
          		  }
          	  }
			}
        } catch (IndexOutOfBoundsException e) {
        }
	}
	
	private int getPlaceToFall(int xx, int yy) {
		int x = xx;
		int y = yy;
		
		if(x < 0) {
			x = 0;
		}

		if(x > 9) {
			x = 9;
		}
		
		if(y > 19) {
			return 20;
		}
		while (!map[x][y]) {
			y++;
			if(y > 19) {
				return 20;
			}
		}
		return y;
	}
	
}

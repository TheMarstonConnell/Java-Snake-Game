import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import hsa2.GraphicsConsole;

public class Snake {
	//screen size
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//window & snake size
	static int size = 1000;
	static int box = size / 20;
	static Rectangle boost = new Rectangle(size / 2, size / 2, box , box);

	//game rules
	static Timer timer = new Timer(0, null);
	static GraphicsConsole c;
	
	//direction
	static byte dir = 3;
	
	//points
	static int points = 0;
	
	
	public static void endGame() {
		timer.stop();
		
		c.clear();
		c.setColor(Color.red);
		c.setFont(new Font("Lucida Console", Font.PLAIN, 32));
		c.drawString("Game Over", size / 2 - 85, size / 2);
		c.setColor(Color.white);
		c.setFont(new Font("Lucida Console", Font.PLAIN, 24));
		c.drawString("Points: " + points, size / 2 - 65, size / 2 + 50);

	}
	
	public static void main(String[] args) throws InterruptedException {
		
		size = screenSize.height - 80;
		while((size % 20) != 0) {
			size -= 1;
		}
		
		
		
		c = new GraphicsConsole(size, size);
		c.setLocationRelativeTo(null);
		c.setBackgroundColor(Color.black);
		c.clear();
		
		c.setFont(new Font("Lucida Console", Font.PLAIN, 64));
		c.setColor(Color.GREEN);
		c.drawString("Snake", size / 2 - 100, size / 2);
		c.setFont(new Font("Lucida Console", Font.PLAIN, 48));
		c.setColor(Color.white);
		c.drawString("Get Ready...", size / 2 - 180, size / 2 + 50);
		c.drawString("Use: ◄ ▲ ► ▼", size / 2 - 182, size / 2 + 100);
		Thread.sleep(4000);
		
		
		ArrayList<Rectangle> body = new ArrayList<Rectangle>();

		int length = 15;
		for(int y = 0; y < length; y ++) {
			body.add(new Rectangle(box * y, 0, box, box));
		}

		
		
		ActionListener taskPerformer = new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				//...Perform a task...
				for(int g = body.size() - 1; g >= 0; g --) {
					//					System.out.println(g);
					if(g != 0) {
						//						System.out.println(body.get(g).x + ", " + body.get(g).y + " <- " + body.get(g-1).x + ", " + body.get(g-1).y);
						body.get(g).y = body.get(g - 1).y;
						body.get(g).x = body.get(g - 1).x;
					}


				}
//				System.out.println();
				//moving directions
				if(dir == 0) {
					body.get(0).x -= body.get(0).width;
				}else if(dir == 1) {
					body.get(0).x += body.get(0).width;
				}else if(dir == 2) {
					body.get(0).y -= body.get(0).height;
				}else if(dir == 3) {
					body.get(0).y += body.get(0).height;
				}


				c.clear();
				for(int z = 0; z < body.size(); z ++) {
					c.setColor(Color.white);
					c.fillRect(body.get(z).x, body.get(z).y, body.get(z).width, body.get(z).height);
					c.setColor(Color.black);
					c.drawRect(body.get(z).x, body.get(z).y, body.get(z).width, body.get(z).height);
				}
				c.setColor(Color.blue);
				c.fillRect(boost.x, boost.y, boost.width,boost.height);
				
				c.setFont(new Font("Lucida Console", Font.PLAIN, 18));
				c.setColor(Color.white);
				c.drawString("Points:" + points, 10, 20);
				
				
				if(body.get(0).intersects(boost)) {
					points += 1;
					body.add(new Rectangle(0, 0, box, box));
					boost.x = box * ((int) (Math.random() * (size / box)));
					boost.y = box * ((int) (Math.random() * (size / box)));
				}
				for(int h = 1; h < body.size(); h ++) {
					if(body.get(0).intersects(body.get(h))) {
						endGame();

					}
				}
				if(body.get(0).x + box > size) {
					endGame();
				}
				if(body.get(0).x < 0) {
					endGame();
				}
				if(body.get(0).y + box > size) {
					endGame();
				}
				if(body.get(0).y < 0) {
					endGame();
				}
				
				
			}
		};
		timer = new Timer(100 ,taskPerformer);
		timer.setRepeats(true);
		timer.start();

		while(true) {
			//setting direction
			if (c.getKeyCode() == 37) {
				//				System.out.println("Left");
				dir = 0;
			}
			if (c.getKeyCode() == 39) {
				//				System.out.println("Right");
				dir = 1;
			}
			if (c.getKeyCode() == 38) {
				//				System.out.println("Up");
				dir = 2;
			}
			if (c.getKeyCode() == 40) {
				//				System.out.println("Down");
				dir = 3;
			}


		}



	}
}

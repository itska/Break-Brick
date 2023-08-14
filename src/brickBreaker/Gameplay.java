package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

// This class will be panel to run the game 
// Gameplay extends JPanel to make Gameplay as Panel 
//It implements interfaces:  KeyListener, ActionListener
@SuppressWarnings("serial")
public class Gameplay extends JPanel implements KeyListener,ActionListener{
/*
 * KeyListner is addeed to detect the arrow key pressed when moving the slider and
 * ActionListner is for moving the ball
 */
	//adding few properties
	
	
	private boolean play  =false;//so that when game starts it should not start in playing state by itself
	private int score=0;
	private int totalBricks=21; //can be changed, 21 is for 7*3 grid
	
	private Timer timer;//for setting the time of ball so how fast it should move
	private double delay =0.1;//speed we are going to give to timer, which will be in INT
	
	//setting properties for x axis and y axis of bar and ball both
	
	
	private int playerX=310;//starting position of slider
	private int ballposX=120;//starting position of ball on x axis
	private int ballposY=350;//starting position of ball on y axis
	//setting direction for the ball
	private int ballXdir=-1;
	private int ballYdir=-2;
	
	private MapGenerator map;
	
	
	
	public Gameplay()
	{
		map=new MapGenerator(3,7);
		addKeyListener(this);
		//in order  to work with keyListener we need to add 
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		//creating object for timer , speed for which is in delay variable and context is this
		timer= new Timer((int) delay,this);
		timer.start();
	}
	
	//Graphics is inbuilt class to draw different shapes like slider , ball, tiles
	public void paint(Graphics g)
	{
		//background
		g.setColor(Color.black);
		//creating rectangle for background
		g.fillRect(1,1,692,592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0,0,3,592);
		g.fillRect(0,0,692,3);
		g.fillRect(691,0,3,592);
		//no boundary for down because we need to end the game when ball falls to down
	
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("SCORE: "+score, 50, 30);
		
		
		
		
		//paddle
		g.setColor(Color.CYAN);
		g.fillRect(playerX,550,100,8);
		
		// the ball
		g.setColor(Color.green);
		g.fillOval(ballposX,ballposY,20,20);
		
		//to detect if game has finished
		if(totalBricks<=0)
		{
			play=false;
			ballXdir=0;
			ballYdir=0;
			
			//ending game subscript
			g.setColor(Color.MAGENTA);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("YOU WON, SCORE IS: "+score, 160, 300);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart ", 240, 360);
		}
		
		
		//code for game over
		if(ballposY>570)
		{
			play=false;
			ballXdir=0;
			ballYdir=0;
			
			//ending game subscript
			g.setColor(Color.MAGENTA);
			g.setFont(new Font("serif",Font.BOLD,30));
			g.drawString("GAME OVER, SCORE IS: "+score, 160, 300);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Press Enter to Restart ", 240, 360);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();//game starts
		if(play)//checks if variable play is true, that is either right or left arrow key is pressed
		{
			
			//for detecting paddle when it comes in contact with ball
			//first we need to create rectangle around the ball so as to detect the intersection of ball and paddle
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8)))
			{
				ballYdir=-ballYdir;
			}	
			
			//for detecting brick interaction with ball
			//first let's iterate every brick
			//adding label so as to break directly outside
		A:	for(int i=0;i<map.map.length;i++)
			{//one map is for map generator object and second map is variable map [][] in map generator class 
				for(int j=0;j<map.map[0].length;j++)
				{
					//if >0 then detect intersection
					if(map.map[i][j]>0)
					{
						//for intersection we need to know the  position of ball and position of brick
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int  brickHeight=map.brickHeight;
						
						//lets create the rectangle  around the brick
						Rectangle  rect = new Rectangle(brickX,brickY,brickWidth,brickHeight);
						//creating rectangle around  the ball to detect intersection
						Rectangle ballRect= new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						
						//checking if its intersect or not
						if(ballRect.intersects(brickRect))
						{
							//if it intersects then we need to call a function and change value to  0
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5; 
							
							//lets add check for left and right intersection
							if(ballposX+19<=brickRect.x||ballposX+1>=brickRect.x+brickRect.width)
							{
								//moving ball to opposite direction
								ballXdir=-ballXdir;
							}
							else
							{
								//moving ball to top or bottom direction
								ballYdir=-ballYdir;
							}
							break A;
							//breaking outside label
						}
					}
				}
			}
			
			
			//need to detect if ball is touching top,left,right or down
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			
			if(ballposX<0)//for left border
			{
				ballXdir=-ballXdir;
			}
			if(ballposY<0)//for top border
			{
				ballYdir=-ballYdir;
			}
			if(ballposX>670)//for right boundary
			{
				ballXdir=-ballXdir;
			}
			
		}	
		repaint();
		//it will recall paint method and draw each and everything again	
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		//if detected a right key being pressed
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			//checking if its not outside the panel
			//if  it is then keep it to border itself
			if(playerX>=600)
			{
				playerX=600;
			}
			else
			{
				moveRight();
			}
			
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			//checking if its not outside the panel
			//if  it is then keep it to border itself
			if(playerX<10)
			{
				playerX=10;
			}
			else
			{
				moveLeft();
			}
			
			
		}
		
		//adding event for pressing ENTER key
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(!play)//when game is over and play=false
			{
				//AGAIN SETTING DEFAULT
				play=true;
				ballposX=120;
				ballposY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=310;
				score=0;
				totalBricks=21;
				map=new MapGenerator(3, 7);
				
				repaint();
			}
			
		}
		
	}
	
	public void moveRight()
	{
		play=true;
		playerX+=20;//if its pressed right then it  should move 20  pixels to the right side
	}
	public void moveLeft()
	{
		play=true;
		playerX-=20;//if its pressed left then it  should move 20  pixels to the left side
	}
	
	

	
}

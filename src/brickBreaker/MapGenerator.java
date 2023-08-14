package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

//to create bricks
public class MapGenerator {
	public int map[][]; //contains all the bricks
	public int brickWidth;
	public int brickHeight;
	//number of rows and columns to be generated for the particular number of bricks
	public MapGenerator(int row,int col){
		 map=new int[row][col];
		 //traversing through row
		 for(int i=0;i<map.length;i++)
		 {
			 //iterating through columns
			 for(int j=0;j<map[0].length;j++)
			 {
				 //1 will detect that this particular brick has not been intersected by ball 
				 //if i don't want  to draw any panel then it can be changed to zero
				 map[i][j]=1;
			 }
		 }
		 brickWidth= 540/col;
		 brickHeight=150/row;
	}
	//when this function is called bricks will be drawn on the particular position where there is a value of 1
	public void draw(Graphics2D g)
	{
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				if(map[i][j]>0)//then create the  brick at the particular position
				{
					g.setColor(Color.white);
					g.fillRect(j* brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
				
					//creating blackborder for panel
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j* brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
				
				}
			}
		}
		
		
	}
	//for detecting collision with brick
	public void setBrickValue(int value,int row, int col)
	{
		map[row][col]=value;
		
		
	}
	
}

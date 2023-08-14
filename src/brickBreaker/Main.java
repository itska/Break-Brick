package brickBreaker;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		//JFRAME 
		//Outer box, minimize icon, cross icon, built in class
		JFrame obj = new JFrame();
		//object of panel
		Gameplay gameplay = new Gameplay();
		//setting properties for jframe
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		
		
	}
}

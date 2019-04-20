// Arthur: Demetrios Doumas          12/5/14
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Base.SQLOperations;
import Entities.Album;
import Entities.Artist;
import Entities.Song;


public class TestGUI 
{

	public static void main(String[] args) throws IOException 
	{				
		
		JFrame visual = new JFrame("Software Design Final Project");
		visual.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		gui g = new gui(visual);		
		visual.setSize(800,400);	
		visual.setVisible(true);       // "this" Frame shows

	}

}

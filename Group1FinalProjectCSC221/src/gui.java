// Arthur: Demetrios Doumas          12/5/14
// More information below for gui:
//http://www.ntu.edu.sg/home/ehchua/programming/java/j4a_gui.html#zz-8.1
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import Base.SQLOperations;
import Entities.Album;
import Entities.Artist;
import Entities.Song;

public class gui extends JPanel implements ActionListener
{
	public String[] searchCat = new String[] {"Artist", "Album", "Song"};
	public JComboBox<String> menu = new JComboBox<>(searchCat);
	public JMenuBar menuBar= new JMenuBar();
	public Button Search=new Button("Search");;
	public JTextField textbox= new JTextField("");
		
	private JFrame frame;	
	
	public gui(JFrame window)
	{
		frame = window;		
		Search.addActionListener(this);
		window.setJMenuBar(menuBar);
		menuBar.add(menu);
		menuBar.add(textbox);
		menuBar.add(Search);   	
	}	
		
	
	// when Search button is clicked
	//Search function goes here
	public void actionPerformed(ActionEvent e) 
	{		
		String Category= (String) menu.getSelectedItem();
		String str = textbox.getText(); //read from txt box 			
		
		/*
		 	Search Function call goes here and stores result into ArrayList called rowData
		  
		 */
		
		SQLOperations.ClearAllTable();
		// song usage example
		Song beatles1 = new Song("Hello Goodbye", 3.45, 1, "John Lennon", "The Beatles");
		ArrayList<Song> mmtSongs = new ArrayList<Song>();
		mmtSongs.add(beatles1);
		
		// album usage example
		Album magicalMysteryTour = new Album(1, "Magical Mystery Tour", "The Beatles", 1900, mmtSongs, null);
		ArrayList<Album> beatlesAlbums = new ArrayList<Album>();
		beatlesAlbums.add(magicalMysteryTour);
		
		
		// artist usage example
		Artist theBeatles = new Artist("The Beatles", beatlesAlbums);
		ArrayList<Artist> artists = new ArrayList<Artist>();
		artists.add(theBeatles);
		
		
		
		
		/***/
		theBeatles.Save();
		
		magicalMysteryTour.setpathName("mmt.jpg");
		try {
			magicalMysteryTour.setAlbumImage();
		} catch (SQLException | FileNotFoundException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		/***/
		
		
		
		MusicSearch search = new MusicSearch();
		search.setArtists(artists);
		
		
		ArrayList<ArrayList<Object>> rowData = new ArrayList<ArrayList<Object>>();
		
		if (Category.equals("Artist")) {
			try {
				rowData = search.searchArtist(str);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (Category.equals("Album")) {
			try {	
				rowData = search.searchAlbum(str);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (Category.equals("Song")) {
			try {
				rowData = search.searchSong(str);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}	
		
		new GUITable(rowData,frame);	
	}
	
}
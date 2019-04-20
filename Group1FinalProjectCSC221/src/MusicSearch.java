import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Base.SQLOperations;
import Entities.*;

public class MusicSearch {

	private ArrayList<Artist> artists;
	
	public ArrayList<Artist> getArtists() {return artists;}
	public void setArtists(ArrayList<Artist> artists) {this.artists = artists;}
	
	public ArrayList<ArrayList<Object>> searchArtist(String name) throws IOException {
		
		ArrayList<Object> cols = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
		
		// Row format: Song, Artist, Album, Album Art
		for (Artist artist : artists) {
			if (name.equals(artist.getName())) {
				for (Album album : artist.getAlbums()) {
					for (Song song : album.getSongs()) {
						cols.add(song.getTitle());
						cols.add(artist.getName());
						cols.add(album.getTitle());
						if (album.getAlbumArt() != null) {
							ImageIcon art = scaleImage(blob2ImageIcon(album.getAlbumArt()), 400, 200);
							cols.add(art);
						}
						else cols.add(null);
						rows.add(cols);
					}
				}
			}
		}
		
		return rows;
	}
	
	public ArrayList<ArrayList<Object>> searchAlbum(String name) throws IOException {
		
		ArrayList<Object> cols = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
		
		// Row format: Song, Artist, Album, Album Art
		for (Artist artist : artists) {
			for (Album album : artist.getAlbums()) {
				if (name.equals(album.getTitle())) {
					for (Song song : album.getSongs()) {
						cols.add(song.getTitle());
						cols.add(artist.getName());
						cols.add(album.getTitle());
						if (album.getAlbumArt() != null) {
							ImageIcon art = scaleImage(blob2ImageIcon(album.getAlbumArt()), 400, 200);
							cols.add(art);
						}
						else cols.add(null);
						rows.add(cols);
					}
				}
			}
		}
		
		return rows;
	}
	
	public ArrayList<ArrayList<Object>> searchSong(String name) throws IOException {
		
		ArrayList<Object> cols = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
		
		// Row format: Song, Artist, Album, Album Art
		for (Artist artist : artists) {
			for (Album album : artist.getAlbums()) {
				for (Song song : album.getSongs()) {
					if (name.equals(song.getTitle())) {
						cols.add(song.getTitle());
						cols.add(artist.getName());
						cols.add(album.getTitle());
						if (album.getAlbumArt() != null) {
							ImageIcon art = scaleImage(blob2ImageIcon(album.getAlbumArt()), 400, 200);
							cols.add(art);
						}
						else cols.add(null);
						rows.add(cols);
					}
				}
			}
		}
		
		return rows;
	}
	
	public static ImageIcon blob2ImageIcon(Blob blob) throws IOException { // for easier use with JTable
		
		if (blob != null) {
			try {
				
				int blobLength = (int) blob.length();
				byte[] bytes = blob.getBytes(1,blobLength);
//				blob.free();//<- free resource
				BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
				ImageIcon icon = new ImageIcon(img);
				return icon;
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else return null;
		return null;
	}
	
	public static ImageIcon scaleImage(ImageIcon icon, int width, int height) { // for easier use with JTable
		
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); //scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		
		return icon;
	}

}

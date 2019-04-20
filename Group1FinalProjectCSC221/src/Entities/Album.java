package Entities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import Base.ConnectionHelper;
import Base.EntityBase;
import Base.SQLOperations;
import Base.SQLStatements;

public class Album extends EntityBase {
	
	private int albumId;
	private String albumTitle;
	private int year;
	private String artist;
	private int artistId;
	private ArrayList<Song> songs;
	private Blob albumImage;
	private String pathName="";

	
	public Album() {
		albumId = 0;
		albumTitle = null;
		artist = null;
		year = 1900;
		albumImage = null;
	}
	public Album(String albumTitle, int year){
		setAlbumTitle(albumTitle);
		setYear(year);
		//setSongs(songList);
	}
	public Album(int albumID, String albumTitle, String artist, int year, ArrayList<Song> songs, Blob albumArt) {
		setId(albumID);
		setAlbumTitle(albumTitle);
		setArtist(artist);
		setYear(year);
		setSongs(songs);
		setImage(albumArt);
	}
	
	public Album(int id,String albumTitle, Blob image,int year){
		setImage(image);
		setAlbumTitle(albumTitle);
		setYear(year);	
	}
	public Album(String albumTitle, String pathName) {
		setAlbumTitle(albumTitle);
		setpathName(pathName);
	}

	// constructor used for already existing album to retrieve title image path
	public Album(int ablumId) {
		this.albumId = ablumId;
	}

	// getter
	public String getPathName() {
		return pathName;
	}
	public String getTitle() {
		return albumTitle;
	}
	public int getId() {
		return albumId;
	}
	public Blob getAlbumArt() {
		try {
			// Get image from database and store in memory.
			Connection con = ConnectionHelper.getConnection();
			PreparedStatement st;
			
				st = con.prepareStatement(SQLStatements.ALBUM_GET_IMAGE);
			
			st.setInt(1, getId());
			ResultSet res = st.executeQuery();
			Blob savedImg = null;
			if (res.next()) {
				savedImg = res.getBlob(1);
			}
			st.close();
		return savedImg;
		} catch (SQLException e) {
			return null;
		}
	}
	public int getYear(){
		return year;
	}
	public ArrayList<Song> getSongs() {
		return songs;
	}
	public int getArtistId()
	{
		return artistId;
	}
	public static ArrayList<Song> getSongsFromDB(String albumName) {
		try {
			ArrayList<Song> songs = new ArrayList<Song>();
			String para[] = {albumName };
			ResultSet res = SQLOperations.selectCmd(SQLStatements.SONG_BY_ALBUMTitle, para, null);
			while (res.next()) {
				Song s = new Song(res.getInt(1), res.getString(2),
						res.getInt(3), res.getInt(4),res.getString(5),res.getString(6),res.getInt(7),res.getBlob(8));
				songs.add(s);
			}
			return songs;
		} catch (Exception e) {
			return null;
		}
	}
	
	// setter
	public void setArtistId(int id) {
		artistId=id;
	
	}
	private void setId(int id)
	{
		this.albumId=id;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	private void setArtist(String artist) {
		this.artist=artist;
	}
	public void setYear(int year) 
	{
		this.year = year;
	}
	private void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
		}
	public void setImage(Blob image)
	{
		this.albumImage=image;
	}
	
	public void setpathName(String filePath) {
		this.pathName = filePath;
	}
	
	public void setAlbumImage() throws SQLException, FileNotFoundException {
		// Use class PreparedStatement with setBinaryStream method to insert image
		// into blob column in MySQL
		// Opens file by creating an InputStream object to view file contents as
		// aata will be read from InputStream until end of the file or
		// length specified by the 3rd parameter.
		try {
		Connection con = ConnectionHelper.getConnection();
		String cmd = SQLStatements.ALBUM_UPDATE_ART;
		PreparedStatement st = con.prepareStatement(cmd);
		File f = new File(getPathName());
		InputStream img = new FileInputStream(f);
		// @param 1: int parameter index
		// @param 2: InputStream object
		// @param 3: "length" or number of bytes of the file.
		st.setBinaryStream(1, img, (int) f.length());
		st.setInt(2,getId());
		st.execute();
		st = con.prepareStatement(SQLStatements.ALBUM_GET_IMAGE);
		st.setInt(1,getId());
		ResultSet res = st.executeQuery();
		if(res!=null && res.next())
			setImage(res.getBlob(1));
		st.close();
		img.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void set(int albumID, String albumName, String artist, int year, ArrayList<Song> songs, Blob albumArt) {
		setId(albumID);
		setAlbumTitle(albumName);
		setArtist(artist);
		setYear(year);
		setSongs(songs);
		setImage(albumArt);
	}

	/***************************************
	 * Save image in path of your choice.
	 ***************************************/
	public Boolean SaveImage(String Path) {
		try {
			Blob image = getAlbumArt();
			InputStream bf = image.getBinaryStream();
			OutputStream outputfile = new FileOutputStream(Path + "//"
					+ getTitle() + ".png");
			byte[] buff = image.getBytes(1, (int) image.length());
			outputfile.write(buff);
			outputfile.close();
			return true;
		} catch (IOException | SQLException e) {
			return false;
		}
	}

	@Override
	public Boolean Save() {
		if (!IsDuplicated()) {
			String cmd = SQLStatements.ALBUM_INSERT;
			String paraS[] = { getTitle() };
			int paraI[]={getYear()};
			albumId = SQLOperations.insertCmd(cmd, paraS, paraI,null);
			if(albumId!=0)
			{
				for(Song s : songs)
				{
					s.setAlbumId(getId());
					s.setArtistId(getArtistId());
					s.Save();
				}
			}
			return (albumId != 0);
		} else {
			System.out.println("Duplicated !");
			return false;
		}
	}

	@Override
	public Boolean Update() {
		try {
			Connection con = ConnectionHelper.getConnection();
			PreparedStatement st = con.prepareStatement(SQLStatements.ALBUM_UPDATE_TITILE);

			st.setString(1, getTitle());
			st.setInt(2, getId());
			st.execute();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Boolean IsDuplicated() {
		String cmd = SQLStatements.ALBUM_COUNT;
		String para[] = { getTitle() };
		ResultSet res = SQLOperations.selectCmd(cmd, para, null);
		try {
			int num = 0;
			if (res.next()) {
				num = Integer.parseInt(res.getString(1));
			}
			return num > 0;
		} catch (SQLException e) {
			return false;
		}
	}
		

	
	public boolean Delete() {
		return false;
	}

	
	
	
	
}

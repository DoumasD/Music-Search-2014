package Entities;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Base.ConnectionHelper;
import Base.EntityBase;
import Base.SQLOperations;
import Base.SQLStatements;

public class Song extends EntityBase {
	// Instance variables
	private String AlbumTitle;
		
	public String getAlbumTitle() {
		return AlbumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		AlbumTitle = albumTitle;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}

	private int Id;
	private String Title;
	private double duration;
	private int trackNum;
	private String composer;
	private int year;
	private String artist; //<-----(@)
	private int artistId;
	//private Artist artist;
	//private Album album;
	private int albumId;
	
	// constructors
	public Song() {
			Title = null;
			duration = 0.00;
			trackNum = 0;
			composer = null;
			artist = null;
		}
	public Song(String title, double duration, int trackNum, String composer, String artist) {
		setTitle(title);
		setDuration(duration);
		setTrackNum(trackNum);
		setComposer(composer);
		setArtist(artist);	
		
	}
	public Song(String artist, int Id, int albId, int trackNo) {
		setArtist(artist);
		setId(Id);
		setAlbumId(albId);
		setTrackNum(trackNo);
		
	}
	public Song(int id, String songTitle, int albumId, int trackNum, String albumTitle,String artist, int year, Blob blob) {
		this.Id= id;
		this.Title= songTitle;
		this.albumId=albumId;
		this.trackNum = trackNum;
		this.AlbumTitle = albumTitle;
		this.artist= artist;
		this.year = year;
		blob = null;
	}
	//getter
	public String getTitle() {
		return Title;
	}

	public int getId() {
		return Id;
	}

	public int getAlbumId() {
		return albumId;
	}

	public int getTrackNo() {
		return trackNum;
	}
	public double getDuration() {
		return duration;
	}
	
	public String getComposer() {
		return composer;
	}
	
	public String getArtist() {
		return artist;
	}
	private int getArtistId() {
		return artistId;
	}
	//setter
	private void setId(int Id){ 
		this.Id= Id; 
	}	 
	public void setAlbumId(int albID) {
		this.albumId = albID;
	}
	public void setTitle(String songName) {
		this.Title = songName;
	}
	public void setTrackNum(int i) {
		trackNum = i;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public void setArtistId(int id) {
		this.artistId = id;
	}
	
	public void set(String title, double duration, int trackNum, String composer, String artist) {
		setTitle(title);
		setDuration(duration);
		setTrackNum(trackNum);
		setComposer(composer);
		setArtist(artist);	
	}

	@Override
	public Boolean Update() {
		try {
			Connection con = ConnectionHelper.getConnection();
			PreparedStatement st = con.prepareStatement(SQLStatements.SONG_UPDATE_SONGS);			
			st.setString(1, getTitle());
			st.setDouble(2, getDuration());
			st.setInt(3, getTrackNo());
			st.setString(4, getComposer());
			st.setInt(5, getAlbumId());
			st.setInt(6, getArtistId());
			st.setInt(7, getId());
			st.execute();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public String fetchInfoBySong() {

		try {
			Connection con = ConnectionHelper.getConnection();
			PreparedStatement st = con.prepareStatement(SQLStatements.SONG_FETCH_INFO);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				System.out.println(res.getInt("id") + ""+ res.getString("title")+""+ res.getInt("albumId")+""+res.getInt(trackNum));
			}
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean Save() {
		if (!IsDuplicated()) {
			Object para[] = { getTitle(), getDuration(),
					getTrackNo(),getComposer(),
					getAlbumId(),getArtistId()};
			
			Id = SQLOperations.insertCmd(SQLStatements.SONG_INSERT, para);
			return (Id != 0);
		} else {
			System.out.println("Duplicated !");
			return false;
		}
	}

	@Override
	public Boolean IsDuplicated() {
		return false;
	}

	public boolean Delete() {
			int para[] = { getId()};
			return SQLOperations.deleteCmd(SQLStatements.SONG_DELETE,null, para);	
	}
}

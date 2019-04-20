package Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Base.EntityBase;
import Base.SQLOperations;
import Base.SQLStatements;

public class Artist extends EntityBase {
	// Instance variables
	private ArrayList<Album> albums;
	private String name;
	private int artistId;

	// Constructors

	// Empty
	public Artist() {
		setName(null);
	}
	public Artist(int id, String name) {
		artistId=id;
		setName(name);
		//setAlbums(albums);
	}
	
	public Artist(String name, ArrayList<Album> albums) {
		setName(name);
		setAlbums(albums);
	}

	// getter
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public int getId() {

		return artistId;
	}

	public String getName() {
		return name;
	}
	public static ArrayList<Artist> getArtistFromDB()
	{
		try {
			ArrayList<Artist> myArtist = new ArrayList<Artist>();
			ResultSet res = SQLOperations.selectCmd(
					SQLStatements.ARTIST_FETCH_ALL, null, null);
			while (res.next()) {
				Artist s = new Artist(res.getInt(1),res.getString(2));
				myArtist.add(s);
			}
			return myArtist;
		} 	catch (Exception e) {
			return null;
		}
	}
	public static ArrayList<Album> getAlbumsFromDB(String artistName) {
		try {
			ArrayList<Album> myAlbums = new ArrayList<Album>();
			String para[] = { artistName };
			ResultSet res = SQLOperations.selectCmd(
					SQLStatements.ARTIST_GET_ByNAME, para, null);
			while (res.next()) {
				Album s = new Album(res.getInt(1), res.getString(2),
						res.getBlob(3), res.getInt(4));
				myAlbums.add(s);
			}
			return myAlbums;
		} 	catch (Exception e) {
			return null;
		}
	}

	// setter
	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}

	public void setName(String name) {
		this.name = name;
	}

	// DB Operation
	public Boolean IsDuplicated() {
		String para[] = { getName() };
		ResultSet res = SQLOperations.selectCmd(SQLStatements.ARTIST_COUNT,
				para, null);
		try {
			int num = 0;
			if (res.next()) {// read first row
				num = res.getInt(1);// get col 1
			}
			return (num > 0);
		} catch (SQLException e) {
			return false;
		}
	}

	@Override
	public String toString() {
		return " Name: " + this.getName();
	}

	@Override
	public Boolean Save() {
		if (!IsDuplicated()) {
			String para[] = { getName() };
			artistId = SQLOperations.insertCmd(SQLStatements.ARTIST_INSERT,
					para, null, null);
			if(artistId!=0)//Save all album in albumList
			{
				for(Album a : albums)
				{
					a.setArtistId(this.getId());
					a.Save();
				}
			}
			return (artistId != 0);
		} else {
			System.out.println("Duplicated !");
			return false;
		}
	}

	public Boolean Delete() {
		int para[] = { getId() };
		return SQLOperations.deleteCmd(SQLStatements.ARTIST_DELETE, null, para);
	}

	@Override
	public Boolean Update() {
		try {
			String para1[] = { getName() };
			int para2[] = { getId() };
			return SQLOperations.updateCmd(SQLStatements.ARTIST_UPDATE, para1,
					para2);
		} catch (Exception e) {
			return false;
		}
	}

	/** SQL Search ***/
	public Boolean FetchByName(String name) {
		String para[] = { name };
		ResultSet res = SQLOperations.selectCmd(
				SQLStatements.ARTIST_GET_ByNAME, para, null);
		try {
			if (res.next()) {
				artistId = res.getInt(1);
				setName(res.getString(2));
				return true;
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}


}

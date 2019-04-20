package Base;

public final class SQLStatements {
	public final static String ALBUM_UPDATE_ART = "UPDATE album SET coverArt=? WHERE id=?";
	public final static String ALBUM_INSERT 	= "INSERT INTO album (albumTitle,year) VALUES(?,?)";
	public final static String ALBUM_UPDATE_TITILE 	= "UPDATE music.album SET albumTitle=? WHERE id=?"; //TODO check if right!!!!
	public final static String ALBUM_COUNT 		= "Select COUNT(*) From music.album Where albumTitle=?;";
	public final static String ALBUM_FOREIGN_KEY = "SET FOREIGN_KEY_CHECKS = 0;";
	public final static String ALBUM_TRUNCATE	 = "TRUNCATE music.album;";
	public final static String ALBUM_FOREIGN_KEY1= "SET FOREIGN_KEY_CHECKS = 1;";
	public final static String ALBUM_GET_IMAGE	 = "SELECT coverArt FROM album WHERE Id =?;";
	
	public final static String SONG_UPDATE_SONGS = "Update songs set title=?,duration=?,tracknum=?,composer=?,albumId=?,artistId=? where id=?";
	public final static String SONG_INSERT = "INSERT INTO music.songs (title,duration,trackNum,composer,albumId,artistId) values (?,?,?,?,?,?);";
	public final static String SONG_DELETE = "DELETE FROM music.songs Where id=?;";
	public final static String SONG_BY_ALBUMTitle = "SELECT * FROM songs JOIN album USING (id) WHERE songs.albumId= album.id AND Album.albumTitle=?;";
	public final static String SONG_FETCH_INFO = "Select * From music.songs;";
	
	public final static String ARTIST_UPDATE= "Update music.artist set Name=? where id=? ";
	public final static String ARTIST_COUNT = "Select COUNT(*) From music.artist Where Name=?;";
	public final static String ARTIST_INSERT = "INSERT INTO music.artist (name) values (?);";
	public final static String ARTIST_DELETE = "DELETE FROM music.artist Where ( id=? );";
	public final static String ARTIST_GET_ByNAME = "select albumID,albumTitle,coverArt,Year"+
			 "from album a join songs s ON a.id= s.albumId join artist aa ON s.artistid= aa.id"+
			" where name =? order by name asc;";
	public final static String ARTIST_FETCH_ALL = "SELECT a.id,a.albumTitle,a.coverArt,a.year " + 
												  "FROM music.album a inner join songs s on a.id = s.albumId "
												  + "WHERE s.artistId=?";
	public final static String GET_LASTid = "SELECT LAST_INSERT_ID();";
	
	public final static String SONG_ARTIST_FETCH_ALL= "Select artistID,songsId From music.songartist;";
	public final static String SONG_ARTIST_INSERT= "INSERT INTO music.songartist (artistID,songsId) VALUES (?,?);";
	public final static String SONG_ARTIST_COUNT = "Select COUNT(*) From music.songartist Where artistId=? AND songsId=?;";
	public final static String SONG_ARTIST_FOREIGN_KEY	= "SET FOREIGN_KEY_CHECKS = 0;";
	public final static String SONG_ARTIST_TRUNCATE		="TRUNCATE music.songartist;";
	public final static String SONG_ARTIST_FOREIGN_KEY1	= "SET FOREIGN_KEY_CHECKS = 1;";
	
	public final static String VIEW_ALL = "SELECT * FROM music.alldata;";
	public final static String CLEAR_ALL ="call music.clearAllTable();";
}



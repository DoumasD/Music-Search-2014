package Base;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLOperations {

	public static Integer insertCmd(String cmd, String[] ParaStr,
			int[] ParaInt,String ImagePath) {
		try {
			// Insert operation
			int res;
			Connection con = ConnectionHelper.getConnection();
			PreparedStatement st = con.prepareStatement(cmd);
			int counter=0;
			if (ParaStr != null) {
				for (int i = 0; i < ParaStr.length; i++)
					st.setString(++counter, ParaStr[i]);
			}
			if (ParaInt != null) {
				for (int i = 0; i < ParaInt.length; i++)
					st.setInt(++counter, ParaInt[i]);
			}
			if (ImagePath != null) {
				File f = new File(ImagePath);
				InputStream img = new FileInputStream(f);
				st.setBinaryStream(++counter, img, (int) f.length());
				st.execute();
				img.close();
			}
			else{
				st.execute();
			}
			res = st.getUpdateCount();
			st.close();

			// get id of last inserted value if the value was inserted
			// successfully
			Integer lastId = 0;
			if (res > 0) {
				Statement sid = con.createStatement();
				ResultSet gid = sid.executeQuery(SQLStatements.GET_LASTid);
				if (gid.next()) {
					lastId = Integer.parseInt(gid.getString(1));
				}
			}
			
			return lastId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet selectCmd(String cmd, String[] ParaStr,
			int[] ParaInt) {
		Connection con= ConnectionHelper.getConnection(); ;
		try {
			 
			PreparedStatement st = con.prepareStatement(cmd);
			int counter=0;
			if (ParaStr != null)
				for (int i = 0; i < ParaStr.length; i++)
					st.setString(++counter, ParaStr[i]);
			if (ParaInt != null)
				for (int i = 0; i < ParaInt.length; i++)
					st.setInt(++counter, ParaInt[i]);

			return st.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Integer insertCmd(String cmd, Object[] Para) {
		Connection con= ConnectionHelper.getConnection(); ;
		try {
			 
			PreparedStatement st = con.prepareStatement(cmd);
			int counter=0;
			if (Para != null)
				for (int i = 0; i < Para.length; i++)
				{
					if(Para[i] instanceof String)
						st.setString(++counter, Para[i].toString());
					else if(Para[i] instanceof Integer)
						st.setInt(++counter, Integer.parseInt(Para[i].toString()));
					else if(Para[i] instanceof Double)
						st.setDouble(++counter, Double.parseDouble(Para[i].toString()));
				}
			st.execute();
			
			int res = st.getUpdateCount();
			st.close();
			// get id of last inserted value if the value was inserted
			// successfully
			Integer lastId = 0;
			if (res > 0) {
				Statement sid = con.createStatement();
				ResultSet gid = sid.executeQuery(SQLStatements.GET_LASTid);
				if (gid.next()) {
					lastId = Integer.parseInt(gid.getString(1));
				}
			}
			
			return lastId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Update
	public static Boolean updateCmd(String cmd, String[] ParaStr, int[] ParaInt) {
		try {
			Connection con = ConnectionHelper.getConnection();
			PreparedStatement st = con.prepareStatement(cmd);
			int counter=0;
			if (ParaStr != null)
				for (int i = 0; i < ParaStr.length; i++)
					st.setString(++counter, ParaStr[i]);
			if (ParaInt != null)
				for (int i = 0; i < ParaInt.length; i++)
					st.setInt(++counter, ParaInt[i]);
			ResultSet res = st.executeQuery();
			if (res != null) {
				return res.rowUpdated();
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static Boolean deleteCmd(String cmd, String[] ParaStr, int[] ParaInt) {
			try {
				Connection con = ConnectionHelper.getConnection();
				PreparedStatement st = con.prepareStatement(cmd);
				int counter=0;
				if (ParaStr != null)
					for (int i = 0; i < ParaStr.length; i++)
						st.setString(++counter, ParaStr[i]);
				if (ParaInt != null)
					for (int i = 0; i < ParaInt.length; i++)
						st.setInt(++counter, ParaInt[i]);
				st.execute();
				return true;
			} catch (Exception e) {
				return false;
			}
		}

	public static Boolean ClearAllTable() {
		try {
			Connection con = ConnectionHelper.getConnection();
			PreparedStatement st = con.prepareStatement(SQLStatements.CLEAR_ALL);
			return st.execute();
		} catch (Exception e) {
			return false;
		}
	}
}

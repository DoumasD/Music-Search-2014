import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GUITable extends JFrame
{	
	private final String colNames[] = {"Song", "Artist", "Album", "Album Cover"}; 
	private Object rowData[][];
	
	final int SONG = 1;
    final int ARTIST = 2;
    final int ALBUM = 3;
    final int ART = 4;
	
    /*
    	Constructor
    	Takes in arraylist of row data objects and a jframe
    	Converts the arraylist into an array and then creates a jtable to display
    	the data.
    */
	public GUITable(ArrayList<ArrayList<Object>> row, JFrame frame) 
	{    	
		rowData = new Object[row.size()][4];
		for (int i = 0; i < row.size(); i++)
		{
			for (int j = 0; j < 4; j++)
			{
				rowData[i][j] = row.get(i).get(j);
			}
		}      
		
		//Required overloading in order to display images on jtable
		DefaultTableModel model = new DefaultTableModel(rowData, colNames) 
		{
			@Override
			public Class<?> getColumnClass(int column) 
			{
				switch(column) 
				{		    	 
					case 3: 
						return ImageIcon.class;
					default: 
						return Object.class;
				}
			}
		}; 
		
		//JPanel panel = new JPanel();
		//panel.setLayout(new BorderLayout());  
		
		JTable table = new JTable(model);  	
		
		for (int i = 0; i < row.size(); i++)
        {
        	if (rowData[i][3] != null)
        		table.setRowHeight(i,200);	
        } 
		
		JScrollPane tableContainer = new JScrollPane(table);		
		frame.add(tableContainer);
		frame.setVisible(true);    
	}
    
	
    //Returns row data array in case needed
    public Object[][] getRowData()
    {
    	return rowData;
    }    
  	
}
    

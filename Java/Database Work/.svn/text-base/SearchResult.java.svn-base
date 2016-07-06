package database;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * An implementation of the TableModel interface that utilises a List of DataRow objects to
 * store the data.
 * 
 * @version 18/03/2014
 * @author Jake Darby
 * 
 * @see DataRow
 * @see TableModel
 */
public class SearchResult implements TableModel, Serializable
{
	private static final long serialVersionUID = 22378008L;
	private final List<DataRow> DATA;
	private static final int NUM_COLUMNS = 8;
	private final int NUM_ROWS;
	
	/**
	 * Constructor that generates a SearchResult object from a set of Results of a query to the 'products' table
	 * 
	 * @param data The results of an SQL query of the 'products' table
	 * @throws SQLException
	 * 
	 * @see ResultSet
	 */
	public SearchResult (ResultSet data) throws SQLException
	{
		super();
		DATA = DataRow.generate(data);
		NUM_ROWS = DATA.size();
	}
	
	/**
	 * Constructor creates an empty TableModel
	 */
	public SearchResult()
	{
		super();
		DATA = new ArrayList<DataRow>();
		NUM_ROWS = 0;
	}
	
	@Override
	public int getRowCount() 
	{
		return NUM_ROWS;
	}

	@Override
	public int getColumnCount() 
	{
		return NUM_COLUMNS;
	}

	@Override
	public String getColumnName(int columnIndex) 
	{
		switch(columnIndex)
		{
		case 0:
			return "ID";
		case 1:
			return "Name";
		case 2:
			return "Price";
		case 3:
			return "Pack Quantity";
		case 4:
			return "Quantity in Stock";
		case 5:
			return "VAT";
		case 6:
			return "Order Date";
		case 7:
			return "Expiry Date";
		default:
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
		switch(columnIndex)
		{
		case 0:
		case 3:
		case 4:
			return Integer.class;
		case 1:
		case 2:
		case 6:
		case 7:
			return String.class;
		case 5:
			return Boolean.class;
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) 
	{
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		DataRow row = DATA.get(rowIndex);
		switch(columnIndex)
		{
		case -1:
			return row.getPrice();
		case 0:
			return row.getID();
		case 1:
			return row.getName();
		case 2:
			return String.format("£%d.%02d", row.getPrice()/100, row.getPrice()%100);
		case 3:
			return row.getPackQuantity();
		case 4:
			return row.getQuantity();
		case 5:
			return row.getVAT();
		case 6:
			return row.getOrderDate() == null ? "N/A" : row.getOrderDate();
		case 7:
			return row.getExpiryDate() == null ? "N/A" : row.getExpiryDate();
		default:
			return null;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
	{/*No implementation*/}
	@Override
	public void addTableModelListener(TableModelListener l) 
	{/*No implementation*/}

	@Override
	public void removeTableModelListener(TableModelListener l) 
	{/*No implementation*/}
	
	@Override
	public String toString()
	{
		String result = "";
		for (int i = 0; i < getRowCount(); i++)
		{
			for (int j = 0; j < getColumnCount(); j++)
			{
				result += ": " + getValueAt(i, j) + " :";
			}
			result += "\n";
		}
		return result;
	}
}

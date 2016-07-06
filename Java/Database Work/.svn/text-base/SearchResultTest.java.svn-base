package database;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class SearchResultTest 
{
	private SearchResult data;
	
	@Before
	public void setUp() throws SQLException
	{
		Object[][] data = 
			{{0, "Apples", 500, 50, 12, false, "2014-04-03", "2014-04-05"},
			 {1, "Jam", 7200, 24, 20, false, "2014-04-15", "2014-05-01"},
			 {2, "Quiche", 1000, 50, 17, false, "2014-04-20", "2014-05-08"},
			 {3, "Salt", 200, 16, 7, false, "2014-04-20", null},
			 {4, "Thyme", 599, 10, 12, true, null, "2014-09-14"}};
		this.data = new SearchResult(new ResultSetImp(data));
	}

	@Test
	public void test() 
	{
		//Tests of the row and column counts
		assertEquals(5, data.getRowCount());
		assertEquals(8, data.getColumnCount());
		//Tests of the column headers
		assertEquals("ID", data.getColumnName(0));
		assertEquals("Name", data.getColumnName(1));
		assertEquals("Price", data.getColumnName(2));
		assertEquals("Pack Quantity", data.getColumnName(3));
		assertEquals("Quantity in Stock", data.getColumnName(4));
		assertEquals("VAT", data.getColumnName(5));
		assertEquals("Order Date", data.getColumnName(6));
		assertEquals("Expiry Date", data.getColumnName(7));
		//Tests of the column headers for invalid columns
		assertNull(data.getColumnName(-2));
		assertNull(data.getColumnName(8));
		//Tests of the column classes
		assertEquals(Integer.class, data.getColumnClass(0));
		assertEquals(String.class, data.getColumnClass(1));
		assertEquals(String.class, data.getColumnClass(2));
		assertEquals(Integer.class, data.getColumnClass(3));
		assertEquals(Integer.class, data.getColumnClass(4));
		assertEquals(Boolean.class, data.getColumnClass(5));
		assertEquals(String.class, data.getColumnClass(6));
		assertEquals(String.class, data.getColumnClass(7));
		//Tests of the column classes for invalid columns
		assertNull(data.getColumnClass(-1));
		assertNull(data.getColumnClass(8));
		//Tests of the getValueAt method
		assertEquals(0, data.getValueAt(0, 0));
		assertEquals("Jam", data.getValueAt(1, 1));
		assertEquals("£10.00", data.getValueAt(2,2));
		assertEquals(16, data.getValueAt(3, 3));
		assertEquals(12, data.getValueAt(4, 4));
		assertEquals(false, data.getValueAt(0, 5));
		assertEquals("2014-04-15", data.getValueAt(1, 6));
		assertEquals("2014-05-08", data.getValueAt(2, 7));
		assertEquals(200, data.getValueAt(3, -1));
		assertNull(data.getValueAt(4, 8));
	}

}

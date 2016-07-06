package database;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataRowTest 
{
	private DataRow[] data;
	private static int NUM_TESTS = 5;
	
	@Before
	public void setUp()
	{
		data = new DataRow[NUM_TESTS];
		data[0] = new DataRow(0, "Apples", 500, 50, 12, false, "2014-04-03", "2014-04-05");
		data[1] = new DataRow(1, "Jam", 7200, 24, 20, false, "2014-04-15", "2014-05-01");
		data[2] = new DataRow(2, "Quiche", 1000, 50, 17, true, "2014-04-20", "2014-05-08");
		data[3] = new DataRow(3, "Salt", 200, 16, 7, false, "2014-04-20", null);
		data[4] = new DataRow(4, "Thyme", 599, 10, 12, true, null, "2014-09-14");
	}
	
	
	@Test
	public void getIDTest() 
	{
		for(int i = 0; i < NUM_TESTS; i++)
		{
			assertEquals(i, data[i].getID());
		}
	}
	
	@Test
	public void nameTest()
	{
		assertEquals("Apples", data[0].getName());
		assertEquals("Jam", data[1].getName());
		assertEquals("Quiche", data[2].getName());
		assertEquals("Salt", data[3].getName());
		assertEquals("Thyme", data[4].getName());
		
		data[0].setName("Bananas");
		data[1].setName("");
		data[2].setName(null);
		
		assertEquals("Bananas", data[0].getName());
		assertEquals("", data[1].getName());
		assertNull(data[2].getName());
	}
	
	@Test
	public void priceTest()
	{
		assertEquals(500, data[0].getPrice());
		assertEquals(7200, data[1].getPrice());
		assertEquals(1200, data[2].getPrice());
		assertEquals(200, data[3].getPrice());
		assertEquals(718, data[4].getPrice());
		
		assertEquals(500, data[0].getPriceWithoutVAT());
		assertEquals(7200, data[1].getPriceWithoutVAT());
		assertEquals(1000, data[2].getPriceWithoutVAT());
		assertEquals(200, data[3].getPriceWithoutVAT());
		assertEquals(599, data[4].getPriceWithoutVAT());
		
		data[1].setPrice(7000);
		data[3].setVAT(true);
		assertEquals(7000, data[1].getPrice());
		assertEquals(7000, data[1].getPriceWithoutVAT());
		assertEquals(240, data[3].getPrice());
		assertEquals(200, data[3].getPriceWithoutVAT());
	}

	@Test
	public void packQuantityTest()
	{
		assertEquals(50, data[0].getPackQuantity());
		assertEquals(24, data[1].getPackQuantity());
		assertEquals(50, data[2].getPackQuantity());
		assertEquals(16, data[3].getPackQuantity());
		assertEquals(10, data[4].getPackQuantity());
		
		data[3].setPackQuantity(20);
		data[4].setPackQuantity(599);
		
		assertEquals(20, data[3].getPackQuantity());
		assertEquals(599,data[4].getPackQuantity());
	}
	
	@Test
	public void quantityTest()
	{
		assertEquals(12, data[0].getQuantity());
		assertEquals(20, data[1].getQuantity());
		assertEquals(17, data[2].getQuantity());
		assertEquals(07, data[3].getQuantity());
		assertEquals(12, data[4].getQuantity());
		
		data[0].setQuantity(15);
		data[3].setQuantity(32);
		data[4].setQuantity(0);
		
		assertEquals(15, data[0].getQuantity());
		assertEquals(32, data[3].getQuantity());
		assertEquals(00, data[4].getQuantity());
	}
	
	@Test
	public void vatTest()
	{
		assertFalse(data[0].getVAT());
		assertFalse(data[1].getVAT());
		assertTrue (data[2].getVAT());
		assertFalse(data[3].getVAT());
		assertTrue (data[4].getVAT());
		
		data[2].setVAT(false);
		data[3].setVAT(false);
		data[4].setVAT(Math.abs(((float)2/3)*Math.cos(Math.PI/3) - Math.log(Math.pow(Math.E, (float)1/3))) < 0.000000000000001);
		
		assertFalse(data[3].getVAT());
		assertFalse(data[3].getVAT());
		assertTrue (data[4].getVAT());
	}
	
	@Test
	public void orderDateTest()
	{
		assertEquals("2014-04-03", data[0].getOrderDate());
		assertEquals("2014-04-15", data[1].getOrderDate());
		assertEquals("2014-04-20", data[2].getOrderDate());
		assertEquals("2014-04-20", data[3].getOrderDate());
		assertNull(data[4].getOrderDate());
		
		data[0].setOrderDate(null);
		data[1].setOrderDate(null);
		data[2].setOrderDate("2014-05-14");
		data[3].setOrderDate("05-14-2014");
		data[4].setOrderDate("2014-04-19");
	
		assertNull(data[0].getOrderDate());
		assertNull(data[1].getOrderDate());
		assertEquals("2014-05-14", data[2].getOrderDate());
		assertNull(data[3].getOrderDate());
		assertEquals("2014-04-19", data[4].getOrderDate());
	}
	
	@Test
	public void testExpiryDate()
	{
		assertEquals("2014-04-05", data[0].getExpiryDate());
		assertEquals("2014-05-01", data[1].getExpiryDate());
		assertEquals("2014-05-08", data[2].getExpiryDate());
		assertNull(data[3].getExpiryDate());
		assertEquals("2014-09-14", data[4].getExpiryDate());
		
		data[0].setExpiryDate("2014-05-05");
		data[1].setExpiryDate("2014-06-01");
		data[2].setExpiryDate("08-05-2014");
		data[3].setExpiryDate("2017-08-08");
		data[4].setExpiryDate(null);
		
		assertEquals("2014-05-05", data[0].getExpiryDate());
		assertEquals("2014-06-01", data[1].getExpiryDate());
		assertNull(data[2].getExpiryDate());
		assertEquals("2017-08-08", data[3].getExpiryDate());
		assertNull(data[4].getExpiryDate());
	}
	
	@Test
	public void toArrayTest()
	{
		Object[] temp = data[0].toArray();
		assertEquals(0, temp[0]);
		assertEquals("Apples", temp[1]);
		assertEquals(500, temp[2]);
		assertEquals(50, temp[3]);
		assertEquals(12, temp[4]);
		assertEquals(false, temp[5]);
		assertEquals("2014-04-03", temp[6]);
		assertEquals("2014-04-05", temp[7]);
	}
}

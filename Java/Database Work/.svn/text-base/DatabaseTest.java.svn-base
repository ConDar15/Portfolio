package database;

import static org.junit.Assert.*;

import javax.swing.table.TableModel;

import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseTest 
{
	/* These tests assume that the database starts in the following state:
products (id may differ):
 id  |         name         | price | pack_quantity | quantity | vat | order_date | expiry_date 
-----+----------------------+-------+---------------+----------+-----+------------+-------------
  68 | Apples               |   500 |            50 |       12 | f   | 2014-04-03 | 2014-04-05
  69 | Bananas              |  1000 |            50 |        7 | f   | 2014-03-30 | 2014-03-27
  70 | Cereal Bars          |   500 |            10 |       14 | t   | 2014-04-30 | 2014-09-22
  71 | Doughnuts            |   499 |            12 |       20 | t   | 2014-03-20 | 2014-04-03
  72 | Eclairs              |   599 |            15 |        8 | f   | 2014-05-12 | 2014-04-01
  73 | Flour                |  2000 |            50 |       13 | t   | 2014-03-29 | 2014-10-12
  74 | Gravy Granules       |  1750 |            25 |        8 | t   | 2014-03-29 | 2014-06-19
  75 | Green Peppers        |   500 |            50 |        7 | f   | 2014-04-03 | 2014-03-24
  76 | Helmans Mayonnaise   |  1750 |            25 |        4 | f   | 2014-04-03 | 2014-03-24
  77 | Ice Cream            |  7200 |            24 |        6 | t   | 2014-03-20 | 2014-05-12
  78 | Jam                  |  7200 |            24 |       20 | f   | 2014-04-15 | 2014-05-01
  79 | Kit Kat (Individual) |   500 |            50 |        5 | f   | 2014-03-29 | 2014-08-17
  80 | Kit Kat (8 pack)     |  1000 |            50 |        8 | f   | 2014-03-29 | 2014-08-17
  81 | Kit Kat Chunky       |   500 |            50 |        0 | f   | 2014-03-29 | 2014-08-17
  82 | Kitchen Roll         |  2100 |            30 |       19 | t   | 2014-04-12 | 
  83 | Lemons               |   700 |            50 |        7 | f   | 2014-04-30 | 2014-04-03
  84 | Milk                 |  1000 |            20 |       14 | f   | 2014-03-26 | 2014-03-28
  85 | Mixed Peppers        |   500 |            50 |        4 | f   | 2014-04-03 | 2014-03-24
  86 | Nutmeg (Ground)      |   500 |            10 |       10 | t   | 2014-03-31 | 2014-08-12
  87 | Nutmeg (Whole)       |   500 |            10 |       10 | t   | 2014-03-31 | 2014-08-12
  88 | Onions               |   500 |            50 |        6 | f   | 2014-04-03 | 2014-03-27
  89 | Pineapple            |  1200 |            50 |        3 | f   | 2014-04-03 | 2014-04-12
  90 | Quiche               |  1000 |            50 |       17 | t   | 2014-04-20 | 2014-05-08
  91 | Red Peppers          |   500 |            50 |        3 | t   | 2014-04-03 | 2014-03-24
  92 | Rubber Gloves        |  2000 |            20 |       18 | t   | 2014-04-15 | 
  93 | Salt                 |   200 |            16 |        7 | f   |            | 
  94 | Sugar                |  5000 |           100 |        5 | f   | 2014-08-16 | 2015-09-23
  95 | Thyme                |   500 |            10 |       12 | f   | 2014-03-31 | 2014-08-12
  96 | Vegitarian Bacon     |  1500 |            15 |        1 | f   | 2014-03-28 | 2014-04-12
  97 | Wensleydale Cheese   |  4000 |            20 |       11 | f   | 2014-03-26 | 2014-05-07
  98 | Xylophone            | 80000 |             2 |   358793 | t   |            | 
  99 | Yellow Peppers       |   500 |            50 |       18 | f   | 2014-04-03 | 2014-03-24
 100 | Zesty Peppers        |   500 |            50 |       14 | f   | 2014-04-03 | 2014-03-24
(33 rows)

login:
   usr    |               pass               
----------+----------------------------------
 jake     | f9480c8873775a676e74d33378b92ae6    (md5 hash of "beepbeep")
 nick     | 7813258ef8c6b632dde8cc80f6bda62f    (md5 hash of "bacon")
 hari     | 06d80eb0c50b49a509b49f2424e8c805    (md5 hash of "dog")
 danny    | f0e166dc34d14d6c228ffac576c9a43c    (md5 hash of "anything")
 faromarz | fe01d67a002dfa0f3ac084298142eccd    (md5 hash of "orange")
 user     | 5f4dcc3b5aa765d61d8327deb882cf99    (md5 hash of "password")
(6 rows)
	 */
	
	private static Database db;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		db = new Database();
	}

	@Test
	public void searchByNameTest() 
	{
		TableModel tm = db.searchByName("kit");
		assertEquals(4, tm.getRowCount());
		assertEquals("Kit Kat (Individual)", tm.getValueAt(0, 1));
		assertEquals("£10.00", tm.getValueAt(1, 2));
		assertEquals(50, tm.getValueAt(2, 3));
		assertEquals(19, tm.getValueAt(3, 4));
		
		tm = db.searchByName("ApPlE");
		assertEquals(2, tm.getRowCount());
		assertEquals("Apples", tm.getValueAt(0, 1));
		assertEquals(false, tm.getValueAt(0, 5));
		assertEquals("Pineapple", tm.getValueAt(1,1));
		assertEquals("2014-04-03", tm.getValueAt(1, 6));
	}

	@Test
	public void loginTest()
	{
		assertTrue(db.login("jake", "beepbeep"));
		assertTrue(db.login("nick", "bacon"));
		assertTrue(db.login("hari", "dog"));
		assertTrue(db.login("danny", "anything"));
		assertTrue(db.login("faromarz", "orange"));
		assertTrue(db.login("user", "password"));
		
		assertFalse(db.login("maria", "salama"));
		assertFalse(db.login("admin", "admin"));
		assertFalse(db.login("jake", "BeepBeep"));
		assertFalse(db.login("Jake", "beepbeep"));
		assertFalse(db.login("jake", ""));
		
		assertFalse(db.login("newUser", "boom"));
		assertTrue(db.register("newUser", "boom"));
		assertTrue(db.login("newUser", "boom"));
		assertFalse(db.register("jake", "beepbeep"));
		assertFalse(db.register("jake", "newPass"));
	}
	
	@Test
	public void stockTest()
	{
		TableModel tm = db.searchByName("Flour");
		assertEquals(4, db.purchase((Integer)tm.getValueAt(0, 0), 4));
		assertEquals(5, db.purchase((Integer)tm.getValueAt(0, 0), 5));
		tm = db.searchByName("Flour");
		assertEquals(4, tm.getValueAt(0, 4));
		
		tm = db.searchByName("Jam");
		assertEquals(15, db.purchase((Integer)tm.getValueAt(0, 0), 15));
		assertEquals(-5, db.purchase((Integer)tm.getValueAt(0, 0), 10));
		tm = db.searchByName("Jam");
		assertEquals(5, tm.getValueAt(0, 4));
		
		db.restock((Integer)tm.getValueAt(0, 0), 12, "2014-04-15", "2014-05-01");
		tm = db.searchByName("Jam");
		assertEquals(12, tm.getValueAt(0, 4));
		assertEquals(-12, db.purchase((Integer)tm.getValueAt(0, 0), 15));
		assertEquals(10, db.purchase((Integer)tm.getValueAt(0, 0), 10));
		tm = db.searchByName("Jam");
		assertEquals(2, tm.getValueAt(0, 4));
	}
	
	@Test
	public void addAndRemoveTest()
	{
		TableModel tm = db.searchByName("Xyl");
		assertTrue(db.removeProduct((Integer)tm.getValueAt(0, 0)));
		tm = db.searchByName("Xyl");
		assertEquals(0, tm.getRowCount());
		
		tm = db.searchByName("Potatoes");
		assertEquals(0, tm.getRowCount());
		assertTrue(db.addProduct(new DataRow("Potatoes", 1000, 10, 13, false, "2014-04-15", null)));
		tm = db.searchByName("Potatoes");
		assertEquals(1, tm.getRowCount());
		assertEquals("Potatoes", tm.getValueAt(0, 1));
		assertEquals("£10.00", tm.getValueAt(0, 2));
		assertEquals(10, tm.getValueAt(0, 3));
		assertEquals(13, tm.getValueAt(0, 4));
		assertEquals(false, tm.getValueAt(0, 5));
		assertEquals("2014-04-15", tm.getValueAt(0, 6));
		assertEquals("N/A", tm.getValueAt(0, 7));
	}
}

package sciaArtifactID;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runners.Suite.SuiteClasses;

import edu.UC.PhD.CodeProject.nicholdw.Table;
import edu.UC.PhD.CodeProject.nicholdw.Tables;
import edu.UC.PhD.CodeProject.nicholdw.exception.DataTypeException;

//@RunWith(Suite.class)
@SuiteClasses({
	Tables_test.class,
//	MySecondClassTest.class
})


public class Tables_test {

	@Test
	public void test() {
		try {
			Tables tables = new Tables();
			Table t1 =  new Table("table1", "schema1");
			Table t2 =  new Table("table2", "schema2");
			Table t3 =  new Table("table3", "schema3");
			Table t3a = new Table("table3", "schema3");		// Intentional duplicate
			Table tx =  new Table("tablex", "schemax");
			Table tx1 =  new Table("tablex", "schemax");	// Intentional duplicate
			tables.addTable(t1);
			tables.addTable(t2);
			tables.addTable(t3);
			tables.addTable(t3a);
			tables.addTable(tx);
			tables.addTable(tx);
			tables.addTable(tx1);
			tables.print(System.out);

	    	assertEquals("4 tables added to HaspMap", 4, tables.getTableHashMap().size());

		} catch (DataTypeException ex) {

		}
	}

}

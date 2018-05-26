package edu.UC.PhD.CodeProject.nicholdw;

import lib.MySQL;
import lib.SQLUtils;

public class Main {

	public static void main(String[] args) {


		// Test generation of table create script
		//testTableScripting();

		// Test Determinant Logic
		//testDeterminantLogic();
		//testAttributeExtractionFromTable();
		//testReferentialConstraintExtractionFromTable();
		//testDetectDesignPattern001();
		//testDetectDesignPattern002();
		//testManyToManySchema();

		//testNullableAttributes();

//		testMyDLookup();

		//MySQL my = new MySQL();
		//PrimaryKey primaryKey;

		/* Test Case 1 : Batch Production Data from MC Technology */
		//primaryKey = new PrimaryKey();
		//primaryKey.Find("TestCases/PrimaryKey01/tBatch.csv", true, true);

		/* Test Case 2 : Formula table from MC Technology */
		//primaryKey = new PrimaryKey();
		//primaryKey.Find("TestCases/PrimaryKey02/formula.csv", true, false);

		/* Test Case 3 : Additive table from MC Technology */
		//primaryKey = new PrimaryKey();
		//primaryKey.Find("TestCases/PrimaryKey03/Additive.csv", true, false);

		/* Test Case 4 : Transaction Detail table from Grocery Store Database */
		//primaryKey = new PrimaryKey();
		//primaryKey.Find("TestCases/PrimaryKey04/TransactionDetail.csv", true, false);

		/* Test Case 5 : Transaction Detail table from Grocery Store Database */
		//primaryKey = new PrimaryKey();
		//primaryKey.Find("TestCases/PrimaryKey05/Transaction.csv", "tTransaction", true, false);

		/* Test Case 6 : Transaction Detail table from Grocery Store Database */
		//primaryKey = new PrimaryKey();
		//primaryKey.Find("TestCases/PrimaryKey06/Store.csv", "tStore", true, false);
/*
		try {
			my.readDataBase(my.connectToDatabase());
			my.getConnection().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}

	public static void testTableScripting() {
		Table t = new Table("tTestTable", "");
		t.getAttributes().addAttribute(new Attribute("testTableID"            , "", false, "int"    , "no" , "yes", "yes", "auto_increment", 11, (Aliases)null));
		t.getAttributes().addAttribute(new Attribute("testTable"              , "", false,  "int"    , "no" , "yes", "no" , "", 11, (Aliases)null));
		Attribute indexableAttribute = new Attribute("testTableString"        , "", false,  "varchar", "no" , "yes", "no" , "", 50, (Aliases)null);
		t.getAttributes().addAttribute(indexableAttribute);
		t.getAttributes().addAttribute(new Attribute("testTableStringNullable", "", false,  "varchar", "yes", "yes", "no" , "", 50, (Aliases)null));
		Attribute indexableAttribute01 = new Attribute("testTableDate"        , "", false,  "date"   , "no" , "yes", "no" , "", 0, (Aliases)null);
		t.getAttributes().addAttribute(indexableAttribute01);

		// Add an index to the table because we can
		Index index = new Index("", true, false);
		index.AddAttribute(indexableAttribute);		// Thank goodness for reference types in Java
		t.addIndex(index);

		Index index01 = new Index("", false, false);
		index01.AddAttribute(indexableAttribute01);		// Thank goodness for reference types in Java
		t.addIndex(index01);

		String script = t.generateScript();
		System.out.println("testTableScripting(): Script = " + script);
	}

	public static void testDeterminantLogic() {
		Table table = new Table("tTransaction", Config.getConfig().getMySQLDefaultDatabaseName());
		//Table table = new Table("tdeterminanttest", Config.getConfig().databaseName);
		//Table table = new Table("tStore", Config.getConfig().databaseName);
		Determinants determinants = new Determinants(table);
		determinants.AnalyzeTableForDeterminants(false, "");
		for (Determinant myDeterminant: determinants) {
			Config.getConfig().DebugLine(myDeterminant.toString());
		}
	}

	public static void testNullableAttributes() {
		DataBehavior db = new DataBehavior();
		db.checkForNulls("tAttributesThatAreNullable", Config.getConfig().getMySQLDefaultDatabaseName());
	}

	public static void testManyToManySchema() {
		Schema schema = new Schema("???");
		schema.addTable(new Table("tFlight", Config.getConfig().getMySQLDefaultDatabaseName()));
		schema.addTable(new Table("tPilot", Config.getConfig().getMySQLDefaultDatabaseName()));
		schema.addTable(new Table("tPilot_Flight", Config.getConfig().getMySQLDefaultDatabaseName()));
		TableAnalysis tableAnalysis = new TableAnalysis();
		tableAnalysis.DetectAndAnalyzeManyToManyRelationship(schema);
	}

	public static void testDetectDesignPattern002() {
		TableAnalysis tableAnalysis = new TableAnalysis();
		tableAnalysis.DetectDesignPattern002(Config.getConfig().getMySQLDefaultDatabaseName(), "tStudent_Class");
		tableAnalysis.DetectDesignPattern002(Config.getConfig().getMySQLDefaultDatabaseName(), "tPilot_Flight");
	}
	public static void testAttributeExtractionFromTable() {
		TableAnalysis tableAnalysis = new TableAnalysis();
		Attributes attributes = Table.readAttributesFromTableDefinition("tStore", Config.getConfig().getMySQLDefaultDatabaseName());
	}

	public static void testReferentialConstraintExtractionFromTable() {
		TableAnalysis tableAnalysis = new TableAnalysis();
		tableAnalysis.ProcessReferentialConstraints(Config.getConfig().getMySQLDefaultDatabaseName(), "tCustomer", null);
	}
	public static  void testMyDLookup() {
		Object foo ;
		java.sql.Connection connection = new MySQL().connectToDatabase("Acme");

		foo = SQLUtils.MyDLookup("BufferID", "tBuffer", "Attribute01 = \"keyboard\"", "Count", "", connection);
		System.out.println("BufferID = " + (Long)foo + " where Attribute01 = Keyboard");		// It's a Long, not an Integer

		foo = SQLUtils.MyDLookup("BufferID", "tBuffer", "", "Count", "", connection);
		System.out.println("Count of records in tBuffer = " + (Long)foo);		// It's a Long, not an Integer
	}

	/**
	 * String.split() quits splitting if there are only delimiters remaining in the string and nothing after the last comma.
	 * Solution: append a blank after the last comma.
	 */
	private static void testSplit() {
		String test = "1,,,,5,,, ";	// 7 commas

		String[] mySplit = test.split(",");

		System.out.println("test: " + mySplit.length + " elements were found.");

		test =  "1,2,3,4,5,,,a";	// 7 commas, just like before
		mySplit = test.split(",");

		System.out.println("test: " + mySplit.length + " elements were found.");
	}
}

package edu.UC.PhD.CodeProject.nicholdw.query;

import java.util.HashMap;

import edu.UC.PhD.CodeProject.nicholdw.Schema;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/**
 * SQL Statements that are simple and parseable by our limited parser logic until the good parser gets here.
 * Also useful for some quick testing and demonstrations
 * @author nicomp
 *
 */
public class TestQueries {

	private static void init() {
		SQLRepository = new HashMap<String, String>();
//		The query names must match query names that are already in the database.
//		The SQL must reference other queries in this list and/or existing tables in the schema. Watch case.
//		These can be in any schema. Watch out for duplicates.
		SQLRepository.put("qb",				"select ttableb.testfieldatableb AS testfieldatableb FROM ttableb");
		SQLRepository.put("qc",				"select testFieldATableC, testFieldBTableC, testFieldCTableC from ttablec");
		SQLRepository.put("qd",				"select testFieldATableD from ttabled");
		SQLRepository.put("qlevelaa",		"select testFieldATableE, testFieldATableF from qlevelbb");
		SQLRepository.put("qlevelbb",		"select testFieldATableE, testFieldATableF from qlevelcc");
		SQLRepository.put("qlevelcc",		"select testFieldATableE, testFieldATableF from ttablee, ttablef");
		SQLRepository.put("qnestedquery",	"select testFieldATableC, testFieldATableD from qc, qd");

//		Queries in the SchemaTopologyTest schema.
		SQLRepository.put("qalpha", 		"select AlphaFieldA from talpha");
		SQLRepository.put("qalphaparttwo",  "select AlphaFieldA from talpha");
		SQLRepository.put("qalphapartthree","select AlphaFieldA from talpha");
		SQLRepository.put("qalphapartfour", "select AlphaFieldB from talpha");

//		Queries in the SchemaTopologyTest02 schema.
		SQLRepository.put("qb", 			"select BetaFieldA from tbeta");
		SQLRepository.put("qa",				"select BetaFieldA, AlphaFieldA from qb, talpha");

//		Queries in the Schema sakila. Heavily modified so the weak parser will handle it. The attribute names and table names are preserved so our graphs will be equivalent.
		SQLRepository.put("actor_info",		"select actor_id, first_name, last_name, name, title, film_id, category_id, actor_id from actor, film, film_category, film_actor, category	" );
		SQLRepository.put("customer_list",  "select customer_id, first_name, lt_name, address, postal_code, phone, city, country, active, store_id, address_id, city_id, country_id from customer, address , city, country");
		SQLRepository.put("film_list",		"select film_id, title, description, name, rental_rate, length, rating, first_name, last_name, actor_id, name from category, film, film_category, film_actor, actor, film_actor, actor");
		SQLRepository.put("nicer_but_slower_film_list",
				                            "select film_id, title, description,  name, rental_rate, length, rating, first_name, last_name, film_id, actor_id, category_id from category, film_category, film, actor");
		SQLRepository.put("sales_by_film_category",
											"select name, amount, category_id, film_id, rental_id from payment, rental, inventory, film, film_category, category");
		SQLRepository.put("sales_by_store",
											"select city. country, first_name, amount, payment, rental_id, inventory_id, store_id, address_id, city_id, country_id, manager_staff_id, staff_id from rental, inventory, store, address, city, country, staff");
		SQLRepository.put("staff_list",		"select staff_id, first_name, last_name, address, postal_code, phone, city, country, store_id, staff, address_id, country_id, city_id from staff, address, city, country");

//		Queries in the Schema AttributeProvenanceTest
		SQLRepository.put("qlevela",		"select tableaa_fielda from qlevelb");
		SQLRepository.put("qlevelb",		"select tableaa_fielda from qlevelc");
		SQLRepository.put("qlevelc",		"select tableaa_fielda from ttableaa");

	}
	private static HashMap<String, String> SQLRepository;

	/**
	 * Look up the SQL for a given query name. Used for testing purposes and to support a weak query parser.
	 * These will be used if Config.useTestData is true
	 * @param queryName The name of the query.
	 * @return The SQL that goes with queryName
	 * @throws Exception if there is no match for queryName in the repository of SQL
	 */
	public static String getSQL(String queryName) throws Exception {
		if (SQLRepository == null) {init();}
		String sql = null;
		sql =  SQLRepository.get(queryName.toLowerCase());
		if (sql == null) {
			Log.logError("TestQueries.getSQL(): unable to match query '" + queryName + "' in SQLRepository");
			throw new Exception ("TestQueries.getSQL(): unable to match query '" + queryName + "' in SQLRepository");
		}
/*
		if (queryName.toLowerCase().equals("qb")) { sql = "select ttableb.testfieldatableb AS testfieldatableb FROM ttableb";}
		if (queryName.toLowerCase().equals("qc")) { sql = "select testFieldATableC, testFieldBTableC, testFieldCTableC from ttablec";}
		if (queryName.toLowerCase().equals("qd")) { sql = "select testFieldATableD from ttabled";}
		if (queryName.toLowerCase().equals("qlevelaa")) {sql = "select testFieldATableE, testFieldATableF from qlevelbb";}
		if (queryName.toLowerCase().equals("qlevelbb")) {sql = "select testFieldATableE, testFieldATableF from qlevelcc";}
		if (queryName.toLowerCase().equals("qlevelcc")) {sql = "select testFieldATableE, testFieldATableF from ttablee, ttablef";}
		if (queryName.toLowerCase().equals("qnestedquery")) {sql = "select qc.testFieldATableC AS testFieldATableC, qd.testFieldATableD AS testFieldATableD FROM qc JOIN qd";}
*/
		return sql;
	}


}

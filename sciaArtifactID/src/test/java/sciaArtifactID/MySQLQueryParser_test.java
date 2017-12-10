/*
 * 12/9/2017
 * An attempt to get the attribute and table info I need from the command object available in JDBC. Didn't give me what I needed.
 * 
 */
package sciaArtifactID;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.ResultSetMetaData;

import edu.UC.PhD.CodeProject.nicholdw.Config;
import lib.MySQL;

public class MySQLQueryParser_test {

	public static void main(String[] args) {
		PreparedStatement preparedStatement;
		java.sql.Connection connection;
		try {
			connection = new MySQL().connectToDatabase(Config.getConfig().getMySQLDefaultHostname(), Config.getConfig().getMySQLDefaultDatabaseName(), Config.getConfig().getMySQLDefaultLoginName(),Config.getConfig().getMySQLDefaultPassword());
			//preparedStatement = connection.prepareStatement("SELECT city.name, country.name FROM world.city inner join world.country on city.countryCode = country.Code");
			//preparedStatement = connection.prepareStatement("Select max(ID) as maxID from City");
			String q = "SELECT `a`.`actor_id` AS `actor_id`, `a`.`first_name` AS `first_name`, `a`.`last_name` AS `last_name`, GROUP_CONCAT(DISTINCT CONCAT(`c`.`name`,': ',(SELECT   GROUP_CONCAT(`f`.`title` ORDER BY `f`.`title` ASC SEPARATOR ', ')    FROM   ((`sakila`.`film` `f`   JOIN `sakila`.`film_category` `fc` ON ((`f`.`film_id` = `fc`.`film_id`)))   JOIN `sakila`.`film_actor` `fa` ON ((`f`.`film_id` = `fa`.`film_id`)))    WHERE   ((`fc`.`category_id` = `c`.`category_id`)  AND (`fa`.`actor_id` = `a`.`actor_id`))))  ORDER BY `c`.`name` ASC  SEPARATOR '; ') AS `film_info`    FROM   (((`sakila`.`actor` `a`   LEFT JOIN `sakila`.`film_actor` `fa` ON ((`a`.`actor_id` = `fa`.`actor_id`)))   LEFT JOIN `sakila`.`film_category` `fc` ON ((`fa`.`film_id` = `fc`.`film_id`)))   LEFT JOIN `sakila`.`category` `c` ON ((`fc`.`category_id` = `c`.`category_id`)))    GROUP BY `a`.`actor_id` , `a`.`first_name` , `a`.`last_name`";
			preparedStatement = connection.prepareStatement(q);
			java.sql.ResultSetMetaData rsmd = preparedStatement.getMetaData(); 
			
			ResultSet rs = preparedStatement.getResultSet();
			System.out.println("Column Count = " + rsmd.getColumnCount());
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				System.out.print(rsmd.getColumnName(i+1));
				System.out.print(", "+ rsmd.getColumnLabel(i+1));
				System.out.print(", "+ rsmd.getColumnType(i+1));
				System.out.print(", "+ rsmd.getColumnTypeName(i+1));
				System.out.println(", " + rsmd.getTableName(i+1));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}

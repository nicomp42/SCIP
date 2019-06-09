/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 */
package edu.UC.PhD.CodeProject.nicholdw.databaseEngine;

/**
 * The Database Engine creator. 
 * There's one copy of this used throughout the application. See the Config class
 * @author nicomp
 *
 */
public class DatabaseEngineFactory {
	public enum DATABASE_ENGINE_TYPE {MySQL};
	public static DatabaseEngine  createDatabaseEngine(DATABASE_ENGINE_TYPE databaseEngineType) throws DatabaseEngineException {
		switch (databaseEngineType) {
		case MySQL:
			return new MySQLDatabaseEngine();
		default:
			throw new DatabaseEngineException("Unsupported Database Engine Type: " + databaseEngineType.toString());
		}
	}
}

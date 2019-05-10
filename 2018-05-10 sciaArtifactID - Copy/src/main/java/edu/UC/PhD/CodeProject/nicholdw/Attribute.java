/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * 11/19/2017 Migrating to GitHub
 * Edited on Laptop not Desktop
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * An attribute in a table. See Attributes class
 * @author nicomp
 *
 */
public class Attribute {

	/**
	 * An attribute that is nullable according to the table definition may not have any null values in the table.
	 * @author nicomp
	 */
	public static final String typeUnknown = "Unknown";
	public static enum enumNullableCheck {notCheckedYet, nullsFoundInTheData, noNullsFoundInTheData, notNullable};
	public static enum enumType {tinyInt, mediumInt, intType, BigInt, decimalType, floatType, doubleType, real, bit, booleanType, serial, dateType, dateTime, timeStamp, time, year, charType, varchar, tinytext, textType, mediumText, longText, binary, varBinary, enumType, setType, timestamp, unknown};

	private boolean autoIncrement;
	protected String attributeName;
	private String type;
	private String nullable;
	private String key;
	private String theDefault;
	private String extra;
	private ArrayList<ForeignKey> foreignKeyRefs;
	private enumNullableCheck nullableCheck;
	private int length;
	private boolean isPrimaryKey;
	protected String tableName;		// This is redundant if the attribute is contained in the Attributes collection contained in a table object.
	private Aliases aliases;		// A table attribute won't have aliases but a query attribute will
	public boolean getAutoIncrement() {return autoIncrement;}
	public void setAutoIncrement(boolean autoIncrement) {this.autoIncrement = autoIncrement;}
	public int getLength() {return length;}
	public void setLength(int length) {this.length = length;}
	public boolean isAutoIncrement() {if (extra.toLowerCase().contains("auto_increment")) return true; else return false;}

	/***
	 * Probably not a good idea to use this -- lots of defaults are assumed.
	 * @param name
	 */
	public Attribute(String name) {
		this.attributeName = name;
		this.nullable = "no";
		this.key = "no";
		this.theDefault = "";
		this.extra = "";
		this.length = 11;		// This number comes from the length that mySQL uses for an attribute that is of type int.
		this.isPrimaryKey = false;
		this.type = typeUnknown;
		foreignKeyRefs = new ArrayList<ForeignKey>();
		nullableCheck = enumNullableCheck.notCheckedYet;
		setAutoIncrement(false);
		aliases = new Aliases();
	}

	/***
	 * Constructor
	 * @param name name of attribute
	 * @param isPrimaryKey True is attribute is the primary key, false otherwise
	 * @param type data type
	 * @param nullable is nullable
	 * @param key key
	 * @param theDefault default
	 * @param extra extra
	 */
	public Attribute(String name, String tableName, Boolean isPrimaryKey, String type, String nullable, String key, String theDefault, String extra, int length, Aliases aliases ) {
		this.attributeName = name;
		this.type = type;
		this.nullable = nullable;
		this.key = key;
		this.theDefault = theDefault;
		this.extra = extra;
		this.length = length;
		foreignKeyRefs = new ArrayList<ForeignKey>();
		nullableCheck = enumNullableCheck.notCheckedYet;
		setAutoIncrement(false);
		this.isPrimaryKey = isPrimaryKey;
		this.tableName = tableName;
		this.aliases = Aliases.clone(aliases);
	}
	public boolean isTypeUnknown() {return type.equals(typeUnknown) ? true:false;}
	public String getAttributeName() {return attributeName;}
	public String getType() {return type;}

	public ArrayList<ForeignKey> getForeignKeyRefs() {return foreignKeyRefs;}

	public boolean isNullable() {return nullable.toLowerCase().equals("yes")? true: false;}

	/**
	 * Add a foreign key reference to the attribute
	 * @param referencedTableName
	 * @param foreignKeyName
	 * @param referencedAttributeName
	 */
	public void addForeignKeyRef(String referencedTableName, String foreignKeyName, String referencedAttributeName) {
		foreignKeyRefs.add(new ForeignKey(null, null, referencedTableName, foreignKeyName, referencedAttributeName));
	}
	/**
	 * Get the foreign key references
	 * @return A reference to the list of foreign key references. Not a clone. Be careful.
	 */
	public ArrayList<ForeignKey> GetForeignKeyRefs() {return foreignKeyRefs;}

	/**
	 * Is the attribute a foreign key?
	 * @return True if the attribute is a foreign key, false otherwise
	 */
	public boolean isForeignKey() {return foreignKeyRefs.size() > 0 ? true: false;}

	/**
	 * Is the attribute nullable?
	 * @return True if nullable, false otherwise
	 */
	public enumNullableCheck getNullableCheck() {return nullableCheck;}
	
	/**
	 * Define if the attribute is nullable
	 * @param nullableCheck True is the attribute is nullable, false otherwise
	 * @return Whatever you passed in.
	 */
	public enumNullableCheck setNullableCheck(enumNullableCheck nullableCheck) {this.nullableCheck = nullableCheck; return nullableCheck;}

	/**
	 * Map the type of attribute as a string to the enumerated data type
	 * @param type The attribute as a string
	 * @return The corresponding value of the enumerated data type
	 * @throws Exception if type cannot be mapped.
	 */
	public static Attribute.enumType mapType(String type) throws Exception {
		Attribute.enumType myEnumType = Attribute.enumType.intType;
		boolean notFound = false;
		switch(type.toLowerCase().trim()) {
			case "int":
				myEnumType = Attribute.enumType.intType;
				break;

			case "varchar":
				myEnumType = Attribute.enumType.varchar;
				break;

			case "date":
				myEnumType = Attribute.enumType.dateType;
				break;

			default:
				notFound = true;
				break;
		}
		if (notFound) {
			if (type.toLowerCase().startsWith("varchar")) {
				myEnumType = Attribute.enumType.varchar;
			} else if (type.toLowerCase().startsWith("int(")) {
				myEnumType = Attribute.enumType.intType;
			} else {
				Log.logError("Attribute.mapType(" + type.trim() + "): unknown type");
				throw new Exception("Attribute.mapType(" + type.trim() + "): unknown type");
			}
		}
		return myEnumType;
	}
	public boolean isPrimaryKey() {return isPrimaryKey;}
	public void setPrimaryKey(boolean isPrimaryKey) {this.isPrimaryKey = isPrimaryKey;}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
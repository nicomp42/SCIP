/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * 11/19/2017 Migrating to GitHub
 * Edited on Laptop not Desktop
 */
package edu.UC.PhD.CodeProject.nicholdw;

import java.util.ArrayList;

import edu.UC.PhD.CodeProject.nicholdw.GraphNodeAnnotation.GRAPH_NODE_ANNOTATION;
import edu.UC.PhD.CodeProject.nicholdw.log.Log;

/***
 * An attribute in a table. See Attributes class
 * @author nicomp
 *
 */
public class TableAttribute extends ImpactGraphNode implements Attributable, java.io.Serializable  {

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
	private String schemaName;
	private ArrayList<ForeignKey> foreignKeyRefs;
	private enumNullableCheck nullableCheck;
	private int length;
	private boolean isPrimaryKey;
	private String attributeKey;
	protected String tableName;		// This is redundant if the attribute is contained in the Attributes collection contained in a table object.
	private Aliases aliases;		// A table attribute won't have aliases but a query attribute will
	private Boolean affectedByActionQuery;		// Defaults to false until an action query is applied to the query where this attribute lives
	private GraphNodeAnnotation graphNodeAnnotation;
	public boolean getAutoIncrement() {return autoIncrement;}
	public void setAutoIncrement(boolean autoIncrement) {this.autoIncrement = autoIncrement;}
	public int getLength() {return length;}
	public void setLength(int length) {this.length = length;}
	public boolean isAutoIncrement() {if (extra.toLowerCase().contains("auto_increment")) return true; else return false;}
	private boolean indirectlyAffectedByActionQuery;
	public Boolean getIndirectlyAffectedByActionQuery() {return indirectlyAffectedByActionQuery;}
	public void setIndirectlyAffectedByActionQuery(Boolean indirectlyAffectedByActionQuery) {
		this.indirectlyAffectedByActionQuery = indirectlyAffectedByActionQuery;
	}

	/***
	 * We use this when we only need the attribute name -- lots of defaults are assumed.
	 * @param attributeName Attribute Name
	 */
	public TableAttribute(String attributeName) {
		this.shotgunTheDefaults();
		this.attributeName = attributeName;
	}
	/***
	 * We use this when we only need the attribute name -- lots of defaults are assumed.
	 * @param tableName Table Name
	 * @param attributeName Attribute Name
	 */
	public TableAttribute(String tableName, String attributeName) {
		this.shotgunTheDefaults();
		this.attributeName = attributeName;
		this.tableName = tableName;
	}
	/***
	 * We use this when we only need the attribute name -- lots of defaults are assumed.
	 * @param tableName Table Name
	 * @param attributeName Attribute Name
	 */
	public TableAttribute(String schemaName, String tableName, String attributeName) {
		this.shotgunTheDefaults();
		this.attributeName = attributeName;
		this.tableName = tableName;
		this.schemaName = schemaName;
	}
	/***
	 * If we are  creating an attribute with limited information, use this to default everything first.
	 */
	private void shotgunTheDefaults() {
		this.nullable = "no";
		this.setKey("");
		this.theDefault = "";
		this.extra = "";
		this.length = 11;		// This number comes from the length that mySQL uses for an attribute that is of type int.
		this.isPrimaryKey = false;
		this.type = typeUnknown;
		foreignKeyRefs = new ArrayList<ForeignKey>();
		nullableCheck = enumNullableCheck.notCheckedYet;
		setAutoIncrement(false);
		aliases = new Aliases();
		setAffectedByActionQuery(false);
		setDefaultGraphNodeAnnotation();
		setSchemaName("");
	}
	/***
	 * Constructor
	 * @param name name of attribute
	 * @param isPrimaryKey True is attribute is the primary key, false otherwise
	 * @param type data type
	 * @param nullable is nullable
	 * @param attributeKey attribute key participation, if any
	 * @param theDefault default
	 * @param extra extra
	 */
	public TableAttribute(String name, String tableName, Boolean isPrimaryKey, 
			             String type, String nullable, String attributeKey, String theDefault, 
			             String extra, int length, Aliases aliases, String schemaName ) {
		this.attributeName = name;
		this.type = type;
		this.nullable = nullable;
		this.setAttributeKey(attributeKey);
		this.theDefault = theDefault;
		this.extra = extra;
		this.length = length;
		foreignKeyRefs = new ArrayList<ForeignKey>();
		nullableCheck = enumNullableCheck.notCheckedYet;
		setAutoIncrement(false);
		this.isPrimaryKey = isPrimaryKey;
		this.tableName = tableName;
		this.aliases = Aliases.clone(aliases);
		setAffectedByActionQuery(false);
		setDefaultGraphNodeAnnotation();
		setSchemaName(schemaName);
		setKey("");
	}
	private void setDefaultGraphNodeAnnotation() {
		GraphNodeAnnotation graphNodeAnnotation = new GraphNodeAnnotation();
		graphNodeAnnotation.setGraphNodeAnnotation(GRAPH_NODE_ANNOTATION.None);
		this.setGraphNodeAnnotation(graphNodeAnnotation);		
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
	public static TableAttribute.enumType mapType(String type) throws Exception {
		TableAttribute.enumType myEnumType = TableAttribute.enumType.intType;
		boolean notFound = false;
		switch(type.toLowerCase().trim()) {
			case "int":
				myEnumType = TableAttribute.enumType.intType;
				break;

			case "varchar":
				myEnumType = TableAttribute.enumType.varchar;
				break;

			case "date":
				myEnumType = TableAttribute.enumType.dateType;
				break;

			default:
				notFound = true;
				break;
		}
		if (notFound) {
			if (type.toLowerCase().startsWith("varchar")) {
				myEnumType = TableAttribute.enumType.varchar;
			} else if (type.toLowerCase().startsWith("int(")) {
				myEnumType = TableAttribute.enumType.intType;
			} else {
				Log.logError("Attribute.mapType(" + type.trim() + "): unknown type");
				throw new Exception("Attribute.mapType(" + type.trim() + "): unknown type");
			}
		}
		return myEnumType;
	}
	public boolean isPrimaryKey() {return isPrimaryKey;}
	public void setPrimaryKey(boolean isPrimaryKey) {this.isPrimaryKey = isPrimaryKey;}
	public String getContainerName() {
		return tableName;
	}
	public void setContainerName(String tableName) {
		this.tableName = tableName;
	}
	@Override
	public Boolean getAffectedByActionQuery() {
		return affectedByActionQuery;
	}
	@Override
	public void setAffectedByActionQuery(Boolean affectedByActionQuery) {
		this.affectedByActionQuery = affectedByActionQuery;
	}
	public String toString() {
		return ((schemaName.trim().length()>0)?schemaName + ".":"") + tableName + "." + attributeName;
	}
	public void setGraphNodeAnnotation(GraphNodeAnnotation graphNodeAnnotation) {
		this.graphNodeAnnotation = new GraphNodeAnnotation(graphNodeAnnotation);
	}
	/***
	 * Get a copy of the GraphNodeAnnotation for the current object
	 * @return A copy of the GraphNodeAnnotation for the current object
	 */
	public GraphNodeAnnotation getGraphNodeAnnotation() {return new GraphNodeAnnotation(graphNodeAnnotation);}
	public String getKey() {
		if (key == null || key.trim().length() == 0) {
			if ((getSchemaName() == null || getSchemaName().trim().length() == 0) ||
				(getContainerName() == null || getContainerName().trim().length() == 0) || 
				(getAttributeName() == null || getAttributeName().trim().length() == 0)) {
				Log.logError("TableAttribute.getKey(): Cannot build key due to missing data");
				setKey("KEY MISSING");
			} else {
				setKey(Utils.cleanForGraph(getSchemaName())
		                + "." 
		                + Utils.cleanForGraph(getContainerName())
		                + "." 
		                + Utils.cleanForGraph(getAttributeName()));
			}
		}
		return key;
	}
	public void setKey(String key) {
		this.key = key;
		if (key.trim().equals("MUL")) {
			System.out.println("Poof");
		}
	}
	@Override
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	@Override
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
		
	}
	public String getAttributeKey() {
		return attributeKey;
	}
	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}


}

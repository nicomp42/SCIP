package edu.UC.PhD.CodeProject.nicholdw;
import java.util.ArrayList;
import java.util.Iterator;

	/***
	 * List of ForeignKey objects
	 * @author nicomp
	 *
	 */
	public class ForeignKeys implements Iterable<ForeignKey> {

		private ArrayList<ForeignKey> foreignKeys;

		/**
		 * Constructor
		 */
		public ForeignKeys() {
			foreignKeys = new ArrayList<ForeignKey>();
		}

		/**
		 * Retrieve the list of foreignKeys
		 * @return A reference to the foreignKey list in the current object.
		 */
		public ArrayList<ForeignKey> getForeignKeys ()
		{
			return foreignKeys;
		}

		public void addForeignKey(ForeignKey foreignKey) {
			foreignKeys.add(foreignKey);
		}

		@Override
		public Iterator<ForeignKey> iterator() {
			Iterator<ForeignKey> iprof = foreignKeys.iterator();
	        return iprof;
	    }
	}

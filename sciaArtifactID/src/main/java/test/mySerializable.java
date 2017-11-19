package test;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;


/**
 * This is an attempt to get static properties to serialize. It doesn't work.
 * At run-time if throws the error "save(): test.mySerializable; unmatched serializable field(s) declared" on the writeObject statement
 * @author nicomp
 */
public class mySerializable implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4868993751022567758L;
	private static Integer f = 42;
	private static Integer g = 43;

    private static final ObjectStreamField[] serialPersistentFields = {
    		new ObjectStreamField("serialVersionUID", Long.class),
    		new ObjectStreamField("f", Integer.class),
    		new ObjectStreamField("g", Integer.class),
    };

	public static void main(String[] args) {
		save();
	}
	public static void save() {
		try {
			 FileOutputStream fileOut = new FileOutputStream("config" + ".ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(new mySerializable());
	         out.close();
	         fileOut.close();
		} catch (Exception ex) {
			System.out.println("save(): " + ex.getLocalizedMessage());		}
	}
	public static int getF() {
		return f;
	}
	public static void setF(int f) {
		mySerializable.f = f;
	}
	public static int getG() {
		return g;
	}
	public static void setG(int g) {
		mySerializable.g = g;
	}
}

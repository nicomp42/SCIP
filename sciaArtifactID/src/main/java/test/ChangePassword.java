package test;

//Add a node to an existing DB. You may have to change the DB credentials in the HelloWorldExample constructor.

//CALL dbms.security.changePassword("yournewpassword");

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;
import static org.neo4j.driver.v1.Values.parameters;

public class ChangePassword implements AutoCloseable
{
 private final Driver driver;

 public static void main( String... args ) throws Exception {
     try ( HelloWorldExample greeter = new HelloWorldExample( "bolt://localhost:7687", "neo4j", "Danger42" ) ) {
         greeter.printGreeting( "hello, world" );
     } catch (Exception ex) {
     	System.out.println(ex.getLocalizedMessage());
     }
 }
 public ChangePassword( String uri, String user, String password ) {driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );}

 @Override
 public void close() throws Exception   {driver.close();}

 public void printGreeting( final String message ) {
     try ( Session session = driver.session() ) {
         String greeting = session.writeTransaction( new TransactionWork<String>()
         {
             @Override
             public String execute( Transaction tx ) {
                 StatementResult result = tx.run( "CALL dbms.security.changePassword(\"yournewpassword\");" );
                 return result.single().get( 0 ).asString();
             }
         } );
         System.out.println( greeting );
     }
 }
}
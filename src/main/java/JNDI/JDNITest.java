package JNDI;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JDNITest {
    public static void main(String[] args) {
        String addr = "rmi://localhost:1099/Hello";
        InitialContext ic;
        try {
            // Initialize the InitialContext
            ic = new InitialContext();
            System.out.println("InitialContext created.");

            // Attempt to look up the object
            Object obj = ic.lookup(addr);
            System.out.println("Object looked up: " + obj);

            // Check if the object is of the expected type
            if (obj instanceof RMIHello) {
                RMIHello rmiHello = (RMIHello) obj;
                rmiHello.sayHello("World");
            } else {
                System.out.println("Looked up object is not an instance of RMIHello.");
            }
        } catch (NamingException e) {
            System.out.println("NamingException encountered:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception encountered:");
            e.printStackTrace();
        }
    }
}

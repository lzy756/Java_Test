package JNDI;


import com.sun.jndi.rmi.registry.ReferenceWrapper;

import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;

public class RMI_Server_Reference {
    void register() throws Exception
    {
        LocateRegistry.createRegistry(1099);
        Reference ref=new Reference("RMIHello","RMIHello","http://localhost:8080/");
        ReferenceWrapper refWrapper=new ReferenceWrapper(ref);
        java.rmi.Naming.rebind("rmi://localhost:1099/Hello",refWrapper);
        System.out.println("RMI server started...");
    }
    public static void main(String[] args) throws Exception {
        try {
            new RMI_Server_Reference().register();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

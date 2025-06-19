package JNDI;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMI_Server {
    public class RMIHello extends UnicastRemoteObject implements IHello {
        public RMIHello() throws RemoteException {
            super();
        }
        @Override
        public String sayHello(String name) throws RemoteException {
            System.out.println("Hello " + name);
            return "Hello " + name;
        }
    }

    public void register() throws Exception {
        RMIHello hello = new RMIHello();
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        java.rmi.Naming.rebind("rmi://localhost:1099/Hello", hello);
        System.out.println("RMI server started...");
    }
    public static void main(String[] args) throws Exception {
        try {
            new RMI_Server().register();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

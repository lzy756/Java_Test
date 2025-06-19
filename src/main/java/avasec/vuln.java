package avasec;

public class vuln {
    private static void test_payload0()throws Exception
    {
        Class<?> classz=Class.forName("java.lang.ProcessBuilder");
        classz.getMethod("start").invoke(classz.getConstructor(String[].class).newInstance(new String[][]{{"calc"}}));
    }
}

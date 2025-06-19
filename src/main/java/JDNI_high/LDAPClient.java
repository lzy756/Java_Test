package JDNI_high;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class LDAPClient {
    public static void main(String[] args) {
        try {
            System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");
            // 配置JNDI LDAP连接环境
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

            // 连接到LDAP服务器
            String ldapUrl = "ldap://127.0.0.1:1389/dc=example,dc=com";
            env.put(Context.PROVIDER_URL, ldapUrl);

            System.out.println("连接到LDAP服务器: " + ldapUrl);

            // 创建初始上下文
            InitialContext ctx = new InitialContext(env);

            // 尝试查找一个对象，这会触发LDAP服务器返回恶意的序列化对象
            System.out.println("尝试查询LDAP对象...");
            Object obj = ctx.lookup("cn=vulnerable");

            System.out.println("返回的对象: " + obj);

        } catch (NamingException e) {
            System.out.println("JNDI LDAP连接异常：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("发生其他异常：");
            e.printStackTrace();
        }
    }
}

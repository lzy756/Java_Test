package Student;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class iotest {
    public static void main(String[] args) throws Exception {
        byte[] test = new byte[]{
                0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10
        };
        FileOutputStream fileOut = new FileOutputStream("test.txt");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(test);
        byteArrayOutputStream.writeTo(fileOut);
        fileOut.close();
    }
}

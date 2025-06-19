import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Exp implements java.io.Serializable
{
    private static final long serialVersionUID = 100L;
    public static int num = 0;
    private void readObject(ObjectInputStream input) throws Exception {
        input.defaultReadObject();
    }
    public static void main(String[] args) throws IOException {
        Exp t = new Exp();
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.ser"));
        out.writeObject(t);
        out.writeObject(t); //第二次写入
        out.close();
    }
}

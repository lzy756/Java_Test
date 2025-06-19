//导入所需的类
import org.apache.commons.codec.binary.Base64;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
// 创建User类并实现Serializable接口

public class User implements Serializable {
    protected String name;
    protected User parent;
    public User(String name)
    {
        this.name = name;
    }
    public void setParent(User parent)
    {
        this.parent = parent;
    }
    //main func
    public static void main(String[] args)throws Exception
    {
        User user = new User("Bob");
        user.setParent(new User("Josua"));
        ByteArrayOutputStream byteSteam = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteSteam);
        oos.writeObject(user);
        System.out.println(Base64.encodeBase64String(byteSteam.toByteArray()));
    }
}
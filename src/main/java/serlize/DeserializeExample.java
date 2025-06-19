package serlize;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class DeserializeExample {

    public static void main(String[] args) {
        // 检查命令行参数，决定使用文件输入或直接通过Base64字符串反序列化
        if (args.length == 2 && args[0].equals("file")) {
            deserializeFromFile(args[1]);
        } else if (args.length == 2 && args[0].equals("base64")) {
            deserializeFromBase64(args[1]);
        } else {
            System.out.println("用法：");
            System.out.println("  使用文件: java DeserializeExample file <filename>");
            System.out.println("  使用Base64: java DeserializeExample base64 <base64_string>");
        }
    }

    // 从文件读取并反序列化对象
    private static void deserializeFromFile(String filename) {
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            // 读取对象
            Object obj = in.readObject();
            System.out.println("反序列化成功，读取到对象：" + obj);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 从Base64字符串反序列化对象
    private static void deserializeFromBase64(String base64) {
        try {
            // 将Base64字符串解码为字节数组
            byte[] data = Base64.getDecoder().decode(base64);
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
                 ObjectInputStream in = new ObjectInputStream(byteIn)) {
                // 反序列化对象
                Object obj = in.readObject();
                System.out.println("反序列化成功，读取到对象：" + obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
